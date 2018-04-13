package com.yuanshanbao.ms.controller.sms;

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
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.sms.model.MarketingSms;
import com.yuanshanbao.dsp.sms.service.MarketingSmsService;
import com.yuanshanbao.dsp.sms.service.MarketingTaskService;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/marketing/sms")
public class AdminMarketingSmsController extends PaginationController {

	public static final String PAGE_LIST = "advertisement/marketing/sms/listMarketingSms";

	private static final String PAGE_INSERT = "advertisement/marketing/sms/insertMarketingSms";

	private static final String PAGE_UPDATE = "advertisement/marketing/sms/updateMarketingSms";

	private static final String PAGE_VIEW = "advertisement/marketing/sms/viewMarketingSms";

	@Autowired
	private MarketingSmsService marketingSmsService;

	@Autowired
	private MarketingTaskService marketingTaskService;

	@RequestMapping("/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, MarketingSms marketingSms, HttpServletRequest request, HttpServletResponse response) {
		Object object = marketingSmsService.selectMarketingSms(marketingSms, getPageBounds(range, request));
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
	public Object insert(MarketingSms marketingSms, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			validateParameters(marketingSms);
			marketingSmsService.insertMarketingSms(marketingSms);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("[ms insert marketing]", e2);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.FAIL);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/delete.do")
	public Object delete(Long marketingSmsId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			if (marketingSmsId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			MarketingSms marketingSms = new MarketingSms();
			marketingSms.setStatus(CommonStatus.OFFLINE);
			marketingSms.setMarketingSmsId(marketingSmsId);
			marketingSmsService.updateMarketingSms(marketingSms);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(HttpServletRequest request, HttpServletResponse response, MarketingSms marketingSms) {
		List<MarketingSms> list = marketingSmsService.selectMarketingSms(marketingSms, new PageBounds());
		if (list != null && list.size() > 0) {
			marketingSms = list.get(0);
		}

		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());
		request.setAttribute("itemEdit", marketingSms);
		return PAGE_UPDATE;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object update(MarketingSms marketingSms, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			validateParameters(marketingSms);
			marketingSmsService.updateMarketingSms(marketingSms);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("[ms update marketingSms]", e2);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.FAIL);
		}

		return result;
	}

	@RequestMapping("/view.do")
	public String view(MarketingSms marketingSms, ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		List<MarketingSms> list = marketingSmsService.selectMarketingSms(marketingSms, new PageBounds());
		if (list != null && list.size() > 0) {
			marketingSms = list.get(0);
		}
		request.setAttribute("itemEdit", marketingSms);
		return PAGE_VIEW;
	}

}