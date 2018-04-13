package com.yuanshanbao.dsp.location.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.location.model.IpLocation;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class IpLocationDaoImpl extends BaseDaoImpl implements IpLocationDao {

	@Override
	public List<IpLocation> selectIpLocations(IpLocation ipLocation, PageBounds pageBounds) {
		return getSqlSession().selectList("ipLocation.selectIpLocation", ipLocation, pageBounds);
	}

	@Override
	public int insertIpLocation(IpLocation ipLocation) {
		return getSqlSession().insert("ipLocation.insertIpLocation", ipLocation);
	}

	@Override
	public int deleteIpLocation(Long ipLocationId) {
		return getSqlSession().delete("ipLocationId.deleteIpLocation", ipLocationId);
	}

	@Override
	public int updateIpLocation(IpLocation ipLocation) {
		return getSqlSession().update("ipLocation.updateIpLocation", ipLocation);
	}

	@Override
	public List<IpLocation> selectIpLocations(List<Long> ipLocationIdList) {
		if (ipLocationIdList == null || ipLocationIdList.size() == 0) {
			return new ArrayList<IpLocation>();
		}
		return getSqlSession().selectList("ipLocation.selectIpLocationByIds", ipLocationIdList);
	}

}
