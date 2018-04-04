package com.yuanshanbao.ms.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;

import com.yuanshanbao.common.constant.SpringContextConstants;
import com.yuanshanbao.ms.model.admin.Menu;
import com.yuanshanbao.ms.model.admin.MenuCategory;
import com.yuanshanbao.ms.model.admin.User;
import com.yuanshanbao.ms.service.admin.MenuService;

public class MenuFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		User user = null;

		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (obj != null) {
			user = (User) obj;
		}
		if (user != null) {
			MenuService menuService = SpringContextConstants.APPCTX.getBean(MenuService.class);
			List<Menu> menus = menuService.queryMenusByUsername(user.getUsername(), null);
			List<MenuCategory> categorys = new ArrayList<MenuCategory>();

			Map<String, MenuCategory> categoryNameMap = new HashMap<String, MenuCategory>();

			for (Menu m : menus) {
				MenuCategory category = categoryNameMap.get(m.getCategory_name());
				if (category == null) {
					MenuCategory mc = new MenuCategory();
					mc.setId(m.getCategory_id());
					mc.setName(m.getCategory_name());
					categorys.add(mc);
					categoryNameMap.put(m.getCategory_name(), category);
				}
				category.addMenu(m);
			}
			request.setAttribute("categoryList", categorys);
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}
}
