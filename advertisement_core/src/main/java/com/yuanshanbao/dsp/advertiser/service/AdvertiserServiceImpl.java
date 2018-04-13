package com.yuanshanbao.dsp.advertiser.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.advertiser.dao.AdvertiserDao;
import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class AdvertiserServiceImpl implements AdvertiserService {

	@Autowired
	private AdvertiserDao advertiserDao;

	@Override
	public void insertAdvertiser(Advertiser advertiser) {
		int result = -1;

		result = advertiserDao.insertAdvertiser(advertiser);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateAdvertiser(Advertiser advertiser) {
		int result = -1;

		result = advertiserDao.updateAdvertiser(advertiser);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteAdvertiser(Advertiser advertiser) {
		int result = -1;

		result = advertiserDao.deleteAdvertiser(advertiser);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public List<Advertiser> selectAdvertiser(Advertiser advertiser, PageBounds pageBounds) {
		return advertiserDao.selectAdvertiser(advertiser, pageBounds);
	}

	@Override
	public Advertiser selectAdvertiser(Long advertiserId) {
		if (advertiserId == null) {
			return null;
		}
		Advertiser advertiser = new Advertiser();
		advertiser.setAdvertiserId(advertiserId);
		List<Advertiser> list = selectAdvertiser(advertiser, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<Long, Advertiser> selectAdvertiserByIds(List<Long> advertiserIds) {
		Map<Long, Advertiser> map = new HashMap<Long, Advertiser>();
		if (advertiserIds == null || advertiserIds.size() == 0) {
			return map;
		}
		List<Advertiser> list = advertiserDao.selectAdvertiserByIds(advertiserIds);
		for (Advertiser ad : list) {
			map.put(ad.getAdvertiserId(), ad);
		}
		return map;
	}

}
