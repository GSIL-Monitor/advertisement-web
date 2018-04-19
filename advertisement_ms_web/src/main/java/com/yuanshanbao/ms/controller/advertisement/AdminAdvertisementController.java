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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.UploadUtils;
import com.yuanshanbao.dsp.advertisement.model.Advertisement;
import com.yuanshanbao.dsp.advertisement.service.AdvertisementService;
import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.advertiser.service.AdvertiserService;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
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
@RequestMapping("/admin/advertisement")
public class AdminAdvertisementController extends PaginationController {

	private static final String PAGE_LIST = "advertisement/advertisement/listAdvertisement";

	private static final String PAGE_INSERT = "advertisement/advertisement/insertAdvertisement";

	private static final String PAGE_UPDATE = "advertisement/advertisement/updateAdvertisement";

	private static final String PAGE_VIEW = "advertisement/advertisement/viewAdvertisement";

	@Autowired
	private AdvertisementService advertisementService;

	@Autowired
	private AdvertiserService advertiserService;

	@Autowired
	private ProbabilityService probabilityService;

	@Autowired
	private QuotaService quotaService;

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
		Object object = advertisementService.selectAdvertisement(advertisement, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/insertWindow.do")
	public String insertGiftWindow(HttpServletRequest request, HttpServletResponse response, Long advertiserId,
			Integer type, ModelMap modelMap) {
		setProperty(request, getProjectId(request), advertiserId);
		modelMap.put("categories", ConfigManager.getCategoryMap());
		return PAGE_INSERT;
	}

	private void setProperty(HttpServletRequest request, Long projectId, Long advertiserId) {
		Advertiser advertiser = advertiserService.selectAdvertiser(advertiserId);
		if (advertiser != null) {
			request.setAttribute("advertiser", advertiser);
		} else {
			Advertiser param = new Advertiser();
			param.setProjectId(projectId);
			request.setAttribute("advertiserList", advertiserService.selectAdvertiser(param, new PageBounds()));
		}
		request.setAttribute("positionList", ConstantsManager.getPositionList(projectId));
		request.setAttribute("typeList", QuotaType.getCodeDescriptionMap().entrySet());
		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());
	}

	@ResponseBody
	@RequestMapping("/insert.do")
	public Object insert(HttpServletRequest request, HttpServletResponse response, Advertisement advertisement,
			Probability probability, Quota quota, MultipartFile image) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			if (image != null && !image.isEmpty()) {
				advertisement.setImageUrl(UploadUtils.uploadFile(image, "file/game"));
			}

			validateParameters(advertisement);
			advertisement.setProjectId(getProjectId(request));
			advertisementService.insertAdvertisement(advertisement);
			probability.setProjectId(getProjectId(request));
			probability.setAdvertisementId(advertisement.getAdvertisementId());
			probabilityService.insertProbability(probability);
			quota.setProjectId(getProjectId(request));
			quota.setAdvertisementId(advertisement.getAdvertisementId());
			quotaService.insertQuota(quota);
			AdminServerController.refreshConfirm();
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("advertisement insert function - upload image error", e2);
		}

		return result;
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(HttpServletRequest request, HttpServletResponse response, Advertisement advertisement) {
		List<Advertisement> list = advertisementService.selectAdvertisement(advertisement, new PageBounds());
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
			advertisementService.updateAdvertisement(advertisement);
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
			advertisementService.updateAdvertisement(advertisement);

			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}

	@RequestMapping("/view.do")
	public String view(Advertisement advertisement, ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		List<Advertisement> list = advertisementService.selectAdvertisement(advertisement, new PageBounds());
		if (list != null && list.size() >= 0) {
			advertisement = list.get(0);
		}
		request.setAttribute("itemEdit", advertisement);
		return PAGE_VIEW;
	}

}
