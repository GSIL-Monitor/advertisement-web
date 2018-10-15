package com.yuanshanbao.dsp.probability.dao;

import java.util.List;

import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface ProbabilityDao {

	public List<Probability> selectProbabilitys(Probability probability, PageBounds pageBounds);

	public List<Probability> selectGeneralProbabilitys(Probability probability, PageBounds pageBounds);

	public int insertProbability(Probability probability);

	public int deleteProbability(Long probabilityId);

	public int updateProbability(Probability probability);

	public List<Probability> selectProbabilitys(List<Long> probabilityIdList);

	public List<Long> getWheelsActivityIds();

	public List<Probability> selectProbabilityByOrderIds(List<Long> orderIds);
}
