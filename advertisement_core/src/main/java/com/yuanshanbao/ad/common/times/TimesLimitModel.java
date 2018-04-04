package com.yuanshanbao.ad.common.times;

import com.yuanshanbao.ad.core.IniBean;

public class TimesLimitModel {

	private TimesLimitService timesLimitService;
	private Integer times;
	private String type;
	private String key;

	public TimesLimitModel(String type, String key, TimesLimitService timesLimitService) {
		this.type = type;
		this.key = key;
		this.timesLimitService = timesLimitService;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public void increaseTimes() {
		if (times == null) {
			times = 0;
		}
		times++;
		timesLimitService.updateTimesLimit(type, key, times);
	}

	public boolean isOverTimesLimit() {
		String value = IniBean.getIniValue(TimesLimitConstants.INI_TIMES_LIMIT_PREFIX + type);
		try {
			Integer limit = new Integer(value);
			if (times == null) {
				return false;
			}
			if (times >= limit) {
				return true;
			}
		} catch (NumberFormatException e) {
		}
		return false;
	}

}
