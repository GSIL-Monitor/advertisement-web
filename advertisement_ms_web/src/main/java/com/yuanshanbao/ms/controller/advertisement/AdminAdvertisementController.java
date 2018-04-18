package com.yuanshanbao.ms.controller.advertisement;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.UploadUtils;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.advertisement.model.Advertisement;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementShowType;
import com.yuanshanbao.dsp.advertisement.service.AdvertisementService;
import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.advertiser.service.AdvertiserService;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.ms.controller.common.AdminServerController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/gift")
public class AdminAdvertisementController extends PaginationController {

	private static final String PAGE_LIST = "advertisement/advertisement/listAdvertisement";

	private static final String PAGE_INSERT = "advertisement/advertisement/insertAdvertisement";

	private static final String PAGE_UPDATE = "advertisement/advertisement/updateAdvertisement";

	private static final String PAGE_VIEW = "advertisement/advertisement/viewAdvertisement";

	@Autowired
	private AdvertisementService advertService;

	@Autowired
	private AdvertiserService advertiserService;

	@RequestMapping("/list.do")
	public String list(String advertiserId, HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("advertiserId", advertiserId);
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, Advertisement advertisement, HttpServletRequest request,
			HttpServletResponse response) {
		Object object = advertService.selectAdvertisement(advertisement, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/insertWindow.do")
	public String insertGiftWindow(String advertiserId, Integer type, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		setProperty(request, advertiserId, type);
		modelMap.put("categories", ConfigManager.getCategoryMap());
		return PAGE_INSERT;
	}

	private void setProperty(HttpServletRequest request, String advertiserId, Integer type) {
		Advertiser advertiser = advertiserService.selectAdvertiser(Long.parseLong(advertiserId));
		request.setAttribute("type", type);
		request.setAttribute("advertiser", advertiser);
		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());
		request.setAttribute("dynamicShowTypes", AdvertisementShowType.getDynamicDescriptionMap().entrySet());
		request.setAttribute("staticShowTypes", AdvertisementShowType.getStaticDescriptionMap().entrySet());
		request.setAttribute("tagsList", ConstantsManager.getTagsList(ConstantsManager.ADVERTISEMENT));
	}

	@ResponseBody
	@RequestMapping("/insert.do")
	public Object insert(Advertisement advertisement, Long productId,
			@RequestParam(value = "smallImage", required = false) MultipartFile smallImage,
			@RequestParam(value = "bigImage", required = false) MultipartFile bigImage,
			@RequestParam(value = "image", required = false) MultipartFile image, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			// checkForm(advertisement);
			if (image != null && !image.isEmpty()) {
				advertisement.setImageUrl(UploadUtils.uploadFile(image, "file/game"));
			}
			if (bigImage == null || bigImage.isEmpty()) {
				throw new BusinessException("大图不能为空");
			}

			validateParameters(advertisement);
			advertService.insertAdvertisement(advertisement);
			AdminServerController.refreshConfirm();
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("advertisement insert function - upload image error", e2);
		}

		return result;
	}

	private void checkForm(Advertisement advertisement) {
		advertisement.setLink(StringUtils.trim(advertisement.getLink()));
		if (StringUtils.isNotBlank(advertisement.getLink()) && !ValidateUtil.isUrl(advertisement.getLink())) {
			throw new BusinessException("广告链接格式错误");
		}
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(HttpServletRequest request, HttpServletResponse response, Advertisement advertisement) {
		List<Advertisement> list = advertService.selectAdvertisement(advertisement, new PageBounds());
		if (list != null && list.size() >= 0) {
			advertisement = list.get(0);
		}
		request.setAttribute("categories", ConfigManager.getCategoryMap());
		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());
		request.setAttribute("tagsList", ConstantsManager.getTagsList(ConstantsManager.ADVERTISEMENT));
		request.setAttribute("itemEdit", advertisement);
		return PAGE_UPDATE;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object update(Advertisement advertisement, String prizeDesc,
			@RequestParam(value = "smallImage", required = false) MultipartFile smallImage,
			@RequestParam(value = "bigImage", required = false) MultipartFile bigImage,
			@RequestParam(value = "image", required = false) MultipartFile image, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			// checkForm(advertisement);
			if (image != null && !image.isEmpty()) {
				advertisement.setImageUrl(UploadUtils.uploadFile(image, "file/game"));
			}
			validateParameters(advertisement);
			advertService.updateAdvertisement(advertisement);
			AdminServerController.refreshConfirm();
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("advertisement update function - upload image error", e2);
		}

		return result;
	}

	@ResponseBody
	@RequestMapping("/delete.do")
	public Object delete(Long advertisementId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {

			if (advertisementId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			Advertisement advertisement = new Advertisement();
			advertisement.setStatus(CommonStatus.OFFLINE);
			advertisement.setAdvertisementId(advertisementId);
			advertService.updateAdvertisement(advertisement);

			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}

	@RequestMapping("/view.do")
	public String view(Advertisement advertisement, ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		List<Advertisement> list = advertService.selectAdvertisement(advertisement, new PageBounds());
		if (list != null && list.size() >= 0) {
			advertisement = list.get(0);
		}
		request.setAttribute("itemEdit", advertisement);
		return PAGE_VIEW;
	}

}
