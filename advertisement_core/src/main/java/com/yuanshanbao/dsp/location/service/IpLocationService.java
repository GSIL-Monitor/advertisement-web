package com.yuanshanbao.dsp.location.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.dsp.location.model.IpLocation;
import com.yuanshanbao.dsp.location.model.Location;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface IpLocationService {

	public List<IpLocation> selectIpLocations(IpLocation ipLocation, PageBounds pageBounds);

	public IpLocation selectIpLocation(Long ipLocationId);
	
	public IpLocation selectIpLocation(String key);

	public void insertIpLocation(IpLocation ipLocation);

	public void deleteIpLocation(Long ipLocationId);

	public void updateIpLocation(IpLocation ipLocation);

	public void insertOrUpdateIpLocation(IpLocation ipLocation);

	public Map<Long, IpLocation> selectIpLocations(List<Long> ipLocationIdList);

	public Location queryIpLocation(String ip);

}
