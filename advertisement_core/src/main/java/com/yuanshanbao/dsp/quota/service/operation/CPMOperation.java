package com.yuanshanbao.dsp.quota.service.operation;

import org.springframework.stereotype.Service;

import com.yuanshanbao.dsp.common.constant.RedisConstant;

@Service
public class CPMOperation extends AdvertisementOperation {

	@Override
	public String getResult() {
		return redisCacheService.get(RedisConstant.getAdvertisementClickCountPVKey(null, this.getQuota()
				.getAdvertisementId() + "", this.getQuota().getChannel()));
	}

	@Override
	public String getProbabilityResult() {
		// TODO Auto-generated method stub
		return null;
	}

}
