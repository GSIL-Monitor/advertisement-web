package com.yuanshanbao.ad.information.model;

import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.ValidateUtil;

public class FieldConstrains {

	private Long constrainsId;
	private String name;
	private String description;
	private Integer type;
	private String value;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	public Long getConstrainsId() {
		return constrainsId;
	}

	public void setConstrainsId(Long constrainsId) {
		this.constrainsId = constrainsId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

	public boolean check(String constant) {
		try {
			if (type != null) {
				if (type.equals(ConstantsType.MAX) && ValidateUtil.isNumber(constant) && ValidateUtil.isNumber(value)) {
					Integer a = Integer.parseInt(constant);
					Integer b = Integer.parseInt(value);
					return Integer.compare(a, b) <= 0;
				}
				if (type.equals(ConstantsType.MIN) && ValidateUtil.isNumber(constant) && ValidateUtil.isNumber(value)) {
					Integer a = Integer.parseInt(constant);
					Integer b = Integer.parseInt(value);
					return Integer.compare(a, b) >= 0;
				}
				if (type.equals(ConstantsType.REGEX)) {
					Pattern p = Pattern.compile(value);
					Matcher m = p.matcher(constant);
					return m.matches();
				}
				if (type.equals(ConstantsType.INCLUDING)) {
					return StringUtils.isNotBlank(constant) && constant.contains(value);
				}
				if (type.equals(ConstantsType.EXCLUDING)) {
					return StringUtils.isNotBlank(constant) && value.contains(constant);
				}
				if (type.equals(ConstantsType.MAX_LENGTH) && ValidateUtil.isNumber(value)) {
					byte[] b = constant.getBytes();
					return b.length <= Integer.parseInt(value);
				}
				if (type.equals(ConstantsType.MIN_LENGTH) && ValidateUtil.isNumber(value)) {
					byte[] b = constant.getBytes();
					return b.length >= Integer.parseInt(value);
				}

			}
		} catch (Exception e) {
			throw new BusinessException("请填写正确的信息", ComRetCode.WRONG_PARAMETER);
		}
		return false;
	}

}
