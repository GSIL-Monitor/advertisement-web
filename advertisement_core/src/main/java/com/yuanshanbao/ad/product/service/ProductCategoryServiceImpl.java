package com.yuanshanbao.ad.product.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.paginator.domain.PageBounds;

import org.springframework.stereotype.Service;

import com.yuanshanbao.ad.product.dao.ProductCategoryDao;
import com.yuanshanbao.ad.product.model.ProductCategory;
import com.yuanshanbao.ad.product.service.ProductCategoryService;

/**
 * @description
 * @author
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	// ~ fields =======================================================
	@Autowired
	private ProductCategoryDao productCategoryDao;

	// ~ Method =======================================================

	@Override
	public void insertProductCategory(ProductCategory productCategory) {

		int result = -1;

		result = productCategoryDao.insertProductCategory(productCategory);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateProductCategory(ProductCategory productCategory) {

		int result = -1;

		result = productCategoryDao.updateProductCategory(productCategory);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteProductCategory(ProductCategory productCategory) {

		int result = -1;

		result = productCategoryDao.deleteProductCategory(productCategory);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public List<ProductCategory> selectProductCategorys(ProductCategory productCategory, PageBounds pageBounds) {
		return productCategoryDao.selectProductCategorys(productCategory, pageBounds);
	}

	@Override
	public Map<Long, ProductCategory> selectProductCategoryByIds(List<Long> productCategoryIds) {
		Map<Long, ProductCategory> map = new HashMap<Long, ProductCategory>();
		if (productCategoryIds == null || productCategoryIds.size() == 0) {
			return map;
		}
		List<ProductCategory> list = productCategoryDao.selectProductCategoryByIds(productCategoryIds);
		for (ProductCategory productCategory : list) {
			map.put(productCategory.getProductCategoryId(), productCategory);
		}
		return map;
	}

	@Override
	public ProductCategory selectProductCategorys(Long categoryId) {
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryId(categoryId);
		List<ProductCategory> list = selectProductCategorys(productCategory, new PageBounds());
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}