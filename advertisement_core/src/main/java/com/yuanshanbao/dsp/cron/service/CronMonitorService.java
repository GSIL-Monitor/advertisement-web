package com.yuanshanbao.dsp.cron.service;

import com.yuanshanbao.dsp.cron.model.CronMonitor;

public interface CronMonitorService {
    Boolean monitorCron();

    CronMonitor getCronMonitor(Boolean isLock);
}
