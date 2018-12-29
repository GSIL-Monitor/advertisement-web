package com.yuanshanbao.dsp.controller.product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.yuanshanbao.common.constant.SessionConstants;
import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.CommonUtil;
import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.MD5Util;
import com.yuanshanbao.dsp.activity.model.Activity;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.common.constant.RedisConstant;
import com.yuanshanbao.dsp.common.redis.base.BaseRedis;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.IniBean;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.product.model.Product;
import com.yuanshanbao.dsp.product.model.ProductCategory;
import com.yuanshanbao.dsp.product.model.ProductStatus;
import com.yuanshanbao.dsp.product.model.vo.ProductVo;
import com.yuanshanbao.dsp.product.service.ProductService;
import com.yuanshanbao.dsp.tags.model.Tags;
import com.yuanshanbao.dsp.tags.model.TagsSearchType;
import com.yuanshanbao.dsp.tags.model.vo.TagsVo;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.paginator.domain.Order;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;
import com.yuanshanbao.paginator.domain.Paginator;

@Controller
@RequestMapping({ "/product", "/i/product" })
public class ProductController extends BaseController {
	private static final String WANGZHUAN = "wangzhuan";

	private static final String WZXCX_PRODUCT_CHANNEL = "wzxcxProductChannel";

	@Autowired
	private ProductService productService;

	@Autowired
	private BaseRedis baseRedis;

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest request, Product product, PageBounds pageBounds, Long categoryId,
			String token, HttpServletResponse response, ModelMap modelMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Activity activity = ConfigManager.getActivityByKey(WANGZHUAN);
			if (activity == null) {
				throw new BusinessException();
			}
			product.setActivityId(activity.getActivityId());
			product.setStatus(ProductStatus.ONLINE);
			// isApprovalEdition(request, product);
			PageList<Product> productList = null;
			productList = setProductProperty(product, categoryId, token, pageBounds);
			PageList<ProductVo> voList = convertVo(request, productList, token, formatPageBounds(pageBounds));
			voList.setPaginator(productList.getPaginator());
			resultMap.put("productList", voList);
			resultMap.put(ComRetCode.PAGINTOR, productList.getPaginator());
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[productList error:]", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	protected PageList<Product> setProductProperty(Product product, Long categoryId, String token, PageBounds pageBounds) {
		PageList<Product> productList = null;
		User user = userService.selectUserByToken(token);
		if (categoryId != null) {
			ProductCategory category = ConfigManager.getProductCategory(categoryId);
			if (category == null) {
				throw new BusinessException();
			}
			if (category.getType() != null && category.getType().equals(TagsSearchType.CREDIT)) {
				product.setAuthorizeTags(category.getValue());
			} else if (category.getType().equals(TagsSearchType.FEATURE)) {
				product.setFeatureTags(category.getValue());
			}
		}
		if (product.getSortCondition() != null) {
			String orderString = ConstantsManager.getTags(product.getSortCondition().longValue()).getValue();
			if (StringUtils.isNotBlank(orderString)) {
				pageBounds.setOrders(Order.formString(orderString));
			} else {
				return getProductByApplyCountDesc(product, formatPageBounds(pageBounds));
			}
		} else {
			pageBounds.setOrders(Order.formString("sort.asc"));
		}

