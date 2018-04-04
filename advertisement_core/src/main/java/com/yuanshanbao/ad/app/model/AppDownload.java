package com.yuanshanbao.ad.app.model;

public class AppDownload {

	public static final String IOS_DOWNLOAD_URL_KEY = "app_ios_download_url";
	public static final String ANDROID_DOWNLOAD_URL_KEY = "app_android_download_url";
	public static final String WEIXIN_DOWNLOAD_URL_KEY = "app_weixin_download_url";

	public static final String IOS_DOWNLOAD_URL_RKEY = "ruidai_app_ios_download_url";
	public static final String ANDROID_DOWNLOAD_URL_RKEY = "ruidai_app_android_download_url";
	public static final String WEIXIN_DOWNLOAD_URL_RKEY = "ruidai_app_weixin_download_url";

	private String iosUrl;
	private String weixinUrl;
	private String androidUrl;

	public String getIosUrl(Long appId) {
		if (appId.equals(AppType.RUIDAI_ID)) {
			iosUrl = IOS_DOWNLOAD_URL_RKEY;
		}else {
			iosUrl = IOS_DOWNLOAD_URL_KEY;
		}
		return iosUrl;
	}

	public void setIosUrl(String iosUrl) {
		this.iosUrl = iosUrl;
	}

	public String getWeixinUrl(Long appId) {
		if (appId.equals(AppType.RUIDAI_ID)) {
			weixinUrl = WEIXIN_DOWNLOAD_URL_RKEY;
		}else {
			weixinUrl = WEIXIN_DOWNLOAD_URL_KEY;
		}
		return weixinUrl;
	}

	public void setWeixinUrl(String weixinUrl) {
		this.weixinUrl = weixinUrl;
	}

	public String getAndroidUrl(Long appId) {
		if (appId.equals(AppType.RUIDAI_ID)) {
			androidUrl = ANDROID_DOWNLOAD_URL_RKEY;
		}else {
			androidUrl = ANDROID_DOWNLOAD_URL_KEY;
		}
		return androidUrl;
	}

	public void setAndroidUrl(String androidUrl) {
		this.androidUrl = androidUrl;
	}

}
