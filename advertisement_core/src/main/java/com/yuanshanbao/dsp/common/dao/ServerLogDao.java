package com.yuanshanbao.dsp.common.dao;

import java.util.List;

import com.yuanshanbao.dsp.common.model.ServerLog;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface ServerLogDao {

	public List<ServerLog> selectServerLogs(ServerLog serverLog, PageBounds pageBounds);

	public int insertServerLog(ServerLog serverLog);

	public int deleteServerLog(ServerLog serverLog);

	public int updateServerLog(ServerLog serverLog);
}
