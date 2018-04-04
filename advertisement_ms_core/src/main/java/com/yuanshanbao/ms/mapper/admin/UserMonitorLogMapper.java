package com.yuanshanbao.ms.mapper.admin;

import java.util.List;

import com.yuanshanbao.ms.model.admin.UserMonitorLog;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface UserMonitorLogMapper {
	public void insertUserMonitorLog(UserMonitorLog userMonitorLog);
	
	public List<UserMonitorLog> queryUserMonitorLogs(UserMonitorLog userMonitorLog, PageBounds pageBounds);
}
