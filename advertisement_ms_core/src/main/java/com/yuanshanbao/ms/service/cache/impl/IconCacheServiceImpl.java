package com.yuanshanbao.ms.service.cache.impl;

import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;

import com.yuanshanbao.ms.service.cache.IconCacheService;
import com.yuanshanbao.ms.utils.CacheKeyUtils;

@Service
public class IconCacheServiceImpl extends AbstractBytesServiceImpl implements IconCacheService {
	@Override
	public byte[] query(String iconName) {
		// TODO Auto-generated method stub
		String key = CacheKeyUtils.getIconnameKey(iconName);
		ShardedJedis jedis = getIconJedis();
		byte[] result = jedis.get(key.getBytes());
		returnIconJedis(jedis);
		return result;
	}

	@Override
	public void insert(String iconName, byte[] bytes) {
		// TODO Auto-generated method stub
		String key = CacheKeyUtils.getIconnameKey(iconName);
		ShardedJedis jedis = getIconJedis();
		jedis.set(key.getBytes(), bytes);
		returnIconJedis(jedis);
	}

	@Override
	public void update(String iconName, byte[] bytes) {
		// TODO Auto-generated method stub
		String key = CacheKeyUtils.getIconnameKey(iconName);
		ShardedJedis jedis = getIconJedis();
		jedis.set(key.getBytes(), bytes);
		returnIconJedis(jedis);
	}

	@Override
	public void delete(String iconName) {
		// TODO Auto-generated method stub
		String key = CacheKeyUtils.getIconnameKey(iconName);
		ShardedJedis jedis = getIconJedis();
		jedis.del(key.getBytes());
		returnIconJedis(jedis);
	}
}