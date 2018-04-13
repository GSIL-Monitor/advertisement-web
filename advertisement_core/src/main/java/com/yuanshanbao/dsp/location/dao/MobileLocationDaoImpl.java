package com.yuanshanbao.dsp.location.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.location.model.MobileLocation;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class MobileLocationDaoImpl extends BaseDaoImpl implements MobileLocationDao {
	@Override
	public List<MobileLocation> selectMobileLocations(MobileLocation mobileLocation, PageBounds pageBounds) {
		return getSqlSession().selectList("mobileLocation.selectMobileLocation", mobileLocation, pageBounds);
	}

	@Override
	public int insertMobileLocation(MobileLocation mobileLocation) {
		return getSqlSession().insert("mobileLocation.insertMobileLocation", mobileLocation);
	}

	@Override
	public int deleteMobileLocation(Long mobileLocationId) {
		return getSqlSession().delete("mobileLocation.deleteMobileLocation", mobileLocationId);
	}

	@Override
	public int updateMobileLocation(MobileLocation mobileLocation) {
		return getSqlSession().update("mobileLocation.updateMobileLocation", mobileLocation);
	}

	@Override
	public List<MobileLocation> selectMobileLocations(List<Long> mobileLocationIdList) {
		if (mobileLocationIdList == null || mobileLocationIdList.size() == 0) {
			return new ArrayList<MobileLocation>();
		}
		return getSqlSession().selectList("mobileLocation.selectMobileLocationByIds", mobileLocationIdList);
	}

	@Override
	public List<MobileLocation> selectMobileLocationByPrefixs(List<String> prefixs) {
		if (prefixs == null || prefixs.size() == 0) {
			return new ArrayList<MobileLocation>();
		}
		return getSqlSession().selectList("mobileLocation.selectMobileLocationByPrefixs", prefixs);
	}

}
