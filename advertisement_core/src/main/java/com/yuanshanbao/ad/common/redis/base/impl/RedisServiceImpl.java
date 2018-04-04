package com.yuanshanbao.ad.common.redis.base.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import com.yuanshanbao.common.util.CommonUtil;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.SerializeUtil;
import com.yuanshanbao.ad.common.constant.RedisConstant;
import com.yuanshanbao.ad.common.redis.base.BaseRedis;
import com.yuanshanbao.ad.common.redis.base.JedisWay;
import com.yuanshanbao.ad.common.redis.base.RedisService;

//redis基础操作类，业务组合操作请使用RedisUtilServiceImpl
@Service
public class RedisServiceImpl implements RedisService {

	@Autowired
	private BaseRedis baseRedis;

	@Override
	@JedisWay
	public Long hessian2ZRemRangeByRank(String key, int start, int end) {
		try {
			key = CommonUtil.getCacheKey(key);
			byte[] keyBytes = key.getBytes(RedisConstant.REDIS_DEFAULT_CHARSET);
			Long result = getJedis().zremrangeByRank(keyBytes, start, end);
			return result == null ? 0L : result;
		} catch (Exception e) {
			LoggerUtil.error("Error hessian2ZRemRangeByRank, key:" + key, e);
			return null;
		}
	}

	@Override
	@JedisWay
	public Boolean hessian2ZAddSet(String key, Long timeLine, Object value) {
		try {
			key = CommonUtil.getCacheKey(key);
			byte[] keyBytes = key.getBytes(RedisConstant.REDIS_DEFAULT_CHARSET);
			byte[] valueBytes = SerializeUtil.hessian2Serialize(value);

			Long numMembers = getJedis().zadd(keyBytes, timeLine, valueBytes);
			return numMembers.equals(RedisConstant.REDIS_DEFAULT_RESULT);
		} catch (Exception e) {
			LoggerUtil.error("Error hessian2ZAddSet, key:" + key, e);
			return false;
		}
	}

	@Override
	@JedisWay
	public <T> T hessian2ZRangeByScoreGet(String key, Long min, Long max) {
		try {
			key = CommonUtil.getCacheKey(key);
			List<T> resultList = new ArrayList<>();
			byte[] keyBytes = key.getBytes(RedisConstant.REDIS_DEFAULT_CHARSET);

			Set docs = getJedis().zrangeByScore(keyBytes, min, max);

			for (Object valueBytes : docs) {
				resultList.add((T) SerializeUtil
						.hessian2Deserialize((byte[]) valueBytes));
			}
			return (T) resultList;
		} catch (Exception e) {
			LoggerUtil.error("Error hessian2ZRangeByScoreGet, key:" + key, e);
			return null;
		}
	}

	@Override
	@JedisWay
	public <T> T hessian2ZRangeByScoreGet(String key, Long min, Long max,
			int offset, int limit) {
		try {
			key = CommonUtil.getCacheKey(key);
			List<T> resultList = new ArrayList<>();
			byte[] keyBytes = key.getBytes(RedisConstant.REDIS_DEFAULT_CHARSET);

			Set docs = getJedis().zrangeByScore(keyBytes, min, max, offset,
					limit);

			for (Object valueBytes : docs) {
				resultList.add((T) SerializeUtil
						.hessian2Deserialize((byte[]) valueBytes));
			}
			return (T) resultList;
		} catch (Exception e) {
			LoggerUtil.error("Error sadd, hessian2ZRangeByScoreGet:" + key, e);
			return null;
		}
	}

	@Override
	@JedisWay
	public <T> T hessian2ZRevRangeByScoreGet(String key, Long min, Long max) {
		try {
			key = CommonUtil.getCacheKey(key);
			List<T> resultList = new ArrayList<>();
			byte[] keyBytes = key.getBytes(RedisConstant.REDIS_DEFAULT_CHARSET);

			Set docs = getJedis().zrevrangeByScore(keyBytes, min, max);

			for (Object valueBytes : docs) {
				resultList.add((T) SerializeUtil
						.hessian2Deserialize((byte[]) valueBytes));
			}
			return (T) resultList;
		} catch (Exception e) {
			LoggerUtil
					.error("Error hessian2ZRevRangeByScoreGet, key:" + key, e);
			return null;
		}
	}

