package com.yuanshanbao.dsp.statistics.dao;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.dsp.statistics.model.Statistics;

public interface CommonStatisticsDao {

	public List<Statistics> selectApplyCount(Map<String, Object> map);

	public List<Statistics> selectRegisterCount(Map<String, Object> map);

	public List<Statistics> selectDownloadCount(Map<String, Object> map);

	public List<Statistics> selectFirstLoginCount(Map<String, Object> map);

	public List<Statistics> selectApplyUserCount(Map<String, Object> map);
	
	public List<Statistics> selectApplySuccessCount(Map<String, Object> map);

}
