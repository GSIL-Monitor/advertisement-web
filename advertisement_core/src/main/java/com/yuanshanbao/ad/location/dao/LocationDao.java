package com.yuanshanbao.ad.location.dao;

import java.util.List;

import com.yuanshanbao.ad.location.model.Location;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface LocationDao {

	public List<Location> selectLocations(Location location, PageBounds pageBounds);

	public int insertLocation(Location location);

	public int deleteLocation(Long locationId);

	public int updateLocation(Location location);

	public List<Location> selectLocations(List<Long> locationIdList);

}
