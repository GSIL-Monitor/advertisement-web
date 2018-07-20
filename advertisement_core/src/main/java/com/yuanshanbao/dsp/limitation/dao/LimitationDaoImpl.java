package com.yuanshanbao.dsp.limitation.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.limitation.model.Limitation;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class LimitationDaoImpl extends BaseDaoImpl implements LimitationDao {

	@Override
	public List<Limitation> selectLimitations(Limitation limitation, PageBounds pageBounds) {
		return getSqlSession().selectList("limitation.selectLimitation", limitation, pageBounds);
	}

	@Override
	public List<Limitation> selectLimitationsWithStock(Limitation limitation, PageBounds pageBounds) {
		return getSqlSession().selectList("limitation.selectLimitationWithStock", limitation, pageBounds);
	}

	@Override
	public int insertLimitation(Limitation limitation) {
		return getSqlSession().insert("limitation.insertLimitation", limitation);
	}

	@Override
	public int deleteLimitation(Long limitationId) {
		return getSqlSession().delete("limitation.deleteLimitation", limitationId);
	}

	@Override
	public int updateLimitation(Limitation limitation) {
		return getSqlSession().update("limitation.updateLimitation", limitation);
	}

	@Override
	public List<Limitation> selectLimitations(List<Long> limitationIdList) {
		if (limitationIdList == null || limitationIdList.size() == 0) {
			return new ArrayList<Limitation>();
		}
		return getSqlSession().selectList("limitation.selectLimitationByIds", limitationIdList);
	}

	@Override
	public int lockStock(Map<String, Object> parameters) {
		return getSqlSession().update("limitation.lockStock", parameters);
	}

	@Override
	public int restoreStock(Map<String, Object> parameters) {
		return getSqlSession().update("limitation.restoreStock", parameters);
	}

	@Override
	public List<Limitation> selectAllLimitations(Limitation limitation, PageBounds pageBounds) {
		return getSqlSession().selectList("limitation.selectAllLimitation", limitation, pageBounds);
	}

	@Override
	public List<Long> selectInsuranceIdsByLimitation(Limitation limitation) {
		return getSqlSession().selectList("limitation.selectInsuranceIdsByLimitation", limitation);
	}

}
