package com.yuanshanbao.dsp.quota.service.operation;

import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.dsp.quota.model.Quota;

public abstract class AdvertisementOperation {

	protected RedisService redisCacheService;

	public void setRedisCacheService(RedisService redisCacheService) {
		this.redisCacheService = redisCacheService;
	}

	private Quota quota;

	private Probability probability;

	public Quota getQuota() {
		return quota;
	}

	public void setQuota(Quota quota) {
		this.quota = quota;
	}

	public abstract String getResult();

	public abstract String getProbabilityResult();

	public Probability getProbability() {
		return probability;
	}

	public void setProbability(Probability probability) {
		this.probability = probability;
	}

}
