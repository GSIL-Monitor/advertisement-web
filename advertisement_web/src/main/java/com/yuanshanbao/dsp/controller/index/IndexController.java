package com.yuanshanbao.dsp.controller.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.constant.SessionConstants;
import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.CookieUtils;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.activity.model.Activity;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementPosition;
import com.yuanshanbao.dsp.app.service.AppService;
import com.yuanshanbao.dsp.config.ConfigConstants;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.IniBean;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.core.http.HttpServletRequestWrapper;
import com.yuanshanbao.dsp.message.model.Message;
import com.yuanshanbao.dsp.message.service.MessageService;
import com.yuanshanbao.dsp.product.model.Product;
import com.yuanshanbao.dsp.product.model.ProductCategory;
import com.yuanshanbao.dsp.product.model.ProductStatus;
import com.yuanshanbao.dsp.product.service.ProductService;
import com.yuanshanbao.dsp.tags.model.Tags;
import com.yuanshanbao.dsp.user.model.LoginToken;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.dsp.user.model.UserLevel;
import com.yuanshanbao.dsp.user.service.TokenService;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@RequestMapping({ "/index", "/i/index" })
@Controller
public class IndexController extends BaseController {

	private static final String WANGZHUAN = "wangzhuan";

	private static final String BAOXIAN = "baoxian";

	private static final String JIAOYU = "jiaoyu";

	private static final String WANGZHUANAGENT = "wangzhuanagent";

	private static final String WZXCX_PRODUCT_CHANNEL = "wzxcxProductChannel";

	private static final String MYZT = "蚂蚁智投";
	private static final String TJKT = "坤涛科技";

	@Autowired
	private ProductService productService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private AppService appService;

	@Autowired
	private TokenService tokenService;

	// 客户端首页
	@RequestMapping("/home")
	@ResponseBody
	public Object index(HttpServletRequest request, HttpServletResponse response, String activityKey, Product product,
			PageBounds pageBounds, String token, Integer client, String version) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			Activity activity = null;
			// 区分小程序登录 h5登录
			if (StringUtils.isBlank(token)) {
				User sUser = (User) request.getSession().getAttribute(SessionConstants.SESSION_USER);
				String H5Token = (String) request.getSession().getAttribute(SessionConstants.SESSION_TOKEN);
				LoggerUtil.info("[home SESSION_USER--]: " + sUser);
				LoggerUtil.info("[home SESSION_TOKEN--]: " + H5Token);
				if (StringUtils.isBlank(H5Token)) {
					activity = ConfigManager.getActivityByKey(WANGZHUANAGENT);
					getHomeInfos(resultMap, activity, product, pageBounds, request, client);
					resultMap.put("H5Token", H5Token);
				} else {

					User h5User = getLoginUser(H5Token);
					// = userService.selectUserByToken(H5Token);
					if (h5User == null) {
						throw new BusinessException(ComRetCode.WEIXIN_LOGIN_FAIL);
					} else {
						if (h5User.getLevel() == null) {
							h5User.setLevel(UserLevel.MANAGER);
						}
						if (h5User.getLevel() == UserLevel.VIP_AGENT) {
							activity = ConfigManager.getActivityByKey(WANGZHUANAGENT);
							getHomeInfos(resultMap, activity, product, pageBounds, request, client);
						} else {
							activity = ConfigManager.getActivityByKey(WANGZHUAN);
							getHomeInfos(resultMap, activity, product, pageBounds, request, client);
						}
					}
				}
			} else {
				if (StringUtils.isNoneBlank(version) && version.equals(IniBean.getIniValue("wzxcxProductChannel"))) {
					resultMap.put("version", true);
				} else {
					resultMap.put("version", false);
				}

				User user = userService.selectUserByToken(token);
				LoggerUtil.info("[home xcxUserToken--]: " + token);
				if (user != null) {
					if (user.getLevel() == null) {
						user.setLevel(UserLevel.MANAGER);
					}
					if (user.getLevel() == UserLevel.VIP_AGENT) {
						activity = ConfigManager.getActivityByKey(WANGZHUANAGENT);

						getHomeInfos(resultMap, activity, product, pageBounds, request, client);
					} else {
						activity = ConfigManager.getActivityByKey(WANGZHUAN);
						getHomeInfos(resultMap, activity, product, pageBounds, request, client);
					}
				} else {
					activity = ConfigManager.getActivityByKey(WANGZHUAN);
					getHomeInfos(resultMap, activity, product, pageBounds, request, client);
				}
			}

