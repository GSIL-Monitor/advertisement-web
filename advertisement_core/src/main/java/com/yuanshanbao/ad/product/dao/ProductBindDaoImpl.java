package com.yuanshanbao.ad.product.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.ad.base.dao.BaseDaoImpl;
import com.yuanshanbao.ad.product.dao.ProductBindDao;
import com.yuanshanbao.ad.product.model.ProductBind;

/**
 * 
 * @description 
 *
 * @author 
 */
@Repository
public class ProductBindDaoImpl extends BaseDaoImpl implements ProductBindDao {
	
	
	@Override
	public int insertProductBind(ProductBind productBind) {
		return getSqlSession()
				.insert("productBind.insertProductBind", productBind);
	}

	@Override
	public int updateProductBind(ProductBind productBind) {
		return getSqlSession()
				.update("productBind.updateProductBind", productBind);
	}

	@Override
	public int deleteProductBind(ProductBind productBind) {
		return getSqlSession()
				.delete("productBind.deleteProductBind", productBind);
	}

	@Override
	public List<ProductBind> selectProductBinds(ProductBind productBind,
			PageBounds pageBounds) {
		return getSqlSession().selectList("productBind.selectProductBinds",
				productBind, pageBounds);
	}

	@Override
	public List<ProductBind> selectProductBindByIds(List<Long> bindIds) {
		if (bindIds == null || bindIds.size() == 0) {
			return new ArrayList<ProductBind>();
		}
		return getSqlSession().selectList(
				"productBind.selectProductBindByBindIds", bindIds);
	}
}