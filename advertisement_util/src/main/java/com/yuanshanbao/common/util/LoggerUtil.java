package com.yuanshanbao.common.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.yuanshanbao.common.LoggerHandler;

/**
 * 日志工具类
 */
public class LoggerUtil {

	public static final int LEVEL_DEBUG = 1;
	public static final String LEVEL_DEBUG_DESCRIPTION = "Debug";
	public static final int LEVEL_INFO = 2;
	public static final String LEVEL_INFO_DESCRIPTION = "Info";
	public static final int LEVEL_WARN = 3;
	public static final String LEVEL_WARN_DESCRIPTION = "Warn";
	public static final int LEVEL_ERROR = 4;
	public static final String LEVEL_ERROR_DESCRIPTION = "Error";

	private static LoggerHandler loggerHandler;

	public static void info(Object msg) {
		/** 信息logger */
		LogManager.getLogger("info").info(msg);
	}

	public static void info(final String message, final Object... params) {
		/** 信息logger */
		LogManager.getLogger("info").info(message, params);
	}

	public static void access(Object msg) {
		/** 信息logger */
		LogManager.getLogger("access").info(msg);
	}

	public static void order(String msg, final Object... params) {
		/** 信息logger */
		LogManager.getLogger("order").info(msg, params);
	}

	/** debugLogger */
	public static void debug(Object msg) {
		LogManager.getLogger("debug").info(msg);
	}

	/** 发送验证码短信接口的logger */
	public static void sendMessageInfo(Object msg) {
		LogManager.getLogger("message").info(msg);
	}

	/** 登录log */
	public static void loginInfo(String eTag) {
		LogManager.getLogger("login").info(eTag);
	}

	/** 重发log */
	public static void resendInfo(String eTag) {
		LogManager.getLogger("resend").info(eTag);
	}

	/**
	 * 需人工干预的报警logger 调用程序：1：大部分不用改，还是用LoggerUtil.alarmInfo
	 * */
	public static void alarmInfo(Object msg) {
		if (StackTraceUtil.checkIsBatchCallClass()) {
			LoggerUtil.alarmInfoStrategy1(msg);
		} else {
			LoggerUtil.alarmInfoBasic("[comn] " + msg);
		}
	}

	/**
	 * 60m,10 改成 LoggerUtil.alarmInfoStrategy1 或者 LoggerUtil.alarmInfoStrategy2
	 * 等 以这个为准吧，注意下 '[comn] ' '[60m,10] ' 和后面的msg之间有个空格，也是应运维的需求
	 */
	public static void alarmInfoStrategy1(Object msg) {
		LoggerUtil.alarmInfoBasic("[60m,10] " + msg);
	}

	/** 5m,10 */
	public static void alarmInfoStrategy2(Object msg) {
		LoggerUtil.alarmInfoBasic("[5m,10] " + msg);
	}

	/** 10m,1 */
	public static void alarmInfoStrategy10m1(Object msg) {
		LoggerUtil.alarmInfoBasic("[10m,1] " + msg);
	}

	/** 10m,10 */
	public static void alarmInfoStrategy10m10(Object msg) {
		LoggerUtil.alarmInfoBasic("[10m,10] " + msg);
	}

	/** 需人工干预的报警logger */
	protected static void alarmInfoBasic(Object msg) {
		LogManager.getLogger("alarm").info(msg);
		sendLog(LEVEL_WARN, msg.toString());
	}

	public static void alarmInfo(String msg, Exception e) {
		printAlarm(LogManager.getLogger("alarm"), msg, e);
	}

	/** 缓存logger */
	public static void cacheInfo(Object msg) {
		LogManager.getLogger("cache").info(msg);
	}

	public static void printAlarm(Logger logger, String msg, Exception e) {
		if (msg != null) {
			logger.info(msg);
		}
		if (e != null) {
			ByteArrayOutputStream buf = new ByteArrayOutputStream();
			e.printStackTrace(new PrintWriter(buf, true));
			logger.error(buf.toString());
		}
	}

	/** errorLogger */
	public static void error(String msg, Exception e) {
		printError(LogManager.getLogger("error.log"), msg, e);
		sendLog(LEVEL_ERROR, msg, e);
	}

	public static void error(final String message, final Object... params) {
		/** 信息logger */
		LogManager.getLogger("error.log").error(message, params);
		sendLog(LEVEL_ERROR, message, null);
	}

	public static void printError(Logger logger, String msg, Exception e) {
		if (msg != null) {
			logger.error(msg);
		}
		if (e != null) {
			ByteArrayOutputStream buf = new ByteArrayOutputStream();
			e.printStackTrace(new PrintWriter(buf, true));
			logger.error(buf.toString());
		}
	}

	public static void error(String msg, Throwable e) {
		printError(LogManager.getLogger("error.log"), msg, e);
		sendLog(LEVEL_ERROR, msg, e);
	}

	private static void sendLog(int level, String message) {
		if (loggerHandler != null) {
			loggerHandler.sendLog(level, message);
		}
	}

	private static void sendLog(int level, String msg, Throwable e) {
		if (loggerHandler != null) {
			loggerHandler.sendLog(level, msg + getContent(e));
		}
	}

	public static void printError(Logger logger, String msg, Throwable e) {
		if (msg != null) {
			logger.error(msg);
		}
		if (e != null) {
			logger.error(getContent(e));
		}
	}

	public static String getContent(Throwable e) {
		if (e == null) {
			return "";
		}
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		e.printStackTrace(new PrintWriter(buf, true));
		return buf.toString();
	}

	public static void setLoggerHandler(LoggerHandler loggerHandler) {
		LoggerUtil.loggerHandler = loggerHandler;
	}

	public static boolean isInfoEnabled() {
		return true;
	}

	public static void trace(String message) {
		info(message);
	}

	public static void warn(String message) {
		if (loggerHandler != null) {
			loggerHandler.sendLog(LEVEL_WARN, message);
		}
		alarmInfo(message);
	}

	public static boolean isTraceEnabled() {
		return true;
	}

	public static void orderInfo(String string) {
		// TODO Auto-generated method stub

	}

	public static void epayInfo(String string) {
		// TODO Auto-generated method stub

	}

	public static void epayInfo(Map pfResult) {
		// TODO Auto-generated method stub

	}

	public static boolean isDebugEnabled() {
		return LogManager.getLogger("debug").isDebugEnabled();
	}

}
