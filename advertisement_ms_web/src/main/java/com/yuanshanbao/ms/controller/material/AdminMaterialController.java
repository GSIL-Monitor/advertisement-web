package com.yuanshanbao.ms.controller.material;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
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
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.material.model.Material;
import com.yuanshanbao.dsp.material.model.MaterialStatus;
import com.yuanshanbao.dsp.material.model.MaterialType;
import com.yuanshanbao.dsp.material.service.MaterialService;
import com.yuanshanbao.dsp.order.model.Order;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.dsp.probability.service.ProbabilityService;
import com.yuanshanbao.dsp.quota.model.Quota;
import com.yuanshanbao.dsp.quota.service.QuotaService;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.ms.controller.common.AdminServerController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/creative")
public class AdminMaterialController extends PaginationController {
	private static final String PAGE_LIST = "advertisement/creative/listMaterial";

	private static final String PAGE_INSERT = "advertisement/creative/insertMaterial";

	private static final String PAGE_UPDATE = "advertisement/creative/updateMaterial";

	private static final String PAGE_UNREVIEW_LIST = "advertisement/creative/listUnreview";

	private static final String PAGE_REVIEW = "advertisement/creative/reviewMaterial";

	@Autowired
	private MaterialService creativeService;

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

	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, Material creative, HttpServletRequest request, HttpServletResponse response) {
		Object object = creativeService.selectMaterial(creative, getPageBounds(range, request));
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
		request.setAttribute("typeList", MaterialType.getCodeDescriptionMap().entrySet());
	}

	@ResponseBody
	@RequestMapping("/insert.do")
	public Object insert(HttpServletRequest request, HttpServletResponse response, Material creative,
			MultipartFile image) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
			if (bufferedImage == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			if (image != null && !image.isEmpty()) {
				creative.setImageUrl(UploadUtils.uploadFile(image, "test/img"));
			}
			creative.setWidth(bufferedImage.getWidth());
			creative.setHeight(bufferedImage.getHeight());
			validateParameters(creative);
			creative.setStatus(MaterialStatus.UNREVIEWED);
			creativeService.insertMaterial(creative);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("creative insert function - upload image error", e2);
		}

		return result;
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(HttpServletRequest request, HttpServletResponse response, Material creative,
			Probability probability, Quota quota) {
		String isDisplay = "false";
		List<Material> list = creativeService.selectMaterial(creative, new PageBounds());
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
	public Object update(Material creative, String prizeDesc, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			validateParameters(creative);
			creativeService.updateMaterial(creative);
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
	public Object reviewQuery(String range, Material creative, Order order, HttpServletRequest request,
			HttpServletResponse response) {
		creative.setStatus(MaterialStatus.UNREVIEWED);
		Object object = creativeService.selectMaterial(creative, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/reviewDetails.do")
	public String reviewDetails(String range, Long creativeId, HttpServletRequest request, HttpServletResponse response) {
		Material creative = creativeService.selectMaterial(creativeId);
		request.setAttribute("itemEdit", creative);
		request.setAttribute("statusList", MaterialStatus.getCodeDescriptionMap().entrySet());
		return PAGE_REVIEW;
	}

	@ResponseBody
	@RequestMapping("/review.do")
	public Object review(HttpServletRequest request, HttpServletResponse response, Material creative) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (creative.getMaterialId() == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			creativeService.updateMaterial(creative);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("plan update function - upload image error", e2);
		}
		return result;
	}
}