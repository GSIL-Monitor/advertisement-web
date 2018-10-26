package com.yuanshanbao.dsp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.channel.model.Channel;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.project.model.Project;

@RequestMapping({ "/dsp" })
@Controller
public class IndexDspController {

	// dsp请求广告接口
	@RequestMapping("/content")
	@ResponseBody
	public Object getContent(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject body) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			Project project = ConstantsManager.getProjectByKey("dsp");
			if (project != null) {
				Channel channelObject = ConfigManager.getChannel(body.getString("channel"));
				if (channelObject != null) {
					// Probability probability = pro
				}
			}
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setSpecAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[advertisement dsp]: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}

		return resultMap;
	}

	// 广告点击
	@RequestMapping("/{projectKey}/adShow")
	@ResponseBody
	public Object adShow(HttpServletRequest request, HttpServletResponse response, String userId, String pId,
			String channel, @PathVariable("projectKey") String projectKey) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			Project project = ConstantsManager.getProjectByKey(projectKey);
			if (project != null) {
				Channel channelObject = ConfigManager.getChannel(channel);
				if (channelObject != null) {

				}
			}
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);

		} catch (BusinessException e) {
			InterfaceRetCode.setSpecAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());

		} catch (Exception e) {
			LoggerUtil.error("[adClick index]: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}

		return resultMap;
	}

	// 广告点击
	@RequestMapping("/{projectKey}/adclick")
	@ResponseBody
	public Object adClick(HttpServletRequest request, HttpServletResponse response, String userId,
			String advertisementId, String channel, @PathVariable("projectKey") String projectKey) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			Project project = ConstantsManager.getProjectByKey(projectKey);
			if (project != null) {
				Channel channelObject = ConfigManager.getChannel(channel);
				if (channelObject != null) {

				}
			}
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);

		} catch (BusinessException e) {
			InterfaceRetCode.setSpecAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());

		} catch (Exception e) {
			LoggerUtil.error("[adClick dsp]: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}

		return resultMap;
	}
}
