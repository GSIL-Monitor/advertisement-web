package com.yuanshanbao.ad.cron.service;

import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.NetUtil;
import com.yuanshanbao.ad.common.constant.CommonConstant;
import com.yuanshanbao.ad.cron.dao.CronMonitorDao;
import com.yuanshanbao.ad.cron.model.CronMonitor;

@Service
public class CronMonitorServiceImpl implements CronMonitorService {

	@Autowired
	private CronMonitorDao cronMonitorDao;

	@Override
	public CronMonitor getCronMonitor(Boolean isLock) {
		return cronMonitorDao.getCronMonitor(isLock);
	}

	@Override
	@Transactional
	public Boolean monitorCron() {
		CronMonitor cronMonitor = cronMonitorDao.getCronMonitor(Boolean.TRUE);
		long refreshTime = DateUtils.formatToTimestamp(cronMonitor.getRefreshTime(), DateUtils.DATE_FORMAT_YYYYMMDD_HHMMSS)
				.getTime();
		long now = System.currentTimeMillis();
		String nowStr = DateUtils.format(new Timestamp(now), DateUtils.DATE_FORMAT_YYYYMMDD_HHMMSS);
		if (now - refreshTime > CommonConstant.CRON_MAX_MONITOR_INTERVAL) {
			String localIp = NetUtil.getLocalIP();
			LoggerUtil.error("[cron change]" + cronMonitor.getCronIp() + " is error. change to " + localIp);
			if (StringUtils.isBlank(localIp)) {
				LoggerUtil.error("[cron change]localIp is null");
				return Boolean.FALSE;
			}
			cronMonitorDao.updateCronMonitor(nowStr, NetUtil.containsIp(cronMonitor.getCronIp()) ? null : localIp);
			return Boolean.TRUE;
		} else {
			if (NetUtil.containsIp(cronMonitor.getCronIp())) {// 如果现在执行的cronIp是本机ip
				cronMonitorDao.updateCronMonitor(nowStr,
						NetUtil.DEFAULT_LOCAL_IP.equals(cronMonitor.getCronIp()) ? NetUtil.getLocalIP() : null);
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
}
