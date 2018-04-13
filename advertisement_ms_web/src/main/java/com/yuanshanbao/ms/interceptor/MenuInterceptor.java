package com.yuanshanbao.ms.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.project.model.Project;
import com.yuanshanbao.dsp.project.service.ProjectService;
import com.yuanshanbao.common.constant.SpringContextConstants;
import com.yuanshanbao.ms.model.admin.Menu;
import com.yuanshanbao.ms.model.admin.MenuCategory;
import com.yuanshanbao.ms.model.admin.User;
import com.yuanshanbao.ms.service.admin.MenuService;

public class MenuInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		try {
			String uri = request.getRequestURI();
			String contextPath = request.getContextPath();
			if (uri.startsWith(contextPath)) {
				uri = uri.substring(contextPath.length());
			}
			String sessionUri = (String) request.getSession().getAttribute("session_last_menu");
			User user = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication == null) {
				return true;
			}
			Object obj = authentication.getPrincipal();

			if (obj != null && obj instanceof User) {
				user = (User) obj;
			}
			if (user == null) {
				return true;
			}
			MenuService menuService = SpringContextConstants.APPCTX.getBean(MenuService.class);
			ProjectService projectService = SpringContextConstants.APPCTX.getBean(ProjectService.class);
			List<Menu> menus = menuService.queryMenusByUsername(user.getUsername(), null);
			List<MenuCategory> categorys = new ArrayList<MenuCategory>();

			Map<String, MenuCategory> categoryNameMap = new HashMap<String, MenuCategory>();

			Menu selectMenu = null;

			for (Menu m : menus) {
				MenuCategory category = categoryNameMap.get(m.getCategory_name());
				if (category == null) {
					category = new MenuCategory();
					category.setId(m.getCategory_id());
					category.setName(m.getCategory_name());
					categorys.add(category);
					categoryNameMap.put(m.getCategory_name(), category);
				}

				if (m.getRight_url().equals(uri)) {
					selectMenu = m;
				}
				if (m.getRight_url().equals(sessionUri) && selectMenu == null) {
					selectMenu = m;
				}
				category.addMenu(m);
			}

			if (selectMenu != null) {
				request.getSession().setAttribute("session_last_menu", selectMenu.getRight_url());
				selectMenu.setSelected(true);
				categoryNameMap.get(selectMenu.getCategory_name()).setSelected(true);
			}
			if (StringUtils.isBlank(user.getCompanyName())) {
				Project project = ConstantsManager.getProject(projectService, request);
				if (project != null) {
					user.setCompanyName(project.getCompanyName());
				}
			}
			request.setAttribute("user", user);
			request.setAttribute("categoryList", categorys);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}