	@Override
	@JedisWay
	public <T> T hessian2ZRevRangeByScoreGet(String key, Long min, Long max,
			int x, int y) {
		try {
			key = CommonUtil.getCacheKey(key);
			List<T> resultList = new ArrayList<>();
			byte[] keyBytes = key.getBytes(RedisConstant.REDIS_DEFAULT_CHARSET);

			Set docs = getJedis().zrevrangeByScore(keyBytes, min, max, x, y);

			for (Object valueBytes : docs) {
				resultList.add((T) SerializeUtil
						.hessian2Deserialize((byte[]) valueBytes));
			}
			return (T) resultList;
		} catch (Exception e) {
			LoggerUtil
					.error("Error hessian2ZRevRangeByScoreGet, key:" + key, e);
			return null;
		}
	}

	@Override
	@JedisWay
	public <T> T hessian2Get(String key) {
		try {
			key = CommonUtil.getCacheKey(key);
			byte[] keyBytes = key.getBytes(RedisConstant.REDIS_DEFAULT_CHARSET);
			byte[] valueBytes = getJedis().get(keyBytes);
			return (T) SerializeUtil.hessian2Deserialize(valueBytes);
		} catch (Exception e) {
			LoggerUtil.error("Error hessian2Get, key:" + key, e);
			return null;
		}
	}

	@Override
	@JedisWay
	public Boolean hessian2Set(String key, Object value) {
		try {
			key = CommonUtil.getCacheKey(key);
			byte[] keyBytes = key.getBytes(RedisConstant.REDIS_DEFAULT_CHARSET);
			byte[] valueBytes = SerializeUtil.hessian2Serialize(value);
			Boolean result = RedisConstant.REDIS_DEFAULT_OK
					.equalsIgnoreCase(getJedis().set(keyBytes, valueBytes));
			return result;
		} catch (Exception e) {
			LoggerUtil.error("Error hessian2Set, key:" + key, e);
			return null;
		}
	}

	@Override
	@JedisWay
	public Boolean hset(String key, String field, Object value) {
		try {
			key = CommonUtil.getCacheKey(key);
			byte[] keyBytes = key.getBytes(RedisConstant.REDIS_DEFAULT_CHARSET);
			byte[] fieldBytes = SerializeUtil.hessian2Serialize(field);
			byte[] valueBytes = SerializeUtil.hessian2Serialize(value);
			Long result = getJedis().hset(keyBytes, fieldBytes, valueBytes);
			return result.equals(RedisConstant.REDIS_DEFAULT_RESULT);
		} catch (Exception e) {
			LoggerUtil.error("Error hessian2Set, key:" + key, e);
			return null;
		}
	}

	@Override
	@JedisWay
	public Boolean hessian2SetEx(String key, int seconds, Object value) {
		try {
			key = CommonUtil.getCacheKey(key);
			byte[] keyBytes = key.getBytes(RedisConstant.REDIS_DEFAULT_CHARSET);
			byte[] valueBytes = SerializeUtil.hessian2Serialize(value);
			Boolean result = RedisConstant.REDIS_DEFAULT_OK
					.equalsIgnoreCase(getJedis().setex(keyBytes, seconds,
							valueBytes));
			return result;
		} catch (Exception e) {
			LoggerUtil.error("Error hessian2SetEx, key:" + key, e);
			return false;
		}
	}

	@Override
	@JedisWay
	public Long hessian2SetNx(String key, Object value) {
		try {
			key = CommonUtil.getCacheKey(key);
			byte[] keyBytes = key.getBytes(RedisConstant.REDIS_DEFAULT_CHARSET);
			byte[] valueBytes = SerializeUtil.hessian2Serialize(value);
			Long result = getJedis().setnx(keyBytes, valueBytes);
			return result == null ? 0L : result;
		} catch (Exception e) {
			LoggerUtil.error("Error hessian2SetNx, key:" + key, e);
			return 0L;
		}
	}

