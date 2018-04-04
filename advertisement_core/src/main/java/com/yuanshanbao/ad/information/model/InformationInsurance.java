package com.yuanshanbao.ad.information.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.yuanshanbao.common.util.ValidateUtil;

/**
 * 
 * @description
 *
 * @author
 */
public class InformationInsurance extends Information {

	// ~ Field
	// =============================================================================================

	private Long informationId;

	private String mobile;

	private String favors;

	private String recommends;

	private String infos;

	private String acceptablePremium;

	private String hasInsurance;

	private String relativeIds;

	private String familyStatus;

	private String familyIncome;

	private String childName;

	private Long childGender;

	private Timestamp childBirthday;

	private Integer status;

	private Timestamp createTime;

	private Timestamp updateTime;

	private List<InformationRelative> relativeInsurants;

	// ~ Constructors Methods
	// =================================================================================
	public InformationInsurance() {
		super();
	}

	// ~ Get and Set Methods
	// =================================================================================

	public Long getInformationId() {
		return informationId;
	}

	public void setInformationId(Long informationId) {
		this.informationId = informationId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFavors() {
		return favors;
	}

	public void setFavors(String favors) {
		this.favors = favors;
	}

	public String getRecommends() {
		return recommends;
	}

	public void setRecommends(String recommends) {
		this.recommends = recommends;
	}

	public String getAcceptablePremium() {
		return acceptablePremium;
	}

	public void setAcceptablePremium(String acceptablePremium) {
		this.acceptablePremium = acceptablePremium;
	}

	public String getHasInsurance() {
		return hasInsurance;
	}

	public void setHasInsurance(String hasInsurance) {
		this.hasInsurance = hasInsurance;
	}

	public String getRelativeIds() {
		return relativeIds;
	}

	public List<Long> getRelativeIdList() {
		List<Long> list = new ArrayList<Long>();
		if (StringUtils.isNotBlank(relativeIds)) {
			String[] ids = relativeIds.split(",");
			for (String id : ids) {
				if (ValidateUtil.isNumber(id)) {
					list.add(Long.parseLong(id));
				}
			}
		}
		return list;
	}

	public void setRelativeIds(String relativeIds) {
		this.relativeIds = relativeIds;
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

	public String getInfos() {
		return infos;
	}

	public void setInfos(String infos) {
		this.infos = infos;
	}

	public List<InformationRelative> getRelativeInsurants() {
		return relativeInsurants;
	}

	public void setRelativeInsurants(List<InformationRelative> relativeInsurants) {
		this.relativeInsurants = relativeInsurants;
	}

	public String getFamilyStatus() {
		return familyStatus;
	}

	public void setFamilyStatus(String familyStatus) {
		this.familyStatus = familyStatus;
	}

	public String getFamilyIncome() {
		return familyIncome;
	}

	public void setFamilyIncome(String familyIncome) {
		this.familyIncome = familyIncome;
	}

	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

	public Long getChildGender() {
		return childGender;
	}

	public void setChildGender(Long childGender) {
		this.childGender = childGender;
	}

	public Timestamp getChildBirthday() {
		return childBirthday;
	}

	public void setChildBirthday(Timestamp childBirthday) {
		this.childBirthday = childBirthday;
	}

	public String getGenderValue() {
		Long gender = getGender();
		if (gender != null) {
			if (gender.equals(1L)) {
				return "男";
			} else if (gender.equals(2L)) {
				return "女";
			}
		}
		return "";
	}

}