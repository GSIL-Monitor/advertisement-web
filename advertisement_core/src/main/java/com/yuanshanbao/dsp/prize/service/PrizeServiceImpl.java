package com.yuanshanbao.dsp.prize.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.advertisement.model.Advertisement;
import com.yuanshanbao.dsp.advertisement.service.AdvertisementService;
import com.yuanshanbao.dsp.prize.dao.PrizeDao;
import com.yuanshanbao.dsp.prize.model.Prize;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class PrizeServiceImpl implements PrizeService {

	@Autowired
	private PrizeDao prizeDao;

	@Autowired
	private AdvertisementService advertisementService;

	@Override
	public List<Prize> selectPrizes(Prize prize, PageBounds pageBounds) {
		return setProperty(prizeDao.selectPrizes(prize, pageBounds));
	}

	private List<Prize> setProperty(List<Prize> prizeList) {
		List<Long> advertisementIdList = new ArrayList<Long>();
		for (Prize prize : prizeList) {
			advertisementIdList.add(prize.getAdvertisementId());
		}
		Map<Long, Advertisement> advertisementMap = advertisementService.selectAdvertisementByIds(advertisementIdList);
		for (Prize prize : prizeList) {
			prize.setAdvertisement(advertisementMap.get(prize.getAdvertisementId()));
		}
		return prizeList;
	}

	@Override
	public Prize selectPrize(Long prizeId) {
		Prize prize = new Prize();
		if (prizeId == null) {
			return null;
		}
		prize.setPrizeId(prizeId);
		List<Prize> list = selectPrizes(prize, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertPrize(Prize prize) {
		int result = -1;

		result = prizeDao.insertPrize(prize);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deletePrize(Long prizeId) {
		int result = -1;

		result = prizeDao.deletePrize(prizeId);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updatePrize(Prize prize) {
		int result = -1;

		result = prizeDao.updatePrize(prize);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	/**
	 * @param prize
	 * @return
	 * @throws IOException
	 */
	@Override
	public void insertOrUpdatePrize(Prize prize) {
		if (prize.getPrizeId() == null) {
			insertPrize(prize);
		} else {
			updatePrize(prize);
		}
	}

	@Override
	public Map<Long, Prize> selectPrizes(List<Long> prizeIdList) {
		Map<Long, Prize> map = new HashMap<Long, Prize>();
		if (prizeIdList == null || prizeIdList.size() == 0) {
			return map;
		}
		List<Prize> list = setProperty(prizeDao.selectPrizes(prizeIdList));
		for (Prize prize : list) {
			map.put(prize.getPrizeId(), prize);
		}
		return map;
	}

	@Override
	public List<Prize> selectPrizesByIds(List<Long> prizeIdList) {
		return prizeDao.selectPrizes(prizeIdList);
	}

	@Override
	public List<Prize> selectPrizesByAdvertisementId(Long advertisementId) {
		Prize prize = new Prize();
		if (advertisementId == null) {
			return null;
		}
		prize.setAdvertisementId(advertisementId);
		List<Prize> list = selectPrizes(prize, new PageBounds());
		if (list.size() > 0) {
			return list;
		}
		return null;
	}

	@Override
	public List<Prize> selectInsurancePrizes(Prize prize, PageBounds pageBounds) {
		return prizeDao.selectPrizes(prize, pageBounds);
	}

}
