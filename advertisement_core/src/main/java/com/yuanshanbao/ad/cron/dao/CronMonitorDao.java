package com.yuanshanbao.ad.cron.dao;

import com.yuanshanbao.ad.cron.model.CronMonitor;

/**
 * @author Singal
 */
public interface CronMonitorDao {
    CronMonitor getCronMonitor(Boolean isLock);

    int updateCronMonitor(String refreshTime, String cronIp);
}
