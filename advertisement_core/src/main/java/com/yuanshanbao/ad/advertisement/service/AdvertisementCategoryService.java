package com.yuanshanbao.ad.advertisement.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.ad.advertisement.model.AdvertisementCategory;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface AdvertisementCategoryService {

	public List<AdvertisementCategory> selectCategory(AdvertisementCategory category, PageBounds pageBounds);

	public AdvertisementCategory selectCategory(Long categoryId);

	public void insertCategory(AdvertisementCategory category);

	public void deleteCategory(Long categoryId);

	public void deleteCategory(AdvertisementCategory category);

	public void updateCategory(AdvertisementCategory category);

	public void insertOrUpdateCategory(AdvertisementCategory category);

	public Map<Long, AdvertisementCategory> selectCategoryByIds(List<Long> categoryIds);

}
