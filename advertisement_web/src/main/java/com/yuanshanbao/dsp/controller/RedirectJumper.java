package com.yuanshanbao.dsp.controller;

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
import com.yuanshanbao.common.util.CommonUtil;
import com.yuanshanbao.dsp.advertisement.model.Advertisement;
import com.yuanshanbao.dsp.advertisement.service.AdvertisementService;
import com.yuanshanbao.dsp.common.constant.RedisConstant;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.quota.service.QuotaService;

@Controller
@RequestMapping({ "/j", "/m/j" })
public class RedirectJumper extends BaseController {

	@Autowired
	private AdvertisementService advertisementService;

	@Autowired
	private QuotaService quotaService;

	@RequestMapping("/common")
	public String common(HttpServletRequest request, ModelMap modelMap, String id, String position, String channel) {
		addAdvertisementClickCount(request, id, channel);
		Advertisement advertisement = ConfigManager.getAdvertisement(id);
		if (advertisement != null) {
			if (StringUtils.isNotBlank(advertisement.getLink())) {
				String adUrl = advertisement.getLink();
				return redirect(adUrl);
			}
			modelMap.put("url", advertisement.getLink());
		}
		return "/web/activity/common/jump";
	}

	@RequestMapping("/adCount")
	@ResponseBody
	public Object adCount(HttpServletRequest request, ModelMap modelMap, String id, String position, String channel,
			String activityKey, boolean isShow) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (isShow) {
				addAdvertisementShowCount(request, id, channel);
			} else {
				addAdvertisementClickCount(request, id, channel);
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
}
