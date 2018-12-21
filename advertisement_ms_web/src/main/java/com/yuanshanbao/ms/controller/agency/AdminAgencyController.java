package com.yuanshanbao.ms.controller.agency;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.dsp.activity.service.ActivityService;
import com.yuanshanbao.dsp.agency.model.Agency;
import com.yuanshanbao.dsp.agency.model.vo.AgencyStatus;
import com.yuanshanbao.dsp.agency.service.AgencyService;
import com.yuanshanbao.dsp.merchant.service.MerchantService;
import com.yuanshanbao.dsp.quota.service.QuotaService;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/agency")
public class AdminAgencyController extends PaginationController {

	private static final String WANGZHUAN = "wangzhuan";

	private static final String WANGZHUANAGENT = "wangzhuanagent";

	private static final String PAGE_LIST = "advertisement/agency/listAgency";

	private static final String PAGE_AGENT_LIST = "advertisement/agency/agentListProduct";

	@Autowired
	private AgencyService agencyService;

	@Autowired
	private ActivityService activityService;

	@Autowired
	private QuotaService quotaService;

	@Autowired
	private MerchantService merchantService;

	@RequestMapping("/list.do")
	public String list(Long merchantId, HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("merchantId", merchantId);
		request.setAttribute("merchant", merchantService.selectMerchant(merchantId));
		request.setAttribute("statusList", AgencyStatus.getCodeDescriptionMap().entrySet());

		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, Agency agency, HttpServletRequest request, HttpServletResponse response) {

		Object object = agencyService.selectAgencys(agency, getPageBounds(range, request));

		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

}
