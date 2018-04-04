package com.yuanshanbao.ad.advertisement.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.ad.advertisement.model.Advertisement;
import com.yuanshanbao.ad.base.dao.BaseDaoImpl;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class AdvertisementDaoImpl extends BaseDaoImpl implements AdvertisementDao {

	@Override
	public int insertAdvertisement(Advertisement advertisement) {
		return getSqlSession().insert("advertisement.insertAdvertisement", advertisement);
	}

	@Override
	public int updateAdvertisement(Advertisement advertisement) {
		return getSqlSession().update("advertisement.updateAdvertisement", advertisement);
	}

	@Override
	public int deleteAdvertisement(Advertisement advertisement) {
		return getSqlSession().delete("advertisement.deleteAdvertisement", advertisement);
	}

	@Override
	public List<Advertisement> selectAdvertisement(Advertisement advertisement, PageBounds pageBounds) {
		return getSqlSession().selectList("advertisement.selectAdvertisement", advertisement, pageBounds);
	}

	@Override
	public List<Advertisement> selectAdvertisementByIds(List<Long> advertisementIds) {

		if (advertisementIds == null || advertisementIds.size() == 0) {
			return new ArrayList<Advertisement>();
		}

		return getSqlSession().selectList("advertisement.selectAdvertisementByIds", advertisementIds);
	}

}
