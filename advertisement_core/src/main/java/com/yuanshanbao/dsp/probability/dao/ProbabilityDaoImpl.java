package com.yuanshanbao.dsp.probability.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class ProbabilityDaoImpl extends BaseDaoImpl implements ProbabilityDao {
	@Override
	public List<Probability> selectProbabilitys(Probability probability, PageBounds pageBounds) {
		return getSqlSession().selectList("probability.selectProbability", probability, pageBounds);
	}

	@Override
	public int insertProbability(Probability probability) {
		return getSqlSession().insert("probability.insertProbability", probability);
	}

	@Override
	public int deleteProbability(Long probabilityId) {
		return getSqlSession().delete("probability.deleteProbability", probabilityId);
	}

	@Override
	public int updateProbability(Probability probability) {
		return getSqlSession().update("probability.updateProbability", probability);
	}

	@Override
	public List<Probability> selectProbabilitys(List<Long> probabilityIdList) {
		if (probabilityIdList == null || probabilityIdList.size() == 0) {
			return new ArrayList<Probability>();
		}
		return getSqlSession().selectList("probability.selectProbabilityByIds", probabilityIdList);
	}

	@Override
	public List<Probability> selectGeneralProbabilitys(Probability probability, PageBounds pageBounds) {
		return getSqlSession().selectList("probability.selectGeneralProbability", probability, pageBounds);
	}

	@Override
	public List<Long> getWheelsActivityIds() {
		return getSqlSession().selectList("probability.selectWheelsActivityIds");
	}

	@Override
	public List<Probability> selectProbabilityByOrderIds(List<Long> orderIds) {
		return getSqlSession().selectList("probability.selectProbabilityByOrderIds", orderIds);
	}

}
