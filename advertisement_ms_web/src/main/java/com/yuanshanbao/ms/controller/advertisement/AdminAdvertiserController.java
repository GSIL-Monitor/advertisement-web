package com.yuanshanbao.ms.controller.advertisement;

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
import com.yuanshanbao.ad.advertisement.model.Advertiser;
import com.yuanshanbao.ad.advertisement.service.AdvertiserService;
import com.yuanshanbao.ad.core.CommonStatus;
import com.yuanshanbao.ad.core.InterfaceRetCode;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.ms.controller.common.AdminServerController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/advertiser")
public class AdminAdvertiserController extends PaginationController {

	private static final String PAGE_LIST = "advertisement/advertiser/listAdvertiser";

	private static final String PAGE_INSERT = "advertisement/advertiser/insertAdvertiser";

	private static final String PAGE_UPDATE = "advertisement/advertiser/updateAdvertiser";

	private static final String PAGE_VIEW = "advertisement/advertiser/viewAdvertiser";

	@Autowired
	private AdvertiserService advertiserService;

	@RequestMapping("/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, Advertiser advertiser, HttpServletRequest request, HttpServletResponse response) {
		Object object = advertiserService.selectAdvertiser(advertiser, getPageBounds(range, request));
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
	public Object insert(Advertiser advertiser, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			validateParameters(advertiser);
			advertiserService.insertAdvertiser(advertiser);
			AdminServerController.refreshConfirm();
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("advertiser insert function - upload image error", e2);
		}

		return result;
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(HttpServletRequest request, HttpServletResponse response, Advertiser advertiser) {
		List<Advertiser> list = advertiserService.selectAdvertiser(advertiser, new PageBounds());
		if (list != null && list.size() >= 0) {
			advertiser = list.get(0);
		}
		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());
		request.setAttribute("itemEdit", advertiser);
		return PAGE_UPDATE;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object update(Advertiser advertiser, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			validateParameters(advertiser);
			advertiserService.updateAdvertiser(advertiser);
			AdminServerController.refreshConfirm();
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("advertiser update function - upload image error", e2);
		}

		return result;
	}

	@ResponseBody
	@RequestMapping("/delete.do")
	public Object delete(Long advertiserId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {

			if (advertiserId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			Advertiser advertiser = new Advertiser();
			advertiser.setStatus(CommonStatus.OFFLINE);
			advertiser.setAdvertiserId(advertiserId);
			advertiserService.updateAdvertiser(advertiser);

			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}

	@RequestMapping("/view.do")
	public String view(Advertiser advertiser, ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		List<Advertiser> list = advertiserService.selectAdvertiser(advertiser, new PageBounds());
		if (list != null && list.size() >= 0) {
			advertiser = list.get(0);
		}
		request.setAttribute("itemEdit", advertiser);
		return PAGE_VIEW;
	}

}
