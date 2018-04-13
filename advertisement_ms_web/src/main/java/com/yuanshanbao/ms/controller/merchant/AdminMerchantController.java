package com.yuanshanbao.ms.controller.merchant;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.UploadUtils;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.merchant.model.Merchant;
import com.yuanshanbao.dsp.merchant.service.MerchantService;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/merchant")
public class AdminMerchantController extends PaginationController {

	private static final String PAGE_LIST = "advertisement/merchant/listMerchant";

	private static final String PAGE_INSERT = "advertisement/merchant/insertMerchant";

	private static final String PAGE_UPDATE = "advertisement/merchant/updateMerchant";

	private static final String PAGE_VIEW = "advertisement/merchant/viewMerchant";

	@Autowired
	private MerchantService merchantService;

	@RequestMapping("/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, Merchant merchant, HttpServletRequest request, HttpServletResponse response) {
		Object object = merchantService.selectMerchants(merchant, getPageBounds(range, request));
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
	public Object insert(Merchant merchant, @RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {

			if (file != null && !file.isEmpty()) {
				merchant.setImageUrl(UploadUtils.uploadFile(file, UploadUtils.FTP_COMMON_FILE));
			}
			validateParameters(merchant);
			merchantService.insertMerchant(merchant);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (IOException e2) {
			LoggerUtil.error("[ms insert merchant]", e2);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.FAIL);
		}

		return result;
	}

	@ResponseBody
	@RequestMapping("/delete.do")
	public Object delete(Long merchantId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {

			if (merchantId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			Merchant merchant = new Merchant();
			merchant.setStatus(CommonStatus.OFFLINE);
			merchant.setMerchantId(merchantId);
			merchantService.updateMerchant(merchant);

			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(HttpServletRequest request, HttpServletResponse response, Merchant merchant) {
		List<Merchant> list = merchantService.selectMerchants(merchant, new PageBounds());
		if (list != null && list.size() > 0) {
			merchant = list.get(0);
		}

		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());
		request.setAttribute("itemEdit", merchant);
		return PAGE_UPDATE;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object update(Merchant merchant, @RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			
			if (file != null && !file.isEmpty()) {
				merchant.setImageUrl(UploadUtils.uploadFile(file, UploadUtils.FTP_COMMON_FILE));
			}
			validateParameters(merchant);
			merchantService.updateMerchant(merchant);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (IOException e2) {
			LoggerUtil.error("[ms update merchant]", e2);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.FAIL);
		}

		return result;
	}

	@RequestMapping("/view.do")
	public String view(Merchant merchant, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		List<Merchant> list = merchantService.selectMerchants(merchant, new PageBounds());
		if (list != null && list.size() > 0) {
			merchant = list.get(0);
		}
		request.setAttribute("itemEdit", merchant);
		return PAGE_VIEW;
	}

}
