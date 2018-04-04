package com.yuanshanbao.ad.common.redis.refresh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import redis.clients.jedis.JedisPubSub;

import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.ad.common.redis.refresh.handler.sub.RedisSubscribeHandler;

public class RedisPubListener extends JedisPubSub {

	private static Map<String, RedisSubscribeHandler> handlerMap = new ConcurrentHashMap<>();

	@Override
	public void onMessage(String channel, String message) {
		try {
			LoggerUtil.info("onMessage channel=" + channel + ",message=" + message);
			RedisSubscribeHandler handler = handlerMap.get(channel);
			if (handler != null)
				handler.handle(message);
			else
				LoggerUtil.info("handler is null, onMessage channel=" + channel + ",message=" + message);
		} catch (Throwable e) {
			LoggerUtil.error("onMessage error:" + e.getMessage(), e);
		}
	}

	public synchronized void registerHandler(String channel, RedisSubscribeHandler handler) {
		LoggerUtil.info("onMessage registerHandler:" + " channel=" + channel + ", handler=" + handler.getClass());
		handlerMap.put(channel, handler);
	}

	@Override
	public void onPMessage(String s, String s1, String s2) {

	}

	@Override
	public void onSubscribe(String s, int i) {

	}

	@Override
	public void onUnsubscribe(String s, int i) {

	}

	@Override
	public void onPUnsubscribe(String s, int i) {

	}

	@Override
	public void onPSubscribe(String s, int i) {

	}
}
