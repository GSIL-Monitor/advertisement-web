package com.yuanshanbao.ad.cron.service;

import com.yuanshanbao.ad.cron.model.CronMonitor;

public interface CronMonitorService {
    Boolean monitorCron();

    CronMonitor getCronMonitor(Boolean isLock);
}
