package com.yuanshanbao.ad.location.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.ad.base.dao.BaseDaoImpl;
import com.yuanshanbao.ad.location.model.Location;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class LocationDaoImpl extends BaseDaoImpl implements LocationDao {
	@Override
	public List<Location> selectLocations(Location location, PageBounds pageBounds) {
		return getSqlSession().selectList("Location.selectLocation", location, pageBounds);
	}

	@Override
	public int insertLocation(Location location) {
		return getSqlSession().insert("Location.insertLocation", location);
	}

	@Override
	public int deleteLocation(Long locationId) {
		return getSqlSession().delete("Location.deleteLocation", locationId);
	}

	@Override
	public int updateLocation(Location location) {
		return getSqlSession().update("Location.updateLocation", location);
	}

	@Override
	public List<Location> selectLocations(List<Long> locationIdList) {
		if (locationIdList == null || locationIdList.size() == 0) {
			return new ArrayList<Location>();
		}
		return getSqlSession().selectList("Location.selectLocationByIds", locationIdList);
	}

}
