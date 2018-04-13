package com.yuanshanbao.dsp.advertisement.dao;

import java.util.List;

import com.yuanshanbao.dsp.advertisement.model.AdvertisementCategory;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface AdvertisementCategoryDao {

	public List<AdvertisementCategory> selectAdvertisementCategory(AdvertisementCategory category, PageBounds pageBounds);

	public int insertAdvertisementCategory(AdvertisementCategory category);

	public int deleteAdvertisementCategory(AdvertisementCategory category);

	public int updateAdvertisementCategory(AdvertisementCategory category);
	
	public List<AdvertisementCategory> selectAdvertisementCategory(List<Long> categoryId);
}
