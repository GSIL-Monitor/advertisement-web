package com.yuanshanbao.ms.controller.advertisement;

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
import org.springframework.web.multipart.MultipartFile;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.UploadUtils;
import com.yuanshanbao.dsp.advertisement.model.Advertisement;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementStatus;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementType;
import com.yuanshanbao.dsp.advertisement.service.AdvertisementService;
import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.advertiser.service.AdvertiserService;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.dsp.probability.model.ProbabilityStatus;
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
	public String list(Long advertiserId, HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("advertiserId", advertiserId);
		setProperty(request, getProjectId(request), advertiserId);
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, Advertisement advertisement, HttpServletRequest request,
			HttpServletResponse response) {
		Advertiser advertiser = getBindAdvertiserByUser();
		if (advertiser != null) {
			advertisement.setAdvertiserId(advertiser.getAdvertiserId());
		}
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
		request.setAttribute("quotaTypeList", QuotaType.getCodeDescriptionMap().entrySet());
		request.setAttribute("typeList", AdvertisementType.getCodeDescriptionMap().entrySet());
		request.setAttribute("statusList", AdvertisementStatus.getCodeDescriptionMap().entrySet());
	}

	@ResponseBody
	@RequestMapping("/insert.do")
	public Object insert(HttpServletRequest request, HttpServletResponse response, Advertisement advertisement,
			Probability probability, Quota quota, MultipartFile image) {
		Map<String, Object> result = new HashMap<String, Object>();
		System.out.println("[InsertAd] start");
		try {
			if (image != null && !image.isEmpty()) {
				advertisement.setImageUrl(UploadUtils.uploadFile(image, "file/game"));
			}
			LoggerUtil.info("[InsertAd] uploadImage");
			System.out.println("[InsertAd] uploadImage");
			validateParameters(advertisement);
			LoggerUtil.info("[InsertAd] validateParameters");
			System.out.println("[InsertAd] validateParameters");
			String quotaType = request.getParameter("quotaType");
			advertisement.setProjectId(getProjectId(request));
			advertisementService.insertAdvertisement(advertisement);
			LoggerUtil.info("[InsertAd] insertAdvertisement");
			System.out.println("[InsertAd] insertAdvertisement");
			probability.setProjectId(getProjectId(request));
			probability.setAdvertisementId(advertisement.getAdvertisementId());
			probabilityService.insertProbability(probability);
			LoggerUtil.info("[InsertAd] insertProbability");
			System.out.println("[InsertAd] insertProbability");
			quota.setProjectId(getProjectId(request));
			quota.setType(Integer.valueOf(quotaType));
			quota.setAdvertisementId(advertisement.getAdvertisementId());
			quotaService.insertQuota(quota);
			LoggerUtil.info("[InsertAd] insertQuota");
			System.out.println("[InsertAd] insertQuota");
			AdminServerController.refreshConfirm();
			LoggerUtil.info("[InsertAd] refreshConfirm");
			System.out.println("[InsertAd] refreshConfirm");
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("advertisement insert function - upload image error", e2);
		}

		return result;
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(HttpServletRequest request, HttpServletResponse response, Advertisement advertisement,
			Probability probability, Quota quota) {
		String isDisplay = "false";
		List<Advertisement> list = advertisementService.selectAdvertisement(advertisement, new PageBounds());
		List<Probability> proList = probabilityService.selectProbabilitys(probability, new PageBounds());
		List<Quota> quotaList = quotaService.selectQuota(quota, new PageBounds());
		if (list != null && list.size() >= 0) {
			advertisement = list.get(0);
		}
		if (proList != null && proList.size() == 1) {
			if (quotaList != null && quotaList.size() == 1) {
				probability = proList.get(0);
				quota = quotaList.get(0);
				isDisplay = "true";
			}
		}
		Long advertiserId = null;
		request.setAttribute("isDisplay", isDisplay);
		setProperty(request, getProjectId(request), advertiserId);
		request.setAttribute("categories", ConfigManager.getCategoryMap());
		request.setAttribute("tagsList", ConstantsManager.getTagsList(ConstantsManager.ADVERTISEMENT));
		request.setAttribute("itemEdit", advertisement);
		request.setAttribute("probability", probability);
		request.setAttribute("quota", quota);
		return PAGE_UPDATE;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object update(Advertisement advertisement, String prizeDesc, Probability probability, Quota quota,
			MultipartFile image, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			// checkForm(advertisement);
			if (image != null && !image.isEmpty()) {
				advertisement.setImageUrl(UploadUtils.uploadFile(image, "file/game"));
			}
			validateParameters(advertisement);
			advertisementService.updateAdvertisement(advertisement);
			probabilityService.updateProbability(probability);
			quotaService.updateQuota(quota);
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
			List<Probability> proList = new ArrayList<Probability>();
			Probability probability = new Probability();
			probability.setAdvertisementId(advertisementId);
			proList = probabilityService.selectProbabilitys(probability, new PageBounds());
			if (proList.size() > 0) {
				probability = proList.get(0);
			}
			probability.setStatus(ProbabilityStatus.DELETE);
			probabilityService.updateProbability(probability);
			Advertisement advertisement = new Advertisement();
			advertisement.setStatus(AdvertisementStatus.DELETE);
			advertisement.setAdvertisementId(advertisementId);
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

	@ResponseBody
	@RequestMapping("/updateStatus.do")
	public Object updateStatus(Long advertisementId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {

			if (advertisementId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			List<Probability> proList = new ArrayList<Probability>();
			Probability probability = new Probability();
			probability.setAdvertisementId(advertisementId);
			proList = probabilityService.selectProbabilitys(probability, new PageBounds());
			if (proList.size() > 0) {
				probability = proList.get(0);
			}
			Advertisement advertisement = new Advertisement();
			advertisement.setAdvertisementId(advertisementId);
			advertisement = advertisementService.selectAdvertisement(advertisement);
			if (advertisement.getStatus() == 1) {
				advertisement.setStatus(AdvertisementStatus.OFFLINE);
				probability.setStatus(ProbabilityStatus.OFFLINE);
			} else if (advertisement.getStatus() == 2) {
				advertisement.setStatus(AdvertisementStatus.ONLINE);
				probability.setStatus(ProbabilityStatus.ONLINE);
			}
			advertisementService.updateAdvertisement(advertisement);
			probabilityService.updateProbability(probability);
			AdminServerController.refreshConfirm();
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("advertisement update function - upload image error", e2);
		}

		return result;
	}
}
