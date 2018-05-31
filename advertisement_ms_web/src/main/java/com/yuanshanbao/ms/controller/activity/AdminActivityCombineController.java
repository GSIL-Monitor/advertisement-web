package com.yuanshanbao.ms.controller.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.activity.model.Activity;
import com.yuanshanbao.dsp.activity.model.ActivityCombine;
import com.yuanshanbao.dsp.activity.model.ActivityStatus;
import com.yuanshanbao.dsp.activity.service.ActivityCombineService;
import com.yuanshanbao.dsp.activity.service.ActivityService;
import com.yuanshanbao.dsp.advertisement.model.Advertisement;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementDisplayType;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementStatus;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementType;
import com.yuanshanbao.dsp.advertisement.model.vo.AdvertisementVo;
import com.yuanshanbao.dsp.advertisement.service.AdvertisementService;
import com.yuanshanbao.dsp.channel.model.Channel;
import com.yuanshanbao.dsp.channel.model.ChannelAllocateStatus;
import com.yuanshanbao.dsp.channel.model.ChannelIndependentStatus;
import com.yuanshanbao.dsp.channel.service.ChannelService;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.dsp.probability.service.ProbabilityService;
import com.yuanshanbao.dsp.quota.model.Quota;
import com.yuanshanbao.dsp.quota.model.QuotaType;
import com.yuanshanbao.dsp.quota.service.QuotaService;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/activity/combine")
public class AdminActivityCombineController extends PaginationController {
	private static final String PAGE_LIST = "advertisement/activity/combine/listActivityCombine";

	private static final String PAGE_INSERT = "advertisement/activity/combine/insertActivityCombine";

	private static final String PAGE_UPDATE = "advertisement/activity/updateActivity";

	private static final String PAGE_COMBINE_LIST = "advertisement/activity/combine/listActivityCombineDetails";

	private static final String PAGE_CHANNEL_LIST = "advertisement/activity/combine/listChannelOfActivityCombine";

	private static final String PAGE_CHANNEL_ALLOCATE = "advertisement/activity/combine/addChannelOfActivityCombine";

	private static final String PAGE_LIST_GIFT = "advertisement/activity/combine/listCombineGift";

	private static final String PAGE_ALLOCATE_GIFT = "advertisement/activity/combine/allocateCombineGift";

	private static final String PAGE_ADD_ACTIVITY = "advertisement/activity/combine/addActivity";
	@Autowired
	private ActivityService activityService;

	@Autowired
	private ActivityCombineService activityCombineService;

	@Autowired
	private ChannelService channelService;

	@Autowired
	private AdvertisementService advertisementService;

	@Autowired
	private ProbabilityService probabilityService;

	@Autowired
	private QuotaService quotaService;

