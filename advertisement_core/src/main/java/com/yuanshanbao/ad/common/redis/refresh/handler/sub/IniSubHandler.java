package com.yuanshanbao.ad.common.redis.refresh.handler.sub;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.ad.cache.IniCache;

@Service
public class IniSubHandler extends RedisSubscribeHandler {

	@Autowired
	private IniCache iniCache;

	@Override
	public void handle(String message) {
		if (StringUtils.isBlank(message) || iniCache == null) {
			LoggerUtil.warn("subscribe message or iniCache is blank,message:" + message + ",iniCache:" + iniCache);
			return;
		}
		LoggerUtil.info("redis自动刷新了！！！！！！！！！！！！！！");
		iniCache.refresh();
	}

}
