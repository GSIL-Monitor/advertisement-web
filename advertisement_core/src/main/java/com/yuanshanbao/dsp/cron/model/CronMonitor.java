package com.yuanshanbao.dsp.cron.model;

public class CronMonitor {
	private String monitorId;
	private String cronIp;
	private String refreshTime;

	public String getMonitorId() {
		return monitorId;
	}

	public void setMonitorId(String monitorId) {
		this.monitorId = monitorId;
	}

	public String getCronIp() {
		return cronIp;
	}

	public void setCronIp(String cronIp) {
		this.cronIp = cronIp;
	}

	public String getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(String refreshTime) {
		this.refreshTime = refreshTime;
	}

}
