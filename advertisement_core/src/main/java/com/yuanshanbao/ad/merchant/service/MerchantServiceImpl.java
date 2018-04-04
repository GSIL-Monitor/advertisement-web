package com.yuanshanbao.ad.merchant.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.ad.merchant.dao.MerchantDao;
import com.yuanshanbao.ad.merchant.model.Merchant;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class MerchantServiceImpl implements MerchantService {

	@Autowired
	private MerchantDao merchantDao;

	@Override
	public List<Merchant> selectMerchants(Merchant merchant, PageBounds pageBounds) {
		return merchantDao.selectMerchants(merchant, pageBounds);
	}

	@Override
	public Merchant selectMerchant(Long merchantId) {
		Merchant merchant = new Merchant();
		if (merchantId == null) {
			return null;
		}
		merchant.setMerchantId(merchantId);
		List<Merchant> list = selectMerchants(merchant, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertMerchant(Merchant merchant) {
		int result = -1;

		result = merchantDao.insertMerchant(merchant);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteMerchant(Long merchantId) {
		int result = -1;

		result = merchantDao.deleteMerchant(merchantId);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateMerchant(Merchant merchant) {
		int result = -1;

		result = merchantDao.updateMerchant(merchant);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	/**
	 * @param merchant
	 * @return
	 * @throws IOException
	 */
	@Override
	public void insertOrUpdateMerchant(Merchant merchant) {
		if (merchant.getMerchantId() == null) {
			insertMerchant(merchant);
		} else {
			updateMerchant(merchant);
		}
	}

	@Override
	public Map<Long, Merchant> selectMerchantByIds(List<Long> merchantIdList) {
		Map<Long, Merchant> map = new HashMap<Long, Merchant>();
		if (merchantIdList == null || merchantIdList.size() == 0) {
			return map;
		}
		List<Merchant> list = merchantDao.selectMerchants(merchantIdList);
		for (Merchant merchant : list) {
			map.put(merchant.getMerchantId(), merchant);
		}
		return map;
	}

	@Override
	public List<Merchant> selectMerchantsWithInterface(Merchant merchant) {
		List<Merchant> resultList = new ArrayList<Merchant>();
		if (merchant.getProjectId() == null) {
			return resultList;
		}
		List<Merchant> list = selectMerchants(merchant, new PageBounds());
		for (Merchant m : list) {
			if (m.getInterfaceType() != null && m.getInterfaceType().equals(1)) {
				resultList.add(m);
			}
		}
		return resultList;
	}

}
