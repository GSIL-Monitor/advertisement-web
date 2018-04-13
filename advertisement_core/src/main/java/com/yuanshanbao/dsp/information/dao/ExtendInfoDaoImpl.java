package com.yuanshanbao.dsp.information.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.information.model.ExtendInfo;

@Repository
public class ExtendInfoDaoImpl extends BaseDaoImpl implements ExtendInfoDao {

	@Override
	public int insertExtendInfo(ExtendInfo extendInfo) {
		return getSqlSession().insert("extendInfo.insertExtendInfo", extendInfo);
	}

	@Override
	public int updateExtendInfo(ExtendInfo extendInfo) {
		return getSqlSession().update("extendInfo.updateExtendInfo", extendInfo);
	}

	@Override
	public int deleteExtendInfo(ExtendInfo extendInfo) {
		return getSqlSession().delete("extendInfo.deleteExtendInfo", extendInfo);
	}

	@Override
	public List<ExtendInfo> selectExtendInfo(ExtendInfo extendInfo, PageBounds pageBounds) {
		return getSqlSession().selectList("extendInfo.selectExtendInfo", extendInfo, pageBounds);
	}

	@Override
	public List<ExtendInfo> selectExtendInfoById(Map<String, Object> params) {
		if (params == null) {
			return new ArrayList<ExtendInfo>();
		}

		return getSqlSession().selectList("extendInfo.selectExtendInfoByIds", params);
	}

	@Override
	public List<ExtendInfo> selectExtendInfoByInformationIds(List<Long> insurantIds) {
		if (insurantIds == null || insurantIds.size() == 0) {
			return new ArrayList<ExtendInfo>();
		}

		return getSqlSession().selectList("extendInfo.selectExtendInfoByInformationIds", insurantIds);
	}

}
