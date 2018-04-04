package com.yuanshanbao.ad.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.ad.base.dao.BaseDaoImpl;
import com.yuanshanbao.ad.common.model.ServerLog;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class ServerLogDaoImpl extends BaseDaoImpl implements ServerLogDao {
	@Override
	public List<ServerLog> selectServerLogs(ServerLog serverLog, PageBounds pageBounds) {
		return getSqlSession().selectList("serverLog.selectServerLog", serverLog, pageBounds);
	}

	@Override
	public int insertServerLog(ServerLog serverLog) {
		return getSqlSession().insert("serverLog.insertServerLog", serverLog);
	}

	@Override
	public int deleteServerLog(ServerLog serverLog) {
		return getSqlSession().delete("serverLog.deleteServerLog", serverLog);
	}

	@Override
	public int updateServerLog(ServerLog serverLog) {
		return getSqlSession().update("serverLog.updateServerLog", serverLog);
	}

}
