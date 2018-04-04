package com.yuanshanbao.common.constant;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class SpringContextConstants implements ApplicationContextAware {
	
	public static ApplicationContext APPCTX = null;

	@Override
	public void setApplicationContext(ApplicationContext appCtx)
			throws BeansException {
		APPCTX = appCtx;
	}
}
