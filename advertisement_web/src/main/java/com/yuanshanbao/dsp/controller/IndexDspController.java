package com.yuanshanbao.dsp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.advertisement.model.Instance;
import com.yuanshanbao.dsp.advertisement.model.vo.AdvertisementDetails;
import com.yuanshanbao.dsp.channel.model.Channel;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.probability.service.ProbabilityService;
import com.yuanshanbao.dsp.project.model.Project;

@RequestMapping({ "/dsp" })
@Controller
public class IndexDspController {

	@Autowired
	private ProbabilityService probabilityService;

	// dsp请求广告接口
	@RequestMapping(value = "/content", method = RequestMethod.POST)
	@ResponseBody
	public Object getContent(HttpServletRequest request, HttpServletResponse response, @RequestBody Instance instance) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			Project project = ConstantsManager.getProjectByKey("dsp");
			if (project != null) {
				Channel channelObject = ConfigManager.getChannel(instance.getChannel());
				if (channelObject != null) {
					List<AdvertisementDetails> seatBid = probabilityService.pickProbabilityByPlan(request,
							project.getProjectId(), channelObject, instance);
					resultMap.put("seatBid", seatBid);
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

	// 广告曝光
	@RequestMapping(value = "/ad/show", method = RequestMethod.POST)
	@ResponseBody
	public Object adShow(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject body) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			String channel = (String) body.get("channel");
			String pId = (String) body.get("pId");
			if (StringUtils.isEmpty(channel)) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			if (StringUtils.isEmpty(pId)) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			Project project = ConstantsManager.getProjectByKey("dsp");
			if (project != null) {
				Channel channelObject = ConfigManager.getChannel(channel);
				if (channelObject != null) {
					probabilityService.recordPlanCount(pId, channel, false);
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
	@RequestMapping(value = "/ad/click", method = RequestMethod.POST)
	@ResponseBody
	public Object adClick(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject body) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			String channel = (String) body.get("channel");
			String pId = (String) body.get("pId");
			if (StringUtils.isEmpty(channel)) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			if (StringUtils.isEmpty(pId)) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			Project project = ConstantsManager.getProjectByKey("dsp");
			if (project != null) {
				Channel channelObject = ConfigManager.getChannel(channel);
				if (channelObject != null) {
					probabilityService.recordPlanCount(pId, channel, true);
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
