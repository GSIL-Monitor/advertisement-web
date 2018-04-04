package com.yuanshanbao.ad.common.redis.refresh.handler.pub;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.ad.common.constant.ResultConstant;
import com.yuanshanbao.ad.common.redis.base.RedisService;

@Repository
public class RedisPublishHandler {
	@Autowired
	private RedisService redisService;

	public long publish(String channel, String message) {
		if (StringUtils.isBlank(message) || StringUtils.isBlank(channel)) {
			LoggerUtil.warn("refresh param is blank");
			return ResultConstant.ERROR;
		}
		try {
			Long result = redisService.publish(channel, message);
			return result;
		} catch (Exception e) {
			LoggerUtil.error("error in redisPublish!", e);
			return ResultConstant.ERROR;
		}
	}
}
