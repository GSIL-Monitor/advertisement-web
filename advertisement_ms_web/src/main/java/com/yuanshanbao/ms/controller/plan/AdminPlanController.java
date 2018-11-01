package com.yuanshanbao.ms.controller.plan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
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
import com.yuanshanbao.common.util.JacksonUtil;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementType;
import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.advertiser.service.AdvertiserService;
import com.yuanshanbao.dsp.channel.model.Channel;
import com.yuanshanbao.dsp.channel.model.ChannelType;
import com.yuanshanbao.dsp.channel.service.ChannelService;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.material.model.Material;
import com.yuanshanbao.dsp.material.service.MaterialService;
import com.yuanshanbao.dsp.order.model.Order;
import com.yuanshanbao.dsp.order.service.OrderService;
import com.yuanshanbao.dsp.plan.model.Plan;
import com.yuanshanbao.dsp.plan.model.PlanStatus;
import com.yuanshanbao.dsp.plan.service.PlanService;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.dsp.probability.model.ProbabilityStatus;
import com.yuanshanbao.dsp.probability.service.ProbabilityService;
import com.yuanshanbao.dsp.quota.model.QuotaType;
import com.yuanshanbao.dsp.quota.service.QuotaService;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/plan")
public class AdminPlanController extends PaginationController {

	public static final String PLAN_ENCRYPT_KEY = "aadecfe68c1c06a7";

	private static final String PAGE_LIST = "advertisement/plan/listPlan";

	private static final String PAGE_INSERT = "advertisement/plan/insertPlan";

	private static final String PAGE_UPDATE = "advertisement/plan/updatePlan";

	private static final String PAGE_UNREVIEW_LIST = "advertisement/plan/listUnreview";

	private static final String PAGE_REVIEW = "advertisement/plan/reviewPlan";

	private static final String PAGE_ALLOCATE_LIST = "advertisement/plan/listAllocatePlan";

	private static final String PAGE_ALLOCATE_PLAN = "advertisement/plan/allocatePlan";

	private static final String PAGE_SELECT_MATERIAL = "advertisement/plan/selectMaterial";

	@Autowired
	private OrderService orderService;

	@Autowired
	private AdvertiserService advertiserService;

	@Autowired
	private ProbabilityService probabilityService;

	@Autowired
	private QuotaService quotaService;

	@Autowired
	private MaterialService materialService;

	@Autowired
	private PlanService planService;

	@Autowired
	private ChannelService channelService;

	@RequestMapping("/list.do")
	public String list(Long advertiserId, HttpServletRequest request, HttpServletResponse response, Long orderId) {
		Advertiser advertiser = getBindAdvertiserByUser();
		if (advertiser != null) {
			advertiserId = advertiser.getAdvertiserId();
		}
		setProperty(request, getProjectId(request), orderId, advertiserId);
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, Plan plan, Order order, HttpServletRequest request, HttpServletResponse response) {
		Advertiser advertiser = getBindAdvertiserByUser();
		if (advertiser != null) {
			order.setAdvertiserId(advertiser.getAdvertiserId());
		}
		List<Plan> list = planService.selectPlan(plan, getPageBounds(range, request));
		PageList pageList = (PageList) list;
		return setPageInfo(request, response, pageList);
	}

	private void setProperty(HttpServletRequest request, Long projectId, Long orderId, Long advertiserId) {
		request.setAttribute("orderId", orderId);
		request.setAttribute("advertiserId", advertiserId);
		request.setAttribute("projectId", projectId);
		request.setAttribute("positionList", ConstantsManager.getPositionList(projectId));
		request.setAttribute("quotaTypeList", QuotaType.getCodeDescriptionMap().entrySet());
		request.setAttribute("typeList", AdvertisementType.getCodeDescriptionMap().entrySet());
		request.setAttribute("statusList", PlanStatus.getCodeDescriptionMap().entrySet());
		request.setAttribute("channelTypeList", ChannelType.getTypeDescriptionMap().entrySet());
	}

