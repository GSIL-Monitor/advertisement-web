package com.yuanshanbao.dsp.limitation.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.dsp.limitation.model.Limitation;
import com.yuanshanbao.dsp.quota.model.Quota;
import com.yuanshanbao.dsp.statistics.model.Statistics;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface LimitationService {

	public List<Limitation> selectLimitations(Limitation limitation, PageBounds pageBounds);

	public Limitation selectLimitation(Long limitationId);

	public void insertLimitation(Limitation limitation);

	public void deleteLimitation(Long limitationId);

	public void updateLimitation(Limitation limitation);

	public void insertOrUpdateLimitation(Limitation limitation);

	public Map<Long, Limitation> selectLimitations(List<Long> limitationIdList);

	public List<Limitation> selectLimitationsWithStock(Limitation limitation, PageBounds pageBounds);

	public boolean lockStock(Quota quota);

	public boolean restoreStock(Quota quota);

	/**
	 * 查询所有limitation记录
	 * 
	 * @param limitation
	 * @param pageBounds
	 * @return
	 */
	public List<Limitation> selectAllLimitations(Limitation limitation, Statistics statistics, PageBounds pageBounds);

	/**
	 * 查询limitation中的去重insuranceId
	 * 
	 * @param limitation
	 * @return
	 */
	public List<Long> selectInsuranceIdsByLimitation(Limitation limitation);

	/**
	 * 查询所有状态下的单条数据
	 * 
	 * @param limitationId
	 * @return
	 */
	public Limitation selectLimitationById(Long limitationId);

}