	@Override
	@JedisWay
	public <T, S> Long hessian2HSet(String key, T field, S value) {
		try {
			key = CommonUtil.getCacheKey(key);
			byte[] keyBytes = key.getBytes(RedisConstant.REDIS_DEFAULT_CHARSET);
			byte[] fieldBytes = SerializeUtil.hessian2Serialize(field);
			byte[] valueBytes = SerializeUtil.hessian2Serialize(value);
			Long result = getJedis().hset(keyBytes, fieldBytes, valueBytes);
			return result == null ? 0L : result;
		} catch (Exception e) {
			LoggerUtil.error("Error hessian2HSet, key:" + key, e);
			return 0L;
		}
	}

	@Override
	@JedisWay
	public <T, S> Boolean hessian2Hmset(String key, Map<T, S> fieldValueMap) {
		try {
			key = CommonUtil.getCacheKey(key);
			byte[] keyBytes = key.getBytes(RedisConstant.REDIS_DEFAULT_CHARSET);
			Map<byte[], byte[]> valueMap = new HashMap<byte[], byte[]>();
			for (Map.Entry<T, S> entry : fieldValueMap.entrySet()) {
				T entryKey = entry.getKey();
				S entryOb = entry.getValue();
				byte[] fieldBytes = SerializeUtil.hessian2Serialize(entryKey);
				byte[] valueBytes = SerializeUtil.hessian2Serialize(entryOb);
				valueMap.put(fieldBytes, valueBytes);
			}
			Boolean result = RedisConstant.REDIS_DEFAULT_OK
					.equalsIgnoreCase(getJedis().hmset(keyBytes, valueMap));
			return result;
		} catch (Exception e) {
			LoggerUtil.error("Error hessian2Hmset, key:" + key, e);
			return false;
		}
	}

	@Override
	@JedisWay
	public <T, S> T hessian2HGet(String key, S field) {
		try {
			key = CommonUtil.getCacheKey(key);
			byte[] keyBytes = key.getBytes(RedisConstant.REDIS_DEFAULT_CHARSET);
			byte[] fieldBytes = SerializeUtil.hessian2Serialize(field);
			byte[] valueBytes = getJedis().hget(keyBytes, fieldBytes);
			return (T) SerializeUtil.hessian2Deserialize(valueBytes);
		} catch (Exception e) {
			LoggerUtil.error("Error hessian2HGet, key:" + key, e);
			return null;
		}
	}

	@Override
	@JedisWay
	public <S, T> Map<S, T> hessian2HGetAll(String key) {
		try {
			key = CommonUtil.getCacheKey(key);
			byte[] keyBytes = key.getBytes(RedisConstant.REDIS_DEFAULT_CHARSET);
			Map<byte[], byte[]> fieldValueBytes = getJedis().hgetAll(keyBytes);
			if (fieldValueBytes.size() == 0 || fieldValueBytes == null) {
				return null;
			}
			Map<S, T> resultMap = new HashMap<>();
			for (Map.Entry<byte[], byte[]> entry : fieldValueBytes.entrySet()) {
				byte[] entryKey = entry.getKey();
				byte[] entryValue = entry.getValue();
				resultMap.put((S) SerializeUtil.hessian2Deserialize(entryKey),
						(T) SerializeUtil.hessian2Deserialize(entryValue));
			}
			return resultMap;
		} catch (Exception e) {
			LoggerUtil.error("Error hessian2HGetAll, key:" + key, e);
			return null;
		}
	}

	@Override
	@JedisWay
	public Long incr(String key) {
		try {
			key = CommonUtil.getCacheKey(key);
			byte[] keyBytes = key.getBytes(RedisConstant.REDIS_DEFAULT_CHARSET);
			Long result = getJedis().incr(keyBytes);
			return result == null ? 0L : result;
		} catch (Exception e) {
			LoggerUtil.error("Error incr, key:" + key, e);
			return 0L;
		}
	}

