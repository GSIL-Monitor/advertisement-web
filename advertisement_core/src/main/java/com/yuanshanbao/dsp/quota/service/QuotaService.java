package com.yuanshanbao.dsp.quota.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.dsp.information.model.Information;
import com.yuanshanbao.dsp.product.model.Product;
import com.yuanshanbao.dsp.quota.model.Quota;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface QuotaService {
	public void insertQuota(Quota quota);

	public void updateQuota(Quota quota);

	public void deleteQuota(Quota quota);

	public List<Quota> selectQuota(Quota quota, PageBounds pageBounds);

	public Quota selectQuota(Long quotaId);

	public Map<Long, Quota> selectQuotaByIds(List<Long> quotaIds);

	public List<Quota> selectQuotaFromCache(Long projectId, Long positionId, List<Long> advertisementIdList);

	public List<Quota> selectQuotaByKeyFromCache(Long projectId, String activityKey, String channelKey);

	public Quota pickGoodsForInformation(Long activityId, Information information);

	public Quota pickProductForApply(User user, Product product);

	public Map<Long, Quota> selectQuotaByProbabilityId(List<Long> ids);
}
