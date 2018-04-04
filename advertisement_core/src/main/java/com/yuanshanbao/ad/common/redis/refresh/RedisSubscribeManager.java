package com.yuanshanbao.ad.common.redis.refresh;

import java.beans.Introspector;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.NetUtil;
import com.yuanshanbao.common.web.SpringContextHolder;
import com.yuanshanbao.ad.cache.IniCache;
import com.yuanshanbao.ad.common.constant.IniConstant;
import com.yuanshanbao.ad.common.enums.RedisPubEnum;
import com.yuanshanbao.ad.common.redis.base.RedisService;

public class RedisSubscribeManager {

	@Autowired
	private RedisService redisService;

	private boolean ifOnlineConfirm = false;

	private void init() {
		LoggerUtil.info("[subscribe] enter RedisSubscribeManager init");

		ifOnlineConfirm = checkIfGameOnlineConfirmMachine();

		final RedisPubListener redisPubListener = new RedisPubListener();

		for (RedisPubEnum pub : RedisPubEnum.values()) {
			try {
				redisPubListener.registerHandler(ifOnlineConfirm ? pub.getChannelConfirm() : pub.getChannel(),
						SpringContextHolder.getBean(Introspector.decapitalize(pub.getHandlerClass().getSimpleName())));
			} catch (Exception e) {
				LoggerUtil.error("RedisSubscribeManager init error.", e);
			}
		}

		new Thread(() -> {
			try {
				List<String> channels = new ArrayList<>();
				for (RedisPubEnum pub : RedisPubEnum.values()) {
					channels.add(ifOnlineConfirm ? pub.getChannelConfirm() : pub.getChannel());
				}
				redisService.subscribe(redisPubListener, channels.toArray(new String[channels.size()]));
				LoggerUtil.info("refresh redis pubSub initialized.");
			} catch (Throwable e) {
				LoggerUtil.error("refresh initialize redis pubSub error.", e);
			}
		}, "redisRefresh_thread").start();
	}

	private boolean checkIfGameOnlineConfirmMachine() {
		String ConfirmIps = IniCache.getIniValue(IniConstant.REDIS_SUB_AND_PUB_ONLINE_CONFIRM_IP, "10.120.118.85");
		return NetUtil.containsIp(ConfirmIps);
	}
}
