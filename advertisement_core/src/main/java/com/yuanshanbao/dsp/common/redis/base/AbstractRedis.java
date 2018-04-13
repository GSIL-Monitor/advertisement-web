package com.yuanshanbao.dsp.common.redis.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import redis.clients.jedis.Jedis;

/**
 * 所有redis业务操作类继承此类 modified by dujiepeng
 * 为了避免修改业务代码中已有的调用模式，对现有redis封装的不同客户端service方式进行了保留，只调整内部实现方式。
 * 
 * @author KevinLiao
 *
 */
public abstract class AbstractRedis {
	private BaseRedis baseRedis;

	protected Object self;

	public BaseRedis getBaseRedis() {
		return baseRedis;
	}

	@Autowired
	public void setBaseRedis(@Qualifier("baseRedis") BaseRedis baseRedis) {
		this.baseRedis = baseRedis;
	}

	/**
	 * 获取操作的jedis实例
	 * 
	 * @return jedis
	 */
	public Jedis getJedis() {
		return baseRedis.getJedisCluster();
	}

	/**
	 * 释放
	 */
	public void releaseJedis() {
		baseRedis.releaseJedis();
	}

	/**
	 * 释放损坏资源
	 */
	public void releaseBrokenJedis() {
		baseRedis.releaseBrokenJedis();
	}
}