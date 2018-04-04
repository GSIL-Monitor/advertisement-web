package com.yuanshanbao.ms.controller.base;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerAspect {
	@Before(value = "execution(* com.yuanshanbao.ms.controller.*.*.*(..))")
	public void beforeMethod(JoinPoint point) {
//		System.out.println("------test aop before");
	}
}
