package com.yuanshanbao.dsp.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuanshanbao.common.constant.SessionConstants;
import com.yuanshanbao.common.util.ValidateUtil;
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
		String sessionKey = SessionConstants.SESSION_ADVERTISEMENT_CLICK + "_" + position + "_" + id;
		String clickValue = (String) request.getSession().getAttribute(sessionKey);
		if (StringUtils.isBlank(clickValue)) {
			request.getSession().setAttribute(sessionKey, "true");
			redisCacheService.incr(RedisConstant.getAdvertisementClickCountKey(position, id));
			if (StringUtils.isNotBlank(channel)) {
				redisCacheService.incr(RedisConstant.getAdvertisementChannelClickCountKey(position, id, channel));
			}
			if (ValidateUtil.isNumber(id)) {
				// advertisementService.increaseAdvertisementCount(Long.parseLong(id));
			}
		}
		redisCacheService.incr(RedisConstant.getAdvertisementClickPVCountKey(position, id));
		if (StringUtils.isNotBlank(channel)) {
			redisCacheService.sadd(RedisConstant.getAdvertisementChannelKey(), channel);
			redisCacheService.incr(RedisConstant.getAdvertisementChannelClickPVCountKey(position, id, channel));
		}
		Advertisement advertisement = ConfigManager.getAdvertisement(id);
		if (advertisement != null) {
			modelMap.put("url", advertisement.getLink());
		}
		return "/web/activity/common/jump";
	}

}
