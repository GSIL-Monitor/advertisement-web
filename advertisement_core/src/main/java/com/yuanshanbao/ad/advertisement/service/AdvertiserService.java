package com.yuanshanbao.ad.advertisement.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.ad.advertisement.model.Advertiser;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface AdvertiserService {
	public void insertAdvertiser(Advertiser advertiser);

	public void updateAdvertiser(Advertiser advertiser);

	public void deleteAdvertiser(Advertiser advertiser);

	public List<Advertiser> selectAdvertiser(Advertiser advertiser, PageBounds pageBounds);

	public Advertiser selectAdvertiser(Long advertiserId);

	public Map<Long, Advertiser> selectAdvertiserByIds(List<Long> advertiserIds);

}
