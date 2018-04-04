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
import com.yuanshanbao.ad.advertisement.model.Advertisement;
import com.yuanshanbao.ad.advertisement.model.AdvertisementCategory;
import com.yuanshanbao.ad.advertisement.service.AdvertisementCategoryService;
import com.yuanshanbao.ad.advertisement.service.AdvertisementService;
import com.yuanshanbao.ad.core.CommonStatus;
import com.yuanshanbao.ad.core.InterfaceRetCode;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.ms.controller.common.AdminServerController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/adCategory")
public class AdminAdvertisementCategoryController extends PaginationController {

	private static final String PAGE_LIST = "advertisement/advertisement/category/listCategory";

	private static final String PAGE_INSERT = "advertisement/advertisement/category/insertCategory";

	private static final String PAGE_UPDATE = "advertisement/advertisement/category/updateCategory";

	private static final String PAGE_VIEW = "advertisement/advertisement/category/viewCategory";

	private static final String PAGE_LIST_CATEGORY = "advertisement/advertisement/category/listCategoryAd";

	@Autowired
	private AdvertisementCategoryService categoryService;

	@Autowired
	private AdvertisementService advertisementService;

	@RequestMapping("/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, AdvertisementCategory category, HttpServletRequest request,
			HttpServletResponse response) {
		Object object = categoryService.selectCategory(category, getPageBounds(range, request));
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
	public Object insert(AdvertisementCategory category,
			@RequestParam(value = "image", required = false) MultipartFile image, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			validateParameters(category);
			categoryService.insertCategory(category);
			AdminServerController.refreshConfirm();
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("Category insert function - upload image error", e2);
		}

		return result;
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(HttpServletRequest request, HttpServletResponse response, AdvertisementCategory category) {
		List<AdvertisementCategory> list = categoryService.selectCategory(category, new PageBounds());
		if (list != null && list.size() >= 0) {
			category = list.get(0);
		}
		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());
		request.setAttribute("itemEdit", category);
		return PAGE_UPDATE;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object update(AdvertisementCategory category, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			validateParameters(category);
			categoryService.updateCategory(category);
			AdminServerController.refreshConfirm();
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("Category update function - upload image error", e2);
		}

		return result;
	}

	@ResponseBody
	@RequestMapping("/delete.do")
	public Object delete(Long categoryId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			if (categoryId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			AdvertisementCategory category = new AdvertisementCategory();
			category.setStatus(CommonStatus.OFFLINE);
			category.setCategoryId(categoryId);
			categoryService.updateCategory(category);

			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}

	@RequestMapping("/view.do")
	public String view(AdvertisementCategory category, ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		List<AdvertisementCategory> list = categoryService.selectCategory(category, new PageBounds());
		if (list != null && list.size() >= 0) {
			category = list.get(0);
		}
		request.setAttribute("itemEdit", category);
		return PAGE_VIEW;
	}

	@RequestMapping("/listCategory.do")
	public String listCategory(Long categoryId, HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("categoryId", categoryId);
		return PAGE_LIST_CATEGORY;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/queryCategory.do")
	public Object queryCategory(String range, Long categoryId, HttpServletRequest request, HttpServletResponse response) {
		Advertisement advertisement = new Advertisement();
		advertisement.setCategory(categoryId);
		advertisement.setStatus(CommonStatus.ONLINE);
		Object object = advertisementService.selectAdvertisement(advertisement, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

}
