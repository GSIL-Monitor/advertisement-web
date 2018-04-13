package com.yuanshanbao.ms.controller.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.activity.model.Activity;
import com.yuanshanbao.dsp.activity.service.ActivityService;
import com.yuanshanbao.dsp.advertisement.model.Advertisement;
import com.yuanshanbao.dsp.advertisement.service.AdvertisementService;
import com.yuanshanbao.dsp.advertisement.service.AdvertisementStrategyService;
import com.yuanshanbao.dsp.advertiser.service.AdvertiserService;
import com.yuanshanbao.dsp.channel.model.Channel;
import com.yuanshanbao.dsp.channel.service.ChannelService;
import com.yuanshanbao.dsp.config.model.Function;
import com.yuanshanbao.dsp.config.service.ConfigService;
import com.yuanshanbao.dsp.config.service.FunctionService;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/activity")
public class AdminActivityController extends PaginationController {

	private static final String PAGE_LIST = "advertisement/activity/listActivity";

	private static final String PAGE_INSERT = "advertisement/activity/insertActivity";

	private static final String PAGE_UPDATE = "advertisement/activity/updateActivity";

	private static final String PAGE_VIEW = "advertisement/activity/viewActivity";

	private static final String PAGE_CONFIG = "advertisement/activity/configActivity";

	private static final String PAGE_STRATEGY = "advertisement/activity/configAdvertisement";

	private static final String PAGE_ADVERTISEMENT_CONFIG = "advertisement/activity/configAdvertise";

	private static final String PAGE_GIFTS_CONFIG = "advertisement/activity/configGifts";

	private static final String PAGE_WHEELS_CONFIG = "advertisement/activity/configWheels";

	private static final String PAGE_CHANNEL_CONFIG = "advertisement/activity/configChannel";

	private static final String PAGE_GIFTS_CHANNEL_CONFIG = "advertisement/activity/configGiftsChannel";

	@Autowired
	private ActivityService activityService;

	@Autowired
	private FunctionService functionService;

	@Autowired
	private ConfigService configService;

	@Autowired
	private AdvertisementStrategyService strategyService;

	@Autowired
	private AdvertisementService advertisementService;

	@Autowired
	private AdvertiserService advertiserService;

	@Autowired
	private ChannelService channelService;

	@RequestMapping("/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("isSimple", "0");
		return PAGE_LIST;
	}

