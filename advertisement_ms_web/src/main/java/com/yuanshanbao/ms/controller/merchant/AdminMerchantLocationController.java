package com.yuanshanbao.ms.controller.merchant;

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
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;
import com.yuanshanbao.ad.core.CommonStatus;
import com.yuanshanbao.ad.core.InterfaceRetCode;
import com.yuanshanbao.ad.location.model.MerchantLocation;
import com.yuanshanbao.ad.location.service.MerchantLocationService;
import com.yuanshanbao.ad.merchant.model.Merchant;
import com.yuanshanbao.ad.merchant.service.MerchantService;

@Controller
@RequestMapping("/admin/merchant/location")
public class AdminMerchantLocationController extends PaginationController {

	private static final String PAGE_LIST = "insurance/merchant/location/listLocation";

	private static final String PAGE_INSERT = "insurance/merchant/location/insertLocation";

	private static final String PAGE_UPDATE = "insurance/merchant/location/updateLocation";

	private static final String PAGE_VIEW = "insurance/merchant/location/viewLocation";

	@Autowired
	private MerchantLocationService locationService;
	
	@Autowired
	private MerchantService merchantService;

	@RequestMapping("/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, MerchantLocation merchantLocation,
			HttpServletRequest request, HttpServletResponse response) {
		Object object = locationService.selectMerchantLocations(merchantLocation,
				getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/insertWindow.do")
	public String insertWindow(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		setProperty(request);
		return PAGE_INSERT;
	}
	
	private void setProperty(HttpServletRequest request) {
		Merchant merchant = new Merchant();
		merchant.setStatus(CommonStatus.ONLINE);
		List<Merchant> list = merchantService.selectMerchants(merchant, new PageBounds());
		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap()
				.entrySet());
		request.setAttribute("merchantList", list);
	}

	@ResponseBody
	@RequestMapping("/insert.do")
	public Object insert(MerchantLocation merchantLocation,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {

			validateParameters(merchantLocation);
			locationService.insertMerchantLocation(merchantLocation);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(),
					e.getMessage());
		}

		return result;
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(HttpServletRequest request,
			HttpServletResponse response, MerchantLocation merchantLocation) {
		List<MerchantLocation> list = locationService.selectMerchantLocations(merchantLocation,
				new PageBounds());
		if (list != null && list.size() > 0) {
			merchantLocation = list.get(0);
		}

		setProperty(request);
		request.setAttribute("itemEdit", merchantLocation);
		return PAGE_UPDATE;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object update(MerchantLocation merchantLocation, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {

			validateParameters(merchantLocation);
			locationService.updateMerchantLocation(merchantLocation);

			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(),
					e.getMessage());
		}

		return result;
	}
	
	@ResponseBody
	@RequestMapping("/delete.do")
	public Object delete(Long merchantLocationId, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			
			if (merchantLocationId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			MerchantLocation merchantLocation = new MerchantLocation();
			merchantLocation.setStatus(CommonStatus.OFFLINE);
			merchantLocation.setMerchantLocationId(merchantLocationId);
			locationService.updateMerchantLocation(merchantLocation);

			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(),
					e.getMessage());
		}

		return result;
	}

	@RequestMapping("/view.do")
	public String view(MerchantLocation merchantLocation, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		List<MerchantLocation> list = locationService.selectMerchantLocations(merchantLocation,
				new PageBounds());
		if (list != null && list.size() > 0) {
			merchantLocation = list.get(0);
		}
		request.setAttribute("itemEdit", merchantLocation);
		return PAGE_VIEW;
	}

}