	@RequestMapping("/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("isSimple", "0");
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, Activity activity, HttpServletRequest request, HttpServletResponse response) {
		activity.setCombination(1);
		Object object = activityService.selectActivitys(activity, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/insertWindow.do")
	public String insertWindow(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("isSimple", "0");
		return PAGE_INSERT;
	}

	@ResponseBody
	@RequestMapping("/insert.do")
	public Object insert(String range, Activity activity, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			validateParameters(activity);
			activity.setCombination(1);
			activityService.insertActivity(activity);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}
		return result;
	}

	@RequestMapping("/listCombine.do")
	public String listCombine(Long activityId, HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("activityId", activityId);
		return PAGE_COMBINE_LIST;
	}

	@ResponseBody
	@RequestMapping("/queryCombine.do")
	public Object queryCombine(String range, ActivityCombine activityCombine, HttpServletRequest request,
			HttpServletResponse response) {
		Object object = activityCombineService.selectActivityCombine(activityCombine, getPageBounds(range, request));
		PageList<?> pageList = (PageList<?>) object;
		return setPageInfo(request, response, pageList);
	}

	/**
	 * @description 为组合添加活动
	 */
	@RequestMapping("/addActivityWindow.do")
	public String addActivityWindow(Long activityId, HttpServletRequest request, HttpServletResponse response) {
		Activity params = new Activity();
		params.setCombination(0);
		List<Activity> list = activityService.selectActivitys(params, new PageBounds());
		request.setAttribute("activityId", activityId);
		request.setAttribute("activityList", list);
		request.setAttribute("statusList", ActivityStatus.getCodeDescriptionMap().entrySet());
		return PAGE_ADD_ACTIVITY;
	}

	@ResponseBody
	@RequestMapping("/addActivity.do")
	public Object addActivity(ActivityCombine activityCombine, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			validateParameters(activityCombine);
			activityCombineService.insertActivityCombine(activityCombine);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/delete.do")
	public Object delete(Long activityCombineId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (activityCombineId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			ActivityCombine activityCombine = new ActivityCombine();
			activityCombine.setActivityCombineId(activityCombineId);
			activityCombine.setStatus(CommonStatus.OFFLINE);
			activityCombineService.updateActivityCombine(activityCombine);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}
		return result;
	}

	@RequestMapping("/listChannel.do")
	public String listChannel(String activityId, HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		request.setAttribute("activityId", activityId);
		return PAGE_CHANNEL_LIST;
	}

	@RequestMapping("/allocateChannelWindow.do")
	public String allocationChannelWindow(String activityId, HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		Activity activity = activityService.selectActivity(Long.valueOf(activityId));
		if (activity != null) {
			request.setAttribute("activityName", activity.getName());
			request.setAttribute("activityId", activity.getActivityId());
		}
		Channel params = new Channel();
		params.setAllocateType(ChannelAllocateStatus.UNALLOCATED);
		request.setAttribute("channelList", channelService.selectChannels(params, new PageBounds()));
		request.setAttribute("independentList", ChannelIndependentStatus.getCodeDescriptionMap().entrySet());
		return PAGE_CHANNEL_ALLOCATE;
	}

	@ResponseBody
	@RequestMapping("/allocateChannel.do")
	public Object allocationChannel(Channel channel, HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		Channel resultChannel = channelService.selectChannel(channel.getChannelId());
		try {
			if (resultChannel != null) {
				resultChannel.setActivityId(channel.getActivityId());
				resultChannel.setIndependent(channel.getIndependent());
				resultChannel.setAllocateType(ChannelAllocateStatus.ALLOCATED);
				channelService.updateChannel(resultChannel);
			}
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}
		return result;
	}

	@RequestMapping("/giftList.do")
	public String giftList(String activityId, String channel, HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		request.setAttribute("activityId", activityId);
		request.setAttribute("channel", channel);
		return PAGE_LIST_GIFT;
	}

	@ResponseBody
	@RequestMapping("/queryGift.do")
	public Object queryGift(String activityId, String channel, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<AdvertisementVo> list = new ArrayList<AdvertisementVo>();
		try {
			list = advertisementService.selectGift(activityId, channel);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}
		result.put("data", list);
		result.put("draw", request.getParameter("draw"));
		result.put("recordsTotal", 1000);
		result.put("recordsFiltered", 1000);
		return result;
	}

	@RequestMapping("/allocateGiftWindow.do")
	public String allocateGiftWindow(String activityId, String channel, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		List<Advertisement> list = advertisementService.selectAdvertisement(new Advertisement(), new PageBounds());
		request.setAttribute("advertisementList", list);
		setProperty(request, activityId, channel);
		return PAGE_ALLOCATE_GIFT;
	}

	private void setProperty(HttpServletRequest request, String activityId, String channel) {
		request.setAttribute("activityId", activityId);
		request.setAttribute("channel", channel);
		request.setAttribute("typeList", AdvertisementType.getCodeDescriptionMap().entrySet());
		request.setAttribute("quotaTypeList", QuotaType.getCodeDescriptionMap().entrySet());
		request.setAttribute("statusList", AdvertisementStatus.getCodeDescriptionMap().entrySet());
		request.setAttribute("displayList", AdvertisementDisplayType.getCodeDescriptionMap().entrySet());
	}

	@ResponseBody
	@RequestMapping("/allocateGift.do")
	public Object allocateGift(Probability probability, Quota quota, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			probability.setProjectId(getProjectId(request));
			quota.setProjectId(getProjectId(request));
			probabilityService.insertProbability(probability);
			quotaService.insertQuota(quota);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}
		return result;
	}
}
