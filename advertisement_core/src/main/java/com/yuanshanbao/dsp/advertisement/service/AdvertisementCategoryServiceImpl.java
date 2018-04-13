package com.yuanshanbao.dsp.advertisement.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.advertisement.dao.AdvertisementCategoryDao;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementCategory;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class AdvertisementCategoryServiceImpl implements AdvertisementCategoryService {

	@Autowired
	private AdvertisementCategoryDao categoryDao;

	@Override
	public List<AdvertisementCategory> selectCategory(AdvertisementCategory category, PageBounds pageBounds) {
		return categoryDao.selectAdvertisementCategory(category, pageBounds);
	}

	@Override
	public AdvertisementCategory selectCategory(Long categoryId) {
		AdvertisementCategory category = new AdvertisementCategory();
		category.setCategoryId(categoryId);
		List<AdvertisementCategory> list = selectCategory(category, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertCategory(AdvertisementCategory category) {
		int result = -1;

		result = categoryDao.insertAdvertisementCategory(category);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteCategory(Long categoryId) {
		AdvertisementCategory category = new AdvertisementCategory();
		category.setCategoryId(categoryId);
		deleteCategory(category);
	}

	@Override
	public void deleteCategory(AdvertisementCategory category) {
		int result = -1;

		result = categoryDao.deleteAdvertisementCategory(category);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateCategory(AdvertisementCategory category) {
		int result = -1;

		result = categoryDao.updateAdvertisementCategory(category);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	/**
	 * @param category
	 * @return
	 * @throws IOException
	 */
	@Override
	public void insertOrUpdateCategory(AdvertisementCategory category) {
		if (category.getCategoryId() == null) {
			insertCategory(category);
		} else {
			updateCategory(category);
		}
	}

	@Override
	public Map<Long, AdvertisementCategory> selectCategoryByIds(List<Long> categoryIds) {
		Map<Long, AdvertisementCategory> map = new HashMap<Long, AdvertisementCategory>();
		if (categoryIds == null || categoryIds.size() == 0) {
			return map;
		}
		List<AdvertisementCategory> list = categoryDao.selectAdvertisementCategory(categoryIds);
		for (AdvertisementCategory category : list) {
			map.put(category.getCategoryId(), category);
		}
		return map;
	}

}
