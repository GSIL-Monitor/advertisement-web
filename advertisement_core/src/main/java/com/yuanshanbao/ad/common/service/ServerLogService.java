package com.yuanshanbao.ad.common.service;

import java.util.List;

import com.yuanshanbao.ad.common.model.ServerLog;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface ServerLogService {

	public List<ServerLog> selectServerLogs(ServerLog serverLog, PageBounds pageBounds);

	public List<ServerLog> selectServerLogsByType(Integer type);

	public ServerLog selectServerLog(Long serverLogId);

	public void insertServerLog(ServerLog serverLog);

	public void deleteServerLog(Long serverLogId);

	public void deleteServerLog(ServerLog serverLog);

	public void updateServerLog(ServerLog serverLog);

	public void insertOrUpdateServerLog(ServerLog serverLog);

}
