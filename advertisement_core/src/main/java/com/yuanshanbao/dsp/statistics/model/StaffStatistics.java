package com.yuanshanbao.dsp.statistics.model;

import java.sql.Timestamp;

public class StaffStatistics {

	private static final long ONE_DAY = 1000 * 24 * 60 * 60;
	private static final long ONE_HOUR = 1000 * 60 * 60;
	private static final long ONE_MINUTE = 1000 * 60;

	private Long staffStatisticsId;
	private Long staffId;
	private String name;
	/**
	 * 日期
	 */
	private String date;
	/**
	 * 在线状态
	 */
	private Integer onlineStatus;
	/**
	 * 本月累计工作日
	 */
	private Integer workingDays = 0;
	/**
	 * 本月新发数据量
	 */
	private Integer newDataCount = 0;
	/**
	 * 拨打数
	 */
	private Integer callCount = 0;
	/**
	 * 本月通话时长
	 */
	private Integer totalCallTime = 0;
	/**
	 * 本月受理保额
	 */
	private Integer insuredSum = 0;
	/**
	 * 标记
	 */
	private String remark;
	/**
	 * 类型
	 */
	private Integer type;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	/**
	 * 查询指定日期的开始时间
	 */
	private String queryStartTime;
	/**
	 * 查询指定日期的结束时间
	 */
	private String queryEndTime;

	public Long getStaffStatisticsId() {
		return staffStatisticsId;
	}

	public void setStaffStatisticsId(Long staffStatisticsId) {
		this.staffStatisticsId = staffStatisticsId;
	}

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(Integer onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public Integer getWorkingDays() {
		return workingDays;
	}

	public void setWorkingDays(Integer workingDays) {
		this.workingDays = workingDays;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getNewDataCount() {
		return newDataCount;
	}

	public void setNewDataCount(Integer newDataCount) {
		this.newDataCount = newDataCount;
	}

	public Integer getCallCount() {
		return callCount;
	}

	public void setCallCount(Integer callCount) {
		this.callCount = callCount;
	}

	public Integer getTotalCallTime() {
		return totalCallTime;
	}

	public void setTotalCallTime(Integer totalCallTime) {
		this.totalCallTime = totalCallTime;
	}

	public Integer getInsuredSum() {
		return insuredSum;
	}

	public void setInsuredSum(Integer insuredSum) {
		this.insuredSum = insuredSum;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getTypeDescription() {
		return StaffStatisticsType.getTypeDescription(type);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQueryStartTime() {
		return queryStartTime;
	}

	public void setQueryStartTime(String queryStartTime) {
		this.queryStartTime = queryStartTime;
	}

	public String getQueryEndTime() {
		return queryEndTime;
	}

	public void setQueryEndTime(String queryEndTime) {
		this.queryEndTime = queryEndTime;
	}

	public String getTotalCallTimeContent() {
		if (totalCallTime != null) {
			totalCallTime *= 1000;
			String result = "";
			if ((totalCallTime % ONE_DAY / ONE_HOUR) != 0) {
				result = (totalCallTime % ONE_DAY / ONE_HOUR) + "小时";
			}
			if ((totalCallTime % ONE_DAY % ONE_HOUR / ONE_MINUTE) != 0) {
				result += (totalCallTime % ONE_DAY % ONE_HOUR / ONE_MINUTE) + "分钟";
			}
			if ((totalCallTime % ONE_DAY % ONE_HOUR % ONE_MINUTE / 1000) != 0) {
				result += (totalCallTime % ONE_DAY % ONE_HOUR % ONE_MINUTE / 1000) + "秒";
			}
			return result;
		}
		return "0秒";

	}

	public String getOnlineStatusContent() {
		if (onlineStatus != null) {
			return onlineStatus == 1 ? "在线" : "下线";
		}
		return "";
	}

	public void add(StaffStatistics staffStatistics) {
		if (staffStatistics == null) {
			return;
		}
		this.callCount += staffStatistics.callCount;
		this.newDataCount += staffStatistics.newDataCount;
		this.totalCallTime += staffStatistics.totalCallTime;
		this.workingDays += staffStatistics.workingDays;
	}

	public void clearParameter() {
		if (callCount == 0) {
			callCount = null;
		}
		if (newDataCount == 0) {
			newDataCount = null;
		}
		if (totalCallTime == 0) {
			totalCallTime = null;
		}
		if (workingDays == 0) {
			workingDays = null;
		}
	}

}
