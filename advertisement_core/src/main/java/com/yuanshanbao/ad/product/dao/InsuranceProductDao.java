package com.yuanshanbao.ad.product.dao;


import java.util.List;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.ad.product.model.InsuranceProduct;

/**
 * 
 * @description
 *
 * @author 
 */
public interface InsuranceProductDao {

	public int insertInsuranceProduct(InsuranceProduct insuranceProduct);

	public int updateInsuranceProduct(InsuranceProduct insuranceProduct);

	public int deleteInsuranceProduct(InsuranceProduct insuranceProduct);

	public List<InsuranceProduct> selectInsuranceProducts(InsuranceProduct insuranceProduct,
			PageBounds pageBounds);

	public List<InsuranceProduct> selectInsuranceProductByIds(List<Long> insuranceProductIds);
}