package com.yuanshanbao.ms.controller.admin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.ms.filter.security.SysRightsInfo;
import com.yuanshanbao.ms.model.admin.Right;
import com.yuanshanbao.ms.model.admin.SysModule;
import com.yuanshanbao.ms.service.admin.GroupService;
import com.yuanshanbao.ms.service.admin.RightService;
import com.yuanshanbao.ms.service.admin.SysModuleService;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/right")
public class AdminRightController extends PaginationController implements ApplicationContextAware {
	private static final String PAGE_MGRRIGHT = "admin/system/right/adminRight";

	private static final String PAGE_INSERTRIGHT = "admin/system/right/insertRight";

	@Autowired
	private RightService rightService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private SysModuleService moduleService;

	@Autowired
	private SysRightsInfo sysRightsInfo;

	private ApplicationContext applicationContext;

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/queryRights.do")
	public Object queryRights(String range, @RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "dir", required = false) String dir, Right right, HttpServletRequest request,
			HttpServletResponse response) {
		Object result = rightService.queryRights(right, getPageBounds(request));
		PageList pageList = (PageList) result;
		return setPageInfo(request, response, pageList);
	}

	@ResponseBody
	@RequestMapping(value = "/queryRightsForMenu.do")
	public Object queryRightsForMenu(HttpServletRequest request, HttpServletResponse response) {
		PageBounds pageBounds = new PageBounds();
		Right right = new Right();
		List<Right> result = rightService.queryRights(right, pageBounds);
		return result;
	}

	// @ResponseBody
	// @RequestMapping(value = "/queryAllAvailableUrls.do")
	// public Object queryAllAvailableUrls(HttpServletRequest request,
	// HttpServletResponse response) {
	// RequestMappingHandlerMapping handlerMapping =
	// applicationContext.getBean(RequestMappingHandlerMapping.class);
	// Set<String> allUrls = new HashSet<String>();
	// Map<RequestMappingInfo, HandlerMethod> handlerMap =
	// handlerMapping.getHandlerMethods();
	// for (Map.Entry<RequestMappingInfo, HandlerMethod> entry :
	// handlerMap.entrySet()) {
	// allUrls.addAll(entry.getKey().getPatternsCondition().getPatterns());
	// }
	// PageBounds pageBounds = new PageBounds();
	// Right emptyRight = new Right();
	// List<Right> result = rightService.queryRights(emptyRight, pageBounds);
	// Set<String> rightUrls = new HashSet<String>();
	// for (Right right : result) {
	// rightUrls.add(right.getUrl());
	// }
	// Set<Right> urls = new LinkedHashSet<Right>();
	// for (String url : allUrls) {
	// if (!rightUrls.contains(url) && !url.contains("login") &&
	// !url.contains("logout")) {
	// Right right = new Right();
	// right.setUrl(url);
	// urls.add(right);
	// }
	// }
	// return urls;
	// }

	@RequestMapping("/adminRights.do")
	public String adminRights(HttpServletRequest request, HttpServletResponse response) {
		List<SysModule> modules = moduleService.queryModules(new SysModule(), new PageBounds());
		request.setAttribute("modules", modules);
		return PAGE_MGRRIGHT;
	}

	@RequestMapping("/insertRightWindow.do")
	public String insertRight(HttpServletRequest request, HttpServletResponse response) {
		RequestMappingHandlerMapping handlerMapping = (RequestMappingHandlerMapping) applicationContext.getBean("requestMappingHandlerMapping");
		Set<String> allUrls = new HashSet<String>();
		Map<RequestMappingInfo, HandlerMethod> handlerMap = handlerMapping.getHandlerMethods();
		for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMap.entrySet()) {
			allUrls.addAll(entry.getKey().getPatternsCondition().getPatterns());
		}
		PageBounds pageBounds = new PageBounds();
		Right emptyRight = new Right();
		List<Right> result = rightService.queryRights(emptyRight, pageBounds);
		Set<String> rightUrls = new HashSet<String>();
		for (Right right : result) {
			rightUrls.add(right.getUrl());
		}
		Set<Right> urls = new LinkedHashSet<Right>();
		for (String url : allUrls) {
			if (!rightUrls.contains(url) && !url.endsWith("/login.do") && !url.endsWith("/logout.do")) {
				Right right = new Right();
				right.setUrl(url);
				urls.add(right);
			}
		}

		request.setAttribute("urls", urls);

		return PAGE_INSERTRIGHT;
	}

	@ResponseBody
	@RequestMapping("/insertRight.do")
	public Object insertRight(@Valid Right right, BindingResult br, HttpServletRequest request,
			HttpServletResponse response) {
		right.setId(UUID.randomUUID().toString());

		Map<String, String> result = new HashMap<String, String>();

		if (rightService.isExistRight(right.getName(), right.getUrl())) {
			result.put(RET_CODE_PARAM, RET_INTERROR);
			result.put(RET_ERROR_MSG, "已经存在同名权限，请重新命名！");
			return result;
		}

		if (br.hasErrors()) {
			result.put(RET_CODE_PARAM, RET_INTERROR);
			result.put(RET_ERROR_MSG, br.getFieldError().getDefaultMessage());
			return result;
		}

		if (rightService.insertRight(right)) {
			result.put(RET_CODE_PARAM, RET_SUCCESS);
			result.put(RET_HTML, getSuccessHtml("操作成功！"));
		} else {
			result.put(RET_CODE_PARAM, RET_FAILURE);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/deleteRight.do")
	public Object deleteRight(Right right, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> result = new HashMap<String, String>();

		if (rightService.deleteRight(right)) {
			result.put(RET_CODE_PARAM, RET_SUCCESS);
		} else {
			result.put(RET_CODE_PARAM, RET_FAILURE);
		}
		sysRightsInfo.reloadRightsMap();
		return result;
	}

	// @ResponseBody
	// @RequestMapping("/queryAvailableMenuRights.do")
	// public Object queryAvailableMenuRights(HttpServletRequest request,
	// HttpServletResponse response) {
	// List<Map<String, String>> result = new ArrayList<Map<String, String>>();
	//
	// List<Right> rights = rightService.queryAvailableMenuRights();
	//
	// Map<String, String> defaultMap = new HashMap<String, String>();
	//
	// defaultMap.put("label", "请选择");
	// defaultMap.put("value", "");
	//
	// result.add(defaultMap);
	//
	// if (rights != null && rights.size() > 0) {
	// for (Right r : rights) {
	// Map<String, String> map = new HashMap<String, String>();
	//
	// map.put("value", r.getId());
	// map.put("label", r.getName());
	//
	// result.add(map);
	// }
	// }
	//
	// return result;
	// }

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
