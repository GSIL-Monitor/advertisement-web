package com.yuanshanbao.ms.model.other;

public class Checkcode {
	private String checkcode;
	
	private String service;
	
	private long create_time;
	
	private int status;
	
	private long random_id;

	public String getCheckcode() {
		return checkcode;
	}

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public long getCreate_time() {
		return create_time;
	}

	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getRandom_id() {
		return random_id;
	}

	public void setRandom_id(long random_id) {
		this.random_id = random_id;
	}
}
