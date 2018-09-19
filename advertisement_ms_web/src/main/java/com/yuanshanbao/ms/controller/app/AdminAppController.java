package com.yuanshanbao.ms.controller.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.advertisement.model.Advertisement;
import com.yuanshanbao.dsp.advertisement.service.AdvertisementService;
import com.yuanshanbao.dsp.advertisement.service.AdvertisementStrategyService;
import com.yuanshanbao.dsp.advertiser.service.AdvertiserService;
import com.yuanshanbao.dsp.app.model.App;
import com.yuanshanbao.dsp.app.model.AppType;
import com.yuanshanbao.dsp.channel.service.ChannelService;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.config.model.Config;
import com.yuanshanbao.dsp.config.model.ConfigCategory;
import com.yuanshanbao.dsp.config.model.Function;
import com.yuanshanbao.dsp.config.service.ConfigService;
import com.yuanshanbao.dsp.config.service.FunctionService;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.ms.controller.common.AdminServerController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/app")
public class AdminAppController extends PaginationController {

	private static final String PAGE_LIST = "advertisement/app/listApp";

	private static final String PAGE_INSERT = "advertisement/app/insertApp";

	private static final String PAGE_CONFIG = "advertisement/app/configApp";

	private static final String PAGE_GIFTS_CONFIG = "advertisement/app/configGifts";

	private static final String PAGE_WHEELS_CONFIG = "advertisement/app/configWheels";

	private static final String PAGE_CHANNEL_CONFIG = "advertisement/app/configChannel";

	private static final String PAGE_GIFTS_CHANNEL_CONFIG = "advertisement/app/configGiftsChannel";

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

	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, App app, HttpServletRequest request, HttpServletResponse response) {
		PageList<App> pageList = new PageList<App>();
		Long count = 1L;
		for (Entry<String, String> entry : AppType.getCodeDescriptionMap().entrySet()) {
			App params = new App();
			params.setAppId(count);
			params.setAppKey(entry.getKey());
			params.setAppName(entry.getValue());
			count++;
			pageList.add(params);
		}
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/insertWindow.do")
	public String insertWindow(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());
		return PAGE_INSERT;
	}

	@RequestMapping("/configWindow.do")
	public String configWindow(Long appId, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {

		Map<Integer, ConfigCategory> map = configService.getConfigCategoryList(null, null, AppType.getkey(appId));
		request.setAttribute("configCategoryList", map.values());
		request.setAttribute("appId", appId);
		request.setAttribute("appKey", AppType.getDescription(appId));
		return PAGE_CONFIG;
	}

	@ResponseBody
	@RequestMapping("/config.do")
	public Object config(Long appId, Function function, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (function == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}

			configService.updateActivityConfig(request, null, null, AppType.getkey(appId));
			Config config = new Config();
			config.setStatus(CommonStatus.ONLINE);
			List<Config> configsList = configService.selectConfig(config, new PageBounds());
			ConfigManager.refreshConfig(null, null, null, null, null, configsList, null, null, null, null, null);
			AdminServerController.refreshConfirm();

			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("", e);
		}
		return result;
	}

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
	// @RequestMapping("/adConfigWindow.do")
	// public String window(Long activityId, HttpServletRequest request,
	// HttpServletResponse response, ModelMap modelMap) {
	// Advertisement advertisement = new Advertisement();
	// List<Advertisement> advertisementList =
	// advertisementService.selectAdvertisement(advertisement,
	// new PageBounds());
	// Function function = new Function();
	// function.setCategory(7);
	// List<Function> functionList = functionService.selectFunctions(function,
	// new PageBounds());
	//
	// request.setAttribute("advertisementList", advertisementList);
	//
	// List<Long> functionIds = new ArrayList<Long>();
	// for (Function f : functionList) {
	// functionIds.add(f.getFunctionId());
	// }
	//
	// Map<Long, String> map =
	// configService.selectConfigByActivityAndFunctionIds(activityId,
	// functionIds);
	//
	// for (Function f : functionList) {
	// String defaultAction = map.get(f.getFunctionId());
	// if (!StringUtils.isBlank(defaultAction)) {
	// f.setDefaultAction(defaultAction);
	// }
	// }
	// if (activityId != null) {
	// request.setAttribute("activityId", activityId);
	// }
	// request.setAttribute("functionList", functionList);
	// return PAGE_ADVERTISEMENT_CONFIG;
	// }

	/*
	 * @RequestMapping("/configWheelsWindows.do") public String
	 * configWheelsWindows(Long activityId, String channelkey,
	 * HttpServletRequest request, HttpServletResponse response, ModelMap
	 * modelMap) { Activity activity =
	 * activityService.selectActivity(activityId); Channel channel =
	 * channelService.selectChannel(channelkey); if (activity != null) {
	 * request.setAttribute("activityName", activity.getName()); } if (channel
	 * != null) { request.setAttribute("channelName", channel.getName()); }
	 * request.setAttribute("activityId", activityId);
	 * request.setAttribute("channelkey", channelkey); return
	 * PAGE_WHEELS_CONFIG; }
	 * 
	 * @RequestMapping("/configChannelWindows.do") public String
	 * configChannelWindows(Long activityId, HttpServletRequest request,
	 * HttpServletResponse response, ModelMap modelMap) { Activity activity =
	 * activityService.selectActivity(activityId);
	 * 
	 * if (activity != null) { request.setAttribute("activityName",
	 * activity.getName()); }
	 * 
	 * request.setAttribute("activityId", activityId); return
	 * PAGE_CHANNEL_CONFIG; }
	 * 
	 * @RequestMapping("/configGiftsChannelWindows.do") public String
	 * configGiftsChannelWindows(Long activityId, HttpServletRequest request,
	 * HttpServletResponse response, ModelMap modelMap) { Activity activity =
	 * activityService.selectActivity(activityId);
	 * 
	 * if (activity != null) { request.setAttribute("activityName",
	 * activity.getName()); }
	 * 
	 * request.setAttribute("activityId", activityId); return
	 * PAGE_GIFTS_CHANNEL_CONFIG; }
	 */

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
