package com.yuanshanbao.dsp.quota.dao;

import java.util.List;

import com.yuanshanbao.dsp.quota.model.Quota;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface QuotaDao {
	public int insertQuota(Quota quota);

	public int updateQuota(Quota quota);

	public int deleteQuota(Quota quota);

	public List<Quota> selectQuota(Quota quota, PageBounds pageBounds);

	public List<Quota> selectQuotaByIds(List<Long> quotaIds);
}
