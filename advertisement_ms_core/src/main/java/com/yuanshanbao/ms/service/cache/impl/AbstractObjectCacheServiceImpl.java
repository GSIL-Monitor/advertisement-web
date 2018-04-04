package com.yuanshanbao.ms.service.cache.impl;

import javax.annotation.Resource;

import redis.clients.jedis.ShardedJedis;

import com.yuanshanbao.ms.cache.pool.ShardedJedisPoolProxy;

public abstract class AbstractObjectCacheServiceImpl {
	
	@Resource(name = "sessionShardedJedisPoolProxy")
	protected ShardedJedisPoolProxy sessionShardedJedisPoolProxy;
	
	@Resource(name = "iconShardedJedisPoolProxy")
	protected ShardedJedisPoolProxy iconShardedJedisPoolProxy;
	
	@Resource(name = "rightsShardedJedisPoolProxy")
	protected ShardedJedisPoolProxy rightsShardedJedisPoolProxy;
	
	@Resource(name = "userFailedLoginShardedJedisPoolProxy")
	protected ShardedJedisPoolProxy userFailedLoginShardedJedisPoolProxy;
	
	protected ShardedJedis getSessionJedis() {
		return sessionShardedJedisPoolProxy.getJedis();
	}
	
	protected void returnSessionJedis(ShardedJedis jedis) {
		sessionShardedJedisPoolProxy.returnResource(jedis);
	}
	
	protected ShardedJedis getIconJedis() {
		return iconShardedJedisPoolProxy.getJedis();
	}
	
	protected void returnIconJedis(ShardedJedis jedis) {
		iconShardedJedisPoolProxy.returnResource(jedis);
	}
	
	protected ShardedJedis getRightsJedis() {
		return rightsShardedJedisPoolProxy.getJedis();
	}
	
	protected void returnRightsJedis(ShardedJedis jedis) {
		rightsShardedJedisPoolProxy.returnResource(jedis);
	}
	
	protected ShardedJedis getUserFailedLoginJedis() {
		return userFailedLoginShardedJedisPoolProxy.getJedis();
	}
	
	protected void returnUserFailedLoginJedis(ShardedJedis jedis) {
		userFailedLoginShardedJedisPoolProxy.returnResource(jedis);
	}
}
