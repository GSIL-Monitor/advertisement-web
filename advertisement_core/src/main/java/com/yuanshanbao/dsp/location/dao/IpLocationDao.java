package com.yuanshanbao.dsp.location.dao;

import java.util.List;

import com.yuanshanbao.dsp.location.model.IpLocation;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface IpLocationDao {

	public List<IpLocation> selectIpLocations(IpLocation ipLocation, PageBounds pageBounds);

	public int insertIpLocation(IpLocation ipLocation);

	public int deleteIpLocation(Long ipLocationId);

	public int updateIpLocation(IpLocation ipLocation);

	public List<IpLocation> selectIpLocations(List<Long> ipLocationIdList);

}
