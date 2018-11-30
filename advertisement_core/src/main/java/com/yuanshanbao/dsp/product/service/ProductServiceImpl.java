package com.yuanshanbao.dsp.product.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.apply.model.Apply;
import com.yuanshanbao.dsp.apply.model.ApplyUserStatus;
import com.yuanshanbao.dsp.apply.service.ApplyService;
import com.yuanshanbao.dsp.common.constant.RedisConstant;
import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.core.IniBean;
import com.yuanshanbao.dsp.merchant.model.Merchant;
import com.yuanshanbao.dsp.merchant.service.MerchantService;
import com.yuanshanbao.dsp.product.dao.ProductDao;
import com.yuanshanbao.dsp.product.model.Product;
import com.yuanshanbao.dsp.tags.model.Tags;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class ProductServiceImpl implements ProductService {

	private static final String INI_INCREASE_RANGE = "product_count_increase_";

	private static final String INI_INITIAL_RANGE = "product_count_initial_";

	@Autowired
	private ProductDao productDao;

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private ApplyService applyService;

	@Autowired
	private RedisService redisCacheService;

	@Override
	public List<Product> selectProducts(Product product, PageBounds pageBounds) {
		return setProperties(productDao.selectProducts(product, pageBounds));
	}

	private List<Product> setProperties(List<Product> list) {
		List<Long> merchantIdList = new ArrayList<Long>();
		for (Product product : list) {
			merchantIdList.add(product.getMerchantId());
		}
		Map<Long, Merchant> map = merchantService.selectMerchantByIds(merchantIdList);
		for (Product product : list) {
			product.setMerchant(map.get(product.getMerchantId()));
		}
		return list;
	}

	@Override
	public Product selectProduct(Long productId) {
		Product product = new Product();
		if (productId == null) {
			return null;
		}
		product.setProductId(productId);
		List<Product> list = selectProducts(product, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertProduct(Product product) {
		int result = -1;

		result = productDao.insertProduct(product);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteProduct(Long productId) {
		int result = -1;

		result = productDao.deleteProduct(productId);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateProduct(Product product) {
		int result = -1;

		result = productDao.updateProduct(product);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	/**
	 * @param product
	 * @return
	 * @throws IOException
	 */
	@Override
	public void insertOrUpdateProduct(Product product) {
		if (product.getProductId() == null) {
			insertProduct(product);
		} else {
			updateProduct(product);
		}
	}

	@Override
	public Map<Long, Product> selectProductByIds(List<Long> productIdList) {
		Map<Long, Product> map = new HashMap<Long, Product>();
		if (productIdList == null || productIdList.size() == 0) {
			return map;
		}
		List<Product> list = productDao.selectProducts(productIdList);
		for (Product product : list) {
			map.put(product.getProductId(), product);
		}
		return map;
	}

	public void increaseProductCount(Long productId) {
		int increaseCount = 1;
		increaseCount = getRandomFromRange(IniBean.getIniValue(INI_INCREASE_RANGE + productId, "1,3"));
		redisCacheService.increBy(RedisConstant.getProductShowCountKey(productId + ""), increaseCount);

	}

	private Integer getRandomFromRange(String numRange) {
		int random = 0;
		String[] s = numRange.split(",");
		if (s.length == 2) {
			double min = Double.parseDouble(s[0]);
			double max = Double.parseDouble(s[1]);
			random = (int) (Math.random() * (max - min + 1) + min);
		}
		return random;
	}

	@Override
	public Long getApplyCount(Long productId) {
		String key = RedisConstant.getProductShowCountKey(productId + "");
		String str = (String) redisCacheService.get(key);
		long count = 0;
		if (ValidateUtil.isNumber(str)) {
			count = Long.parseLong(str);
		}
		if (count == 0) {
			count = (long) getRandomFromRange(IniBean.getIniValue(INI_INITIAL_RANGE + productId, "3000,5000"));
			redisCacheService.set(key, count + "");
		}
		return count;
	}

	@Override
	public String getApplyInterface(Product product, User user, HttpServletRequest request) {
		Apply apply = new Apply();
		apply.setProductId(product.getProductId());
		apply.setUserId(user.getUserId());
		List<Apply> applies = applyService.selectApplys(apply, new PageBounds());
		if (applies.size() > 0) {
			apply = applies.get(0);
			if (apply.getUserStatus() != null && (apply.getUserStatus().equals(ApplyUserStatus.BLACK))) {
				return null;
			}
		} else {
			// applyService.insertOrUpdateApply(user, product.getProductId(),
			// ApplyStatus.APPLING);
		}
		// PreProductHandler handler = getPreHandle(product, user, request);
		// if (handler != null &&
		// StringUtils.isNotBlank(handler.getApplyInterface())) {
		// return handler.getApplyInterface();
		// }
		return product.getApplyInterface();
	}

	@Override
	public List<Tags> getBrandFeatureMap(String brandFeature) {
		List<Tags> resultlist = new ArrayList<Tags>();

		// Map<String, String> brandFeatureMap = new LinkedMap();
		if (brandFeature == null) {
			// brandFeatureMap.put("brandFeature", brandFeature);
			return resultlist;
		}
		String[] splitBrandFeature = brandFeature.split(" ");
		List<String> arrayList = Arrays.asList(splitBrandFeature);
		for (int i = 0; i < arrayList.size(); i++) {
			List<String> splitBrandList = new ArrayList<>();
			String[] split = arrayList.get(i).split(":");
			splitBrandList = Arrays.asList(split);
			Tags tags = new Tags();
			tags.setName(splitBrandList.get(0));
			tags.setValue(splitBrandList.get(1));
			resultlist.add(tags);
			// brandFeatureMap.put(splitBrandList.get(0),
			// splitBrandList.get(1));
		}
		return resultlist;
	}

}
