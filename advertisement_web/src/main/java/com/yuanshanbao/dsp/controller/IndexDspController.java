package com.yuanshanbao.dsp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.yuanshanbao.dsp.advertisement.model.MediaInformation;
import com.yuanshanbao.dsp.advertisement.model.click.AdvertisementCountInfo;
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
	public Object getContent(HttpServletRequest request, HttpServletResponse response,
			@RequestBody MediaInformation mediaInformation) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			Project project = ConstantsManager.getProjectByKey("dsp");
			if (project != null) {
				Channel channelObject = ConfigManager.getChannel(mediaInformation.getChannel());
				if (channelObject != null) {
					List<AdvertisementDetails> seatBid = probabilityService.pickProbabilityByPlan(request,
							project.getProjectId(), channelObject, mediaInformation);
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
	public Object adShow(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AdvertisementCountInfo info) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			String channel = info.getChannel();
			String pId = info.getPid();
			String key = info.getKey();
			if (StringUtils.isEmpty(channel) || StringUtils.isEmpty(pId) || StringUtils.isEmpty(key)) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			Project project = ConstantsManager.getProjectByKey("dsp");
			if (project != null) {
				Channel channelObject = ConfigManager.getChannel(channel);
				if (channelObject != null) {
					probabilityService.recordPlanCount(pId, key, channel, false);
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
	public Object adClick(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AdvertisementCountInfo info) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			String channel = info.getChannel();
			String pId = info.getPid();
			String key = info.getKey();
			if (StringUtils.isEmpty(channel) || StringUtils.isEmpty(pId) || StringUtils.isEmpty(key)) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			Project project = ConstantsManager.getProjectByKey("dsp");
			if (project != null) {
				Channel channelObject = ConfigManager.getChannel(channel);
				if (channelObject != null) {
					probabilityService.recordPlanCount(pId, key, channel, true);
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

	// -----------------------------------get
	// 广告曝光
	@RequestMapping("/show")
	@ResponseBody
	public Object show(HttpServletRequest request, HttpServletResponse response, AdvertisementCountInfo info) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			String channel = info.getChannel();
			String pId = info.getPid();
			String key = info.getKey();
			if (StringUtils.isEmpty(channel) || StringUtils.isEmpty(pId) || StringUtils.isEmpty(key)) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			Project project = ConstantsManager.getProjectByKey("dsp");
			if (project != null) {
				Channel channelObject = ConfigManager.getChannel(info.getChannel());
				if (channelObject != null) {
					probabilityService.recordPlanCount(info.getPid(), info.getKey(), info.getChannel(), false);
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
	@RequestMapping("/click")
	@ResponseBody
	public Object click(HttpServletRequest request, HttpServletResponse response, AdvertisementCountInfo info) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			String channel = info.getChannel();
			String pId = info.getPid();
			String key = info.getKey();
			if (StringUtils.isEmpty(channel) || StringUtils.isEmpty(pId) || StringUtils.isEmpty(key)) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			Project project = ConstantsManager.getProjectByKey("dsp");
			if (project != null) {
				Channel channelObject = ConfigManager.getChannel(info.getChannel());
				if (channelObject != null) {
					probabilityService.recordPlanCount(info.getPid(), info.getKey(), info.getChannel(), true);
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
