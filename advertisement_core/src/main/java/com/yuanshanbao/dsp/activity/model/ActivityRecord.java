package com.yuanshanbao.dsp.activity.model;

public class ActivityRecord {
	private Activity activity;
	private Integer uvCount;
	private Integer requestCount;
	private String channel;
	private String date;

	public ActivityRecord() {
		super();
		this.requestCount = 0;
		this.uvCount = 0;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getUvCount() {
		return uvCount;
	}

	public void setUvCount(Integer uvCount) {
		this.uvCount = uvCount;
	}

	public Integer getRequestCount() {
		return requestCount;
	}

	public void setRequestCount(Integer requestCount) {
		this.requestCount = requestCount;
	}
}
