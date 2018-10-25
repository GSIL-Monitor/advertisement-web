package com.yuanshanbao.dsp.advertiser.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface AdvertiserService {
	public void insertAdvertiser(Advertiser advertiser);

	public void updateAdvertiser(Advertiser advertiser);

	public void deleteAdvertiser(Advertiser advertiser);

	public List<Advertiser> selectAdvertiser(Advertiser advertiser, PageBounds pageBounds);

	public Advertiser selectAdvertiser(Long advertiserId);

	public Map<Long, Advertiser> selectAdvertiserByIds(List<Long> advertiserIds);

	public Map<String, Object> countAdvertiserSize(Advertiser advertiser);

	public void cutPayment(Long advertiserId, BigDecimal money);

}
