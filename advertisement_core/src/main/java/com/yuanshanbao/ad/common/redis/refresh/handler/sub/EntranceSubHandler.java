package com.yuanshanbao.ad.common.redis.refresh.handler.sub;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.ad.cache.EntranceCache;

/**
 * Created by Ynght on 2016/12/9.
 */
@Service
public class EntranceSubHandler extends RedisSubscribeHandler {

	@Autowired
	private EntranceCache entranceCache;

	@Override
	public void handle(String message) {
		if (StringUtils.isBlank(message) || entranceCache == null) {
			LoggerUtil.warn("subscribe message or entranceCache is blank,message:" + message + ",entranceCache:"
					+ entranceCache);
			return;
		}
		entranceCache.refresh();
	}
}
