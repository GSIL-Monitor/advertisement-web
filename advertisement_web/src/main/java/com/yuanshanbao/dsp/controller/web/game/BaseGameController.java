package com.yuanshanbao.dsp.controller.web.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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

	private static final int EXPIRE_TIME_DAY = 24 * 60 * 60;
	private static final int EXPIRE_TIME_WEEK = 24 * 60 * 60;

	private String[] mobilePrefix = { "189", "188", "187", "186", "150", "151", "152", "153", "155", "156", "158",
			"159", "130", "131", "132", "133", "134", "135", "136", "137", "138", "139" };

	private String[] subChanceProbability = { "4", "3", "2", "1" };

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
			String channel, String[] prizeName, double[] probabilityRandom) {
		request.getSession().setAttribute(activityKey + SessionConstants.SESSION_USER_FROM, channel);
		List<Long> prizeIdList = parsePickedPrize(request, channel, true);
		List<Probability> probabilityList = probabilityService.selectProbabilitys(request, getProjectId(request),
				activityKey, channel);
		Activity activity = activityService.selectActivity(activityKey);
		if (activity != null) {
			ConfigManager.setConfigMap(modelMap, activity.getActivityId(), channel);
			setRuleConfig(activity, channel, modelMap);
		}
		modelMap.put("chance", getChance(activity, probabilityList, prizeIdList, activityKey, channel));
		modelMap.put("uvCountChannel", channel);
		modelMap.put("activityKey", activityKey);
		modelMap.put("channel", ConfigManager.getChannel(channel));
		String[] popAdId = modelMap.get(ConfigConstants.ADVERTISEMENT_POPUP_CONFIG).toString().split(",");
		if (popAdId != null && popAdId[0].length() > 0) {
			Advertisement popUpAdvertisement = ConfigManager.getAdvertisement(popAdId[0]);
			if (popUpAdvertisement != null) {
				// TODO 广告位置是否需要做处理
				popUpAdvertisement.setLink(popUpAdvertisement.getJumperLink("", channel));
				modelMap.put("popUpAdvertisement", popUpAdvertisement);
			}
		}
		recordGameRequestCount(request, channel);
	}

	protected void pickPrizeAndSetResult(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> resultMap, String activityKey) {
		resultMap.put("myPrizeList", getMyPrizeList(request, activityKey));
		Advertisement advertisement = pickPrize(request, response, resultMap, activityKey);
		if (advertisement == null) {
			setGameJumpUrl(request, resultMap, activityKey, false);
			throw new BusinessException(ComRetCode.GAME_NO_PRIZE_ERROR);
		}
		resultMap.put("prize", advertisement);
		resultMap.put("angle", positionAngles[5 - 1]);
	}

	protected Advertisement pickPrize(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> resultMap, String activityKey) {
		String channel = request.getParameter("channel");
		// 该渠道已抽取的奖品
		List<Long> pickedPrizeIdList = parsePickedPrize(request, channel, true);
		List<Long> pickAllPrizeIdList = parseAllPickedPrize(request, channel, true);
		Activity activity = ConfigManager.getActivityByKey(activityKey);
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
			pickAllPrizeIdList.add(probability.getAdvertisementId());
			setPickedPrizeCookie(request, response, channel, pickedPrizeIdList);
			setAllPickedPrizeCookie(request, response, pickAllPrizeIdList);
			advertisement = ConfigManager.getAdvertisement(probability.getAdvertisementId() + "");
			// advertisement.addChannelToLink(channel);
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
		// 根据渠道设置以及抽取过得奖品
		CookieUtils.setPersistCookieValue(response, key + SessionConstants.SESSION_GAME_CHANNEL_PRIZE, value,
				EXPIRE_TIME_DAY);
		request.getSession().setAttribute(key + SessionConstants.SESSION_GAME_CHANNEL_PRIZE, value);
	}

	private void setAllPickedPrizeCookie(HttpServletRequest request, HttpServletResponse response,
			List<Long> pickedPrizeIdList) {
		if (pickedPrizeIdList == null || pickedPrizeIdList.size() == 0) {
			return;
		}
		String prizeIds = "";
		for (Long prizeId : pickedPrizeIdList) {
			prizeIds += prizeId + ",";
		}
		String value = System.currentTimeMillis() / 1000 + ":" + prizeIds.substring(0, prizeIds.length() - 1);
		// 根据渠道设置以及抽取过得奖品
		CookieUtils.setPersistCookieValue(response, SessionConstants.SESSION_GAME_ALL_PRIZE, value, EXPIRE_TIME_WEEK);
		request.getSession().setAttribute(SessionConstants.SESSION_GAME_ALL_PRIZE, value);
	}

	protected void pickSubPrizeAndSetResult(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> resultMap, String activityKey, String parentKey, String channel) {
		Advertisement advertisement = pickSubPrize(request, response, resultMap, activityKey, parentKey, channel);
		if (advertisement == null) {
			setNextActivity(resultMap, activityKey, parentKey);
			if (resultMap.get("nextKey") != null) {
				Activity activity = ConfigManager.getActivityByKey((String) resultMap.get("nextKey"));
				List<Probability> probabilityList = probabilityService.selectProbabilitys(request,
						getProjectId(request), parentKey, channel);
				Map<Long, List<Probability>> chanceMap = allocatePrize(parentKey, activityKey, probabilityList, channel);
				if (chanceMap.get(activity.getActivityId()) != null
						&& chanceMap.get(activity.getActivityId()).size() > 0) {
					resultMap.put("gameJumpImage", activity.getImageUrl());
					resultMap.put("gameJumpUrl", activity.getEntranceUrl());
					throw new BusinessException(ComRetCode.GAME_NO_PRIZE_AND_JUMP);
				} else {
					throw new BusinessException(ComRetCode.GAME_NO_PRIZE_ERROR);
				}
			}
		}
		resultMap.put("prize", advertisement);
		resultMap.put("angle", positionAngles[5 - 1]);
	}

	private Advertisement pickSubPrize(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> resultMap, String activityKey, String parentKey, String channel) {
		Activity activity = ConfigManager.getActivityByKey(activityKey);
		List<Long> pickedPrizeIdList = parsePickedPrize(request, channel, true);
		List<Long> pickAllPrizeIdList = parseAllPickedPrize(request, channel, true);
		// 默认按照概率进行排序
		List<Probability> probabilityList = probabilityService.selectProbabilitys(request, getProjectId(request),
				parentKey, channel);
		// 为组合下的活动分配奖品
		Map<Long, List<Probability>> chanceMap = allocatePrize(parentKey, activityKey, probabilityList, channel);
		List<Probability> subList = chanceMap.get(activity.getActivityId());
		if (subList == null) {
			return null;
		}
		Probability probability = probabilityService.pickSubPrize(subList, pickedPrizeIdList);
		Advertisement advertisement = null;
		// 设置Cookie
		if (probability != null) {
			pickedPrizeIdList.add(probability.getAdvertisementId());
			pickAllPrizeIdList.add(probability.getAdvertisementId());
			setPickedPrizeCookie(request, response, channel, pickedPrizeIdList);
			setAllPickedPrizeCookie(request, response, pickAllPrizeIdList);
			advertisement = ConfigManager.getAdvertisement(probability.getAdvertisementId() + "");
			resultMap.put("pId", probability.getProbabilityId());
			// advertisement.addChannelToLink(channel);
		}
		return advertisement;
	}

	protected List<Advertisement> getMyPrizeList(HttpServletRequest request, String activityKey) {
		List<Long> prizeIdList = parseAllPickedPrize(request, activityKey, false);
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

	protected List<Long> parsePickedPrize(HttpServletRequest request, String channel, boolean isDay) {
		String hasPickedPrize = CookieUtils.getCookieValue(request, channel
				+ SessionConstants.SESSION_GAME_CHANNEL_PRIZE);
		if (StringUtils.isBlank(hasPickedPrize)) {
			hasPickedPrize = (String) request.getSession().getAttribute(
					channel + SessionConstants.SESSION_GAME_CHANNEL_PRIZE);
		}
		List<Long> pickedPrizeIdList = new ArrayList<Long>();
		if (StringUtils.isNotBlank(hasPickedPrize)) {
			String[] segs = hasPickedPrize.split("\\:");
			if (segs.length == 2) {
				if (ValidateUtil.isNumber(segs[0])) {
					Long pickTime = Long.parseLong(segs[0]);
					if ((isDay && !DateUtils.isToday(new Date(pickTime * 1000)))
							|| System.currentTimeMillis() / 1000 - pickTime > EXPIRE_TIME_DAY) {
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

	protected List<Long> parseAllPickedPrize(HttpServletRequest request, String channel, boolean isDay) {
		String hasPickedPrize = CookieUtils.getCookieValue(request, SessionConstants.SESSION_GAME_ALL_PRIZE);
		if (StringUtils.isBlank(hasPickedPrize)) {
			hasPickedPrize = (String) request.getSession().getAttribute(SessionConstants.SESSION_GAME_ALL_PRIZE);
		}
		List<Long> pickedPrizeIdList = new ArrayList<Long>();
		if (StringUtils.isNotBlank(hasPickedPrize)) {
			String[] segs = hasPickedPrize.split("\\:");
			if (segs.length == 2) {
				if (ValidateUtil.isNumber(segs[0])) {
					Long pickTime = Long.parseLong(segs[0]);
					if (System.currentTimeMillis() / 1000 - pickTime > EXPIRE_TIME_WEEK) {
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

		if (checkCombination(activity)) {
			// 若是活动组合
			ActivityCombine activityCombine = activityCombineService.selectActivityCombine(activity.getActivityId());
			ActivityCombine params = new ActivityCombine();
			params.setParentId(activityCombine.getParentId());
			List<ActivityCombine> list = activityCombineService.selectActivityCombine(params, new PageBounds());
			for (ActivityCombine combine : list) {
				if (combine.getSort().equals(activityCombine.getSort() + 1)) {
					return combine.getActivity().getEntranceUrl();
				}
			}
			activity = activityService.selectActivity(activityCombine.getParentId());
		}

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
				// String hasPickedPrize = CookieUtils.getCookieValue(request,
				// activity.getKey()
				// + SessionConstants.SESSION_GAME_PRIZE);
				// if (StringUtils.isBlank(hasPickedPrize)) {
				// list.add(Long.parseLong(id));
				// }
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
			Map<String, Object> resultMap, String activityKey) {
		String channel = request.getParameter("channel");
		List<Probability> probabilityList = probabilityService.selectProbabilitys(request, getProjectId(request),
				activityKey, channel);
		int configChance = probabilityList.size();
		List<Long> prizeIdList = parsePickedPrize(request, channel, true);
		Activity activity = activityService.selectActivity(activityKey);
		if (activity != null && activity.getActivityId() != null) {
			configChance = Math.min(ConfigWrapper.getPickChance(activity.getActivityId(), channel, null, null),
					configChance);
		}
		int chance = configChance - prizeIdList.size();

		if (chance < 0) {
			chance = 0;
		} else {
			chance--;
		}
		resultMap.put("chance", chance);
	}

	protected void setCombineIndex(HttpServletRequest request, Map<String, Object> modelMap, String activityKey,
			String channel, String[] prizeName, double[] probabilityRandom) {
		request.getSession().setAttribute(activityKey + SessionConstants.SESSION_USER_FROM, channel);
		Activity activity = ConfigManager.getActivityByKey(activityKey);
		if (activity == null) {
			return;
		}
		ActivityCombine params = new ActivityCombine();
		params.setParentId(activity.getActivityId());
		List<ActivityCombine> list = activityCombineService.selectActivityCombine(params, new PageBounds());
		// List<ActivityCombine> combineList2 =
		// ConfigManager.getActivityCombineList(activity.getActivityId());
		ActivityCombine activityCombine = getFirstSubActivity(list);
		if (activityCombine != null) {
			// 活动页面进行跳转
			Activity resultActivity = ConfigManager.getActivityById(activityCombine.getActivityId());
			modelMap.put("jumpUrl", resultActivity.getEntranceUrl());
			request.getSession().setAttribute(channel + SessionConstants.SESSION_ACTIVITY_COMBINE_KEY, activityKey);
			request.getSession().setAttribute(SessionConstants.SESSION_ACTIVITY_COMBINE_CHANNEL, channel);
		}
		recordUvCount(request, channel);
	}

	private ActivityCombine getFirstSubActivity(List<ActivityCombine> list) {
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
			resultMap.put("nextKey", nextActivity.getKey());
		} else {
			throw new BusinessException(ComRetCode.GAME_NO_PRIZE_ERROR);
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

	private Integer getChance(Activity activity, List<Probability> probabilityList, List<Long> prizeIdList,
			String activityKey, String channel) {
		int chance;
		if (StringUtils.isNotBlank(activityKey)) {
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
		return chance;
	}

	protected void subIndex(HttpServletRequest request, Map<String, Object> resultMap, String parentKey,
			String activityKey, String channel, String[] prizeName, double[] probabilityRandom) {
		List<Probability> probabilityList = probabilityService.selectProbabilitys(request, getProjectId(request),
				parentKey, channel);
		Activity activity = activityService.selectActivity(activityKey);
		if (activity != null) {
			ConfigManager.setConfigMap(resultMap, activity.getActivityId(), channel);
			setRuleConfig(activity, channel, resultMap);
		}
		// 获取活动奖品
		getSubActivityPrize(request, resultMap, parentKey, activityKey, channel, probabilityList);
		recordUvCount(request, channel);
	}

	private void getSubActivityPrize(HttpServletRequest request, Map<String, Object> resultMap, String parentKey,
			String activityKey, String channel, List<Probability> probabilityList) {
		List<Long> prizeIdList = parsePickedPrize(request, channel, true);
		Activity activity = ConfigManager.getActivityByKey(activityKey);
		Map<Long, List<Probability>> chanceMap = allocatePrize(parentKey, activityKey, probabilityList, channel);
		resultMap.put("chance", getActivityLeftChance(chanceMap.get(activity.getActivityId()), prizeIdList));
	}

	private int getActivityLeftChance(List<Probability> list, List<Long> prizeIdList) {
		if (list == null) {
			return 0;
		}
		int chance = list.size();
		for (Probability probability : list) {
			if (prizeIdList.contains(probability.getAdvertisementId())) {
				chance--;
			}
		}
		if (chance < 0) {
			return 0;
		}
		return chance;
	}

	private Map<Long, List<Probability>> allocatePrize(String parentKey, String activityKey,
			List<Probability> probabilityList, String channel) {
		Activity parentActivity = ConfigManager.getActivityByKey(parentKey);
		ActivityCombine activityCombine = new ActivityCombine();
		activityCombine.setParentId(parentActivity.getActivityId());
		List<ActivityCombine> list = activityCombineService.selectActivityCombine(activityCombine, new PageBounds());
		// List<ActivityCombine> list2 =
		// ConfigManager.getActivityCombineList(parentActivity.getActivityId());
		// 若活动奖品数量过少，则不进行分配
		Iterator<ActivityCombine> iterator = list.iterator();
		while (iterator.hasNext()) {
			if (list.size() == 1) {
				break;
			}
			ActivityCombine ac = iterator.next();
			if (!checkActivityMinSize(ac, probabilityList, channel)) {
				iterator.remove();
			} else {
				break;
			}
		}
		return setChanceBySubActivityKey(list, probabilityList, channel);

	}

	private Map<Long, List<Probability>> setChanceBySubActivityKey(List<ActivityCombine> list,
			List<Probability> probabilityList, String channel) {
		Map<Long, List<Probability>> map = new HashMap<Long, List<Probability>>();
		List<Probability> getList = new ArrayList<Probability>(probabilityList);
		for (ActivityCombine ac : list) {
			List<Probability> acList = new ArrayList<Probability>();
			// 若只有一个活动，将次数全部给他
			if (list.size() == 1) {
				acList.addAll(probabilityList);
				map.put(ac.getActivityId(), acList);
				break;
			}
			int chance = getChance(ac, probabilityList, channel);
			for (int i = 0; i < chance; i++) {
				if (getList.size() > 0) {
					acList.add(getList.get(0));
					getList.remove(0);
				} else {
					break;
				}
			}
			map.put(ac.getActivityId(), acList);
		}

		return map;
	}

	private boolean checkActivityMinSize(ActivityCombine activityCombine, List<Probability> probabilityList,
			String channel) {
		double chance = getChance(activityCombine, probabilityList, channel);
		double minChance = Double.valueOf(ConfigManager.getConfigValue(activityCombine.getActivityId(), null,
				ConfigConstants.PICK_PRIZE_MIN_CHANCE_CONFIG));
		if (chance < minChance) {
			return false;
		}
		return true;
	}

	// 根据次序与分配比例分配奖品个数
	private int getChance(ActivityCombine activityCombine, List<Probability> probabilityList, String channel) {
		String allocateConfig = ConfigManager.getConfigValue(activityCombine.getParentId(), channel,
				ConfigConstants.ACTIVITY_COMBINE_PRIZE_ALLOCATE_CONFIG);
		List<String> countPro = new ArrayList<String>();
		// 设置奖品分配比例
		// if (allocateConfig != null) {
		if (StringUtils.isNotEmpty(allocateConfig)) {
			String[] config = allocateConfig.split(",");
			countPro = Arrays.asList(config);
		} else {
			ActivityCombine params = new ActivityCombine();
			params.setParentId(activityCombine.getParentId());
			List<ActivityCombine> combineList = activityCombineService.selectActivityCombine(params, new PageBounds());
			int size = combineList.size();
			for (int i = size; i > 0; i--) {
				countPro.add(String.valueOf(i));
			}
		}
		int total = 0;
		for (String s : countPro) {
			total += Integer.valueOf(s);
		}
		double chance = probabilityList.size() * Double.valueOf(countPro.get(activityCombine.getSort() - 1)) / total;
		if (chance < 1) {
			return 0;
		}
		return (int) Math.round(chance);
	}

	protected void getSubChanceAndSetResult(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> resultMap, String parentKey, String activityKey, String channel) {
		List<Long> prizeIdList = parsePickedPrize(request, channel, true);
		Activity activity = ConfigManager.getActivityByKey(activityKey);
		List<Probability> probabilityList = probabilityService.selectProbabilitys(request, getProjectId(request),
				parentKey, channel);
		Map<Long, List<Probability>> chanceMap = allocatePrize(parentKey, activityKey, probabilityList, channel);

		List<Probability> activityProbabilityList = chanceMap.get(activity.getActivityId());
		int chance = getActivityLeftChance(activityProbabilityList, prizeIdList);
		if (chance <= 0) {
			chance = 0;
		} else {
			chance--;
		}
		resultMap.put("chance", chance);
	}

	private void setRuleConfig(Activity activity, String channel, Map<String, Object> modelMap) {
		String content = ConfigManager.getConfigValue(activity.getActivityId(), channel,
				ConfigConstants.ACTIVITY_RULE_CONTENT_CONFIG);
		if (StringUtils.isNotEmpty(content)) {
			String[] rule = content.split("\\|");
			modelMap.put("rule", rule);
		}
	}
}
