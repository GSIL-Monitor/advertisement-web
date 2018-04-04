package com.yuanshanbao.ad.location.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.ad.location.model.Location;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface LocationService {

	public List<Location> selectLocations(Location location, PageBounds pageBounds);

	public Location selectLocation(Long locationId);

	public Location selectLocation(String key);

	public void insertLocation(Location location);

	public void deleteLocation(Long locationId);

	public void updateLocation(Location location);

	public void insertOrUpdateLocation(Location location);

	public Map<Long, Location> selectLocations(List<Long> locationIdList);

	public Map<String, Location> selectAllLocationsMap();

}
