package com.yuanshanbao.dsp.product.service;

import java.util.Map;
import java.util.List;

import com.yuanshanbao.paginator.domain.PageBounds;
import org.springframework.stereotype.Service;
import com.yuanshanbao.dsp.product.model.InsuranceProduct;

/**
 * @description
 * @author
 */
@Service
public interface InsuranceProductService {

	public void insertInsuranceProduct(InsuranceProduct insuranceProduct);

	public void updateInsuranceProduct(InsuranceProduct insuranceProduct);

	public void deleteInsuranceProduct(InsuranceProduct insuranceProduct);

	public List<InsuranceProduct> selectInsuranceProducts(InsuranceProduct insuranceProduct, PageBounds pageBounds);
	
	public InsuranceProduct selectInsuranceProducts(Long productId);

	public Map<Long, InsuranceProduct> selectInsuranceProductByIds(List<Long> insuranceProductIds);
}