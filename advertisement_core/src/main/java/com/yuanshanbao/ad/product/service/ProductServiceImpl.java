package com.yuanshanbao.ad.product.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.ad.common.redis.base.RedisService;
import com.yuanshanbao.ad.merchant.model.Merchant;
import com.yuanshanbao.ad.merchant.service.MerchantService;
import com.yuanshanbao.ad.product.dao.ProductDao;
import com.yuanshanbao.ad.product.model.Product;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;

	@Autowired
	private MerchantService merchantService;

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

}
