package com.yuanshanbao.ad.page.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.ad.common.constant.CommonConstant;

/**
 * 
 * @description
 *
 * @author
 */
public class Page {

	private Long pageId;

	private Long projectId;

	private String name;

	private String key;

	private String steps;

	private Double bonus;

	private Integer type;

	private Integer status;

	private Timestamp createTime;

	private Timestamp updateTime;

	private List<PageStep> pageStepList = new ArrayList<PageStep>();

	// ~ Get and Set Methods
	// =================================================================================

	public Long getPageId() {
		return pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSteps() {
		return steps;
	}

	public void setSteps(String steps) {
		this.steps = steps;
	}

	public List<Long> getStepIds() {
		List<Long> stepIds = new ArrayList<Long>();
		if (StringUtils.isNotBlank(steps)) {
			String[] ids = steps.split(CommonConstant.COMMA_SPLIT_STR);
			for (String id : ids) {
				if (ValidateUtil.isNumber(id)) {
					stepIds.add(Long.parseLong(id));
				}
			}
		}
		return stepIds;
	}

	public Double getBonus() {
		return bonus;
	}

	public void setBonus(Double bonus) {
		this.bonus = bonus;
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

	public List<PageStep> getPageStepList() {
		return pageStepList;
	}

	public void setPageStepList(List<PageStep> pageStepList) {
		this.pageStepList = pageStepList;
	}

}