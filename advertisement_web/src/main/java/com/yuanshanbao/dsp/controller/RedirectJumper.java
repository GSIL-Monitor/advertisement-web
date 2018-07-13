package com.yuanshanbao.dsp.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.constant.SessionConstants;
import com.yuanshanbao.common.util.CommonUtil;
import com.yuanshanbao.dsp.advertisement.model.Advertisement;
import com.yuanshanbao.dsp.advertisement.service.AdvertisementService;
import com.yuanshanbao.dsp.common.constant.RedisConstant;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.controller.base.BaseController;

@Controller
@RequestMapping({ "/j", "/m/j" })
public class RedirectJumper extends BaseController {

	@Autowired
	private AdvertisementService advertisementService;

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

	@RequestMapping("/addShow")
	@ResponseBody
	public void addShow(HttpServletRequest request, ModelMap modelMap, String id, String position, String channel) {
		addAdvertisementShowCount(request, id, channel);
	}

	private void addAdvertisementShowCount(HttpServletRequest request, String id, String channel) {
		String sessionKey = SessionConstants.SESSION_ADVERTISEMENT_SHOW + "_" + channel + "_" + id;
		String showValue = (String) request.getSession().getAttribute(sessionKey);
		if (StringUtils.isBlank(showValue)) {
			request.getSession().setAttribute(sessionKey, "true");
			if (StringUtils.isNotBlank(channel)) {
				redisCacheService.incr(RedisConstant.getAdvertisementShowCountUVKey(null, id, channel));
			} else {
				redisCacheService.incr(RedisConstant.getAdvertisementActivityShowCountUVKey(null, id, channel));
			}
		}

		if (StringUtils.isNotBlank(channel)) {
			request.getSession().setAttribute(SessionConstants.SESSION_USER_FROM, channel);
			redisCacheService.sadd(RedisConstant.getAdvertisementChannelAndIdKey(), id + ":" + channel);
			redisCacheService.incr(RedisConstant.getAdvertisementShowCountPVKey(null, id, channel));
		} else {
			redisCacheService.incr(RedisConstant.getAdvertisementActivityShowCountPVKey(null, id, channel));
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
		} else {
			redisCacheService.incr(RedisConstant.getAdvertisementActivityClickCountPVKey(null, id, channel));
		}
	}

}
