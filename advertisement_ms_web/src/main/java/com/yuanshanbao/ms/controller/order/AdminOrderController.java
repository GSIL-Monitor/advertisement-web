package com.yuanshanbao.ms.controller.order;

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
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.advertiser.service.AdvertiserService;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.order.model.Order;
import com.yuanshanbao.dsp.order.model.OrderStatus;
import com.yuanshanbao.dsp.order.model.OrderType;
import com.yuanshanbao.dsp.order.service.OrderService;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.dsp.probability.service.ProbabilityService;
import com.yuanshanbao.dsp.quota.model.Quota;
import com.yuanshanbao.dsp.quota.model.QuotaType;
import com.yuanshanbao.dsp.quota.service.QuotaService;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.ms.controller.common.AdminServerController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/order")
public class AdminOrderController extends PaginationController {
	private static final String PAGE_LIST = "advertisement/order/listOrder";

	private static final String PAGE_INSERT = "advertisement/order/insertOrder";

	private static final String PAGE_UPDATE = "advertisement/order/updateOrder";

	@Autowired
	private OrderService orderService;

	@Autowired
	private AdvertiserService advertiserService;

	@Autowired
	private ProbabilityService probabilityService;

	@Autowired
	private QuotaService quotaService;

	@RequestMapping("/list.do")
	public String list(Long advertiserId, HttpServletRequest request, HttpServletResponse response) {
		Advertiser advertiser = getBindAdvertiserByUser();
		if (advertiser != null) {
			advertiserId = advertiser.getAdvertiserId();
		}
		request.setAttribute("advertiserId", advertiserId);
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, Order order, HttpServletRequest request, HttpServletResponse response) {
		Object object = orderService.selectOrder(order, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/insertWindow.do")
	public String insertGiftWindow(HttpServletRequest request, HttpServletResponse response, Integer type,
			ModelMap modelMap) {
		setProperty(request, getProjectId(request));
		modelMap.put("categories", ConfigManager.getCategoryMap());
		return PAGE_INSERT;
	}

	private void setProperty(HttpServletRequest request, Long projectId) {
		Advertiser advertiser = getBindAdvertiserByUser();
		if (advertiser != null) {
			request.setAttribute("advertiser", advertiser);
		} else {
			Advertiser param = new Advertiser();
			param.setProjectId(projectId);
			request.setAttribute("advertiserList", advertiserService.selectAdvertiser(param, new PageBounds()));
		}
		request.setAttribute("positionList", ConstantsManager.getPositionList(projectId));
		request.setAttribute("quotaTypeList", QuotaType.getCodeDescriptionMap().entrySet());
		request.setAttribute("typeList", OrderType.getCodeDescriptionMap().entrySet());
		request.setAttribute("statusList", OrderStatus.getCodeDescriptionMap().entrySet());
	}

	@ResponseBody
	@RequestMapping("/insert.do")
	public Object insert(HttpServletRequest request, HttpServletResponse response, Order order) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			validateParameters(order);
			order.setProjectId(getProjectId(request));
			order.setStatus(CommonStatus.ONLINE);
			orderService.insertOrder(order);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("order insert function - upload image error", e2);
		}

		return result;
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(HttpServletRequest request, HttpServletResponse response, Order order,
			Probability probability, Quota quota) {
		String isDisplay = "false";
		List<Order> list = orderService.selectOrder(order, new PageBounds());
		List<Probability> proList = probabilityService.selectProbabilitys(probability, new PageBounds());
		List<Quota> quotaList = quotaService.selectQuota(quota, new PageBounds());
		if (list != null && list.size() >= 0) {
			order = list.get(0);
		}
		if (proList != null && proList.size() == 1) {
			if (quotaList != null && quotaList.size() == 1) {
				probability = proList.get(0);
				quota = quotaList.get(0);
				isDisplay = "true";
			}
		}
		Long advertiserId = null;
		request.setAttribute("isDisplay", isDisplay);
		// setProperty(request, getProjectId(request), advertiserId);
		request.setAttribute("categories", ConfigManager.getCategoryMap());
		request.setAttribute("tagsList", ConstantsManager.getTagsList(ConstantsManager.ADVERTISEMENT));
		request.setAttribute("itemEdit", order);
		request.setAttribute("probability", probability);
		request.setAttribute("quota", quota);
		return PAGE_UPDATE;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object update(Order order, String prizeDesc, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			validateParameters(order);
			orderService.updateOrder(order);
			AdminServerController.refreshConfirm();
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("order update function - upload image error", e2);
		}

		return result;
	}
}