	@Override
	@JedisWay
	public Long increBy(String key, int increaseCount) {

		try {
			key = CommonUtil.getCacheKey(key);
			Long result = getJedis().incrBy(key, increaseCount);
			return result == null ? 0L : result;
		} catch (Exception e) {
			LoggerUtil.error("Error incrBy, key:" + key + " increaseCount:"
					+ increaseCount, e);
			return null;
		}
	}

	@Override
	@JedisWay
	public Long hdel(String key, String field) {
		try {
			key = CommonUtil.getCacheKey(key);
			byte[] keyBytes = key.getBytes(RedisConstant.REDIS_DEFAULT_CHARSET);
			byte[] fieldBytes = field
					.getBytes(RedisConstant.REDIS_DEFAULT_CHARSET);
			Long result = 0L;
			if (getJedis().exists(keyBytes)) {
				result = getJedis().hdel(keyBytes, fieldBytes);
			}
			return result == null ? 0L : result;
		} catch (Exception e) {
			LoggerUtil.error("Error hdel, key:" + key, e);
			return 0L;
		}
	}

	@Override
	@JedisWay
	public Boolean isKeyByteExist(String key) {
		try {
			key = CommonUtil.getCacheKey(key);
			byte[] keyBytes = key.getBytes(RedisConstant.REDIS_DEFAULT_CHARSET);
			return getJedis().exists(keyBytes);
		} catch (Exception e) {
			LoggerUtil.error("Error isKeyByteExist, key:" + key, e);
			return false;
		}
	}

	@Override
	@JedisWay
	public Boolean isKeyExist(String key) {
		try {
			key = CommonUtil.getCacheKey(key);
			return getJedis().exists(key);
		} catch (Exception e) {
			LoggerUtil.error("Error isKeyExist, key:" + key, e);
			return false;
		}
	}

	@Override
	@JedisWay
	public Long del(String key) {
		try {
			key = CommonUtil.getCacheKey(key);
			byte[] keyBytes = key.getBytes(RedisConstant.REDIS_DEFAULT_CHARSET);
			Long result = 0L;
			if (getJedis().exists(keyBytes)) {
				result = getJedis().del(keyBytes);
			}
			return result == null ? 0L : result;
		} catch (Exception e) {
			LoggerUtil.error("Error del, key:" + key, e);
			return 0L;
		}
	}

	@Override
	@JedisWay
	public Long expire(String key, int seconds) {
		try {
			key = CommonUtil.getCacheKey(key);
			byte[] keyBytes = key.getBytes(RedisConstant.REDIS_DEFAULT_CHARSET);
			Long result = getJedis().expire(keyBytes, seconds);
			return result == null ? 0L : result;
		} catch (Exception e) {
			LoggerUtil.error("Error expire, key:" + key, e);
			return 0L;
		}
	}

	@Override
	@JedisWay
	public Long ttl(String key) {
		try {
			key = CommonUtil.getCacheKey(key);
			byte[] keyBytes = key.getBytes(RedisConstant.REDIS_DEFAULT_CHARSET);
			Long result = getJedis().ttl(keyBytes);
			return result == null ? 0L : result;
		} catch (Exception e) {
			LoggerUtil.error("Error ttl, key:" + key, e);
			return 0L;
		}
	}

	// list相关操作
	@Override
	@JedisWay
	public Long hessian2RPush(String key, Object value) {
		try {
			key = CommonUtil.getCacheKey(key);
			byte[] keyBytes = key.getBytes(RedisConstant.REDIS_DEFAULT_CHARSET);
			byte[] valueBytes = SerializeUtil.hessian2Serialize(value);
			Long length = getJedis().rpush(keyBytes, valueBytes);// 返回list长度
			return length;
		} catch (Exception e) {
			LoggerUtil.error("Error hessian2RPush, key:" + key, e);
			return 0L;
		}
	}

