package com.yuanshanbao.dsp.app.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.yuanshanbao.dsp.app.model.AppInfo;

@Service
public interface AppService {

	public void initialAppInfo(AppInfo appInfo);

	public String decryptAppKey(String appId);

	public Map<String, String> decryptParameters(String appId, String params);

	public String getApplicationType(String appId);

	public String getAppKey(String appId);

	public String getAppChannel(String appId);

}
