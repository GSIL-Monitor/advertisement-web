package com.yuanshanbao.dsp.common.redis.base;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;

import com.yuanshanbao.common.util.LoggerUtil;

/**
 * 
 * @author KevinLiao
 * modified by dujiepeng
 */
//@Aspect
//@Service
public class JedisAop
{

	@Resource(name = "baseRedis")
	private BaseRedis baseRedisSentinel;

	// private static ThreadLocal<Stack<String>> hierarchyLocal = new
	// ThreadLocal<Stack<String>>();

	/**
	 * 
	 * @param jp
	 */
	// @Around("execution(* com.netease.advertisement.redis.interfaces.*.*(..)) && @annotation(com.netease.advertisement.redis.RedisWay)")
	public Object around(ProceedingJoinPoint pjp) throws Throwable
	{
		// MethodSignature methodSignature = (MethodSignature)
		// pjp.getSignature();
		// Method method = methodSignature.getMethod();
		// Object target = pjp.getTarget();
		// Stack<String> local = hierarchyLocal.get();
		// if (null == local || local.isEmpty()) {
		// local = new Stack<String>();
		// local.push("1");
		// hierarchyLocal.set(local);
		// } else {
		// local.push("1");
		// }
		// 执行目标命令
		Object returnVal = pjp.proceed();
		// local = hierarchyLocal.get();
		// if (null != local && !local.isEmpty()) {
		// local.pop();
		// if (local.empty()) {
		baseRedisSentinel.releaseJedis();
		// }
		// }
		return returnVal;
	}

	@After("@annotation(com.yuanshanbao.dsp.redis.base.JedisWay) || @within(com.yuanshanbao.dsp.redis.base.JedisWay)")
	public void doAfter(JoinPoint jp)
	{
		baseRedisSentinel.releaseJedis();
	}

	//Handle broken client after exception by dujiepeng
	@AfterThrowing("@annotation(com.yuanshanbao.dsp.redis.base.JedisWay) || @within(com.yuanshanbao.dsp.redis.base.JedisWay)")
	public void afterThrowing()
	{
		LoggerUtil.info("Release brokenJedis after Throwing Exception!");
		baseRedisSentinel.releaseBrokenJedis();
		//        logger.info("Exception occured:",ex);  
	}
}
