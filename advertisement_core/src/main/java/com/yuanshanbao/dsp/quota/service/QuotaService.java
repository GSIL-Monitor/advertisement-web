package com.yuanshanbao.dsp.quota.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.dsp.quota.model.Quota;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface QuotaService {
	public void insertQuota(Quota quota);

	public void updateQuota(Quota quota);

	public void deleteQuota(Quota quota);

	public List<Quota> selectQuota(Quota quota, PageBounds pageBounds);

	public Quota selectQuota(Long quotaId);

	public Map<Long, Quota> selectQuotaByIds(List<Long> quotaIds);
	
	public List<Quota> selectQuotaFromCache(Long projectId, Long positionId, List<Long> advertisementIdList);

}
