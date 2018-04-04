package com.yuanshanbao.ad.common.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.yuanshanbao.common.LoggerHandler;
import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.CommonUtil;
import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.ad.common.dao.ServerLogDao;
import com.yuanshanbao.ad.common.model.ServerLog;
import com.yuanshanbao.ad.weixin.service.WeixinService;
import com.yuanshanbao.paginator.domain.PageBounds;

public class ServerLogServiceImpl implements ServerLogService, LoggerHandler, MessageListener {

	@Autowired
	private ServerLogDao serverLogDao;

	@Autowired
	private WeixinService weixinService;

	private Map<String, ServerLog> logMap = new ConcurrentHashMap<String, ServerLog>();

	public void init() {
		LoggerUtil.setLoggerHandler(this);
	}

	@Override
	public List<ServerLog> selectServerLogs(ServerLog serverLog, PageBounds pageBounds) {
		return serverLogDao.selectServerLogs(serverLog, pageBounds);
	}

	@Override
	public List<ServerLog> selectServerLogsByType(Integer type) {
		ServerLog serverLog = new ServerLog();
		serverLog.setType(type);
		return serverLogDao.selectServerLogs(serverLog, new PageBounds());
	}

	@Override
	public ServerLog selectServerLog(Long serverLogId) {
		ServerLog serverLog = new ServerLog();
		serverLog.setLogId(serverLogId);
		List<ServerLog> list = selectServerLogs(serverLog, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertServerLog(ServerLog serverLog) {
		int result = -1;

		result = serverLogDao.insertServerLog(serverLog);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteServerLog(Long serverLogId) {
		ServerLog serverLog = new ServerLog();
		serverLog.setLogId(serverLogId);
		deleteServerLog(serverLog);
	}

	@Override
	public void deleteServerLog(ServerLog serverLog) {
		int result = -1;

		result = serverLogDao.deleteServerLog(serverLog);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateServerLog(ServerLog serverLog) {
		int result = -1;

		result = serverLogDao.updateServerLog(serverLog);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	/**
	 * @param serverLog
	 * @return
	 * @throws IOException
	 */
	@Override
	public void insertOrUpdateServerLog(ServerLog serverLog) {
		if (serverLog.getLogId() == null) {
			insertServerLog(serverLog);
		} else {
			updateServerLog(serverLog);
		}
	}

	@Override
	public void sendLog(int type, String content) {
		if ("dev".equals(CommonUtil.getEnvironment())) {
			// messageQueueService.sendServerAlarmMessage(System.currentTimeMillis()
			// + "", type + "", content);
			return;
		}
		try {
			ServerLog serverLog = new ServerLog();
			serverLog.setContent(content);
			serverLog.setType(type);
			String[] segs = content.split("\n");
			if (segs.length > 1) {
				serverLog.setTitle(segs[0] + " " + segs[1]);
			} else {
				serverLog.setTitle(content);
			}
			serverLog.setCreateTime(DateUtils.getCurrentTime());
			if (needSendAlarm(serverLog)) {
				int minute = 5;
				if (type == LoggerUtil.LEVEL_WARN) {
					minute = 20;
				}
				ServerLog existLog = logMap.get(serverLog.getTitle());
				if (existLog != null) {
					existLog.incrementAndGet();
					long time = System.currentTimeMillis() - existLog.getCreateTime().getTime();
					if (time > 1000 * 60 * minute) {
						insertOrUpdateServerLog(existLog);
						weixinService.sendServerAlarmMessage(existLog);
						logMap.remove(serverLog.getTitle());
					}
				} else {
					if (type == LoggerUtil.LEVEL_ERROR) {
						insertOrUpdateServerLog(serverLog);
						weixinService.sendServerAlarmMessage(serverLog);
					}
					logMap.put(serverLog.getTitle(), serverLog);
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	@Override
	public Action consume(Message message, ConsumeContext context) {
		try {
			String key = message.getKey();
			String content = new String(message.getBody());
			ServerLog serverLog = new ServerLog();
			serverLog.setContent(content);
			int type = Integer.parseInt(message.getTag());
			serverLog.setType(type);
			String[] segs = content.split("\n");
			if (segs.length > 1) {
				serverLog.setTitle(segs[0] + " " + segs[1]);
			}
			serverLog.setCreateTime(new Timestamp(Long.parseLong(key)));
			if (needSendAlarm(serverLog)) {
				synchronized (ServerLogServiceImpl.class) {
					ServerLog existLog = logMap.get(serverLog.getTitle());
					if (existLog != null) {
						existLog.incrementAndGet();
						long time = System.currentTimeMillis() - existLog.getCreateTime().getTime();
						if (time > 1000 * 60 * 60) {
							insertOrUpdateServerLog(serverLog);
							weixinService.sendServerAlarmMessage(serverLog);
							logMap.put(serverLog.getTitle(), serverLog);
						}
						if (time > 1000 * 60 * 5) {
							insertOrUpdateServerLog(existLog);
							weixinService.sendServerAlarmMessage(existLog);

							logMap.put(serverLog.getTitle(), serverLog);
						}
					} else {
						insertOrUpdateServerLog(serverLog);
						weixinService.sendServerAlarmMessage(serverLog);

						logMap.put(serverLog.getTitle(), serverLog);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.CommitMessage;
	}

	private boolean needSendAlarm(ServerLog serverLog) {
		if (serverLog.getTitle() != null
				&& serverLog.getTitle().contains("org.springframework.remoting.RemoteInvocationFailureException")) {
			if (serverLog.getTitle().contains("返回结果为：505")
					|| serverLog.getTitle().contains("java.net.MalformedURLException: Illegal character in URL")) {
				return false;
			}
		}
		if (serverLog.getTitle() != null
				&& serverLog
						.getTitle()
						.contains(
								"org.springframework.beans.TypeMismatchException: Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException")) {
			return false;
		}
		return true;
	}

}
