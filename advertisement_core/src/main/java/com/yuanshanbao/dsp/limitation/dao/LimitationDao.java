package com.yuanshanbao.dsp.limitation.dao;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.dsp.limitation.model.Limitation;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface LimitationDao {

	public List<Limitation> selectLimitations(Limitation limitation, PageBounds pageBounds);

	public int insertLimitation(Limitation limitation);

	public int deleteLimitation(Long limitationId);

	public int updateLimitation(Limitation limitation);

	public List<Limitation> selectLimitations(List<Long> limitationIdList);

	public List<Limitation> selectLimitationsWithStock(Limitation limitation, PageBounds pageBounds);

	public int lockStock(Map<String, Object> parameters);

	public int restoreStock(Map<String, Object> parameters);

	public List<Limitation> selectAllLimitations(Limitation limitation, PageBounds pageBounds);

	public List<Long> selectInsuranceIdsByLimitation(Limitation limitation);
}