	@Override
	@JedisWay
	public Long hessian2LPush(String key, Object value) {
		try {
			key = CommonUtil.getCacheKey(key);
			byte[] keyBytes = key.getBytes(RedisConstant.REDIS_DEFAULT_CHARSET);
			byte[] valueBytes = SerializeUtil.hessian2Serialize(value);
			Long length = getJedis().lpush(keyBytes, valueBytes);// 返回list长度
			return length;
		} catch (Exception e) {
			LoggerUtil.error("Error hessian2LPush, key:" + key, e);
			return 0L;
		}
	}

	@Override
	@JedisWay
	public Long hessian2LRem(String key, long count, Object value) {
		try {
			key = CommonUtil.getCacheKey(key);
			byte[] keyBytes = key.getBytes(RedisConstant.REDIS_DEFAULT_CHARSET);
			byte[] valueBytes = SerializeUtil.hessian2Serialize(value);
			Long result = getJedis().lrem(keyBytes, count, valueBytes);
			return result == null ? 0L : result;
		} catch (Exception e) {
			LoggerUtil.error("Error hessian2LRem, key:" + key, e);
			return 0L;
		}
	}

	@Override
	@JedisWay
	public Boolean hessian2LTrim(String key, long start, long end) {
		try {
			key = CommonUtil.getCacheKey(key);
			byte[] keyBytes = key.getBytes(RedisConstant.REDIS_DEFAULT_CHARSET);
			Boolean result = RedisConstant.REDIS_DEFAULT_OK
					.equalsIgnoreCase(getJedis().ltrim(keyBytes, start, end));
			return result;
		} catch (Exception e) {
			LoggerUtil.error("Error hessian2LTrim, key:" + key, e);
			return false;
		}
	}

	@Override
	@JedisWay
	public <T> T hessian2LPop(String key) {
		try {
			key = CommonUtil.getCacheKey(key);
			byte[] keyBytes = key.getBytes(RedisConstant.REDIS_DEFAULT_CHARSET);
			byte[] valueBytes = getJedis().lpop(keyBytes);
			return (T) SerializeUtil.hessian2Deserialize(valueBytes);
		} catch (Exception e) {
			LoggerUtil.error("Error hessian2LPop, key:" + key, e);
			return null;
		}
	}

	@Override
	@JedisWay
	public <T> List<T> hessian2LRange(String key, Long start, Long end) {
		try {
			key = CommonUtil.getCacheKey(key);
			byte[] keyBytes = key.getBytes(RedisConstant.REDIS_DEFAULT_CHARSET);
			List<byte[]> tempList = getJedis().lrange(keyBytes, start, end);
			List<T> resultList = new ArrayList<>();
			for (byte[] temp : tempList) {
				resultList.add((T) SerializeUtil.hessian2Deserialize(temp));
			}
			return resultList;
		} catch (Exception e) {
			LoggerUtil.error("Error hessian2LRange, key:" + key, e);
			return null;
		}
	}

	// SortedSet
	@Override
	@JedisWay
	public <T> List<T> hessian2ZRange(String key, Long start, Long end) {
		try {
			key = CommonUtil.getCacheKey(key);
			byte[] keyBytes = key.getBytes(RedisConstant.REDIS_DEFAULT_CHARSET);
			Set<byte[]> set = getJedis().zrange(keyBytes, start, end);// LinkedHashSet
			List<T> resultList = new ArrayList<>();
			for (byte[] temp : set) {
				resultList.add((T) SerializeUtil.hessian2Deserialize(temp));
			}
			return resultList;
		} catch (Exception e) {
			LoggerUtil.error("Error hessian2ZRange, key:" + key, e);
			return null;
		}
	}

	@Override
	@JedisWay
	public Long hessian2ZRank(String key, Integer awardNumber) {
		try {
			key = CommonUtil.getCacheKey(key);
			byte[] keyBytes = key.getBytes(RedisConstant.REDIS_DEFAULT_CHARSET);
			byte[] valueBytes = SerializeUtil.hessian2Serialize(awardNumber);
			Long rank = getJedis().zrank(keyBytes, valueBytes);
			return rank;
		} catch (Exception e) {
			LoggerUtil.error("Error hessian2ZRank, key:" + key, e);
			return null;
		}
	}

