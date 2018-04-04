package com.yuanshanbao.ms.service.cache.impl;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;

import com.yuanshanbao.ms.model.cache.UserLoginFailInfo;
import com.yuanshanbao.ms.service.cache.UserLoginFailCacheService;
import com.yuanshanbao.ms.utils.CacheKeyUtils;

@Service
public class UserLoginFailCacheServiceImpl extends AbstractJsonCacheServiceImpl<UserLoginFailInfo> implements UserLoginFailCacheService {

	@Override
	public void delete(String username) {
		String key = CacheKeyUtils.getLoginFailCountKey(username);
		ShardedJedis jedis = getUserFailedLoginJedis();
		jedis.del(key.getBytes());
		returnUserFailedLoginJedis(jedis);
	}

	@Override
	public void insert(String username, UserLoginFailInfo t) {
		JSONObject jobj = JSONObject.fromObject(t);
		String key = CacheKeyUtils.getLoginFailCountKey(username);
		ShardedJedis jedis = getUserFailedLoginJedis();
		jedis.set(key, jobj.toString());
		jedis.expire(key, 60*60*24);
		returnUserFailedLoginJedis(jedis);
	}

	@Override
	public void update(String username, UserLoginFailInfo t) {
		JSONObject jobj = JSONObject.fromObject(t);
		String key = CacheKeyUtils.getLoginFailCountKey(username);
		ShardedJedis jedis = getUserFailedLoginJedis();
		jedis.set(key, jobj.toString());
		returnUserFailedLoginJedis(jedis);
	}

	@Override
	public UserLoginFailInfo query(String username) {
		String key = CacheKeyUtils.getLoginFailCountKey(username);
		ShardedJedis jedis = getUserFailedLoginJedis();
		String json = jedis.get(key);
		returnUserFailedLoginJedis(jedis);
		UserLoginFailInfo ulfi = null;
		if (json != null) {
			ulfi = (UserLoginFailInfo) JSONObject.toBean(JSONObject.fromObject(json), clazz);
		}
		return ulfi;
	}
}