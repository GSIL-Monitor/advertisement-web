package com.yuanshanbao.dsp.location.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.dsp.location.model.Location;
import com.yuanshanbao.dsp.location.model.MobileLocation;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface MobileLocationService {

	public List<MobileLocation> selectMobileLocations(MobileLocation mobileLocation, PageBounds pageBounds);

	public MobileLocation selectMobileLocation(Long mobileLocationId);

	public MobileLocation selectMobileLocation(String key);

	public void insertMobileLocation(MobileLocation mobileLocation);

	public void deleteMobileLocation(Long mobileLocationId);

	public void updateMobileLocation(MobileLocation mobileLocation);

	public void insertOrUpdateMobileLocation(MobileLocation mobileLocation);

	public Map<Long, MobileLocation> selectMobileLocations(List<Long> mobileLocationIdList);

	public Location queryMobileLocation(String mobile);

	public Map<String, MobileLocation> selectMobileLocationByPrefixs(List<String> prefixs);

}
