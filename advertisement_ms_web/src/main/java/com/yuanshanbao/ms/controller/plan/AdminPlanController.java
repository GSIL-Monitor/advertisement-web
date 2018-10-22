package com.yuanshanbao.ms.controller.plan;

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
import com.yuanshanbao.dsp.advertisement.model.Advertisement;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementStatus;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementType;
import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.advertiser.service.AdvertiserService;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.creative.model.Creative;
import com.yuanshanbao.dsp.creative.service.CreativeService;
import com.yuanshanbao.dsp.order.model.Order;
import com.yuanshanbao.dsp.order.service.OrderService;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.dsp.probability.model.ProbabilityStatus;
import com.yuanshanbao.dsp.probability.service.ProbabilityService;
import com.yuanshanbao.dsp.quota.model.Quota;
import com.yuanshanbao.dsp.quota.model.QuotaType;
import com.yuanshanbao.dsp.quota.service.QuotaService;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.ms.controller.common.AdminServerController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;
import com.yuanshanbao.paginator.domain.Paginator;

@Controller
@RequestMapping("/admin/plan")
public class AdminPlanController extends PaginationController {

	private static final String PAGE_LIST = "advertisement/plan/listPlan";

	private static final String PAGE_INSERT = "advertisement/plan/insertPlan";

	private static final String PAGE_UPDATE = "advertisement/advertisement/updateAdvertisement";

	private static final String PAGE_VIEW = "advertisement/advertisement/viewAdvertisement";
	@Autowired
	private OrderService orderService;

	@Autowired
	private AdvertiserService advertiserService;

	@Autowired
	private ProbabilityService probabilityService;

	@Autowired
	private QuotaService quotaService;
	
	@Autowired
	private CreativeService creativeService;

	@RequestMapping("/list.do")
	public String list(Long advertiserId, HttpServletRequest request, HttpServletResponse response, Long orderId) {
		Advertiser advertiser = getBindAdvertiserByUser();
		if (advertiser != null) {
			advertiserId = advertiser.getAdvertiserId();
		}
		setProperty(request, getProjectId(request), orderId,advertiserId);
		return PAGE_LIST;
	}

	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, Advertisement advertisement, Order order, HttpServletRequest request,
			HttpServletResponse response) {
		Advertiser advertiser = getBindAdvertiserByUser();
		if (advertiser != null) {
			order.setAdvertiserId(advertiser.getAdvertiserId());
		}
		List<Long> orderIds = orderService.selectOrderIds(order);
		List<Probability> list = probabilityService.selectProbabilityByOrderIds(orderIds);
		return setPageInfo(request, response, new PageList<Object>(list, new Paginator()));
	}

	private void setProperty(HttpServletRequest request, Long projectId, Long orderId,Long advertiserId) {
		request.setAttribute("orderId", orderId);
		request.setAttribute("advertiserId", advertiserId);
		request.setAttribute("projectId", projectId);
		request.setAttribute("positionList", ConstantsManager.getPositionList(projectId));
		request.setAttribute("quotaTypeList", QuotaType.getCodeDescriptionMap().entrySet());
		request.setAttribute("typeList", AdvertisementType.getCodeDescriptionMap().entrySet());
		request.setAttribute("statusList", AdvertisementStatus.getCodeDescriptionMap().entrySet());
		Creative creative = new Creative();
		creative.setAdvertiserId(advertiserId);
		List<Creative> list = creativeService.selectCreative(creative, new PageBounds());
		request.setAttribute("creative", list);
	} 

	@RequestMapping("/insertWindow.do")
	public String insertWindow(HttpServletRequest request, HttpServletResponse response, Long orderId, Integer type,
			ModelMap modelMap) {
		Long advertiserId = null;
		Advertiser advertiser = getBindAdvertiserByUser();
		if (advertiser != null) {
			advertiserId = advertiser.getAdvertiserId();
		}
		setProperty(request, getProjectId(request), orderId,advertiserId);
		modelMap.put("categories", ConfigManager.getCategoryMap());
		return PAGE_INSERT;
	}

	@ResponseBody
	@RequestMapping("/insert.do")
	public Object insert(HttpServletRequest request, HttpServletResponse response, Probability probability,
			Quota quota, String orderId) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (StringUtils.isBlank(orderId)) {
				throw new BusinessException(ComRetCode.FAIL);
			}
			probability.setProjectId(getProjectId(request));
			probability.setStatus(ProbabilityStatus.UNREVIEWED);
			probabilityService.insertProbability(probability);
			quota.setProjectId(getProjectId(request));
			quota.setProbabilityId(probability.getProbabilityId());
			quota.setStatus(CommonStatus.ONLINE);
			quotaService.insertQuota(quota);
			AdminServerController.refreshConfirm();
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("advertisement insert function - upload image error", e2);
		}
		return result;
	}
}
