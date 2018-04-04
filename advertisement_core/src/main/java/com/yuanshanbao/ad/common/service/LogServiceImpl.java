package com.yuanshanbao.ad.common.service;

import java.util.Date;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.util.CommonUtil;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.ad.cache.IniCache;
import com.yuanshanbao.ad.common.constant.CommonConstant;
import com.yuanshanbao.ad.common.constant.RedisConstant;
import com.yuanshanbao.ad.common.redis.base.BaseRedis;

/**
 * @author singal
 */
@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private BaseRedis baseRedis;

	@Override
	public void monitor(Logger logger, Level level, String key, String message) {
		try {
			long now = new Date().getTime();
			// record[0] 检测时间间隔内第一次产生log的时间 record[1] 检测时间间隔内的累计log报警次数
			String keyStart = RedisConstant.PREFIX_LOG_START + key;
			String keyHits = RedisConstant.PREFIX_LOG_HITS + key;
			String start = baseRedis.getJedisCluster().get(keyStart);
			String hits = baseRedis.getJedisCluster().get(keyHits);
			long[] record = new long[2];
			if (start == null || hits.isEmpty()) {
				record = null;
			} else {
				record[0] = Long.parseLong(start);
				record[1] = Long.parseLong(hits);
			}
			// ruleArray[0] 检测时间间隔（秒）ruleArray[1] log报警允许次数上限
			String ruleStr = IniCache.getIniValue(key, "999999|1");
			String[] ruleArray = ruleStr.split(CommonConstant.COMMON_ESCAPE_STR + CommonConstant.COMMON_VERTICAL_STR);
			// 第一次记录用户行为，或者用户行为次数为零，或者 检测时间间隔外
			if (record == null || record[1] == 0 || (now - record[0] > Long.parseLong(ruleArray[0]))) {
				baseRedis.getJedisCluster().set(keyStart, String.valueOf(now));
				baseRedis.getJedisCluster().set(keyHits, String.valueOf(1));
				baseRedis.getJedisCluster().expire(keyStart, Integer.parseInt(ruleArray[0]));
				baseRedis.getJedisCluster().expire(keyHits, Integer.parseInt(ruleArray[0]));
			} else {
				record[1] = baseRedis.getJedisCluster().incr(keyHits);
				// 如果报警次数已大于允许上限，则报警
				if (record[1] >= Long.parseLong(ruleArray[1])) {
					logger.log(level, message);
					baseRedis.getJedisCluster().del(keyStart);
					baseRedis.getJedisCluster().del(keyHits);
				}
			}
		} catch (Exception e) {
			LoggerUtil.error("LogService monitor error. " + CommonUtil.mergeUnionKey(key, message), e);
		}
	}
}
