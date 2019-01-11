package com.yuanshanbao.dsp.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.constant.SessionConstants;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.AESUtils;
import com.yuanshanbao.common.util.CommonUtil;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.advertisement.service.AdvertisementService;
import com.yuanshanbao.dsp.common.constant.DspConstantsManager;
import com.yuanshanbao.dsp.common.constant.RedisConstant;
import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.plan.model.Plan;
import com.yuanshanbao.dsp.quota.service.QuotaService;

@Controller
@RequestMapping({ "/j", "/i/j" })
public class RedirectJumper extends BaseController {

	public static final String PLAN_ENCRYPT_KEY = "aadecfe68c1c06a7";

	@Autowired
	private AdvertisementService advertisementService;
	@Autowired
	private RedisService redisService;

	@Autowired
	private QuotaService quotaService;

	@RequestMapping("/common")
	public String common(HttpServletRequest request, ModelMap modelMap, String id, String channel) {
		String pidValue = AESUtils.decrypt(PLAN_ENCRYPT_KEY, id);
		String[] values = pidValue.split(":");
		// 获取计划链接
		Plan plan = ConfigManager.getPlanById(Long.valueOf(values[0]));
		if (plan != null) {
			addPlanClickCount(request, values[0], channel);
			modelMap.put("url", plan.getLink());
			if (values.length == 2) {
				if (StringUtils.isEmpty(values[1])) {
					modelMap.put("baseUrl", values[1]);
				}
			}
		}
		return getFtlPath(request, "/activity/common/jump");
	}

	@RequestMapping("/adCount")
	@ResponseBody
	public Object adCount(HttpServletRequest request, ModelMap modelMap, String id, String position, String channel,
			String pId, String activityKey, boolean isShow) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (isShow) {
				addAdvertisementShowCount(request, id, channel);
			} else {
				addAdvertisementClickCount(request, id, channel);
				increConsume(pId, channel);
			}
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (Exception e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	private void addAdvertisementShowCount(HttpServletRequest request, String id, String channel) {
		String sessionKey = SessionConstants.SESSION_ADVERTISEMENT_SHOW + "_" + channel + "_" + id;
		String showValue = (String) request.getSession().getAttribute(sessionKey);
		if (StringUtils.isBlank(showValue)) {
			request.getSession().setAttribute(sessionKey, "true");
			if (StringUtils.isNotBlank(channel)) {
				redisCacheService.sadd(RedisConstant.getAdvertisementChannelAndIdKey(), id + ":" + channel);
				redisCacheService.incr(RedisConstant.getAdvertisementShowCountUVKey(null, id, channel));
			} else {
				redisCacheService.incr(RedisConstant.getAdvertisementActivityShowCountUVKey(null, id, channel));
			}
		}
		if (StringUtils.isNotBlank(channel)) {
			request.getSession().setAttribute(SessionConstants.SESSION_USER_FROM, channel);
			redisCacheService.sadd(RedisConstant.getAdvertisementChannelAndIdKey(), id + ":" + channel);
			redisCacheService.incr(RedisConstant.getAdvertisementShowCountPVKey(null, id, channel));
		}

	}

	private void addAdvertisementClickCount(HttpServletRequest request, String id, String channel) {
		channel = CommonUtil.replaceIlegalChannelName(channel);
		String sessionKey = SessionConstants.SESSION_ADVERTISEMENT_CLICK + "_" + channel + "_" + id;
		String clickValue = (String) request.getSession().getAttribute(sessionKey);
		if (StringUtils.isBlank(clickValue)) {
			request.getSession().setAttribute(sessionKey, "true");
			if (StringUtils.isNotBlank(channel)) {
				redisCacheService.incr(RedisConstant.getAdvertisementClickCountUVKey(null, id, channel));
			} else {
				redisCacheService.incr(RedisConstant.getAdvertisementActivityClickCountUVKey(null, id, channel));
			}
		}
		if (StringUtils.isNotBlank(channel)) {
			request.getSession().setAttribute(SessionConstants.SESSION_USER_FROM, channel);
			redisCacheService.sadd(RedisConstant.getAdvertisementChannelAndIdKey(), id + ":" + channel);
			redisCacheService.incr(RedisConstant.getAdvertisementClickCountPVKey(null, id, channel));
		}
	}

	private void addPlanClickCount(HttpServletRequest request, String id, String channel) {
		channel = CommonUtil.replaceIlegalChannelName(channel);
		String sessionKey = SessionConstants.SESSION_PLAN_CLICK + "_" + channel + "_" + id;
		String clickValue = (String) request.getSession().getAttribute(sessionKey);
		if (StringUtils.isBlank(clickValue)) {
			request.getSession().setAttribute(sessionKey, "true");
			if (StringUtils.isNotBlank(channel)) {
				redisCacheService.incr(RedisConstant.getPlanClickLocalCountUVKey(null, id, channel));
			}
		}
		if (StringUtils.isNotBlank(channel)) {
			redisCacheService.incr(RedisConstant.getPlanClickLocalCountPVKey(null, id, channel));
		}
	}

	private void increConsume(String probabilityId, String channel) {
		try {
			if (StringUtils.isEmpty(probabilityId)) {
				return;
			}
			Map<Long, BigDecimal> proCostMap = DspConstantsManager.getHdBidChannelMap(channel);
			// 在各个媒体消耗金额
			if (proCostMap.get(Long.valueOf(probabilityId)) == null) {
				return;
			}
			redisService.increByDouble(RedisConstant.getProbabilityBalanceCountKey(null, Long.valueOf(probabilityId)),
					proCostMap.get(Long.valueOf(probabilityId)).doubleValue());
		} catch (Exception e) {
			LoggerUtil.error("increConsume", e);
		}
	}
}
