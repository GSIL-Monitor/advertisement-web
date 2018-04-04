package com.yuanshanbao.ad.common.redis.base;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.yuanshanbao.common.util.LoggerUtil;

public class BaseRedis {
	/** 使用threadLocal避免释放的时候传递jedis对象 */
	private static ThreadLocal<Jedis> jedisLocal = new ThreadLocal<Jedis>();

	private volatile JedisPool redisPool;

	private String host;

	private String password;

	private int port;

	private int database;// 连接sentinel的密码

	private int timeout;// 最长有效等待时间

	private JedisPoolConfig config;

	/**
	 * spring注入的时候立即执行初始化
	 */
	public void initPool() {
		if (config == null) {
			config = new JedisPoolConfig();
			config.setMaxIdle(30);
			config.setMinIdle(10);
		}
		if (config.getMaxWaitMillis() == -1) {
			config.setMaxWaitMillis(timeout);
		}
		if (config.getMaxTotal() == JedisPoolConfig.DEFAULT_MAX_TOTAL) {
			config.setMaxTotal(1000);
		}
		if (StringUtils.isBlank(password)) {
			password = null;
		}
		if (null == redisPool) {
			redisPool = new JedisPool(config, host, port, timeout, password);
		}
		/** 添加日志用于判断 */
		String curDate = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").format(new Date());// DateUtil.formatDate(new
																						// Date(),"yyyy-MM-ddHH：mm：ss");
		LoggerUtil.info("Monitoring current master,CurrentTime:" + curDate);
	}

	/** destroy后pool将关闭，不能被使用 */
	public void destroy() {
		if (null != redisPool) {
			try {
				redisPool.destroy();
			} catch (Exception e) {
				LoggerUtil.error("exception while destroy redisSentinelPool", e);
			}
		}
	}

	/**
	 * 获取jedis
	 * 
	 * @return
	 */
	public Jedis getJedisCluster() {
		Jedis jedis = null;
		try {
			/** 去本线程中的jedis */
			jedis = jedisLocal.get();
			if (null != jedis) {
				try {
					// 取出来后执行ping检查下是否依然存活
					if (jedis.isConnected()) {
						return jedis;
					}
				} catch (Exception e) {
					releaseBrokenJedis();
				}
			}
			if (null == redisPool) {
				this.initPool();
			}
			jedis = redisPool.getResource();
			jedisLocal.set(jedis);// 设置本线程中的jedis
		} catch (Exception e) {
			releaseBrokenJedis();
			if (null != jedisLocal.get()) {
				jedisLocal.remove();
			}
			LoggerUtil.error("Could not get a resource from the pool, pls check the host and port settings", e);
		}
		return jedis;
	}

	/**
	 * 释放
	 */
	public void releaseJedis() {
		Jedis jedis = jedisLocal.get();
		if (null != jedis && null != redisPool) {
			if (jedis.isConnected()) {
				redisPool.returnResource(jedis);
			}
		}
		jedisLocal.remove();
	}

	/**
	 * 释放损坏资源
	 */
	public void releaseBrokenJedis() {
		Jedis jedis = jedisLocal.get();
		if (null != jedis && null != redisPool) {
			redisPool.returnBrokenResource(jedis);
		}
		jedisLocal.remove();
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setConfig(JedisPoolConfig config) {
		this.config = config;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}

	public JedisPoolConfig getConfig() {
		return config;
	}

	public void setDatabase(int database) {
		this.database = database;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getDatabase() {
		return database;
	}
}
