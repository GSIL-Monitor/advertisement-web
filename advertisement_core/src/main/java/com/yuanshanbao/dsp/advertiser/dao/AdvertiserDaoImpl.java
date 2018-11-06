package com.yuanshanbao.dsp.advertiser.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class AdvertiserDaoImpl extends BaseDaoImpl implements AdvertiserDao {

	@Override
	public int insertAdvertiser(Advertiser advertiser) {
		return getSqlSession().insert("advertiser.insertAdvertiser", advertiser);
	}

	@Override
	public int updateAdvertiser(Advertiser advertiser) {
		return getSqlSession().update("advertiser.updateAdvertiser", advertiser);
	}

	@Override
	public int deleteAdvertiser(Advertiser advertiser) {
		return getSqlSession().delete("advertiser.deleteAdvertiser", advertiser);
	}

	@Override
	public List<Advertiser> selectAdvertiser(Advertiser advertiser, PageBounds pageBounds) {
		return getSqlSession().selectList("advertiser.selectAdvertiser", advertiser, pageBounds);
	}

	@Override
	public List<Advertiser> selectAdvertiserByIds(List<Long> advertiserIds) {
		if (advertiserIds == null || advertiserIds.size() == 0) {
			return new ArrayList<Advertiser>();
		}

		return getSqlSession().selectList("advertiser.selectAdvertiserByIds", advertiserIds);
	}

	@Override
	public int lockBalance(Map<String, Object> parameters) {
		return getSqlSession().update("advertiser.rechargeBalance", parameters);
	}

	@Override
	public int cutPayment(Map<String, Object> parameters) {
		return getSqlSession().update("advertiser.cutPayment", parameters);
	}

	@Override
	public Advertiser selectAdvertiserForUpdate(Map<String, Object> parameters) {
		return getSqlSession().selectOne("advertiser.selectAdvertiserForUpdate", parameters);
	}

}
