package com.yuanshanbao.dsp.sms.dao;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.dsp.sms.model.MarketingSms;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface MarketingSmsDao {

	public int insertMarketingSms(MarketingSms marketingSms);

	public int updateMarketingSms(MarketingSms marketingSms);

	public int deleteMarketingSms(MarketingSms marketingSms);

	public List<MarketingSms> selectMarketingSms(MarketingSms marketingSms, PageBounds pageBounds);
	
	public List<MarketingSms> selectMarketingSmsByMobile(Map<String, Object> map);

}
