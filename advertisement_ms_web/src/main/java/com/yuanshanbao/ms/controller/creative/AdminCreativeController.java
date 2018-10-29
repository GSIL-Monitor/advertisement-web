package com.yuanshanbao.ms.controller.creative;

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
import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.advertiser.service.AdvertiserService;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.creative.model.Creative;
import com.yuanshanbao.dsp.creative.model.CreativeSize;
import com.yuanshanbao.dsp.creative.model.CreativeType;
import com.yuanshanbao.dsp.creative.service.CreativeService;
import com.yuanshanbao.dsp.order.model.Order;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.dsp.probability.model.ProbabilityStatus;
import com.yuanshanbao.dsp.probability.service.ProbabilityService;
import com.yuanshanbao.dsp.quota.model.Quota;
import com.yuanshanbao.dsp.quota.service.QuotaService;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.ms.controller.common.AdminServerController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/creative")
public class AdminCreativeController extends PaginationController {
	private static final String PAGE_LIST = "advertisement/creative/listCreative";

	private static final String PAGE_INSERT = "advertisement/creative/insertCreative";

	private static final String PAGE_UPDATE = "advertisement/creative/updateCreative";
	
	private static final String PAGE_UNREVIEW_LIST = "advertisement/creative/updateCreative";
	
	private static final String PAGE_REVIEW = "advertisement/creative/reviewCreative";
	
	

	@Autowired
	private CreativeService creativeService;

	@Autowired
	private AdvertiserService advertiserService;

	@Autowired
	private ProbabilityService probabilityService;

	@Autowired
	private QuotaService quotaService;

	@RequestMapping("/list.do")
	public String list(Long advertiserId, HttpServletRequest request, HttpServletResponse response) {
		Advertiser advertiser = getBindAdvertiserByUser();
		if (advertiser != null) {
			advertiserId = advertiser.getAdvertiserId();
		}
		setProperty(request, getProjectId(request));
		request.setAttribute("advertiserId", advertiserId);
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, Creative creative, HttpServletRequest request, HttpServletResponse response) {
		Object object = creativeService.selectCreative(creative, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/insertWindow.do")
	public String insertGiftWindow(HttpServletRequest request, HttpServletResponse response, Integer type,
			ModelMap modelMap) {
		setProperty(request, getProjectId(request));
		modelMap.put("categories", ConfigManager.getCategoryMap());
		return PAGE_INSERT;
	}

	private void setProperty(HttpServletRequest request, Long projectId) {
		Advertiser advertiser = getBindAdvertiserByUser();
		if (advertiser != null) {
			request.setAttribute("advertiser", advertiser);
		} else {
			Advertiser param = new Advertiser();
			param.setProjectId(projectId);
			request.setAttribute("advertiserList", advertiserService.selectAdvertiser(param, new PageBounds()));
		}
		request.setAttribute("typeList", CreativeType.getCodeDescriptionMap().entrySet());
		request.setAttribute("sizeList", CreativeSize.getCodeDescriptionMap().entrySet());
	}

	@ResponseBody
	@RequestMapping("/insert.do")
	public Object insert(HttpServletRequest request, HttpServletResponse response, Creative creative,
			MultipartFile image) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (image != null && !image.isEmpty()) {
				creative.setImageUrl(UploadUtils.uploadFile(image, "test/img"));
			}
			validateParameters(creative);
			creative.setStatus(CommonStatus.ONLINE);
			creativeService.insertCreative(creative);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("creative insert function - upload image error", e2);
		}

		return result;
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(HttpServletRequest request, HttpServletResponse response, Creative creative,
			Probability probability, Quota quota) {
		String isDisplay = "false";
		List<Creative> list = creativeService.selectCreative(creative, new PageBounds());
		List<Probability> proList = probabilityService.selectProbabilitys(probability, new PageBounds());
		List<Quota> quotaList = quotaService.selectQuota(quota, new PageBounds());
		if (list != null && list.size() >= 0) {
			creative = list.get(0);
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
		// setProperty(request, getProjectId(request), advertiserId);
		request.setAttribute("categories", ConfigManager.getCategoryMap());
		request.setAttribute("tagsList", ConstantsManager.getTagsList(ConstantsManager.ADVERTISEMENT));
		request.setAttribute("itemEdit", creative);
		request.setAttribute("probability", probability);
		request.setAttribute("quota", quota);
		return PAGE_UPDATE;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object update(Creative creative, String prizeDesc, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			validateParameters(creative);
			creativeService.updateCreative(creative);
			AdminServerController.refreshConfirm();
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("creative update function - upload image error", e2);
		}

		return result;
	}
	
	@RequestMapping("/reviewWindow.do")
	public String reviewWindow(Long advertiserId, HttpServletRequest request, HttpServletResponse response, Long orderId) {
		Advertiser advertiser = getBindAdvertiserByUser();
		if (advertiser != null) {
			advertiserId = advertiser.getAdvertiserId();
		}
		return PAGE_UNREVIEW_LIST;
	}

	@ResponseBody
	@RequestMapping("/reviewQuery.do")
	public Object reviewQuery(String range, Probability probability, Order order, HttpServletRequest request, HttpServletResponse response) {	
		probability.setStatus(ProbabilityStatus.UNREVIEWED);
		List<Probability> list = probabilityService.selectProbabilitys(probability, new PageBounds());
		PageList pageList = (PageList) list;
		return setPageInfo(request, response, pageList);
	}
	
	@RequestMapping("/reviewDetails.do")
	public String reviewDetails(String range, Long probabilityId, HttpServletRequest request, HttpServletResponse response) {	
		Probability probability = probabilityService.selectProbability(probabilityId);
		request.setAttribute("itemEdit", probability);
		request.setAttribute("statusList", ProbabilityStatus.getCodeDescriptionMap().entrySet());
		return PAGE_REVIEW;
	}
	
	@ResponseBody
	@RequestMapping("/review.do")
	public Object review(HttpServletRequest request, HttpServletResponse response, Probability probability) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (probability.getProbabilityId() == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			probabilityService.updateProbability(probability);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("plan update function - upload image error", e2);
		}
		return result;
	}
}
