package com.yuanshanbao.ad.product.dao;


import java.util.List;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.ad.product.model.ProductCategory;

/**
 * 
 * @description
 *
 * @author 
 */
public interface ProductCategoryDao {

	public int insertProductCategory(ProductCategory productCategory);

	public int updateProductCategory(ProductCategory productCategory);

	public int deleteProductCategory(ProductCategory productCategory);

	public List<ProductCategory> selectProductCategorys(ProductCategory productCategory,
			PageBounds pageBounds);

	public List<ProductCategory> selectProductCategoryByIds(List<Long> productCategoryIds);
}