package com.yuanshanbao.dsp.location.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.location.dao.LocationDao;
import com.yuanshanbao.dsp.location.model.Location;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationDao locationDao;

	@Override
	public List<Location> selectLocations(Location location, PageBounds pageBounds) {
		return locationDao.selectLocations(location, pageBounds);
	}

	@Override
	public Location selectLocation(Long locationId) {
		Location location = new Location();
		if (locationId == null) {
			return null;
		}
		location.setLocationId(locationId);
		List<Location> list = selectLocations(location, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertLocation(Location location) {
		int result = -1;

		result = locationDao.insertLocation(location);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteLocation(Long locationId) {
		int result = -1;

		result = locationDao.deleteLocation(locationId);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateLocation(Location location) {
		int result = -1;

		result = locationDao.updateLocation(location);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	/**
	 * @param location
	 * @return
	 * @throws IOException
	 */
	@Override
	public void insertOrUpdateLocation(Location location) {
		if (location.getLocationId() == null) {
			insertLocation(location);
		} else {
			updateLocation(location);
		}
	}

	@Override
	public Map<Long, Location> selectLocations(List<Long> locationIdList) {
		Map<Long, Location> map = new HashMap<Long, Location>();
		if (locationIdList == null || locationIdList.size() == 0) {
			return map;
		}
		List<Location> list = locationDao.selectLocations(locationIdList);
		for (Location location : list) {
			map.put(location.getLocationId(), location);
		}
		return map;
	}

	@Override
	public Location selectLocation(String code) {
		Location location = new Location();
		if (StringUtils.isBlank(code)) {
			return null;
		}
		location.setCode(code);
		List<Location> list = selectLocations(location, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Location> selectAllLocationsMap() {
		Map<String, Location> locationMap = new HashMap<String, Location>();
		List<Location> list = selectLocations(new Location(), new PageBounds());
		for (Location location : list) {
			locationMap.put(location.getCode(), location);
		}
		return locationMap;
	}

}
