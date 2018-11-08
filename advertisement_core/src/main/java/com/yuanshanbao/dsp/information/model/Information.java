package com.yuanshanbao.dsp.information.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.common.util.VerifyIdcard;
import com.yuanshanbao.dsp.channel.model.ChannelProcedure;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.location.model.Location;
import com.yuanshanbao.dsp.location.model.LocationType;

/**
 * 
 * @description
 *
 * @author
 */
public class Information implements Serializable {

	private static final long serialVersionUID = 3511228480142367698L;

	private Long informationId;
	private Long projectId;
	private Long activityId;
	private Long merchantId;
	private Long userId;
	private Long productId;
	private String mobile;
	private String name;
	private Long gender;
	private String identityCard;
	private Timestamp birthday;
	private Long education;
	private String email;
	private String location;
	private String address;
	private Long position;
	private String province;
	private String city;
	private Integer salary;
	private Integer age;
	private Long marriage;
	private Long hasChild;
	private String inviteUserId;
	private String channel;
	private String ip;

	private Integer type;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	/**
	 * 数据库查询字段
	 */
	private String createTimeStart;

	private String createTimeEnd;

	/**
	 * 分享人手机号
	 */
	private String shareMobile;
	private String shareActivityId;

	private List<ChannelProcedure> procedureList;

	public Information() {
		super();
	}

	public String getInviteUserId() {
		return inviteUserId;
	}

	public void setInviteUserId(String inviteUserId) {
		this.inviteUserId = inviteUserId;
	}

	public Long getInformationId() {
		return informationId;
	}

	public void setInformationId(Long informationId) {
		this.informationId = informationId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getGender() {
		if (StringUtils.isNotBlank(identityCard)) {
			return VerifyIdcard.getGenderFromId(identityCard);
		}
		return gender;
	}

	public void setGender(Long gender) {
		this.gender = gender;
	}

	public String getGenderValue() {
		return ConstantsManager.getTagsValue(getGender());
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public Timestamp getBirthday() {
		if (birthday == null && StringUtils.isNotBlank(identityCard)) {
			try {
				return DateUtils.getTimestamp(VerifyIdcard.getBirthday(identityCard), "yyyyMMdd");
			} catch (ParseException e) {
			}
		}
		return birthday;
	}

	// public int getAge() {
	// Timestamp birthday = getBirthday();
	// if (birthday != null) {
	// return DateUtils.calculateAge(birthday);
	// }
	// return 0;
	// }

	public void setBirthdayValue(String birthdayValue) {
		if (StringUtils.isNotBlank(birthdayValue)) {
			this.birthday = DateUtils.formatToTimestamp(birthdayValue, "yyyy/MM/dd");
		}
	}

	public String getBirthdayValue() {
		Timestamp birthday = getBirthday();
		if (birthday != null) {
			return DateUtils.format(birthday, "yyyy/MM/dd");
		}
		return null;
	}

	public Long getEducation() {
		return education;
	}

	public void setEducation(Long education) {
		this.education = education;
	}

	public String getEducationValue() {
		return ConstantsManager.getTagsValue(getEducation());
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		if (ValidateUtil.isNumber(location)) {
			location = ConstantsManager.getLocationStrByCode(location);
		}
		this.location = location;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}

	public String getPositionValue() {
		return ConstantsManager.getTagsValue(getPosition());
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public Long getMarriage() {
		return marriage;
	}

	public void setMarriage(Long marriage) {
		this.marriage = marriage;
	}

	public String getMarriageValue() {
		return ConstantsManager.getTagsValue(getMarriage());
	}

	public Long getHasChild() {
		return hasChild;
	}

	public void setHasChild(Long hasChild) {
		this.hasChild = hasChild;
	}

	public String getHasChildValue() {
		return ConstantsManager.getTagsValue(getHasChild());
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUpdateTimeContent() {
		return DateUtils.format(updateTime, "yyyy-MM-dd HH:mm:ss");
	}

	public List<ChannelProcedure> getProcedureList() {
		return procedureList;
	}

	public void setProcedureList(List<ChannelProcedure> procedureList) {
		this.procedureList = procedureList;
	}

	public String getUserLocation() {
		Location location = ConstantsManager.getLocationByCode(province);
		Location cityLocation = ConstantsManager.getLocationByCode(city);
		String result = "";
		if (location != null) {
			result = location.getName();
		}
		if (cityLocation != null) {
			if (result.equals(cityLocation.getName())) {
				return result;
			}
			return result + cityLocation.getName();
		}
		return result;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getShareMobile() {
		return shareMobile;
	}

	public void setShareMobile(String shareMobile) {
		this.shareMobile = shareMobile;
	}

	public String getShareActivityId() {
		return shareActivityId;
	}

	public void setShareActivityId(String shareActivityId) {
		this.shareActivityId = shareActivityId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public void setLocation(Location location) {
		try {
			if (location != null) {
				if (location.getType() == LocationType.DISTRICT) {
					location = location.getParent();
				}
				if (location != null && location.getType() == LocationType.CITY) {
					this.city = location.getCode();
					location = location.getParent();
				}
				if (location != null && location.getType() == LocationType.PROVINCE) {
					this.province = location.getCode();
				}
			}
		} catch (Exception e) {
			LoggerUtil.error("", e);
		}
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
}