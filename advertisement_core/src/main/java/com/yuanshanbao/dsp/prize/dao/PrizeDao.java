package com.yuanshanbao.dsp.prize.dao;

import java.util.List;

import com.yuanshanbao.dsp.prize.model.Prize;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface PrizeDao {
	public List<Prize> selectPrizes(Prize prize, PageBounds pageBounds);

	public List<Prize> selectInsurancePrizes(Prize prize, PageBounds pageBounds);

	public int insertPrize(Prize prize);

	public int deletePrize(Long prizeId);

	public int updatePrize(Prize prize);

	public List<Prize> selectPrizes(List<Long> prizeIdList);
}
