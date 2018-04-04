package com.yuanshanbao.ms.service.cache.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.yuanshanbao.ad.common.redis.base.AbstractRedis;
import com.yuanshanbao.ad.common.redis.base.JedisWay;
import com.yuanshanbao.ms.service.cache.SessionCacheService;
import com.yuanshanbao.ms.utils.SerializeUtil;

@Service
public class SessionCacheServiceImpl extends AbstractRedis implements SessionCacheService {

	private static final int SESSION_EXPIRE_TIME = 10 * 60 * 60;

	@Override
	@JedisWay
	public Object query(String sourceKey) {
		// TODO Auto-generated method stub
		Jedis jedis = getJedis();
		byte[] bytes = jedis.get(sourceKey.getBytes());
		if (bytes != null) {
			jedis.expire(sourceKey.getBytes(), SESSION_EXPIRE_TIME);
			return SerializeUtil.unserialize(bytes);
		}
		return null;
	}

	@Override
	@JedisWay
	public void insert(String sourceKey, Serializable t) {
		// TODO Auto-generated method stub
		Jedis jedis = getJedis();
		if (t == null) {
			return;
		}

		byte[] bytes = SerializeUtil.serialize(t);

		jedis.set(sourceKey.getBytes(), bytes);
		jedis.expire(sourceKey.getBytes(), SESSION_EXPIRE_TIME);
	}

	@Override
	@JedisWay
	public void update(String sourceKey, Serializable t) {
		Jedis jedis = getJedis();
		if (t == null) {
			return;
		}

		byte[] bytes = SerializeUtil.serialize(t);

		jedis.set(sourceKey.getBytes(), bytes);

		jedis.expire(sourceKey.getBytes(), SESSION_EXPIRE_TIME);
	}

	@Override
	@JedisWay
	public void delete(String sourceKey) {
		// TODO Auto-generated method stub
		Jedis jedis = getJedis();
		jedis.del(sourceKey.getBytes());
	}

	@Override
	public void expire(String sid, int seconds) {
		Jedis jedis = getJedis();
		jedis.expire(sid.getBytes(), seconds);
	}

	public long ttl(String sid) {
		Jedis jedis = getJedis();
		long ttl = jedis.ttl(sid.getBytes());
		return ttl;
	}
}
