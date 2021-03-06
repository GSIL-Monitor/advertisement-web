package com.yuanshanbao.dsp.product.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.product.model.Product;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class ProductDaoImpl extends BaseDaoImpl implements ProductDao {
	@Override
	public List<Product> selectProducts(Product product, PageBounds pageBounds) {
		return getSqlSession().selectList("product.selectProducts", product, pageBounds);
	}

	@Override
	public Product selectPrdouctById(Long productId) {
		return getSqlSession().selectOne("product.selectProductByProductId", productId);
	}

	@Override
	public int insertProduct(Product product) {
		return getSqlSession().insert("product.insertProduct", product);
	}

	@Override
	public int deleteProduct(Long productId) {
		return getSqlSession().delete("product.deleteProduct", productId);
	}

	@Override
	public int updateProduct(Product product) {
		return getSqlSession().update("product.updateProduct", product);
	}

	@Override
	public List<Product> selectProducts(List<Long> productIdList) {
		if (productIdList == null || productIdList.size() == 0) {
			return new ArrayList<Product>();
		}
		return getSqlSession().selectList("product.selectProductByIds", productIdList);
	}

	@Override
	public List<Product> selectProductByActivityId(Long activityId, PageBounds pageBounds) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList("product.selectProductByActivityId", activityId, pageBounds);
	}

}
