package com.yuanshanbao.dsp.prize.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.dsp.prize.model.Prize;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface PrizeService {
	public List<Prize> selectPrizes(Prize prize, PageBounds pageBounds);

	public List<Prize> selectInsurancePrizes(Prize prize, PageBounds pageBounds);

	public Prize selectPrize(Long prizeId);

	public void insertPrize(Prize prize);

	public void deletePrize(Long prizeId);

	public void updatePrize(Prize prize);

	public void insertOrUpdatePrize(Prize prize);

	public Map<Long, Prize> selectPrizes(List<Long> prizeIdList);

	public List<Prize> selectPrizesByIds(List<Long> prizeIdList);

	public List<Prize> selectPrizesByAdvertisementId(Long advertisementId);
}