	@RequestMapping("/insertWindow.do")
	public String insertWindow(HttpServletRequest request, HttpServletResponse response, Long orderId, Integer type,
			ModelMap modelMap) {
		Long advertiserId = null;
		Advertiser advertiser = getBindAdvertiserByUser();
		if (advertiser != null) {
			advertiserId = advertiser.getAdvertiserId();
		}
		setProperty(request, getProjectId(request), orderId, advertiserId);
		modelMap.put("categories", ConfigManager.getCategoryMap());
		return PAGE_INSERT;
	}

	@ResponseBody
	@RequestMapping("/insert.do")
	public Object insert(HttpServletRequest request, HttpServletResponse response, Plan plan, String orderId) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			validateParameters(plan);
			if (StringUtils.isBlank(orderId)) {
				throw new BusinessException(ComRetCode.FAIL);
			}
			plan.setProjectId(getProjectId(request));
			plan.setStatus(PlanStatus.UNREVIEWED);
			planService.insertPlan(plan);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("advertisement insert function - upload image error", e2);
		}
		return result;
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(HttpServletRequest request, HttpServletResponse response, Long planId, Integer type,
			ModelMap modelMap) {
		Plan plan = planService.selectPlan(planId);
		request.setAttribute("itemEdit", plan);
		modelMap.put("categories", ConfigManager.getCategoryMap());
		return PAGE_UPDATE;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object update(HttpServletRequest request, HttpServletResponse response, Plan plan) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (plan.getPlanId() == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			plan.setStatus(PlanStatus.UNREVIEWED);
			planService.updatePlan(plan);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("plan update function - upload image error", e2);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/refreshCTR")
	public Object refreshCTR() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Probability params = new Probability();
		params.setStatus(CommonStatus.ONLINE);
		List<Probability> list = probabilityService.selectProbabilitys(params, new PageBounds());
		try {
			planService.calculateCTR(list);
		} catch (Exception e2) {
			LoggerUtil.error("calculateCTR  function -  error", e2);
		}
		return resultMap;
	}

	@RequestMapping("/setMaterialWindow.do")
	public String setMaterialWindow(Long planId, HttpServletRequest request, HttpServletResponse response, Long orderId) {
		request.setAttribute("planId", planId);
		return PAGE_SELECT_MATERIAL;
	}

	@ResponseBody
	@RequestMapping("/queryMaterial.do")
	public Object queryMaterial(HttpServletRequest request, HttpServletResponse response, Long planId, Boolean isSelect) {
		Advertiser advertiser = getBindAdvertiserByUser();
		PageList<Material> list = new PageList<Material>();
		if (advertiser != null) {
			Plan plan = planService.selectPlan(planId);
			list = (PageList<Material>) materialService.selectMaterialsByIds(advertiser.getAdvertiserId(),
					plan.getMaterial(), isSelect, getPageBounds(request));
		}
		return setPageInfo(request, response, list);
	}

	@RequestMapping("/reviewWindow.do")
	public String reviewWindow(Long advertiserId, HttpServletRequest request, HttpServletResponse response, Long orderId) {
		Advertiser advertiser = getBindAdvertiserByUser();
		if (advertiser != null) {
			advertiserId = advertiser.getAdvertiserId();
		}
		setProperty(request, getProjectId(request), orderId, advertiserId);
		return PAGE_UNREVIEW_LIST;
	}

	@ResponseBody
	@RequestMapping("/reviewQuery.do")
	public Object reviewQuery(String range, Plan plan, Order order, HttpServletRequest request,
			HttpServletResponse response) {
		plan.setStatus(PlanStatus.UNREVIEWED);
		List<Plan> list = planService.selectPlan(plan, getPageBounds(range, request));
		PageList pageList = (PageList) list;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/reviewDetails.do")
	public String reviewDetails(String range, Long planId, HttpServletRequest request, HttpServletResponse response) {
		Plan plan = planService.selectPlan(planId);
		request.setAttribute("itemEdit", plan);
		request.setAttribute("statusList", PlanStatus.getCodeDescriptionMap().entrySet());
		return PAGE_REVIEW;
	}

	@ResponseBody
	@RequestMapping("/review.do")
	public Object review(HttpServletRequest request, HttpServletResponse response, Plan plan) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (plan.getPlanId() == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			planService.updatePlan(plan);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("plan update function - upload image error", e2);
		}
		return result;
	}

	@RequestMapping("/planList.do")
	public String planList(Long advertiserId, HttpServletRequest request, HttpServletResponse response, Long orderId) {
		Advertiser advertiser = getBindAdvertiserByUser();
		if (advertiser != null) {
			advertiserId = advertiser.getAdvertiserId();
		}
		setProperty(request, getProjectId(request), orderId, advertiserId);
		return PAGE_ALLOCATE_LIST;
	}

	@RequestMapping("/allocatePlanWindow.do")
	public String allocatePlanWindow(Long planId, String channel, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		Channel params = new Channel();
		List<Channel> list = channelService.selectChannels(params, new PageBounds());
		request.setAttribute("channelList", list);
		request.setAttribute("planId", planId);
		return PAGE_ALLOCATE_PLAN;
	}

	@ResponseBody
	@RequestMapping("/allocatePlan.do")
	public Object allocatePlan(HttpServletRequest request, HttpServletResponse response, Probability probability) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			validateParameters(probability);
			Plan plan = ConfigManager.getPlanById(probability.getPlanId());
			probability.setProjectId(getProjectId(request));
			probability.setStatus(ProbabilityStatus.UNREVIEWED);
			probability.setOrderId(plan.getOrderId());
			probabilityService.insertProbability(probability);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
			LoggerUtil.info("allocatePlan, 分配详情信息={},操作人员={}", JacksonUtil.obj2json(probability), getCurrentUser()
					.getUsername());
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("allocatePlan insert function - reload error", e2);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/addMaterial.do")
	public Object addMaterial(HttpServletRequest request, HttpServletResponse response, Long planId, String material) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (planId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			Plan plan = planService.selectPlan(planId);
			if (plan.getMaterial() != null && StringUtils.isNotEmpty(plan.getMaterial())) {
				String resultMaterial = plan.getMaterial();
				String[] selectMaterial = material.split(",");
				for (String id : selectMaterial) {
					resultMaterial = resultMaterial + "," + id;
				}
				plan.setMaterial(resultMaterial);
			} else {
				plan.setMaterial(material);
			}
			planService.updatePlan(plan);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("plan setMaterial", e2);
		}
		return resultMap;
	}

	@ResponseBody
	@RequestMapping("/deleteMaterial.do")
	public Object deleteMaterial(HttpServletRequest request, HttpServletResponse response, Long planId, String material) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (planId == null || StringUtils.isEmpty(material)) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			Plan plan = planService.selectPlan(planId);
			List<String> selectedList = Arrays.asList(plan.getMaterial().split(","));
			selectedList = new ArrayList<String>(selectedList);
			List<String> deletedList = Arrays.asList(material.split(","));
			deletedList = new ArrayList<String>(deletedList);
			Iterator<String> it = selectedList.iterator();
			while (it.hasNext()) {
				String id = it.next();
				if (deletedList.contains(id)) {
					it.remove();
				}
			}
			StringBuffer resultMaterial = new StringBuffer();
			for (String id : selectedList) {
				resultMaterial.append(",");
				resultMaterial.append(id);
			}
			if (selectedList.size() > 0) {
				resultMaterial.deleteCharAt(0);
			}
			plan.setMaterial(resultMaterial.toString());
			// plan.setStatus(PlanStatus.UNREVIEWED);
			planService.updatePlan(plan);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("plan setMaterial", e2);
		}
		return resultMap;
	}
}
