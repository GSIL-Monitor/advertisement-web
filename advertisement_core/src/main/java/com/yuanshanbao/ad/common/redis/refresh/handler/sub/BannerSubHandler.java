package com.yuanshanbao.ad.common.redis.refresh.handler.sub;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.ad.cache.BannerCache;

/**
 * Created by Ynght on 2016/11/30.
 */
@Service
public class BannerSubHandler extends RedisSubscribeHandler {

	@Autowired
	private BannerCache bannerCache;

	@Override
	public void handle(String message) {
		if (StringUtils.isBlank(message) || bannerCache == null) {
			LoggerUtil.warn("subscribe message or bannerCache is blank,message:" + message + ",bannerCache:"
					+ bannerCache);
			return;
		}
		bannerCache.refresh();
	}
}
