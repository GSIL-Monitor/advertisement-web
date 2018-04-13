package com.yuanshanbao.dsp.statistics.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.statistics.model.Statistics;

@Repository
public class CommonStatisticsDaoImpl extends BaseDaoImpl implements CommonStatisticsDao {

	@Override
	public List<Statistics> selectApplyCount(Map<String, Object> map) {
		return getSqlSession().selectList("Statistics.selectApplyCount", map);
	}

	@Override
	public List<Statistics> selectRegisterCount(Map<String, Object> map) {
		return getSqlSession().selectList("Statistics.selectRegisterCount", map);
	}

	@Override
	public List<Statistics> selectDownloadCount(Map<String, Object> map) {
		return getSqlSession().selectList("Statistics.selectDownloadCount", map);
	}

	@Override
	public List<Statistics> selectFirstLoginCount(Map<String, Object> map) {
		return getSqlSession().selectList("Statistics.selectFirstLoginCount", map);
	}

	@Override
	public List<Statistics> selectApplyUserCount(Map<String, Object> map) {
		return getSqlSession().selectList("Statistics.selectApplyUserCount", map);
	}

	@Override
	public List<Statistics> selectApplySuccessCount(Map<String, Object> map) {
		return getSqlSession().selectList("Statistics.selectApplySuccessCount", map);
	}

}
