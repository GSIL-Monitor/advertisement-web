package com.yuanshanbao.ms.model.admin;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserMonitorLog implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private String userName;
	
	private Date operateTime;
	
	private String operateType;
	
	private String operateIp;
	
	private String operateTimeStr;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getOperateIp() {
		return operateIp;
	}

	public void setOperateIp(String operateIp) {
		this.operateIp = operateIp;
	}

	public String getOperateTimeStr() {
		operateTimeStr = sdf.format(operateTime);
		
		return operateTimeStr;
	}

	public void setOperateTimeStr(String operateTimeStr) {
		this.operateTimeStr = operateTimeStr;
	}
}
