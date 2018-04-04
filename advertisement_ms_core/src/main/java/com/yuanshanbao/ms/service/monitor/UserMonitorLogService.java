package com.yuanshanbao.ms.service.monitor;

import java.util.List;

import com.yuanshanbao.ms.model.admin.UserMonitorLog;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface UserMonitorLogService {
	public void insertUserMonitorLog(UserMonitorLog userMonitorLog);
	
	public List<UserMonitorLog> queryUserMonitorLogs(UserMonitorLog userMonitorLog, PageBounds pageBounds);
	
}
