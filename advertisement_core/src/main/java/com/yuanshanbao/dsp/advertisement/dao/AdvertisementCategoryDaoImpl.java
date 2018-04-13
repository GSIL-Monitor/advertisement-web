package com.yuanshanbao.dsp.advertisement.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.advertisement.model.AdvertisementCategory;
import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class AdvertisementCategoryDaoImpl extends BaseDaoImpl implements AdvertisementCategoryDao {
	@Override
	public List<AdvertisementCategory> selectAdvertisementCategory(AdvertisementCategory category, PageBounds pageBounds) {
		return getSqlSession().selectList("category.selectCategory", category, pageBounds);
	}

	@Override
	public int insertAdvertisementCategory(AdvertisementCategory category) {
		return getSqlSession().insert("category.insertCategory", category);
	}

	@Override
	public int deleteAdvertisementCategory(AdvertisementCategory category) {
		return getSqlSession().delete("category.deleteCategory", category);
	}

	@Override
	public int updateAdvertisementCategory(AdvertisementCategory category) {
		return getSqlSession().update("category.updateCategory", category);
	}

	@Override
	public List<AdvertisementCategory> selectAdvertisementCategory(List<Long> categoryId) {
		if (categoryId == null || categoryId.size() == 0) {
			return new ArrayList<AdvertisementCategory>();
		}
		return getSqlSession().selectList("category.selectCategoryByIds", categoryId);
	}

}
