package com.yuanshanbao.ad.sms.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.ad.base.dao.BaseDaoImpl;
import com.yuanshanbao.ad.sms.model.MarketingSms;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class MarketingSmsDaoImpl extends BaseDaoImpl implements MarketingSmsDao {

	@Override
	public int insertMarketingSms(MarketingSms marketingSms) {
		return getSqlSession().insert("marketingSms.insertMarketingSms", marketingSms);
	}

	@Override
	public int updateMarketingSms(MarketingSms marketingSms) {
		return getSqlSession().update("marketingSms.updateMarktingSms", marketingSms);
	}

	@Override
	public int deleteMarketingSms(MarketingSms marketingSms) {
		return getSqlSession().delete("marketingSms.deleteMarketingSms", marketingSms);
	}

	@Override
	public List<MarketingSms> selectMarketingSms(MarketingSms marketingSms, PageBounds pageBounds) {
		return getSqlSession().selectList("marketingSms.selectMarketingSms", marketingSms, pageBounds);
	}

	@Override
	public List<MarketingSms> selectMarketingSmsByMobile(Map<String, Object> map) {
		if (map == null || map.size() == 0) {
			return new ArrayList<MarketingSms>();
		}
		return getSqlSession().selectList("marketingSms.selectMarketingSmsByMobiles", map);
	}

}
