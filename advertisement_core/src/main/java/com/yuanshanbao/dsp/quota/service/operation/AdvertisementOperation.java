package com.yuanshanbao.dsp.quota.service.operation;

import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.quota.model.Quota;

public abstract class AdvertisementOperation {

	protected RedisService redisCacheService;

	public void setRedisCacheService(RedisService redisCacheService) {
		this.redisCacheService = redisCacheService;
	}

	private Quota quota;

	public Quota getQuota() {
		return quota;
	}

	public void setQuota(Quota quota) {
		this.quota = quota;
	}

	public abstract String getResult();
}
