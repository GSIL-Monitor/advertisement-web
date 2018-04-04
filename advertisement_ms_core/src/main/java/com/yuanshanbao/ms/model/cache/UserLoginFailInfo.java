package com.yuanshanbao.ms.model.cache;

import java.io.Serializable;

public class UserLoginFailInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int failCount;
	
	private String create_time;

	public int getFailCount() {
		return failCount;
	}

	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
}