		List<Product> list = productService.selectProducts(product, formatPageBounds(pageBounds));
		if (product.getQueryAge() != null) {
			Iterator<Product> it = list.iterator();
			while (it.hasNext()) {
				it.next();
				if (list.size() > 1) {
					it.remove();
				}
			}
			if (user != null) {
				it = list.iterator();
				while (it.hasNext()) {
					Product pro = it.next();
					if (applyService.checkApplyExist(user, pro.getProductId())) {
						it.remove();
					}
				}
			}
			it = list.iterator();
			while (it.hasNext()) {
				Product pro = it.next();
				if (StringUtils.isEmpty(pro.getBigImageUrl())) {
					it.remove();
				}
			}
		}
		productList = (PageList<Product>) list;
		return productList;
	}

	private PageList<Product> getProductByApplyCountDesc(Product product, PageBounds pageBounds) {
		Jedis jedis = baseRedis.getJedisCluster();
		PageList<Product> resultList = new PageList<Product>(new Paginator(pageBounds.getPage(), pageBounds.getLimit(),
				0));
		try {
			List<Product> productList = productService.selectProducts(product, new PageBounds());
			Pipeline pipeline = jedis.pipelined();
			List<Response<String>> list = new ArrayList<Response<String>>();
			for (Product p : productList) {
				String key = RedisConstant.getProductShowCountKey(p.getProductId() + "");
				Response<String> response = pipeline.get(CommonUtil.getCacheKey(key));
				list.add(response);
			}
			pipeline.sync();
			for (int i = 0; i < list.size(); i++) {
				Product p = productList.get(i);
				p.setApplyCount(Long.parseLong(list.get(i).get()));
				resultList.add(productList.get(i));
			}
			Collections.sort(resultList, new Comparator<Product>() {
				@Override
				public int compare(Product b, Product a) {
					return a.getApplyCount().compareTo(b.getApplyCount());
				}
			});
			return getPageList(resultList, pageBounds);
		} catch (JedisConnectionException jce) {
			LoggerUtil.error("getProductByApplyCountDesc error.", jce);
			jedis = null;
			throw new BusinessException(jce);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	private PageList<Product> getPageList(PageList<Product> resultList, PageBounds pageBounds) {
		PageList<Product> pageList = new PageList<Product>();
		formatPageBounds(pageBounds);
		int size = resultList.size();
		if (size > pageBounds.getLimit()) {
			int start = pageBounds.getLimit() * (pageBounds.getPage() - 1);
			int end = start + pageBounds.getLimit();
			pageList.setPaginator(new Paginator(start, pageBounds.getLimit(), size));
			for (int i = start; i < (end > size ? size : end); i++) {
				pageList.add(resultList.get(i));
			}
		} else {
			for (Product product : resultList) {
				pageList.add(product);
			}
		}
		return pageList;
	}

	@ResponseBody
	@RequestMapping("/detail")
	public Object detail(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			@RequestParam("productId") Long productId, String token, String version) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (StringUtils.isNoneBlank(version) && version.equals(IniBean.getIniValue("wzxcxProductChannel"))) {
			resultMap.put("version", true);
		} else {
			resultMap.put("version", false);
		}
		// isApprovalEdition(request, null);
		try {
			if (productId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			Product product = productService.selectProduct(productId);
			if (product == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			String brandFeature = product.getBrandFeature();
			String[] schoolTime = product.getSchoolTimeValue();

			// Map<String, String> brandFeatureMap =
			// productService.getBrandFeatureMap(brandFeature);
			List<Tags> featureList = productService.getBrandFeatureMap(brandFeature);
			resultMap.put("brandFeatureList", featureList);
			product.setApplyCount(applyService.getProductApplyCount(product.getProductId()));
			ProductVo vo = new ProductVo(product);
			List<TagsVo> recommendTagsList = vo.getRecommendTagsList();
			checkApplyStatus(token, vo);
			// if (isApprovalEdition(request, product)) {
			// vo.setApplyInterface(null);
			// }
			if (vo.getActivityId() != null) {
				if (vo.getActivityId() == 117 || vo.getActivityId() == 118) {
					resultMap.put("activity", schoolTime);
				} else {
					resultMap.put("activity", null);

				}
			}
			resultMap.put("product", vo);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[detail error: " + productId + "]", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	@ResponseBody
	@RequestMapping("/detail/addClickCount")
	public Object addUv(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Long productId,
			String appId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (productId == null || StringUtils.isBlank(appId)) {
				// 兼容AppId为空的情况
				throw new BusinessException(ComRetCode.SUCCESS);
			}
			Product product = productService.selectProduct(productId);
			if (product == null) {
				// 兼容AppId为空的情况
				throw new BusinessException(ComRetCode.SUCCESS);
			}

			String date = DateUtils.format(new Date());
			String session = SessionConstants.SESSION_PRODUCT_DETAIL_CLICK + "_" + date + "_" + productId + "_"
					+ MD5Util.get(appId);
			String click = (String) request.getSession().getAttribute(session);
			if (StringUtils.isNotBlank(click)) {
				InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
				return resultMap;
			}

			// redisCacheService.incr(RedisConstant.getProductDetailUvCountKey(productId));
			// LoggerUtil.info("[product_detail_uvCount] productId={}, uvCount={}",
			// productId,
			// redisCacheService.get(RedisConstant.getProductDetailUvCountKey(productId)));
			request.getSession().setAttribute(session, date + "_" + productId + "_" + MD5Util.get(appId));
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[detail addUv error:]", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		}
		return resultMap;
	}

	@ResponseBody
	@RequestMapping("/getPromotionAd")
	public Object getPromotionAd(HttpServletRequest request, HttpServletResponse response, Long productId,
			String activityKey, Integer client) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			String channel = appService.getAppChannel(request.getParameter("appId"));
			// 判断是否是审核版本
			// if (isApprovalEdition(request, null)) {
			// channel = channel + "_approval";
			// }
			request.getSession().setAttribute(activityKey + SessionConstants.SESSION_USER_FROM, channel);
			Activity activity = ConfigManager.getActivityByKey(activityKey);
			Long activityId = null;
			if (activity != null) {
				activityId = activity.getActivityId();
			}
			String appKey = getAppKey(request);
			// List<AdvertisementVo> promotionAdvertisments =
			// setAdvertisementLink(
			// AdvertisementPosition.ADVERTISEMENT_WELFARE,
			// AdvertisementPosition.PROMOTION, channel, appKey,
			// activityId, client);
			// if (promotionAdvertisments.size() > 1) {
			// resultMap.put("promotionAd",
			// promotionAdvertisments.get((int) (Math.random() *
			// promotionAdvertisments.size())));
			// } else if (promotionAdvertisments.size() > 0) {
			// resultMap.put("promotionAd", promotionAdvertisments.get(0));
			// }
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setSpecAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[product getPromotionAd]: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping("/search/initial") public Object
	 * searchList(HttpServletRequest request, HttpServletResponse response,
	 * ModelMap modelMap) { Map<String, Object> resultMap = new HashMap<String,
	 * Object>(); try {
	 * 
	 * List<Tags> sortType =
	 * ConstantsManager.getTagsListByTypeName(TagsTypeConstant.SEARCH_TYPE);
	 * 
	 * resultMap.put("sortTypeList", sortType);
	 * InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS); } catch
	 * (BusinessException e) { InterfaceRetCode.setAppCodeDesc(resultMap,
	 * e.getReturnCode(), e.getMessage()); } catch (Exception e) {
	 * LoggerUtil.error("[search list error:]", e);
	 * InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS); } return
	 * resultMap; }
	 * 
	 * @ResponseBody
	 * 
	 * @RequestMapping("/search/query") public Object query(HttpServletRequest
	 * request, HttpServletResponse response, ModelMap modelMap, String type,
	 * String amount, String tagsId, PageBounds pageBounds) { Map<String,
	 * Object> resultMap = new HashMap<String, Object>(); try {
	 * 
	 * if (StringUtils.isBlank(type)) { type = "zonghe"; } if (pageBounds ==
	 * null) { pageBounds = new PageBounds(1, 10); } List<Product> productList =
	 * ConfigManager.getProducts(); PageList<ProductVo> voList = new
	 * PageList<ProductVo>(); int start = voList.size() / pageBounds.getPage();
	 * if (type.equals("zonghe")) {
	 * 
	 * }
	 * 
	 * resultMap.put("queryList", voList);
	 * InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS); } catch
	 * (BusinessException e) { InterfaceRetCode.setAppCodeDesc(resultMap,
	 * e.getReturnCode(), e.getMessage()); } catch (Exception e) {
	 * LoggerUtil.error("[search list error:]", e);
	 * InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS); } return
	 * resultMap; }
	 */
	private void checkApplyStatus(String token, ProductVo vo) {
		User user = null;
		if (StringUtils.isNotEmpty(token)) {
			user = userService.selectUserByToken(token);
		}
		if (user != null) {
			applyService.checkExist(user, vo);
		}
	}
}
