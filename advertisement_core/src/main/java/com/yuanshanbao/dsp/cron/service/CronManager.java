package com.yuanshanbao.dsp.cron.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.NetUtil;
import com.yuanshanbao.dsp.cache.IniCache;
import com.yuanshanbao.dsp.common.constant.CommonConstant;
import com.yuanshanbao.dsp.cron.dao.CronTabDao;
import com.yuanshanbao.dsp.cron.model.CronTab;

public class CronManager {

	@Autowired
	private CronTabDao cronTabDao;
	@Autowired
	private CronMonitorService cronMonitorService;

	public static boolean isInit = false;// 是否初始化成功标记
	public static byte[] initLock = new byte[0];

	private static ScheduledExecutorService scheduler;
	private static List<CronTab> cronList = new ArrayList<>();

	public void init() {
		String deployHostIps = IniCache.getIniValue("clusterCronIp", "127.0.0.1");
		boolean ifValidCandidate = NetUtil.containsIps(deployHostIps, ",");
		LoggerUtil.info("[CronManager] Deploy Ip:" + deployHostIps + ", ifValid = " + ifValidCandidate);
		if (!ifValidCandidate) {
			return;
		}
		loadCronConfigure();
		startMonitorCron();
	}

	private synchronized void loadCronConfigure() {
		List<CronTab> cronTabs = cronTabDao.getAllCronTab();
		cronList.clear();
		cronList.addAll(cronTabs);
		scheduler = Executors.newScheduledThreadPool(cronList.size() == 0 ? 5 : cronList.size() * 2);
		if (cronList.size() >= 25) {
			LoggerUtil.error("ScheduledThreadPool is large,pls check it!");
		}
	}

	private void startMonitorCron() {
		Long delay = CronTab.getNextDelayTime(CommonConstant.CRON_MONITOR);
		scheduler.schedule(new MonitorCron(cronMonitorService, cronList, scheduler), delay, TimeUnit.MILLISECONDS);
	}
}
