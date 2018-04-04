package com.yuanshanbao.ad.product.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.ad.base.dao.BaseDaoImpl;
import com.yuanshanbao.ad.product.dao.ProductCategoryDao;
import com.yuanshanbao.ad.product.model.ProductCategory;

/**
 * 
 * @description
 *
 * @author
 */
@Repository
public class ProductCategoryDaoImpl extends BaseDaoImpl implements ProductCategoryDao {

	@Override
	public int insertProductCategory(ProductCategory productCategory) {
		return getSqlSession().insert("productCategory.insertProductCategory", productCategory);
	}

	@Override
	public int updateProductCategory(ProductCategory productCategory) {
		return getSqlSession().update("productCategory.updateProductCategory", productCategory);
	}

	@Override
	public int deleteProductCategory(ProductCategory productCategory) {
		return getSqlSession().delete("productCategory.deleteProductCategory", productCategory);
	}

	@Override
	public List<ProductCategory> selectProductCategorys(ProductCategory productCategory, PageBounds pageBounds) {
		return getSqlSession().selectList("productCategory.selectProductCategorys", productCategory, pageBounds);
	}

	@Override
	public List<ProductCategory> selectProductCategoryByIds(List<Long> productCategoryIds) {
		if (productCategoryIds == null || productCategoryIds.size() == 0) {
			return new ArrayList<ProductCategory>();
		}
		return getSqlSession().selectList("productCategory.selectProductCategoryByProductCategoryIds",
				productCategoryIds);
	}
}