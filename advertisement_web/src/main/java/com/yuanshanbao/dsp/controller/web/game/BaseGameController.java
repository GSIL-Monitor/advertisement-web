package com.yuanshanbao.dsp.controller.web.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuanshanbao.common.constant.SessionConstants;
import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.CookieUtils;
import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.activity.model.Activity;
import com.yuanshanbao.dsp.activity.model.ActivityCombine;
import com.yuanshanbao.dsp.activity.model.LuckUser;
import com.yuanshanbao.dsp.activity.service.ActivityCombineService;
import com.yuanshanbao.dsp.activity.service.ActivityService;
import com.yuanshanbao.dsp.advertisement.model.Advertisement;
import com.yuanshanbao.dsp.advertisement.service.AdvertisementService;
import com.yuanshanbao.dsp.channel.model.Channel;
import com.yuanshanbao.dsp.common.constant.RedisConstant;
import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.config.ConfigConstants;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.config.ConfigWrapper;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.prize.service.PrizeService;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.dsp.probability.service.ProbabilityService;
import com.yuanshanbao.paginator.domain.PageBounds;

public class BaseGameController extends BaseController {

	private static final int EXPIRE_TIME = 24 * 60 * 60;

	private String[] mobilePrefix = { "189", "188", "187", "186", "150", "151", "152", "153", "155", "156", "158",
			"159", "130", "131", "132", "133", "134", "135", "136", "137", "138", "139" };

	protected int[] positionAngles = { 331, 269, 211, 149, 91, 29 };

	@Autowired
	protected PrizeService prizeService;

	@Autowired
	protected ProbabilityService probabilityService;

	@Autowired
	protected ActivityService activityService;

	@Autowired
	protected RedisService redisService;

	@Autowired
	protected AdvertisementService advertisementService;

	@Autowired
	protected ActivityCombineService activityCombineService;

	protected void setIndex(HttpServletRequest request, Map<String, Object> modelMap, String activityKey,
			String channel, String[] prizeName, double[] probabilityRandom, String parentKey) {
		if (StringUtils.isNotBlank(parentKey)) {
			activityKey = parentKey;
		}
		request.getSession().setAttribute(activityKey + SessionConstants.SESSION_USER_FROM, channel);
		List<Long> prizeIdList = parsePickedPrize(request, activityKey, true);
		List<Probability> probabilityList = probabilityService.selectProbabilitys(request, getProjectId(request),
				activityKey, channel);
		Activity activity = activityService.selectActivity(activityKey);
		if (activity != null) {
			ConfigManager.setConfigMap(modelMap, activity.getActivityId(), channel);
		}
		int chance;
		if (StringUtils.isBlank(parentKey)) {
			int configChance = probabilityList.size();
			// 获取配置的抽奖次数
			if (activity != null && activity.getActivityId() != null) {
				configChance = Math.min(ConfigWrapper.getPickChance(activity.getActivityId(), channel, null, null),
						configChance);
			}
			chance = configChance - prizeIdList.size();
			if (chance < 0) {
				chance = 0;
			}
		} else {
			chance = 1;
		}
		modelMap.put("chance", chance);
		modelMap.put("uvCountChannel", channel);
		modelMap.put("activityKey", activityKey);
		modelMap.put("channel", ConfigManager.getChannel(channel));
		// modelMap.put("luckUserList", getLuckUserList(prizeName,
		// probabilityRandom));
		recordGameRequestCount(request, channel);
	}

	protected void pickPrizeAndSetResult(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> resultMap, String activityKey, String parentKey) {
		resultMap.put("myPrizeList", getMyPrizeList(request, activityKey));
		if (StringUtils.isNotBlank(parentKey)) {
			activityKey = parentKey;
		}
		Advertisement advertisement = pickPrize(request, response, resultMap, activityKey);
		if (StringUtils.isNotBlank(parentKey)) {
			setNextActivity(resultMap, activityKey, parentKey);
		}
		if (advertisement == null) {
			setGameJumpUrl(request, resultMap, activityKey, false);
			throw new BusinessException(ComRetCode.GAME_NO_PRIZE_ERROR);
		}
		resultMap.put("prize", advertisement);
		resultMap.put("angle", positionAngles[5 - 1]);
	}

