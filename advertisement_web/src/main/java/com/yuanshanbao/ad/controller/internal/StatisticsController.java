package com.yuanshanbao.ad.controller.internal;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.ad.advertisement.service.AdvertisementService;
import com.yuanshanbao.ad.common.constant.RedisConstant;
import com.yuanshanbao.ad.controller.base.BaseController;
import com.yuanshanbao.ad.core.InterfaceRetCode;
import com.yuanshanbao.ad.statistics.model.StaffStatisticsType;
import com.yuanshanbao.common.constant.SessionConstants;
import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.MD5Util;
import com.yuanshanbao.common.util.ValidateUtil;

@Controller
@RequestMapping({ "/statistics", "/i/statistics" })
public class StatisticsController extends BaseController {

	@Autowired
	private AdvertisementService advertisementService;

	@ResponseBody
	@RequestMapping("/app")
	public Object app(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, String id,
			String position, String appId, String channel, Boolean adStat) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String sessionKey = SessionConstants.SESSION_ADVERTISEMENT_CLICK + "_" + position + "_" + id + "_"
					+ MD5Util.get(appId);
			String clickValue = (String) request.getSession().getAttribute(sessionKey);
			if (StringUtils.isBlank(clickValue)) {
				request.getSession().setAttribute(sessionKey, "true");
				redisCacheService.incr(RedisConstant.getAdvertisementClickCountKey(position, id));
				if (StringUtils.isNotBlank(channel)) {
					redisCacheService.incr(RedisConstant.getAdvertisementChannelClickCountKey(position, id, channel));
				}
				if (ValidateUtil.isNumber(id)) {
					advertisementService.increaseAdvertisementCount(Long.parseLong(id));
				}
			}
			redisCacheService.incr(RedisConstant.getAdvertisementClickPVCountKey(position, id));
			if (StringUtils.isNotBlank(channel)) {
				redisCacheService.sadd(RedisConstant.getAdvertisementChannelKey(), channel);
				redisCacheService.incr(RedisConstant.getAdvertisementChannelClickPVCountKey(position, id, channel));
			}
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[appStatistics error:", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	/**
	 * 
	 * channel 多个以，分隔
	 * 
	 * @param request
	 * @param response
	 * @param fromDay
	 * @param diffDay
	 * @param channel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/runStatistics")
	public Object app(HttpServletRequest request, HttpServletResponse response, String fromDay, Integer diffDay,
			Integer type) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (diffDay == null) {
				diffDay = 1;
			}
			if (StringUtils.isNotBlank(fromDay)) {
				Date from = DateUtils.formatToDate(fromDay, "yyyy-MM-dd");
				diffDay = DateUtils.getDiffDays(from, new Date());
			}
			if (type == null) {
				type = StaffStatisticsType.DAILY_DATA;
			}
			for (int i = 1; i <= diffDay; i++) {
				// staffStatisticsService.runAndInsertStatistics(i, type);
			}
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[statistics error:", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

}
