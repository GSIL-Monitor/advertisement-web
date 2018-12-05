package com.yuanshanbao.ms.controller.probablity;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.JacksonUtil;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.order.model.Order;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.dsp.probability.model.ProbabilityStatus;
import com.yuanshanbao.dsp.probability.service.ProbabilityService;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.ms.controller.common.AdminServerController;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/probability")
public class AdminProbabilityController extends PaginationController {
	private static final String PAGE_LIST = "advertisement/probability/listProbability";

	private static final String PAGE_INSERT = "advertisement/plan/insertPlan";

	private static final String PAGE_UPDATE = "advertisement/plan/updatePlan";

	private static final String PAGE_REVIEW = "advertisement/probability/reviewProbability";

	private static final String PAGE_UNREVIEW_LIST = "advertisement/probability/listUnreview";

	@Autowired
	private ProbabilityService probabilityService;

	@RequestMapping("/reviewWindow.do")
	public String reviewWindow(Long advertiserId, HttpServletRequest request, HttpServletResponse response, Long orderId) {
		Advertiser advertiser = getBindAdvertiserByUser();
		if (advertiser != null) {
			advertiserId = advertiser.getAdvertiserId();
		}
		return PAGE_UNREVIEW_LIST;
	}

	@ResponseBody
	@RequestMapping("/reviewQuery.do")
	public Object reviewQuery(String range, Probability probability, Order order, HttpServletRequest request,
			HttpServletResponse response) {
		probability.setProjectId(getProjectId(request));
		Object object = probabilityService.selectProbabilitys(probability, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/reviewDetails.do")
	public String reviewDetails(String range, Long probabilityId, HttpServletRequest request,
			HttpServletResponse response) {
		Probability probability = probabilityService.selectProbability(probabilityId);
		request.setAttribute("itemEdit", probability);
		request.setAttribute("statusList", ProbabilityStatus.getCodeDescriptionMap().entrySet());
		return PAGE_REVIEW;
	}

	@ResponseBody
	@RequestMapping("/review.do")
	public Object review(HttpServletRequest request, HttpServletResponse response, Probability probability) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (probability.getProbabilityId() == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			probabilityService.updateProbability(probability);
			AdminServerController.refreshOnline();
			LoggerUtil.info("reviewProbability, 投放详情信息={},操作人员={}", JacksonUtil.obj2json(probability), getCurrentUser()
					.getUsername());
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("plan update function - upload image error", e2);
		}
		return result;
	}

	@RequestMapping("/list.do")
	public String list(String range, Long planId, String channel, HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("planId", planId);
		request.setAttribute("channel", channel);
		return PAGE_LIST;
	}

	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, HttpServletRequest request, HttpServletResponse response, Probability probability) {
		probability.setProjectId(getProjectId(request));
		Object object = probabilityService.selectProbabilitys(probability, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}
}