	protected Advertisement pickPrize(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> resultMap, String activityKey) {
		// TODO Auto-generated method stub
		String channel = (String) request.getSession().getAttribute(activityKey + SessionConstants.SESSION_USER_FROM);
		List<Long> pickedPrizeIdList = parsePickedPrize(request, activityKey, true);
		Activity activity = activityService.selectActivity(activityKey);
		if (activity != null && activity.getActivityId() != null) {
			int picked = pickedPrizeIdList.size();
			if (picked >= ConfigWrapper.getPickChance(activity.getActivityId(), channel, null, null)) {
				setGameJumpUrl(request, resultMap, activityKey, false);
				throw new BusinessException(ComRetCode.GAME_NO_PRIZE_ERROR);
			}
		}
		Probability probability = probabilityService.pickPrize(request, getProjectId(request), activityKey, channel,
				pickedPrizeIdList);
		Advertisement advertisement = null;
		if (probability != null) {
			pickedPrizeIdList.add(probability.getAdvertisementId());
			setPickedPrizeCookie(request, response, activityKey, pickedPrizeIdList);
			advertisement = ConfigManager.getAdvertisement(probability.getAdvertisementId() + "");
			advertisement.addChannelToLink(channel);
		}
		return advertisement;
	}

	private void setPickedPrizeCookie(HttpServletRequest request, HttpServletResponse response, String key,
			List<Long> pickedPrizeIdList) {
		if (pickedPrizeIdList == null || pickedPrizeIdList.size() == 0) {
			return;
		}
		String prizeIds = "";
		for (Long prizeId : pickedPrizeIdList) {
			prizeIds += prizeId + ",";
		}
		String value = System.currentTimeMillis() / 1000 + ":" + prizeIds.substring(0, prizeIds.length() - 1);
		CookieUtils.setPersistCookieValue(response, key + SessionConstants.SESSION_GAME_PRIZE, value, EXPIRE_TIME);
		request.getSession().setAttribute(key + SessionConstants.SESSION_GAME_PRIZE, value);
	}

	protected List<Advertisement> getMyPrizeList(HttpServletRequest request, String activityKey) {
		String channel = (String) request.getSession().getAttribute(activityKey + SessionConstants.SESSION_USER_FROM);
		List<Long> prizeIdList = parsePickedPrize(request, activityKey, false);
		Map<Long, Advertisement> prizeMap = advertisementService.selectAdvertisementByIds(prizeIdList);
		List<Advertisement> result = new ArrayList<Advertisement>();
		Collections.reverse(prizeIdList);
		for (Long prizeId : prizeIdList) {
			Advertisement advertisement = prizeMap.get(prizeId);
			if (advertisement != null && !result.contains(advertisement)) {
				result.add(advertisement);
			}
		}
		return result;
	}

	protected List<LuckUser> getLuckUserList(String[] prizeName, double[] probabilityRandom) {
		List<LuckUser> list = new ArrayList<LuckUser>();
		for (int i = 0; i < 20; i++) {
			LuckUser luckUser = new LuckUser();
			luckUser.setName(prizeName[randomGift(probabilityRandom)]);
			luckUser.setMobile(randomMobile());
			list.add(luckUser);
		}
		return list;
	}

	protected String randomMobile() {
		String mobile = mobilePrefix[(int) (Math.random() * mobilePrefix.length)];
		mobile += "****";
		for (int i = 0; i < 4; i++) {
			mobile += (int) (Math.random() * 10);
		}
		return mobile;
	}

	protected int randomGift(double[] probability) {
		double total = 0;
		for (double pro : probability) {
			total += pro;
		}
		double value = Math.random() * total;
		double current = 0;
		for (int i = 0; i < probability.length; i++) {
			current += probability[i];
			if (value < current) {
				return i;
			}
		}
		return 1;
	}

	protected void recordGameRequestCount(HttpServletRequest request, String channel) {
		try {
			String hasRecord = (String) request.getSession().getAttribute(
					SessionConstants.SESSION_GAME_REQUEST_COUNT + channel);
			if (StringUtils.isBlank(hasRecord)) {
				if (channel == null) {
					channel = "";
				}
				request.getSession().setAttribute(SessionConstants.SESSION_GAME_REQUEST_COUNT + channel, "true");
				redisService.sadd(RedisConstant.getAdvertisementChannelKey(), channel);
				redisService.incr(RedisConstant.getRequestCountKey(channel));
			}
		} catch (Exception e) {
			LoggerUtil.error("[recordWheelsUVCount]", e);
		}
	}

