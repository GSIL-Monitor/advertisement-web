package com.yuanshanbao.dsp.product.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.dsp.merchant.model.Merchant;
import com.yuanshanbao.dsp.merchant.service.MerchantService;
import com.yuanshanbao.dsp.product.dao.InsuranceProductDao;
import com.yuanshanbao.dsp.product.model.InsuranceProduct;
import com.yuanshanbao.dsp.product.model.Product;

/**
 * @description
 * @author
 */
@Service
public class InsuranceProductServiceImpl implements InsuranceProductService {

	// ~ fields =======================================================
	@Autowired
	private InsuranceProductDao insuranceProductDao;

	@Autowired
	private ProductService productService;

	@Autowired
	private MerchantService merchantService;

	// ~ Method =======================================================

	@Override
	public void insertInsuranceProduct(InsuranceProduct insuranceProduct) {

		int result = -1;

		result = insuranceProductDao.insertInsuranceProduct(insuranceProduct);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateInsuranceProduct(InsuranceProduct insuranceProduct) {

		int result = -1;

		result = insuranceProductDao.updateInsuranceProduct(insuranceProduct);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteInsuranceProduct(InsuranceProduct insuranceProduct) {

		int result = -1;

		result = insuranceProductDao.deleteInsuranceProduct(insuranceProduct);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public List<InsuranceProduct> selectInsuranceProducts(InsuranceProduct insuranceProduct, PageBounds pageBounds) {
		return setPropertys(insuranceProductDao.selectInsuranceProducts(insuranceProduct, pageBounds));
	}

	private List<InsuranceProduct> setPropertys(List<InsuranceProduct> list) {
		List<Long> productIds = new ArrayList<Long>();
		for (InsuranceProduct product : list) {
			productIds.add(product.getProductId());
		}
		Map<Long, Product> map = productService.selectProductByIds(productIds);
		for (InsuranceProduct product : list) {
			product.addProduct(map.get(product.getProductId()));
			if (product.getMerchantId() != null) {
				Merchant merchant = merchantService.selectMerchant(product.getMerchantId());
				if (merchant != null) {
					product.setMerchant(merchant);
				}
			}
		}
		return list;
	}

	@Override
	public Map<Long, InsuranceProduct> selectInsuranceProductByIds(List<Long> insuranceProductIds) {
		Map<Long, InsuranceProduct> map = new HashMap<Long, InsuranceProduct>();
		if (insuranceProductIds == null || insuranceProductIds.size() == 0) {
			return map;
		}
		List<InsuranceProduct> list = setPropertys(insuranceProductDao.selectInsuranceProductByIds(insuranceProductIds));
		for (InsuranceProduct insuranceProduct : list) {
			map.put(insuranceProduct.getInsuranceProductId(), insuranceProduct);
		}
		return map;
	}

	@Override
	public InsuranceProduct selectInsuranceProducts(Long productId) {
		InsuranceProduct insuranceProduct = new InsuranceProduct();
		insuranceProduct.setProductId(productId);
		List<InsuranceProduct> list = setPropertys(selectInsuranceProducts(insuranceProduct, new PageBounds()));
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}