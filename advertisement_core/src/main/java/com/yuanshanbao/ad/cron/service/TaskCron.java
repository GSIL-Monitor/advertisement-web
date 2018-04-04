package com.yuanshanbao.ad.cron.service;

import java.lang.reflect.Method;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.web.SpringContextHolder;
import com.yuanshanbao.ad.common.constant.ResultConstant;
import com.yuanshanbao.ad.common.enums.ExecuteModeEnum;
import com.yuanshanbao.ad.cron.model.CronTab;

public class TaskCron implements Runnable {

	private CronTab cronTab;
	private ScheduledExecutorService scheduler;

	TaskCron(CronTab cronTab, ScheduledExecutorService scheduler) {
		this.cronTab = cronTab;
		this.scheduler = scheduler;
	}

	@Override
	public void run() {
		try {
			LoggerUtil.info("TaskCron execute start." + cronTab.toString());
			Class clazz = SpringContextHolder.getBean(cronTab.getBeanName()).getClass();
			Method method = clazz.getDeclaredMethod(cronTab.getBeanMethod());
			int resultCode = (int) method.invoke(SpringContextHolder.getBean(cronTab.getBeanName()));
			if (ResultConstant.SUCCESS != resultCode) {
				LoggerUtil.error("cron execute error.resultCode:" + resultCode + "|" + cronTab.toString());
			}
			LoggerUtil.info("TaskCron execute end." + cronTab.toString() + "|resultCode:" + resultCode);
		} catch (Exception e) {
			LoggerUtil.error("[TaskCron]cron method error." + cronTab.toString(), e);
		} finally {
			if (cronTab.getExecuteMode() == ExecuteModeEnum.CRON.getExecuteMode()) {
				Long delay = CronTab.getNextDelayTime(cronTab.getCron());
				scheduler.schedule(new TaskCron(cronTab, scheduler), delay, TimeUnit.MILLISECONDS);
			}
		}
	}
}
