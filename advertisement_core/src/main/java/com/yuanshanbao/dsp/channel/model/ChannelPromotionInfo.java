package com.yuanshanbao.dsp.channel.model;

import java.lang.reflect.Field;
import java.sql.Timestamp;

import javax.sound.midi.MidiDevice.Info;

import com.yuanshanbao.common.util.JacksonUtil;

public class ChannelPromotionInfo {

	private Long promotionInfoId;
	private String uuid;
	private String clickid;
	private String subchannel;
	private String partnerId;
	private String channel;
	private String ip;
	private String deviceType;
	private String systemName;
	private String systemVersion;
	private String sourceChannel;
	private String idfa;
	private String androidId;
	private String mac;
	private String imei;
	private String userAgent;
	private String callback;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	private Channel channelObject;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getClickid() {
		return clickid;
	}

	public void setClickid(String clickid) {
		this.clickid = clickid;
	}

	public String getSubchannel() {
		return subchannel;
	}

	public void setSubchannel(String subchannel) {
		this.subchannel = subchannel;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

	public String getSourceChannel() {
		return sourceChannel;
	}

	public void setSourceChannel(String sourceChannel) {
		this.sourceChannel = sourceChannel;
	}

	public String getIdfa() {
		return idfa;
	}

	public void setIdfa(String idfa) {
		this.idfa = idfa;
	}

	public String getAndroidId() {
		return androidId;
	}

	public void setAndroidId(String androidId) {
		this.androidId = androidId;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
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

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Channel getChannelObject() {
		return channelObject;
	}

	public void setChannelObject(Channel channelObject) {
		this.channelObject = channelObject;
	}

	public Long getPromotionInfoId() {
		return promotionInfoId;
	}

	public void setPromotionInfoId(Long promotionInfoId) {
		this.promotionInfoId = promotionInfoId;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}


	public static void main(String[] args) {
		System.out.println("start");
		try {
			ChannelPromotionInfo info = new ChannelPromotionInfo();
			//info.setIdfa("${idfa}");
			String string = JacksonUtil.obj2json(info);
			System.out.println(string);
			if (string.contains("${")) {
				System.out.println("yes");
			}
		} catch (Exception e) {
			System.out.println("error");
		}
		System.out.println("end");
	}

}
