package com.yuanshanbao.ms.controller.admin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.ms.model.admin.MenuCategory;
import com.yuanshanbao.ms.service.admin.MenuCategoryService;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/menucategory")
public class AdminMenuCategoryController extends PaginationController {
	private static final String PAGE_ADMIN = "admin/system/menucategory/adminMenuCategory";

	private static final String PAGE_INSERT = "admin/system/menucategory/insertMenuCategory";

	@Autowired
	private MenuCategoryService mcService;

	@RequestMapping("/adminMenuCategorys.do")
	public String adminMenuCategory(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_ADMIN;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/queryMenuCategorys.do")
	public Object queryMenuCategorys(String range, @RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "dir", required = false) String dir, MenuCategory menuCategory,
			HttpServletRequest request, HttpServletResponse response) {
		Object result = mcService.queryMenuCategorys(menuCategory, getPageBounds(range, request));
		PageList pageList = (PageList) result;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/insertMenuCategoryWindow.do")
	public String insertMenuCategoryWindow(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_INSERT;
	}

	@ResponseBody
	@RequestMapping("/insertMenuCategory.do")
	public Object insertMenuCategory(@Valid MenuCategory menuCategory, BindingResult br, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> result = new HashMap<String, String>();

		if (mcService.isExistMenuCategory(menuCategory.getName())) {
			result.put(RET_CODE_PARAM, RET_INTERROR);
			result.put(RET_ERROR_MSG, "已经存在同名栏目，请重新输入名称！");
			return result;
		}

		if (br.hasErrors()) {
			result.put(RET_CODE_PARAM, RET_INTERROR);
			result.put(RET_ERROR_MSG, br.getAllErrors().get(0).getDefaultMessage());
			return result;
		}

		menuCategory.setId(UUID.randomUUID().toString());

		if (mcService.insertMenuCategory(menuCategory)) {
			result.put(RET_CODE_PARAM, RET_SUCCESS);
			result.put(RET_HTML, getSuccessHtml("添加成功"));
		} else {
			result.put(RET_CODE_PARAM, RET_FAILURE);
		}

		return result;
	}

	@ResponseBody
	@RequestMapping("/deleteMenuCategory.do")
	public Object deleteMenuCategory(MenuCategory menuCategory, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> result = new HashMap<String, String>();

		if (mcService.deleteMenuCategory(menuCategory)) {
			result.put(RET_CODE_PARAM, RET_SUCCESS);
		} else {
			result.put(RET_CODE_PARAM, RET_FAILURE);
		}

		return result;
	}
}
