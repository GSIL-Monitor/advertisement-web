package com.yuanshanbao.ad.location.dao;

import java.util.List;

import com.yuanshanbao.ad.location.model.MobileLocation;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface MobileLocationDao {

	public List<MobileLocation> selectMobileLocations(MobileLocation mobileLocation, PageBounds pageBounds);

	public int insertMobileLocation(MobileLocation mobileLocation);

	public int deleteMobileLocation(Long mobileLocationId);

	public int updateMobileLocation(MobileLocation mobileLocation);

	public List<MobileLocation> selectMobileLocations(List<Long> mobileLocationIdList);

	public List<MobileLocation> selectMobileLocationByPrefixs(List<String> prefixs);
}
