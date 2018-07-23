package com.yuanshanbao.ms.controller.limitation;

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
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.limitation.model.Limitation;
import com.yuanshanbao.dsp.limitation.service.LimitationService;
import com.yuanshanbao.dsp.merchant.service.MerchantService;
import com.yuanshanbao.dsp.product.model.Product;
import com.yuanshanbao.dsp.product.service.ProductService;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/limitation")
public class AdminLimitationController extends PaginationController {

	private static final String PAGE_LIST = "insurance/limitation/listLimitation";

	private static final String PAGE_INSERT = "insurance/limitation/insertLimitation";

	private static final String PAGE_UPDATE = "insurance/limitation/updateLimitation";

	private static final String PAGE_VIEW = "insurance/limitation/viewLimitation";

	@Autowired
	private LimitationService limitationService;

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private ProductService productService;

	@RequestMapping("/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, Limitation limitation, HttpServletRequest request, HttpServletResponse response) {
		Object object = setInsurance(limitationService.selectLimitations(limitation, getPageBounds(range, request)));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	private List<Limitation> setInsurance(List<Limitation> limitationList) {
		List<Long> productIds = new ArrayList<Long>();
		for (Limitation exist : limitationList) {
			productIds.add(exist.getProductId());
		}
		Map<Long, Product> productMap = productService.selectProductByIds(productIds);
		for (Limitation exist : limitationList) {
			exist.setProduct(productMap.get(exist.getProductId()));
		}

		return limitationList;
	}

	@RequestMapping("/insertWindow.do")
	public String insertWindow(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		setProperty(request);
		return PAGE_INSERT;
	}

	private void setProperty(HttpServletRequest request) {
		// Merchant merchant = new Merchant();
		// List<Merchant> list = merchantService.selectMerchants(merchant, new
		// PageBounds());
		//
		// Insurance insurance = new Insurance();
		// List<Insurance> insuranceList =
		// insuranceService.selectInsurances(insurance, new PageBounds());
		// request.setAttribute("statusList",
		// LimitationType.getStatusDescriptionMap().entrySet());
		// request.setAttribute("typeList",
		// LimitationType.getCodeDescriptionMap().entrySet());
		// request.setAttribute("merchantList", list);
		// request.setAttribute("insuranceList", insuranceList);
	}

	@ResponseBody
	@RequestMapping("/insert.do")
	public Object insert(Limitation limitation, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {

			validateParameters(limitation);
			limitationService.insertLimitation(limitation);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(HttpServletRequest request, HttpServletResponse response, Limitation limitation) {
		List<Limitation> list = limitationService.selectLimitations(limitation, new PageBounds());
		if (list != null && list.size() > 0) {
			limitation = list.get(0);
		}

		request.setAttribute("itemEdit", limitation);
		setProperty(request);
		return PAGE_UPDATE;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object update(Limitation limitation, HttpServletRequest Limitation, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {

			validateParameters(limitation);
			limitationService.updateLimitation(limitation);

			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}

	@ResponseBody
	@RequestMapping("/delete.do")
	public Object delete(Long limitationId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {

			if (limitationId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			Limitation limitation = new Limitation();
			limitation.setStatus(CommonStatus.OFFLINE);
			limitation.setLimitationId(limitationId);
			limitationService.updateLimitation(limitation);

			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}

	@RequestMapping("/view.do")
	public String view(Limitation limitation, ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		List<Limitation> list = limitationService.selectLimitations(limitation, new PageBounds());
		if (list != null && list.size() > 0) {
			limitation = list.get(0);
		}
		request.setAttribute("itemEdit", limitation);
		return PAGE_VIEW;
	}

}
