package com.yuanshanbao.dsp.common.times;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuanshanbao.dsp.common.redis.base.RedisService;

@Service
public class TimesLimitServiceImpl implements TimesLimitService {

	private static final int EXPIRE_TIME = 60 * 60 * 24;

	@Resource
	private RedisService redisService;

	@Override
	public Integer getTimesLimit(String type, String key) {
		Object times = redisService.get(type + TimesLimitConstants.TIMES_SPLIT + key);
		if (times != null) {
			try {
				return new Integer((String) times.toString());
			} catch (NumberFormatException e) {
			}
		}
		return null;
	}

	@Override
	public void updateTimesLimit(String type, String key, int newTimes) {
		redisService.set(type + TimesLimitConstants.TIMES_SPLIT + key, EXPIRE_TIME, String.valueOf(newTimes));
	}

	@Override
	public TimesLimitModel getTimesLimitModel(String type, String key) {
		TimesLimitModel model = new TimesLimitModel(type, key, this);
		model.setTimes(getTimesLimit(type, key));
		return model;
	}

}
