package com.yuanshanbao.dsp.controller.web.game.zhuanpan;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.core.InterfaceRetCode;

@RequestMapping({ "/activity/zhuanpan/common", "/m/activity/zhuanpan/common" })
@Controller
public class CommonZhuanPanController extends BaseZhuanPanController {

	@ResponseBody
	@RequestMapping("/chance")
	public Object chance(HttpServletRequest request, HttpServletResponse response, String activityKey, String channel) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			getChanceAndSetResult(request, response, resultMap, activityKey, channel);
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
	@RequestMapping("/index")
	public Object index(HttpServletRequest request, HttpServletResponse response, String activityKey, String channel) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			setIndex(request, resultMap, activityKey, channel, null, null);
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
	@RequestMapping("/luck")
	public Object luck(HttpServletRequest request, HttpServletResponse response, String activityKey) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			pickPrizeAndSetResult(request, response, resultMap, activityKey);
			// resultMap.put("position", POSITION);
			resultMap.put("angle", positionAngles[5 - 1]);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			LoggerUtil.error("[nhshbLuck: error]", e);
		}
		return resultMap;
	}

	@ResponseBody
	@RequestMapping("/luckUserList")
	public Object luckUserList(HttpServletRequest request, HttpServletResponse response, String activityKey,
			String prizeName) {
		double probabilityRandom[] = { 0.5, 0.3, 0.2 };
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String[] prize = prizeName.split(",");
			// TODO 增加配置
			resultMap.put("luckUserList", getLuckUserList(prize, probabilityRandom));
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			LoggerUtil.error("[nhshbLuck: error]", e);
		}
		return resultMap;
	}

	@ResponseBody
	@RequestMapping("/myPrize")
	public Object myPrize(HttpServletRequest request, HttpServletResponse response, String activityKey) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap.put("myPrizeList", getMyPrizeList(request, activityKey));
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			LoggerUtil.error("[MyPrize: error]", e);
		}
		return resultMap;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			double d = (double) ((1 - Math.random()) % 1);
			System.err.println(d);
		}
	}
}
