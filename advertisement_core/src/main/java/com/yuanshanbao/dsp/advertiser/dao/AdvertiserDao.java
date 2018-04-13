package com.yuanshanbao.dsp.advertiser.dao;

import java.util.List;

import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface AdvertiserDao {
	public int insertAdvertiser(Advertiser advertiser);

	public int updateAdvertiser(Advertiser advertiser);

	public int deleteAdvertiser(Advertiser advertiser);

	public List<Advertiser> selectAdvertiser(Advertiser advertiser, PageBounds pageBounds);

	public List<Advertiser> selectAdvertiserByIds(List<Long> advertiserIds);
}
