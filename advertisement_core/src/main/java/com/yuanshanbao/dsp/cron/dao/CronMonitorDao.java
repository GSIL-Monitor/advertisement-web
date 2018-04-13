package com.yuanshanbao.dsp.cron.dao;

import com.yuanshanbao.dsp.cron.model.CronMonitor;

/**
 * @author Singal
 */
public interface CronMonitorDao {
    CronMonitor getCronMonitor(Boolean isLock);

    int updateCronMonitor(String refreshTime, String cronIp);
}
