package com.yuanshanbao.dsp.prize.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.prize.model.Prize;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class PrizeDaoImpl extends BaseDaoImpl implements PrizeDao {

	@Override
	public List<Prize> selectPrizes(Prize prize, PageBounds pageBounds) {
		return getSqlSession().selectList("prize.selectPrize", prize, pageBounds);
	}

	@Override
	public List<Prize> selectInsurancePrizes(Prize prize, PageBounds pageBounds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertPrize(Prize prize) {
		return getSqlSession().insert("prize.insertPrize", prize);
	}

	@Override
	public int deletePrize(Long prizeId) {
		return getSqlSession().delete("prize.deletePrize", prizeId);
	}

	@Override
	public int updatePrize(Prize prize) {
		return getSqlSession().update("prize.updatePrize", prize);
	}

	@Override
	public List<Prize> selectPrizes(List<Long> prizeIdList) {
		if (prizeIdList == null || prizeIdList.size() == 0) {
			return new ArrayList<Prize>();
		}
		return getSqlSession().selectList("prize.selectPrizeByIds", prizeIdList);
	}

}
