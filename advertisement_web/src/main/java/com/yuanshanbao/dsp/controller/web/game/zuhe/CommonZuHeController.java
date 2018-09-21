package com.yuanshanbao.dsp.controller.web.game.zuhe;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.constant.SessionConstants;
import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.controller.web.game.BaseGameController;
import com.yuanshanbao.dsp.core.InterfaceRetCode;

@RequestMapping({ "/activity/zuhe/common", "/i/activity/zuhe/common" })
@Controller
public class CommonZuHeController extends BaseGameController {

	@ResponseBody
	@RequestMapping("/index")
	public Object activityCombineIndex(HttpServletRequest request, ModelMap modelMap, String activityKey, String channel) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			setCombineIndex(request, resultMap, activityKey, channel, null, null);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			LoggerUtil.error("[zuheIndex: error]", e);
		}
		return resultMap;
	}

	@ResponseBody
	@RequestMapping("/subIndex")
	public Object subActivityIndex(HttpServletRequest request, ModelMap modelMap, String parentKey, String activityKey,
			String channel) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			subIndex(request, resultMap, parentKey, activityKey, channel, null, null);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			LoggerUtil.error("[subIndex: error]", e);
		}
		return resultMap;
	}

	@ResponseBody
	@RequestMapping("/luck")
	public Object luck(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, String parentKey,
			String activityKey, String channel) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// 获取次数
			getSubChanceAndSetResult(request, response, resultMap, parentKey, activityKey, channel);
			// 抽奖
			pickSubPrizeAndSetResult(request, response, resultMap, activityKey, parentKey, channel);
			resultMap.put("angle", positionAngles[4 - 1]);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			LoggerUtil.error("[zhuanpanChance: error]", e);
		}
		return resultMap;
	}

	@ResponseBody
	@RequestMapping("/checkIsFromCombine")
	public Object checkIsFromCombine(HttpServletRequest request, ModelMap modelMap, String channel) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String combineChannel = (String) request.getSession().getAttribute(
				SessionConstants.SESSION_ACTIVITY_COMBINE_CHANNEL);
		if (StringUtils.isNotEmpty(channel) && !channel.equals(combineChannel)) {
			return resultMap;
		}
		String parentKey = (String) request.getSession().getAttribute(
				combineChannel + SessionConstants.SESSION_ACTIVITY_COMBINE_KEY);
		if (StringUtils.isNotBlank(parentKey)) {
			resultMap.put("parentKey", parentKey);
			resultMap.put("channel", combineChannel);
		}
		return resultMap;
	}
}
