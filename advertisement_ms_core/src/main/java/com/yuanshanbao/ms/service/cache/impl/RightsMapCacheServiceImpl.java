package com.yuanshanbao.ms.service.cache.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import com.yuanshanbao.common.util.CommonUtil;
import com.yuanshanbao.ad.common.redis.base.AbstractRedis;
import com.yuanshanbao.ad.common.redis.base.JedisWay;
import com.yuanshanbao.ms.service.cache.RightsMapCacheService;
import com.yuanshanbao.ms.utils.SerializeUtil;

@Service
public class RightsMapCacheServiceImpl extends AbstractRedis implements RightsMapCacheService {
	@Override
	@JedisWay
	public Object query() {
		Jedis jedis = getJedis();
		byte[] bytes = jedis.get(generateKey().getBytes());
		if (bytes != null) {
			return SerializeUtil.unserialize(bytes);
		}
		return null;
	}

	@Override
	@JedisWay
	public void insert(Serializable t) {
		if (t == null) {
			return;
		}
		Jedis jedis = getJedis();
		Transaction tx = jedis.multi();
		byte[] bytes = SerializeUtil.serialize(t);

		tx.set(generateKey().getBytes(), bytes);
		tx.exec();
	}

	@Override
	@JedisWay
	public void update(Serializable t) {
		if (t == null) {
			return;
		}
		Jedis jedis = getJedis();
		Transaction tx = jedis.multi();
		byte[] bytes = SerializeUtil.serialize(t);

		tx.set(generateKey().getBytes(), bytes);
		tx.exec();
	}

	@Override
	@JedisWay
	public void delete() {
		Jedis jedis = getJedis();

		Transaction tx = jedis.multi();

		tx.del(generateKey().getBytes());

		tx.exec();
	}

	private String generateKey() {
		return CommonUtil.getCacheKey("SYS_RIGHTS_MAP");
	}
}
