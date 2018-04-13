package com.yuanshanbao.dsp.controller.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.activity.model.Activity;
import com.yuanshanbao.dsp.config.ConfigConstants;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.InterfaceRetCode;

@Controller
@RequestMapping({ "/i/constants" })
public class ConstantsController extends BaseController {

	@ResponseBody
	@RequestMapping("/get")
	public Map<String, Object> get(HttpServletRequest request, String activityKey, String channel) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			/*
			 * resultMap.put("tagsMap", ConstantsManager.getTagsMap());
			 * resultMap.put("typeMap", ConstantsManager.getTypeMap());
			 */
			setConfigValue(resultMap, activityKey, channel, getAppKey(request));
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode());
		} catch (Exception e) {
			LoggerUtil.error("[CompanyController-new error:]", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	private void setConfigValue(Map<String, Object> resultMap, String activityKey, String channel, String appKey) {
		Activity activity = ConfigManager.getActivityByKey(activityKey);
		Long activityId = null;
		if (activity != null) {
			activityId = activity.getActivityId();
		}
		resultMap.put("defaultLogin",
				ConfigManager.getConfigValue(activityId, appKey, channel, ConfigConstants.DEFAULT_LOGIN_CONFIG));
	}
}
