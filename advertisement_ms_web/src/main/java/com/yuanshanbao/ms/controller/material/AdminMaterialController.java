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
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.material.model.Material;
import com.yuanshanbao.dsp.material.model.MaterialStatus;
import com.yuanshanbao.dsp.material.model.MaterialType;
import com.yuanshanbao.dsp.material.service.MaterialService;
import com.yuanshanbao.dsp.order.model.Order;
import com.yuanshanbao.dsp.plan.model.PlanStatus;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.dsp.probability.service.ProbabilityService;
import com.yuanshanbao.dsp.quota.model.Quota;
import com.yuanshanbao.dsp.quota.service.QuotaService;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.ms.controller.common.AdminServerController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/material")
public class AdminMaterialController extends PaginationController {
	private static final String PAGE_LIST = "advertisement/material/listMaterial";

	private static final String PAGE_INSERT = "advertisement/material/insertMaterial";

	private static final String PAGE_UPDATE = "advertisement/material/updateMaterial";

	private static final String PAGE_UNREVIEW_LIST = "advertisement/material/listUnreview";

	private static final String PAGE_REVIEW = "advertisement/material/reviewMaterial";

	@Autowired
	private MaterialService materialService;

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
	public Object query(String range, Material material, HttpServletRequest request, HttpServletResponse response) {
		Object object = materialService.selectMaterial(material, getPageBounds(range, request));
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
	public Object insert(HttpServletRequest request, HttpServletResponse response, Material material,
			MultipartFile image) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
			if (bufferedImage == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			if (image != null && !image.isEmpty()) {
				material.setImageUrl(UploadUtils.uploadFile(image, "test/img"));
			}
			material.setProjectId(getProjectId(request));
			material.setWidth(bufferedImage.getWidth());
			material.setHeight(bufferedImage.getHeight());
			validateParameters(material);
			material.setStatus(MaterialStatus.UNREVIEWED);
			materialService.insertMaterial(material);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("material insert function - upload image error", e2);
		}

		return result;
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(HttpServletRequest request, HttpServletResponse response, Material material,
			Probability probability, Quota quota) {
		Advertiser advertiser = getBindAdvertiserByUser();
		if (advertiser != null) {
			material.setAdvertiserId(advertiser.getAdvertiserId());
		}
		List<Material> list = materialService.selectMaterial(material, new PageBounds());
		Material result = new Material();
		if (list != null && list.size() > 0) {
			result = list.get(0);
		}
		request.setAttribute("itemEdit", result);
		return PAGE_UPDATE;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object update(Material material, MultipartFile image, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
			if (bufferedImage == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			if (image != null && !image.isEmpty()) {
				material.setImageUrl(UploadUtils.uploadFile(image, "test/img"));
				material.setWidth(bufferedImage.getWidth());
				material.setHeight(bufferedImage.getHeight());
			}
			validateParameters(material);
			materialService.updateMaterial(material);
			AdminServerController.refreshConfirm();
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("material update function - upload image error", e2);
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
	public Object reviewQuery(String range, Material material, Order order, HttpServletRequest request,
			HttpServletResponse response) {
		material.setProjectId(getProjectId(request));
		Object object = materialService.selectMaterial(material, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/reviewDetails.do")
	public String reviewDetails(String range, Long materialId, HttpServletRequest request, HttpServletResponse response) {
		Material material = materialService.selectMaterial(materialId);
		request.setAttribute("itemEdit", material);
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(PlanStatus.ONLINE, PlanStatus.ONLINE_DESCRIPTION);
		map.put(PlanStatus.DENIED, PlanStatus.DENIED_DESCRIPTION);
		request.setAttribute("statusList", map.entrySet());
		return PAGE_REVIEW;
	}

	@ResponseBody
	@RequestMapping("/review.do")
	public Object review(HttpServletRequest request, HttpServletResponse response, Material material) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (material.getMaterialId() == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			materialService.updateMaterial(material);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("plan update function - upload image error", e2);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/delete.do")
	public Object delete(Long materialId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {

			if (materialId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			Material material = new Material();
			material.setStatus(CommonStatus.OFFLINE);
			material.setMaterialId(materialId);
			materialService.updateMaterial(material);
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
