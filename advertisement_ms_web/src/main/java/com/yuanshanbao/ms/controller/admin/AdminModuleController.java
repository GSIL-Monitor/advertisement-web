package com.yuanshanbao.ms.controller.admin;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import com.yuanshanbao.ms.filter.security.SysRightsInfo;
import com.yuanshanbao.ms.model.admin.SysModule;
import com.yuanshanbao.ms.service.admin.RightService;
import com.yuanshanbao.ms.service.admin.SysModuleService;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/module")
public class AdminModuleController extends PaginationController {
	private static final String PAGE_ADMINMODULE = "admin/system/module/adminModule";

	private static final String PAGE_INSERTMODULE = "admin/system/module/insertModule";

	private static final String PAGE_UPDATEMODULERIGHT = "admin/system/module/updateModuleRight";

	@Autowired
	private RightService rightService;

	@Autowired
	private SysModuleService sysModuleService;

	@Autowired
	private SysRightsInfo sysRightsInfo;

	@RequestMapping("/adminModules.do")
	public String adminModules(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_ADMINMODULE;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/queryModules.do")
	public Object queryModules(String range, SysModule module, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		if (module.getName() != null && !"".equals(module.getName().trim())) {
			module.setName(URLDecoder.decode(module.getName(), "UTF-8"));
		}
		Object result = sysModuleService.queryModules(module, getPageBounds(range, request));
		PageList pageList = (PageList) result;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/insertModuleWindow.do")
	public String insertModuleWindow(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_INSERTMODULE;
	}

	@ResponseBody
	@RequestMapping("/insertModule.do")
	public Object insertModule(@Valid SysModule module, BindingResult br, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> result = new HashMap<String, String>();

		if (sysModuleService.isExistModule(module.getName())) {
			result.put(RET_CODE_PARAM, RET_INTERROR);
			result.put(RET_ERROR_MSG, "已经存在同名模块，请重新输入模块名称！");
			return result;
		}

		if (br.hasErrors()) {
			result.put(RET_CODE_PARAM, RET_INTERROR);
			result.put(RET_ERROR_MSG, br.getAllErrors().get(0).getDefaultMessage());
			return result;
		}

		module.setId(UUID.randomUUID().toString());

		if (sysModuleService.insertModule(module)) {
			result.put(RET_CODE_PARAM, RET_SUCCESS);
			result.put(RET_HTML, getSuccessHtml("添加模块成功"));
		} else {
			result.put(RET_CODE_PARAM, RET_FAILURE);
		}

		return result;
	}

	@ResponseBody
	@RequestMapping("/deleteModule.do")
	public Object deleteModule(SysModule module, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> result = new HashMap<String, String>();

		if (sysModuleService.deleteModule(module)) {
			result.put(RET_CODE_PARAM, RET_SUCCESS);
		} else {
			result.put(RET_CODE_PARAM, RET_FAILURE);
		}

		return result;
	}

	@RequestMapping("/updateModuleRightWindow.do")
	public String updateModuleRightWindow(@RequestParam(value = "moduleid", required = true) String moduleId,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("moduleId", moduleId);
		return PAGE_UPDATEMODULERIGHT;
	}

	@ResponseBody
	@RequestMapping("/queryRightsByModuleId.do")
	public Object queryRightsByModuleId(@RequestParam(value = "flag", required = true) String flag,
			@RequestParam(value = "moduleid", required = true) String moduleId, HttpServletRequest request,
			HttpServletResponse response) {
		Object rights = rightService.queryRightsByModuleId(flag, moduleId, getPageBounds(null, request));
		return setPageInfo(request, response, (PageList) rights);
	}

	@ResponseBody
	@RequestMapping("/insertModuleRights.do")
	public Object insertModuleRights(@RequestParam(value = "moduleid", required = true) String moduleId,
			@RequestParam(value = "insertRightIds", required = true, defaultValue = "") String insertRightIds,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> result = new HashMap<String, String>();
		String[] rightIds = insertRightIds.split(",");
		if (sysModuleService.insertModuleRights(moduleId, rightIds)) {
			result.put(RET_CODE_PARAM, RET_SUCCESS);
		} else {
			result.put(RET_CODE_PARAM, RET_FAILURE);
		}

		sysRightsInfo.reloadRightsMap();

		return result;
	}

	@ResponseBody
	@RequestMapping("/deleteModuleRights.do")
	public Object deleteModuleRights(@RequestParam(value = "moduleid", required = true) String moduleId,
			@RequestParam(value = "deleteRightIds", required = true, defaultValue = "") String insertRightIds,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> result = new HashMap<String, String>();
		String[] rightIds = insertRightIds.split(",");
		if (sysModuleService.deleteModuleRights(moduleId, rightIds)) {
			result.put(RET_CODE_PARAM, RET_SUCCESS);
		} else {
			result.put(RET_CODE_PARAM, RET_FAILURE);
		}

		sysRightsInfo.reloadRightsMap();

		return result;
	}
}