	@RequestMapping("/giftList.do")
	public String simpleList(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("isSimple", "1");
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, Activity activity, HttpServletRequest request, HttpServletResponse response) {
		Object object = activityService.selectActivitys(activity, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/insertWindow.do")
	public String insertWindow(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());
		return PAGE_INSERT;
	}

	@ResponseBody
	@RequestMapping("/insert.do")
	public Object insert(Activity activity, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {

			validateParameters(activity);
			activityService.insertActivity(activity);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(HttpServletRequest request, HttpServletResponse response, Activity activity) {
		List<Activity> list = activityService.selectActivitys(activity, new PageBounds());
		if (list != null && list.size() >= 0) {
			activity = list.get(0);
		}

		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());
		request.setAttribute("itemEdit", activity);
		return PAGE_UPDATE;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object update(Activity activity, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();

		try {

			validateParameters(activity);
			activityService.updateActivity(activity);

			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}

	@ResponseBody
	@RequestMapping("/delete.do")
	public Object delete(Long activityId, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			if (activityId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			Activity activity = new Activity();
			activity.setActivityId(activityId);
			activity.setStatus(CommonStatus.OFFLINE);
			activityService.updateActivity(activity);

			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}

	@RequestMapping("/view.do")
	public String view(Activity activity, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		List<Activity> list = activityService.selectActivitys(activity, new PageBounds());
		if (list != null && list.size() >= 0) {
			activity = list.get(0);
		}
		request.setAttribute("itemEdit", activity);
		return PAGE_VIEW;
	}

	

	// @RequestMapping("/adConfigWindow.do")
	// public String adConfig(Long activityId, HttpServletRequest request,
	// HttpServletResponse response, ModelMap modelMap) {
	// List<FunctionVo> list =
	// configService.getFunctionVoListByActivityId(activityId);
	// request.setAttribute("functionList", list);
	// if (activityId != null) {
	// request.setAttribute("activityId", activityId.toString());
	// }
	// return PAGE_STRATEGY;
	// }

	// @ResponseBody
	// @RequestMapping("/adConfig.do")
	// public Object adConfig(Long activityId, HttpServletRequest request,
	// HttpServletResponse response)
	// throws IOException {
	// Map<String, Object> result = new HashMap<String, Object>();
	// try {
	// configService.insertAdvertisementConfig(request, activityId);
	// AdminServerController.refreshConfirm();
	// InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
	// } catch (BusinessException e) {
	// InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(),
	// e.getMessage());
	// } catch (Exception e) {
	// LoggerUtil.error("", e);
	// }
	// return result;
	// }

	/**
	 * 广告配置页 原广告配置页为: 一个function配置项对应一个包含广告的全部选项(已选择在上方，未选择按照已有排序)
	 * 修改为：左右分割，左侧为function以及默认值，右侧为advertisement的选择框
	 * 
	 * @param activityId
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/adConfigWindow.do")
	public String window(Long activityId, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		Advertisement advertisement = new Advertisement();
		List<Advertisement> advertisementList = advertisementService.selectAdvertisement(advertisement,
				new PageBounds());
		Function function = new Function();
		function.setCategory(7);
		List<Function> functionList = functionService.selectFunctions(function, new PageBounds());

		request.setAttribute("advertisementList", advertisementList);

		List<Long> functionIds = new ArrayList<Long>();
		for (Function f : functionList) {
			functionIds.add(f.getFunctionId());
		}

		Map<Long, String> map = configService.selectConfigByActivityAndFunctionIds(activityId, functionIds);

		for (Function f : functionList) {
			String defaultAction = map.get(f.getFunctionId());
			if (!StringUtils.isBlank(defaultAction)) {
				f.setDefaultAction(defaultAction);
			}
		}
		if (activityId != null) {
			request.setAttribute("activityId", activityId);
		}
		request.setAttribute("functionList", functionList);
		return PAGE_ADVERTISEMENT_CONFIG;
	}

	@RequestMapping("/configWheelsWindows.do")
	public String configWheelsWindows(Long activityId, String channelkey, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		Activity activity = activityService.selectActivity(activityId);
		Channel channel = channelService.selectChannel(channelkey);
		if (activity != null) {
			request.setAttribute("activityName", activity.getName());
		}
		if (channel != null) {
			request.setAttribute("channelName", channel.getName());
		}
		request.setAttribute("activityId", activityId);
		request.setAttribute("channelkey", channelkey);
		return PAGE_WHEELS_CONFIG;
	}

	@RequestMapping("/configChannelWindows.do")
	public String configChannelWindows(Long activityId, HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		Activity activity = activityService.selectActivity(activityId);

		if (activity != null) {
			request.setAttribute("activityName", activity.getName());
		}

		request.setAttribute("activityId", activityId);
		return PAGE_CHANNEL_CONFIG;
	}

	@RequestMapping("/configGiftsChannelWindows.do")
	public String configGiftsChannelWindows(Long activityId, HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		Activity activity = activityService.selectActivity(activityId);

		if (activity != null) {
			request.setAttribute("activityName", activity.getName());
		}

		request.setAttribute("activityId", activityId);
		return PAGE_GIFTS_CHANNEL_CONFIG;
	}

	@ResponseBody
	@RequestMapping("/advertiserSearch.do")
	public Object advertiserSearch(Long advertiserId, HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Advertisement> advertisements = null;
			if (advertiserId != null) {
				Advertisement advertisement = new Advertisement();
				advertisement.setAdvertiserId(advertiserId);
				advertisements = advertisementService.selectAdvertisement(advertisement, new PageBounds());
			}
			if (advertisements != null) {
				result.put("advertisements", advertisements);
			}
		} catch (Exception e) {
			LoggerUtil.error("[advertiserSearch error]：", e);
		}

		return result;
	}

}
