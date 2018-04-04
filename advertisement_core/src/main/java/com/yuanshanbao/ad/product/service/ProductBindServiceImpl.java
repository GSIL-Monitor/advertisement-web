package com.yuanshanbao.ad.product.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.ad.product.dao.ProductBindDao;
import com.yuanshanbao.ad.product.model.ProductBind;

/**
 * @description
 * @author
 */
@Service
public class ProductBindServiceImpl implements ProductBindService {

	// ~ fields =======================================================
	@Autowired
	private ProductBindDao productBindDao;

	@Autowired
	private InsuranceProductService insuranceProductService;

	// ~ Method =======================================================

	@Override
	public void insertProductBind(ProductBind productBind) {

		int result = -1;

		result = productBindDao.insertProductBind(productBind);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateProductBind(ProductBind productBind) {

		int result = -1;

		result = productBindDao.updateProductBind(productBind);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteProductBind(ProductBind productBind) {

		int result = -1;

		result = productBindDao.deleteProductBind(productBind);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public List<ProductBind> selectProductBinds(ProductBind productBind, PageBounds pageBounds) {
		return productBindDao.selectProductBinds(productBind, pageBounds);
	}

	@Override
	public Map<Long, ProductBind> selectProductBindByIds(List<Long> bindIds) {
		Map<Long, ProductBind> map = new HashMap<Long, ProductBind>();
		if (bindIds == null || bindIds.size() == 0) {
			return map;
		}
		List<ProductBind> list = productBindDao.selectProductBindByIds(bindIds);
		for (ProductBind productBind : list) {
			map.put(productBind.getBindId(), productBind);
		}
		return map;
	}

	@Override
	public void insertOrUpdateProductBind(ProductBind productBind) {
		if (productBind.getBindId() != null) {
			updateProductBind(productBind);
		}else {
			insertProductBind(productBind);
		}
	}

}