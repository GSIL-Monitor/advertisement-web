package com.yuanshanbao.ms.cache.pool;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class ShardedJedisPoolProxy implements InitializingBean {
	private static final Logger logger = Logger.getLogger("jedispool");

	private static final int DEFAULT_PORT = 6379;

	private String cacheName;

	private String serverListStr;

	private String password;

	private JedisPoolConfig jedisPoolConfig;

	private ShardedJedisPool shardedJedisPool;

	private static final String REDIS_AUTH = "Hn@+wwwtxtgrabber";

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		logger.info("initializing cache pool->" + cacheName + "...");

		if (jedisPoolConfig == null) {
			logger.error(cacheName + "'s config cannot be null!");
		}

		if (serverListStr != null) {
			logger.info("server list->" + serverListStr);
			String[] serverList = serverListStr.split(";");
			if (serverList.length > 0) {
				List<JedisShardInfo> infoList = new ArrayList<JedisShardInfo>();
				for (String server : serverList) {
					String[] tmp = server.split(":");
					if (tmp.length == 2) {
						JedisShardInfo jedisShardInfo = new JedisShardInfo(tmp[0], Integer.valueOf(tmp[1]));
						if (StringUtils.isNotBlank(password)) {
							jedisShardInfo.setPassword(password);
						}
						infoList.add(jedisShardInfo);
					} else {
						JedisShardInfo jedisShardInfo = new JedisShardInfo(tmp[0], DEFAULT_PORT);
						if (StringUtils.isNotBlank(password)) {
							jedisShardInfo.setPassword(password);
						}
						infoList.add(jedisShardInfo);
					}
				}

				if (infoList.size() > 0) {
					shardedJedisPool = new ShardedJedisPool(jedisPoolConfig, infoList);
				} else {
					logger.error(cacheName + "'s available server list is empty!");
				}
			}
		} else {
			logger.error(cacheName + "'s server list is null!");
		}
	}

	public ShardedJedis getJedis() {
		if (shardedJedisPool != null) {
			return shardedJedisPool.getResource();
		} else {
			logger.error(cacheName + " initialize failed!");
			return null;
		}
	}

	public void returnResource(ShardedJedis jedis) {
		if (shardedJedisPool != null) {
			shardedJedisPool.returnResource(jedis);
		} else {
			logger.error(cacheName + " initialize failed!");
		}
	}

	public String getCacheName() {
		return cacheName;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	public String getServerListStr() {
		return serverListStr;
	}

	public void setServerListStr(String serverListStr) {
		this.serverListStr = serverListStr;
	}

	public JedisPoolConfig getJedisPoolConfig() {
		return jedisPoolConfig;
	}

	public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
		this.jedisPoolConfig = jedisPoolConfig;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
