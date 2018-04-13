package com.yuanshanbao.dsp.statistics.model;


public class SuccessPageClick {

	private Integer successUvCount;
	private Integer failUvCount;
	private String channel;
	private String date;
	private Integer total;
	
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
	public Integer getSuccessUvCount() {
		return successUvCount;
	}
	public void setSuccessUvCount(Integer successUvCount) {
		this.successUvCount = successUvCount;
	}
	public Integer getFailUvCount() {
		return failUvCount;
	}
	public void setFailUvCount(Integer failUvCount) {
		this.failUvCount = failUvCount;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
}
