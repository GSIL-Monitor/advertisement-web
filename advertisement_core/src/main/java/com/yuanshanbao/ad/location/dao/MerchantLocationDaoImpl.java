package com.yuanshanbao.ad.location.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.ad.base.dao.BaseDaoImpl;
import com.yuanshanbao.ad.location.model.MerchantLocation;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class MerchantLocationDaoImpl extends BaseDaoImpl implements MerchantLocationDao {
	@Override
	public List<MerchantLocation> selectMerchantLocations(MerchantLocation merchantLocation, PageBounds pageBounds) {
		return getSqlSession().selectList("merchantLocation.selectMerchantLocation", merchantLocation, pageBounds);
	}

	@Override
	public int insertMerchantLocation(MerchantLocation merchantLocation) {
		return getSqlSession().insert("merchantLocation.insertMerchantLocation", merchantLocation);
	}

	@Override
	public int deleteMerchantLocation(Long merchantLocationId) {
		return getSqlSession().delete("merchantLocation.deleteMerchantLocation", merchantLocationId);
	}

	@Override
	public int updateMerchantLocation(MerchantLocation merchantLocation) {
		return getSqlSession().update("merchantLocation.updateMerchantLocation", merchantLocation);
	}

	@Override
	public List<MerchantLocation> selectMerchantLocations(List<Long> merchantLocationIdList) {
		if (merchantLocationIdList == null || merchantLocationIdList.size() == 0) {
			return new ArrayList<MerchantLocation>();
		}
		return getSqlSession().selectList("merchantLocation.selectMerchantLocationByIds", merchantLocationIdList);
	}

}
