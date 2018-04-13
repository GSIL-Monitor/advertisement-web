package com.yuanshanbao.dsp.product.service;

import java.util.Map;
import java.util.List;

import com.yuanshanbao.paginator.domain.PageBounds;
import org.springframework.stereotype.Service;
import com.yuanshanbao.dsp.product.model.ProductCategory;

/**
 * @description
 * @author
 */
@Service
public interface ProductCategoryService {

	public void insertProductCategory(ProductCategory productCategory);

	public void updateProductCategory(ProductCategory productCategory);

	public void deleteProductCategory(ProductCategory productCategory);

	public List<ProductCategory> selectProductCategorys(ProductCategory productCategory, PageBounds pageBounds);

	public Map<Long, ProductCategory> selectProductCategoryByIds(List<Long> productCategoryIds);
	
	public ProductCategory selectProductCategorys(Long categoryId);
}