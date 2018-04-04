package com.yuanshanbao.ad.cron.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.ad.base.dao.BaseDaoImpl;
import com.yuanshanbao.ad.cron.model.CronMonitor;

@Repository
public class CronMonitorDaoImpl extends BaseDaoImpl implements CronMonitorDao {
    @Override
    public CronMonitor getCronMonitor(Boolean isLock) {
        Map<String, Object> params = new HashMap<>();
        params.put("isLock", isLock);
        return getSqlSession().selectOne("CronMonitor.getCronMonitor", params);
    }

    @Override
    public int updateCronMonitor(String refreshTime, String cronIp) {
        Map<String, Object> params = new HashMap<>();
        params.put("refreshTime", refreshTime);
        params.put("cronIp", cronIp);
        return getSqlSession().update("CronMonitor.updateCronMonitor", params);
    }
}
