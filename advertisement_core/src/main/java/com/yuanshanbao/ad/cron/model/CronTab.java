package com.yuanshanbao.ad.cron.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.CronExpression;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.ad.common.constant.CommonConstant;

public class CronTab {
	private String beanName;
	private String beanMethod;
	private Integer executeMode;// 1.定时执行 2.顺序执行
	private String cron;

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getBeanMethod() {
		return beanMethod;
	}

	public void setBeanMethod(String beanMethod) {
		this.beanMethod = beanMethod;
	}

	public Integer getExecuteMode() {
		return executeMode;
	}

	public void setExecuteMode(Integer executeMode) {
		this.executeMode = executeMode;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public static Long getNextDelayTime(Object ob) {
		String cronStr;
		if (ob instanceof CronTab) {
			cronStr = ((CronTab) ob).getCron();
		} else {
			cronStr = (String) ob;
		}
		List<CronExpression> crons = new ArrayList<>();
		String[] cronArr = cronStr.split(CommonConstant.COMMON_ESCAPE_STR + CommonConstant.COMMON_VERTICAL_STR);
		for (String cron : cronArr) {
			try {
				CronExpression ce = new CronExpression(cron);
				crons.add(ce);
			} catch (ParseException e) {
				LoggerUtil.error("[getNextDelayTime] invalid cronExpression " + cron);
			}
		}
		if (crons.size() == 0) {
			throw new BusinessException("cron error." + ob.toString());
		}
		Date now = new Date();
		Date result = null;
		for (CronExpression cron : crons) {
			Date nextValidTime = cron.getNextValidTimeAfter(now);
			if (nextValidTime == null) {
				LoggerUtil.error("[getNextDelayTime] getNextFire nextValidTime is null");
				continue;
			}
			if (result == null || nextValidTime.before(result)) {
				result = nextValidTime;
			}
		}
		long delay = result.getTime() - System.currentTimeMillis();
		return delay;
	}
}