	@Override
	@JedisWay
	public String get(String key) {
		try {
			key = CommonUtil.getCacheKey(key);
			return getJedis().get(key);
		} catch (Exception e) {
			LoggerUtil.error("Error get, key:" + key, e);
			return null;
		}
	}

	@Override
	@JedisWay
	public Boolean set(String key, String value) {
		try {
			key = CommonUtil.getCacheKey(key);
			return RedisConstant.REDIS_DEFAULT_OK.equalsIgnoreCase(getJedis().set(key, value));
		} catch (Exception e) {
			LoggerUtil.error("Error set, key:" + key, e);
			return false;
		}
	}

	@Override
	@JedisWay
	public Boolean set(String key, int seconds, String value) {
		try {
			key = CommonUtil.getCacheKey(key);
			Boolean result = RedisConstant.REDIS_DEFAULT_OK
					.equalsIgnoreCase(getJedis().setex(key, seconds, value));
			return result;
		} catch (Exception e) {
			LoggerUtil.error("Error hessian2SetEx, key:" + key, e);
			return false;
		}
	}

	@Override
	@JedisWay
	public Long rpush(String key, String value) {
		try {
			key = CommonUtil.getCacheKey(key);
			Long length = getJedis().rpush(key, value);
			return length;
		} catch (Exception e) {
			LoggerUtil.error("Error rpush, key:" + key, e);
			return 0L;
		}
	}

	@Override
	@JedisWay
	public List<String> lrange(String key, long start, long end) {
		try {
			key = CommonUtil.getCacheKey(key);
			return getJedis().lrange(key, start, end);
		} catch (Exception e) {
			LoggerUtil.error("Error lrange, key:" + key, e);
			return null;
		}
	}

	@Override
	@JedisWay
	public String ltrim(String key, long start, long end) {
		try {
			key = CommonUtil.getCacheKey(key);
			return getJedis().ltrim(key, start, end);
		} catch (Exception e) {
			LoggerUtil.error("Error ltrim, key:" + key, e);
			return null;
		}
	}

	@Override
	@JedisWay
	public void subscribe(JedisPubSub jedisPubSub, String... channels) {
		try {
			getJedis().subscribe(jedisPubSub, channels);
		} catch (Exception e) {
			LoggerUtil.error("Error subscribe, key:", e);
		}
	}

	@Override
	@JedisWay
	public Long publish(String channel, String message) {
		try {
			return getJedis().publish(channel, message);
		} catch (Exception e) {
			LoggerUtil.error("Error publish, channel:" + channel, e);
			return 0L;
		}
	}
	
	@Override
	public Long sadd(String key, String value) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		key = CommonUtil.getCacheKey(key);
		try {
			return getJedis().sadd(key, value);
		} catch (Exception e) {
			LoggerUtil.error("Error sadd, key:" + key, e);
			return null;
		}
	}

	@Override
	@JedisWay
	public List<String> mgetWithEnv(String[] keys) {
		try {
			String[] cacheKeys = new String[keys.length];
			for (int i = 0; i < keys.length; i++) {
				cacheKeys[i] = CommonUtil.getCacheKey(keys[i]);
			}
			return getJedis().mget(cacheKeys);
		} catch (Exception e) {
			LoggerUtil.error("[Redis Error] error get keys:===", e);
			return null;
		}
	}
	
	@Override
	public Set<String> smembers(String key) {
		if (StringUtils.isBlank(key))
			return null;
		key = CommonUtil.getCacheKey(key);
		try {
			return getJedis().smembers(key);
		} catch (Exception e) {
			LoggerUtil.error("Error smembers, key:" + key, e);
			return null;
		}
	}

	private Jedis getJedis() {
		return baseRedis.getJedisCluster();
	}

}
