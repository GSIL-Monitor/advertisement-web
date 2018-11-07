package com.yuanshanbao.dsp.common.redis.base;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.JedisPubSub;

//redis基础操作类，业务组合操作请使用RedisUtilService
public interface RedisService {

	Long hessian2ZRemRangeByRank(String key, int start, int end);

	Boolean hessian2ZAddSet(String key, Long timeline, Object value);

	<T> T hessian2ZRangeByScoreGet(String key, Long min, Long max);

	<T> T hessian2ZRangeByScoreGet(String key, Long min, Long max, int x, int y);

	<T> T hessian2ZRevRangeByScoreGet(String key, Long min, Long max);

	<T> T hessian2ZRevRangeByScoreGet(String key, Long min, Long max, int x, int y);

	Boolean hessian2Set(String key, Object value);

	Boolean hset(String key, String field, Object value);

	Boolean hessian2SetEx(String key, int seconds, Object value);

	Long hessian2SetNx(String key, Object value);

	<T> T hessian2Get(String key);

	<T, S> Long hessian2HSet(String key, T field, S value);

	<T, S> Boolean hessian2Hmset(String key, Map<T, S> fieldValueMap);

	<T, S> T hessian2HGet(String key, S field);

	<S, T> Map<S, T> hessian2HGetAll(String key);

	public Long increBy(String key, int increaseCount);

	Long incr(String key);

	Long hdel(String key, String field);

	Boolean isKeyExist(String key);

	Boolean isKeyByteExist(String key);

	Long del(String key);

	Long expire(String key, int seconds);

	Long ttl(String key);

	Long hessian2RPush(String key, Object value);

	Long hessian2LPush(String key, Object value);

	Long hessian2LRem(String key, long count, Object value);

	Boolean hessian2LTrim(String key, long start, long end);

	<T> T hessian2LPop(String key);

	<T> List<T> hessian2LRange(String key, Long start, Long end);

	<T> List<T> hessian2ZRange(String key, Long start, Long end);

	Long hessian2ZRank(String key, Integer awardNumber);

	String get(String key);

	Boolean set(String key, String value);

	Boolean set(String key, int seconds, String value);

	Long rpush(String key, String value);

	List<String> lrange(String key, long start, long end);

	String ltrim(final String key, final long start, final long end);

	void subscribe(final JedisPubSub jedisPubSub, final String... channels);

	Long publish(final String channel, final String message);

	Set<String> smembers(String key);

	Long sadd(String key, String value);

	List<String> mgetWithEnv(String[] keys);

	Double increByFloat(String key, float increaseCount);

	Double increByDouble(String key, double increaseCount);

	Long srem(String key, String value);
}