	protected List<Long> parsePickedPrize(HttpServletRequest request, String activityKey, boolean isDay) {
		String hasPickedPrize = CookieUtils.getCookieValue(request, activityKey + SessionConstants.SESSION_GAME_PRIZE);
		if (StringUtils.isBlank(hasPickedPrize)) {
			hasPickedPrize = (String) request.getSession().getAttribute(
					activityKey + SessionConstants.SESSION_GAME_PRIZE);
		}
		List<Long> pickedPrizeIdList = new ArrayList<Long>();
		if (StringUtils.isNotBlank(hasPickedPrize)) {
			String[] segs = hasPickedPrize.split("\\:");
			if (segs.length == 2) {
				if (ValidateUtil.isNumber(segs[0])) {
					Long pickTime = Long.parseLong(segs[0]);
					if ((isDay && !DateUtils.isToday(new Date(pickTime * 1000)))
							|| System.currentTimeMillis() / 1000 - pickTime > EXPIRE_TIME) {
						return pickedPrizeIdList;
					}
				}
				String[] prizeIds = segs[1].split(",");
				for (String prizeId : prizeIds) {
					if (ValidateUtil.isNumber(prizeId)) {
						request.getRemoteAddr();
						pickedPrizeIdList.add(Long.parseLong(prizeId));
					}
				}
			}
		}
		return pickedPrizeIdList;
	}

	private String setGameJumpUrl(HttpServletRequest request, Map<String, Object> resultMap, String activityKey,
			boolean isSelfJump) {
		String channel = request.getParameter("channel");
		Activity activity = activityService.selectActivity(activityKey);
		// if (checkCombination(activity)) {
		// // 若是活动组合
		// ActivityCombine activityCombine =
		// activityCombineService.selectActivityCombine(activity.getActivityId());
		// ActivityCombine params = new ActivityCombine();
		// params.setParentId(activityCombine.getParentId());
		// List<ActivityCombine> list =
		// activityCombineService.selectActivityCombine(params, new
		// PageBounds());
		// for (ActivityCombine combine : list) {
		// if (combine.getSort().equals(activityCombine.getSort() + 1)) {
		// return combine.getActivity().getEntranceUrl();
		// }
		// }
		// activity =
		// activityService.selectActivity(activityCombine.getParentId());
		// }
		String gameJump = ConfigManager.getConfigValue(activity.getActivityId(), channel, null, null,
				ConfigConstants.GAME_RESULT_JUMP);
		if (StringUtils.isNotBlank(gameJump)) {
			String[] ids = gameJump.split(",");
			if (ids.length > 0) {
				Long id = getRandomActivityId(request, ids, isSelfJump);
				Activity jumpActivity = activityService.selectActivity(id);
				if (jumpActivity != null) {
					String jumpUrl = jumpActivity.getEntranceUrl() + "?stp=1"
							+ ((StringUtils.isBlank(channel)) ? "" : ("&channel=" + channel));
					if (resultMap != null) {
						resultMap.put("gameJumpUrl", jumpUrl);
						resultMap.put("gameJumpImage", jumpActivity.getImageUrl());
					}
					return jumpUrl;
				}
			}
		}
		return null;
	}

