package com.yuanshanbao.dsp.partner.agent;

import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.information.model.Information;

public abstract class AbstractAgentNotifyHandler implements IAgentNotifyHandler {

	protected RedisService redisService;

	public void setRedisCacheService(RedisService redisService) {
		this.redisService = redisService;
	}

	public void notifyAgent(Information currentInformation) {

	}

	@Override
	public void notifyAgent(String channel, String token) {

	}

	public boolean checkRepeatTokenInCache(String mobile, String token) {
		if (token.endsWith("_send_again")) {
			return true;
		}
		// redisService.setWithEnv(RedisConstant.getAgentNotifyHandlerKey(mobile),
		// token + "_send_again", 60L);
		return false;
	}
}
