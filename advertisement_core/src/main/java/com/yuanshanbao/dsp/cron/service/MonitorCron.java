package com.yuanshanbao.dsp.cron.service;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.NetUtil;
import com.yuanshanbao.dsp.common.constant.CommonConstant;
import com.yuanshanbao.dsp.common.enums.ExecuteModeEnum;
import com.yuanshanbao.dsp.cron.model.CronMonitor;
import com.yuanshanbao.dsp.cron.model.CronTab;

/**
 * 实时更新数据库cron记录
 */
public class MonitorCron implements Runnable {

	private CronMonitorService cronMonitorService;
	private List<CronTab> cronList;
	private ScheduledExecutorService scheduler;

	MonitorCron(CronMonitorService cronMonitorService, List<CronTab> cronList, ScheduledExecutorService scheduler) {
		this.cronMonitorService = cronMonitorService;
		this.cronList = cronList;
		this.scheduler = scheduler;
	}

	@Override
	public void run() {
		try {
			CronMonitor cronMonitor = cronMonitorService.getCronMonitor(Boolean.FALSE);
			long refreshTime = DateUtils.formatToTimestamp(cronMonitor.getRefreshTime(),
					DateUtils.DATE_FORMAT_YYYYMMDD_HHMMSS).getTime();
			long now = System.currentTimeMillis();
			Boolean isDeal = Boolean.FALSE;// 是否处理
			if ((now - refreshTime > CommonConstant.CRON_MAX_MONITOR_INTERVAL)
					|| NetUtil.containsIp(cronMonitor.getCronIp())) {
				isDeal = cronMonitorService.monitorCron();
			}
			// 开始初始化调度程序
			if (!CronManager.isInit) {
				LoggerUtil.info("CronTab init try");
			}
			if (isDeal) {
				startCron();
			}
		} catch (Exception e) {
			LoggerUtil.error("MonitorCron error.", e);
		} finally {
			Long delay = CronTab.getNextDelayTime(CommonConstant.CRON_MONITOR);
			scheduler.schedule(new MonitorCron(cronMonitorService, cronList, scheduler), delay, TimeUnit.MILLISECONDS);
		}
	}

	private void startCron() {
		synchronized (CronManager.initLock) {
			if (CronManager.isInit) {
				LoggerUtil.info("CronTab init is done");
				return;
			} else {
				CronManager.isInit = true;
			}
		}
		LoggerUtil.error("CronTab switch");// 记录切换操作日志，用于监控报警
		for (CronTab tab : cronList) {
			try {
				ExecuteModeEnum modeEnum = ExecuteModeEnum.getEnum(tab.getExecuteMode());
				switch (modeEnum) {
				case CRON:
					Long delay = CronTab.getNextDelayTime(tab.getCron());
					scheduler.schedule(new TaskCron(tab, scheduler), delay, TimeUnit.MILLISECONDS);
					break;
				case INTERVAL:
					scheduler.scheduleWithFixedDelay(new TaskCron(tab, scheduler), 1, Long.parseLong(tab.getCron()),
							TimeUnit.MILLISECONDS);
					break;
				}
			} catch (Exception e) {
				LoggerUtil.error("startCron CronTab error." + tab.toString(), e);
			}
		}
	}

	public static void main(String[] args) {
		long refreshTime = DateUtils.formatToTimestamp("2017-07-31 10:25:00", DateUtils.DATE_FORMAT_YYYYMMDD_HHMMSS)
				.getTime();
		System.out.println(refreshTime);
	}
}
