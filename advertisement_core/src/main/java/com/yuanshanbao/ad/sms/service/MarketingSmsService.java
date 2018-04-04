package com.yuanshanbao.ad.sms.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.ad.sms.model.MarketingSms;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface MarketingSmsService {

	public void insertMarketingSms(MarketingSms marketingSms);

	public void updateMarketingSms(MarketingSms marketingSms);

	public void deleteMarketingSms(MarketingSms marketingSms);

	public List<MarketingSms> selectMarketingSms(MarketingSms marketingSms, PageBounds pageBounds);

	public Map<String, List<MarketingSms>> selectMarketingSmsByMobiles(List<String> mobiles);

	public Map<String, List<MarketingSms>> selectMarketingSmsByMobiles(Map<String, Object> map);

}
