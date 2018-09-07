package com.yuanshanbao.dsp.quota.service.operation;

import org.springframework.stereotype.Service;

import com.yuanshanbao.dsp.common.constant.RedisConstant;

@Service
public class CPCOperation extends AdvertisementOperation {

	@Override
	public String getResult() {
		return redisCacheService.get(RedisConstant.getAdvertisementClickCountPVKey(null, this.getQuota()
				.getAdvertisementId() + "", this.getQuota().getChannel()));
	}

}
