package com.yuanshanbao.dsp.quota.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.quota.model.Quota;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class QuotaDaoImpl extends BaseDaoImpl implements QuotaDao {

	@Override
	public int insertQuota(Quota quota) {
		return getSqlSession().insert("quota.insertQuota", quota);
	}

	@Override
	public int updateQuota(Quota quota) {
		return getSqlSession().update("quota.updateQuota", quota);
	}

	@Override
	public int deleteQuota(Quota quota) {
		return getSqlSession().delete("quota.deleteQuota", quota);
	}

	@Override
	public List<Quota> selectQuota(Quota quota, PageBounds pageBounds) {
		return getSqlSession().selectList("quota.selectQuota", quota, pageBounds);
	}

	@Override
	public List<Quota> selectQuotaByIds(List<Long> quotaIds) {
		if (quotaIds == null || quotaIds.size() == 0) {
			return new ArrayList<Quota>();
		}

		return getSqlSession().selectList("quota.selectQuotaByIds", quotaIds);
	}

	@Override
	public int lockStock(Map<String, Object> parameters) {
		return getSqlSession().update("quota.lockStock", parameters);
	}

}
