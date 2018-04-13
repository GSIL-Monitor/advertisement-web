package com.yuanshanbao.dsp.push.model;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

import com.gexin.rp.sdk.base.payload.MultiMedia;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.PropertyUtil;
import com.yuanshanbao.common.validator.constraints.NotBlank;
import com.yuanshanbao.dsp.app.model.AppType;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.user.model.User;

public class AppPush {

	private static String appId = PropertyUtil.getProperty("getui.appId");
	private static String appKey = PropertyUtil.getProperty("getui.appKey");
	private static String masterSecret = PropertyUtil.getProperty("getui.masterSecret");

	private static String appId_r = PropertyUtil.getProperty("getui.ruidai.appId");
	private static String appKey_r = PropertyUtil.getProperty("getui.ruidai.appKey");
	private static String masterSecret_r = PropertyUtil.getProperty("getui.ruidai.masterSecret");

	private Long pushId;
	private String title;
	private String content;
	private String userId;
	private String url;
	private String sound;
	private Boolean isSimple;
	private Integer client;
	private MultiMedia media;
	private Boolean isOffline;
	@NotBlank(messageCode = ComRetCode.APP_PUSH_EXPIRETIME_NOT_EXIST_ERROR)
	private Long expireTime;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Long type;
	private String appPushKey;
	private String appPushId;
	private String appMasterSecret;

	private User user;

	public AppPush() {
		super();
	}

	public AppPush(String title, String content, String url, String sound, Boolean isSimple, Integer client,
			MultiMedia media, Boolean isOffline, Long expireTime, Long type) {
		super();
		this.title = title;
		this.content = content;
		this.url = url;
		this.sound = sound;
		this.isSimple = isSimple;
		this.client = client;
		this.media = media;
		this.isOffline = isOffline;
		this.expireTime = expireTime;
		this.type = type;
	}

	public Long getPushId() {
		return pushId;
	}

	public void setPushId(Long pushId) {
		this.pushId = pushId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}

	public Boolean getIsSimple() {
		return isSimple;
	}

	public String getIsSimpleStr() {
		return AppPushType.getFlagDescription(isSimple ? AppPushType.YES : AppPushType.NO);
	}

	public void setIsSimple(Boolean isSimple) {
		this.isSimple = isSimple;
	}

	public MultiMedia getMedia() {
		return media;
	}

	public void setMedia(MultiMedia media) {
		this.media = media;
	}

	public Boolean getIsOffline() {
		return isOffline;
	}

	public void setIsOffline(Boolean isOffline) {
		this.isOffline = isOffline;
	}

	public String getIsOfflineStr() {
		return AppPushType.getFlagDescription(isOffline ? AppPushType.YES : AppPushType.NO);
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusStr() {
		return CommonStatus.getDescription(client);
	}

	public Integer getPlatform() {
		return client;
	}

	public void setClient(Integer client) {
		this.client = client;
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

	public Integer getClient() {
		return client;
	}

	public String getClientStr() {
		return AppPushType.getDescription(client);
	}

	public String getCreateTimeContent() {
		return DateUtils.format(createTime, "yyyy-MM-dd HH:mm:ss");
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAppPushKey() {
		if (StringUtils.isBlank(appPushKey)) {
			if (AppType.RUIDAI_ID.equals(type)) {
				appPushKey = appKey_r;
			}
			appPushKey = appKey;
		}
		return appPushKey;
	}

	public void setAppPushKey(String appPushKey) {
		this.appPushKey = appPushKey;
	}

	public String getAppPushId() {
		if (StringUtils.isBlank(appPushId)) {
			if (AppType.RUIDAI_ID.equals(type)) {
				appPushId = appId_r;
			}
			appPushId = appId;
		}
		return appPushId;
	}

	public void setAppPushId(String appPushId) {
		this.appPushId = appPushId;
	}

	public String getAppMasterSecret() {
		if (StringUtils.isBlank(appMasterSecret)) {
			if (AppType.RUIDAI_ID.equals(type)) {
				appMasterSecret = masterSecret_r;
			}
			appMasterSecret = masterSecret;
		}
		return appMasterSecret;
	}

	public void setAppMasterSecret(String appMasterSecret) {
		this.appMasterSecret = appMasterSecret;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

}
