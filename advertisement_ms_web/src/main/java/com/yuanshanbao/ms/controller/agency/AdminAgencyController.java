package com.yuanshanbao.ms.controller.agency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.dsp.activity.model.Activity;
import com.yuanshanbao.dsp.activity.service.ActivityService;
import com.yuanshanbao.dsp.agency.model.Agency;
import com.yuanshanbao.dsp.agency.model.vo.AgencyStatus;
import com.yuanshanbao.dsp.agency.model.vo.AgencyType;
import com.yuanshanbao.dsp.agency.model.vo.AgencyVo;
import com.yuanshanbao.dsp.agency.service.AgencyService;
import com.yuanshanbao.dsp.merchant.service.MerchantService;
import com.yuanshanbao.dsp.quota.service.QuotaService;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.dsp.user.service.UserService;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;
import com.yuanshanbao.paginator.domain.Paginator;

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

	@Autowired
	private UserService userService;

	@RequestMapping("/list.do")
	public String list(Long merchantId, HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("merchantId", merchantId);
		request.setAttribute("merchant", merchantService.selectMerchant(merchantId));
		request.setAttribute("statusList", AgencyStatus.getCodeDescriptionMap().entrySet());
		request.setAttribute("typeList", AgencyType.getCodeDescriptionMap().entrySet());
		request.setAttribute("activityList", activityService.selectActivitys(new Activity(), new PageBounds()));

		return PAGE_LIST;
	}

	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, Agency agency, HttpServletRequest request, HttpServletResponse response) {
		List<AgencyVo> agencyVos = new ArrayList<AgencyVo>();
		List<Agency> agencyList = agencyService.selectAgencys(agency, getPageBounds(range, request));
		AgencyVo agencyVo = null;
		for (Agency agenc : agencyList) {
			agencyVo = new AgencyVo(agenc);
			if (agencyVo.getInviteUserId() != null) {
				User user = userService.selectUserById(agencyVo.getInviteUserId());
				if (user != null) {
					if (user.getInviteUserId() == null) {
						agencyVo.setIndirectUserId("");
					}
					agencyVo.setIndirectUserId(String.valueOf(user.getInviteUserId()));
				}
			}
			agencyVos.add(agencyVo);
		}

		return setPageInfo(request, response, new PageList<AgencyVo>(agencyVos, new Paginator()));
	}

	@ResponseBody
	@RequestMapping("/queryAgencyDB.do")
	public Object queryStatisticFromDB(String queryChannel, Agency agency, HttpServletRequest request,
			HttpServletResponse response) {
		Object object = agencyService.selectAgencys(agency, getPageBounds(queryChannel, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/download.do")
	public Object download(Agency agency, HttpServletRequest request, HttpServletResponse response) {
		String queryChannel = null;
		Map<String, Object> map = new HashMap<String, Object>();

		Map<String, Object> result = new HashMap<String, Object>();

		Object object = new HashMap<String, Object>();
		List<Agency> list = new ArrayList<Agency>();

		map = (Map<String, Object>) queryAgencyFromDB(queryChannel, agency, request, response);
		list = (List<Agency>) map.get("data");
		String path = agencyService.downAgency(list);
		result.put("path", path);
		return result;

	}

	@ResponseBody
	@RequestMapping("/queryAgencyFromDB.do")
	public Object queryAgencyFromDB(String queryChannel, Agency agency, HttpServletRequest request,
			HttpServletResponse response) {
		List<Agency> agencies = agencyService.selectAgencys(agency, new PageBounds());
		return setPageInfo(request, response, new PageList<Agency>(agencies, new Paginator()));
	}

}
