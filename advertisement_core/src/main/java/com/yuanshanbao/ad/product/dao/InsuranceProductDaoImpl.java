package com.yuanshanbao.ad.product.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.ad.base.dao.BaseDaoImpl;
import com.yuanshanbao.ad.product.dao.InsuranceProductDao;
import com.yuanshanbao.ad.product.model.InsuranceProduct;

/**
 * 
 * @description
 *
 * @author
 */
@Repository
public class InsuranceProductDaoImpl extends BaseDaoImpl implements InsuranceProductDao {

	@Override
	public int insertInsuranceProduct(InsuranceProduct insuranceProduct) {
		return getSqlSession().insert("insuranceProduct.insertInsuranceProduct", insuranceProduct);
	}

	@Override
	public int updateInsuranceProduct(InsuranceProduct insuranceProduct) {
		return getSqlSession().update("insuranceProduct.updateInsuranceProduct", insuranceProduct);
	}

	@Override
	public int deleteInsuranceProduct(InsuranceProduct insuranceProduct) {
		return getSqlSession().delete("insuranceProduct.deleteInsuranceProduct", insuranceProduct);
	}

	@Override
	public List<InsuranceProduct> selectInsuranceProducts(InsuranceProduct insuranceProduct, PageBounds pageBounds) {
		return getSqlSession().selectList("insuranceProduct.selectInsuranceProducts", insuranceProduct, pageBounds);
	}

	@Override
	public List<InsuranceProduct> selectInsuranceProductByIds(List<Long> insuranceProductIds) {
		if (insuranceProductIds == null || insuranceProductIds.size() == 0) {
			return new ArrayList<InsuranceProduct>();
		}
		return getSqlSession().selectList("insuranceProduct.selectInsuranceProductByIds", insuranceProductIds);
	}
}