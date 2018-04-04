package com.yuanshanbao.ad.location.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.ad.location.dao.MerchantLocationDao;
import com.yuanshanbao.ad.location.model.MerchantLocation;
import com.yuanshanbao.ad.merchant.model.Merchant;
import com.yuanshanbao.ad.merchant.service.MerchantService;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class MerchantLocationServiceImpl implements MerchantLocationService {

	@Autowired
	private MerchantLocationDao merchantLocationDao;

	@Autowired
	private MerchantService merchantService;

	@Override
	public List<MerchantLocation> selectMerchantLocations(MerchantLocation merchantLocation, PageBounds pageBounds) {
		return setProperty(merchantLocationDao.selectMerchantLocations(merchantLocation, pageBounds));
	}

	private List<MerchantLocation> setProperty(List<MerchantLocation> list) {
		List<Long> idList = new ArrayList<Long>();

		for (MerchantLocation loc : list) {
			idList.add(loc.getMerchantId());
		}
		Map<Long, Merchant> merchantMap = merchantService.selectMerchantByIds(idList);
		for (MerchantLocation loc : list) {
			loc.setMerchant(merchantMap.get(loc.getMerchantId()));
		}
		return list;
	}

	@Override
	public MerchantLocation selectMerchantLocation(Long merchantLocationId) {
		MerchantLocation merchantLocation = new MerchantLocation();
		if (merchantLocationId == null) {
			return null;
		}
		merchantLocation.setMerchantLocationId(merchantLocationId);
		List<MerchantLocation> list = selectMerchantLocations(merchantLocation, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertMerchantLocation(MerchantLocation merchantLocation) {
		int result = -1;

		result = merchantLocationDao.insertMerchantLocation(merchantLocation);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteMerchantLocation(Long merchantLocationId) {
		int result = -1;

		result = merchantLocationDao.deleteMerchantLocation(merchantLocationId);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateMerchantLocation(MerchantLocation merchantLocation) {
		int result = -1;

		result = merchantLocationDao.updateMerchantLocation(merchantLocation);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	/**
	 * @param merchantLocation
	 * @return
	 * @throws IOException
	 */
	@Override
	public void insertOrUpdateMerchantLocation(MerchantLocation merchantLocation) {
		if (merchantLocation.getMerchantLocationId() == null) {
			insertMerchantLocation(merchantLocation);
		} else {
			updateMerchantLocation(merchantLocation);
		}
	}

	@Override
	public Map<Long, MerchantLocation> selectMerchantLocations(List<Long> merchantLocationIdList) {
		Map<Long, MerchantLocation> map = new HashMap<Long, MerchantLocation>();
		if (merchantLocationIdList == null || merchantLocationIdList.size() == 0) {
			return map;
		}
		List<MerchantLocation> list = merchantLocationDao.selectMerchantLocations(merchantLocationIdList);
		for (MerchantLocation merchantLocation : list) {
			map.put(merchantLocation.getMerchantLocationId(), merchantLocation);
		}
		return map;
	}

	@Override
	public MerchantLocation selectMerchantLocation(String locationCode, Long merchantId) {
		MerchantLocation merchantLocation = new MerchantLocation();
		if (StringUtils.isBlank(locationCode) || merchantId == null) {
			return null;
		}
		merchantLocation.setLocationCode(locationCode);
		merchantLocation.setMerchantId(merchantId);
		List<MerchantLocation> list = selectMerchantLocations(merchantLocation, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<String, MerchantLocation> selectMerchantLocationsByMerchantId(Long merchantId) {
		Map<String, MerchantLocation> map = new HashMap<String, MerchantLocation>();
		if (merchantId == null) {
			return map;
		}
		MerchantLocation param = new MerchantLocation();
		param.setMerchantId(merchantId);
		List<MerchantLocation> list = selectMerchantLocations(param, new PageBounds());
		for (MerchantLocation merchantLocation : list) {
			map.put(merchantLocation.getLocationCode(), merchantLocation);
		}
		return map;
	}

	@Override
	public Map<Long, Map<String, MerchantLocation>> selectMerchantLocationMap() {
		Map<Long, Map<String, MerchantLocation>> resultMap = new HashMap<Long, Map<String, MerchantLocation>>();
		MerchantLocation param = new MerchantLocation();
		List<MerchantLocation> list = selectMerchantLocations(param, new PageBounds());
		for (MerchantLocation merchantLocation : list) {
			Map<String, MerchantLocation> map = resultMap.get(merchantLocation.getMerchantId());
			if (map == null) {
				map = new HashMap<String, MerchantLocation>();
				resultMap.put(merchantLocation.getMerchantId(), map);
			}
			map.put(merchantLocation.getLocationCode(), merchantLocation);
		}
		return resultMap;
	}
}