			String sid = CookieUtils.getCookieValue(request, SessionConstants.COOKIE_SID);
			LoggerUtil.info("[home sid--]: " + sid);
			HttpServletRequestWrapper httpServletRequestWrapper = new HttpServletRequestWrapper(sid, request);
			LoginToken loginToken = (LoginToken) httpServletRequestWrapper.getAttribute("loginToken");
			User sessionUser = (User) httpServletRequestWrapper.getAttribute(SessionConstants.SESSION_ACCOUNT);
			// User sessionUser = (User)
			// request.getSession().getAttribute(SessionConstants.SESSION_ACCOUNT);
			// LoginToken loginToken = (LoginToken)
			// request.getSession().getAttribute("loginToken");
			LoggerUtil.info("[home sessionUser--]: " + sessionUser);

			LoggerUtil.info("[home loginToken--]: " + loginToken);

			/*
			 * if (token == null || token == "") { activity =
			 * ConfigManager.getActivityByKey(WANGZHUANAGENT);
			 * getHomeInfos(resultMap, activity, product, pageBounds, request,
			 * client); } else {
			 * 
			 * 
			 * }
			 */

			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setSpecAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[home index]: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	@RequestMapping("/get/sid")
	@ResponseBody
	public Object getSidValue(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String token = (String) request.getSession().getAttribute(SessionConstants.SESSION_TOKEN);
			if (StringUtils.isBlank(token)) {
				resultMap.put("sid", false);
			} else {
				resultMap.put("sid", true);
			}
		} catch (Exception e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			LoggerUtil.error("[Result: error]", e);
		}

		return resultMap;
	}

	@RequestMapping("")
	public String indexWeb(HttpServletRequest request, ModelMap resultMap) {
		try {
			String host = request.getHeader("Host");
			if (StringUtils.isNotBlank(host) && host.contains("d.xingdk.com")) {
				request.setAttribute("company", MYZT);
				return getFtlPath(request, "/site/myzt");
			} else if (StringUtils.isNotBlank(host) && host.contains("xingdk.cn")) {
				request.setAttribute("company", TJKT);
				return getFtlPath(request, "/site/myzt");
			} else if (StringUtils.isNotBlank(host) && host.contains("honghongcai.cn")) {
				request.setAttribute("company", TJKT);
				return getFtlPath(request, "/site/myzt");
			} else if (StringUtils.isNotBlank(host) && host.contains("huhacai.com")) {
				request.setAttribute("company", TJKT);
				return getFtlPath(request, "/site/myzt");
			} else if (StringUtils.isNotBlank(host) && host.contains("huhacp.com")) {
				request.setAttribute("company", TJKT);
				return getFtlPath(request, "/site/myzt");
			}
			return redirect("/404.html");
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			LoggerUtil.error("[Result: error]", e);
		}
		return getFtlPath(request, "/common/error");
	}

	private void getHomeInfos(Map<String, Object> resultMap, Activity activity, Product product, PageBounds pageBounds,
			HttpServletRequest request, Integer client) {
		// 产品列表
		product.setActivityId(activity.getActivityId());
		product.setStatus(ProductStatus.ONLINE);
		PageList<Product> productList = (PageList<Product>) productService.selectProducts(product,
				formatPageBounds(pageBounds));

		List<Object> list = new ArrayList<Object>();
		Map<String, List<Product>> productMap = new HashMap<String, List<Product>>();

		String activityChannels = IniBean.getIniValue("sxzxgActivityChannel");
		String[] activitys = activityChannels.split(",");
		Activity acti = null;
		for (int i = 0; i < activitys.length; i++) {
			acti = ConfigManager.getActivityByKey(activitys[i]);
			product.setActivityId(acti.getActivityId());
			list.addAll(productService.selectProducts(product, pageBounds));
			resultMap.put(activitys[i] + "List", productService.selectProducts(product, pageBounds));
		}

		List<Tags> tagsList = new ArrayList<>();
		for (Product prod : productList) {
			Tags tags = new Tags();
			if (prod.getAdvantage() != null) {
				tags.setImage(prod.getAdvantage());
				tagsList.add(tags);
			}
		}
		// 滚动消息列表
		Message message = new Message();
		message.setProductId(activity.getActivityId());
		List<Message> messageList = messageService.selectMessages(message, new PageBounds());
		String channel = appService.getAppChannel(request.getParameter("appId"));
		String appKey = getAppKey(request);
		Long activityId = null;
		if (activity != null) {
			activityId = activity.getActivityId();
		}
		List<ProductCategory> productCategorys = new ArrayList<ProductCategory>();
		String ids = ConfigManager.getConfigValue(activityId, channel, appKey,
				ConfigConstants.PRODUCT_CATEGORY_INDEX_CONFIG);
		productCategorys = ConfigManager.getProductCategoryList(ids);
		resultMap.put("productCategorys", productCategorys);
		resultMap.put(ComRetCode.PAGINTOR, productList.getPaginator());
		setAdvertisement(client, resultMap, channel, appKey, activityId, AdvertisementPosition.ADVERTISEMENT_INDEX);
		resultMap.put("messageList", messageList);
		resultMap.put("productList", productList);
		resultMap.put("messageList", messageList);
		resultMap.put("tagsList", tagsList);

	}
}