	private boolean checkCombination(Activity activity) {
		Integer combination = activity.getCombination();
		if (combination != null) {
			if (combination.equals("0")) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	private Long getRandomActivityId(HttpServletRequest request, String[] ids, boolean isSelfJump) {
		List<Long> list = new ArrayList<Long>();
		for (String id : ids) {
			Activity activity = activityService.selectActivity(Long.parseLong(id));
			if (activity != null) {
				String hasPickedPrize = CookieUtils.getCookieValue(request, activity.getKey()
						+ SessionConstants.SESSION_GAME_PRIZE);
				if (StringUtils.isBlank(hasPickedPrize)) {
					list.add(Long.parseLong(id));
				}
			}
		}
		if (list.size() > 0) {
			// int random = 0;
			// if (isSelfJump) {
			// random = (int) (Math.random() * (list.size() + 1));
			// } else {
			// random = (int) (Math.random() * list.size());
			// }
			//
			// if (random < list.size()) {
			// return list.get(random);
			// }
			return list.get(0);
		}
		return null;
	}

	/**
	 * 强制跳转到链接
	 * 
	 * @return
	 */
	protected String forceRedirectUrl(HttpServletRequest request, String channel, String pageKey) {
		if (request.getParameter("stp") == null) {
			String randomUrl = setGameJumpUrl(request, null, pageKey, true);
			if (StringUtils.isNotBlank(randomUrl)) {
				return randomUrl;
			}
		}

		if (StringUtils.isBlank(channel) || StringUtils.isBlank(pageKey)) {
			return null;
		}

		Activity activity = ConfigManager.getActivityByKey(pageKey);
		if (activity != null) {
			String redirectUrl = ConfigWrapper.getRedirectUrl(activity.getActivityId(), channel, null, null);
			if (StringUtils.isNotBlank(redirectUrl)) {
				return redirectUrl;
			}
			String hostUrl = hostRedirectUrl(request, channel, activity);
			if (StringUtils.isNotBlank(hostUrl)) {
				return hostUrl;
			}
		}
		return null;
	}

	/**
	 * 域名跳转
	 * 
	 * @return
	 */
	protected String hostRedirectUrl(HttpServletRequest request, String channel, Activity activity) {
		Channel c = ConfigManager.getChannel(channel);
		String host = request.getHeader("Host");
		String isHttps = request.getHeader("IsHttps");
		String redirectHost = "";
		String redirectUrl = "";
		// 青蓝域名强跳
		redirectHost = "www.huhabao.com";
		if (StringUtils.isNotBlank(host) && !host.contains(redirectHost) && c != null) {
			// && ChannelType.QINGLAN.equals(c.getDeliverType())
			// && ConfigWrapper.hasHostRedirect(activity.getActivityId(),
			// channel)) {
			if ("false".equals(isHttps)) {
				redirectUrl = "http://" + redirectHost + request.getRequestURI() + "?" + request.getQueryString();
			} else {
				redirectUrl = "https://" + redirectHost + request.getRequestURI() + "?" + request.getQueryString();
			}
			return redirectUrl;
		}
		return null;
	}

	protected void getChanceAndSetResult(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> resultMap, String activityKey, String channel) {
		List<Probability> probabilityList = probabilityService.selectProbabilitys(request, getProjectId(request),
				activityKey, channel);
		int configChance = probabilityList.size();
		List<Long> prizeIdList = parsePickedPrize(request, activityKey, true);
		Activity activity = activityService.selectActivity(activityKey);
		if (activity != null && activity.getActivityId() != null) {
			configChance = Math.min(ConfigWrapper.getPickChance(activity.getActivityId(), channel, null, null),
					configChance);
		}
		int chance = configChance - prizeIdList.size();
		if (chance < 0) {
			chance = 0;
		}
		resultMap.put("chance", chance);
	}

	protected void setCombineIndex(HttpServletRequest request, Map<String, Object> modelMap, String activityKey,
			String channel, String[] prizeName, double[] probabilityRandom) {
		request.getSession().setAttribute(activityKey + SessionConstants.SESSION_USER_FROM, channel);
		List<Long> prizeIdList = parsePickedPrize(request, activityKey, true);
		List<Probability> probabilityList = probabilityService.selectProbabilitys(request, getProjectId(request),
				activityKey, channel);
		Activity activity = activityService.selectActivity(activityKey);
		ActivityCombine params = new ActivityCombine();
		params.setParentId(activity.getActivityId());
		List<ActivityCombine> list = activityCombineService.selectActivityCombine(params, new PageBounds());
		ActivityCombine activityCombine = getSortActivity(list);
		if (activityCombine != null) {
			// 活动页面进行跳转
			Activity resultActivity = activityService.selectActivity(activityCombine.getActivityId());
			modelMap.put("jumpUrl", resultActivity.getEntranceUrl());
		}
	}

	private ActivityCombine getSortActivity(List<ActivityCombine> list) {
		for (ActivityCombine activityCombine : list) {
			if (activityCombine.getSort() != null & activityCombine.getSort().equals(1)) {
				return activityCombine;
			}
		}
		return null;
	}

	private void setNextActivity(Map<String, Object> resultMap, String activityKey, String parentKey) {
		Activity activity = ConfigManager.getActivityByKey(activityKey);
		Activity parentActivity = ConfigManager.getActivityByKey(parentKey);
		ActivityCombine params = new ActivityCombine();
		params.setParentId(parentActivity.getActivityId());
		List<ActivityCombine> combineList = activityCombineService.selectActivityCombine(params, new PageBounds());
		ActivityCombine activityCombine = getNextActivityCombine(activity, combineList);
		if (activityCombine != null) {
			Activity nextActivity = ConfigManager.getActivityById(activityCombine.getActivityId());
			resultMap.put("nextUrl", nextActivity.getEntranceUrl());
		}
	}

	private ActivityCombine getNextActivityCombine(Activity activity, List<ActivityCombine> combineList) {
		int index = 0;
		Map<Integer, ActivityCombine> map = new HashMap<Integer, ActivityCombine>();
		for (ActivityCombine combine : combineList) {
			if (activity.getActivityId().equals(combine.getActivityId())) {
				index = combine.getSort();
			}
			map.put(combine.getSort(), combine);
		}
		ActivityCombine nextActivityCombine = map.get(index + 1);
		return nextActivityCombine;
	}
}
