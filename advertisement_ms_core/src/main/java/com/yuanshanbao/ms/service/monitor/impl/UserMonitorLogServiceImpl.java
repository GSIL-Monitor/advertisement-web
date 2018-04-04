package com.yuanshanbao.ms.service.monitor.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.ms.mapper.admin.UserMonitorLogMapper;
import com.yuanshanbao.ms.model.admin.UserMonitorLog;
import com.yuanshanbao.ms.service.monitor.UserMonitorLogService;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class UserMonitorLogServiceImpl implements UserMonitorLogService {

	@Autowired
	private UserMonitorLogMapper userMonitorLogMapper;
	
	@Override
	public void insertUserMonitorLog(UserMonitorLog userMonitorLog) {
		userMonitorLogMapper.insertUserMonitorLog(userMonitorLog);
	}

	@Override
	public List<UserMonitorLog> queryUserMonitorLogs(UserMonitorLog userMonitorLog, PageBounds pageBounds) {
		return userMonitorLogMapper.queryUserMonitorLogs(userMonitorLog, pageBounds);
	}
}
