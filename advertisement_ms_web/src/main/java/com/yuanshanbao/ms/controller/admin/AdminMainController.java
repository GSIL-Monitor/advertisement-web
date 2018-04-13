package com.yuanshanbao.ms.controller.admin;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.dsp.project.service.ProjectService;
import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.ms.model.admin.Menu;
import com.yuanshanbao.ms.model.admin.MenuCategory;
import com.yuanshanbao.ms.model.admin.User;
import com.yuanshanbao.ms.service.admin.AdminUserService;
import com.yuanshanbao.ms.service.admin.IconService;
import com.yuanshanbao.ms.service.admin.MenuService;
import com.yuanshanbao.ms.service.cache.IconCacheService;
import com.yuanshanbao.paginator.domain.PageBounds;

@Controller
@RequestMapping("/admin")
public class AdminMainController extends PaginationController {
	private static final String PAGE_MAIN = "welcome";

	private static final String PAGE_HEADER = "header/header";

	private static final String PAGE_WELCOME = "welcome";

	private static final String PAGE_UPDATEPASSWORD = "admin/system/password/updatePassword";

	@Autowired
	private AdminUserService userService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private IconService iconService;

	@Autowired
	private IconCacheService iconCacheService;

	@Autowired
	private ProjectService projectService;

	@RequestMapping("/main.do")
	public String main(HttpServletRequest request, HttpServletResponse response) {
		User user = getCurrentUser();

		List<Menu> menus = menuService.queryMenusByUsername(user.getUsername(), null);

		Set<MenuCategory> categorys = new LinkedHashSet<MenuCategory>();

		Set<String> categoryNameSet = new LinkedHashSet<String>();

		for (Menu m : menus) {
			if (!categoryNameSet.contains(m.getCategory_name())) {
				MenuCategory mc = new MenuCategory();
				mc.setId(m.getCategory_id());
				mc.setName(m.getCategory_name());
				categorys.add(mc);
				categoryNameSet.add(m.getCategory_name());
			}
		}
		request.setAttribute("project", projectService.selectProject(getProjectId(request)));
		request.setAttribute("user", user);
		request.setAttribute("categorys", categorys);
		return PAGE_MAIN;
	}

	private String getCreateTimeEnd(Date date) {
		if (DateUtils.getDiffDays(date, new Date()) == 0) {
			return DateUtils.format(DateUtils.addDays(new Date(), 1), "yyyy-MM-dd");
		} else {
			return DateUtils.format(new Date(), "yyyy-MM-dd");
		}
	}

	@ResponseBody
	@RequestMapping("/queryUserMenusByCategoryId.do")
	public String queryUserMenusByCategoryId(@RequestParam(value = "categoryid", required = true) String categoryId,
			HttpServletRequest request, HttpServletResponse response) {
		User user = getCurrentUser();

		List<Menu> menus = menuService.queryMenusByUsername(user.getUsername(), categoryId);

		List<Menu> allMenus = menuService.queryMenus(new Menu(), new PageBounds());

		Set<Menu> result = new LinkedHashSet<Menu>();

		// 由于dojo tree只能有一个根节点，所以这里首先硬编码一个根节点，用户实际树节点都加载到这个根节点下面
		Menu root = new Menu();
		root.setId(UUID.randomUUID().toString());

		result.add(root);

		Map<String, Menu> allMenuMap = new LinkedHashMap<String, Menu>();

		if (menus != null && allMenus != null) {
			for (Menu m : allMenus) {
				allMenuMap.put(m.getId(), m);
			}

			for (Menu m : menus) {
				Menu menu = getRootMenuByLeafMenu(m, allMenuMap);

				root.addChild(menu);
			}
		}

		JsonConfig config = new JsonConfig();
		config.setJsonPropertyFilter(new PropertyFilter() {
			@Override
			public boolean apply(Object source, String name, Object value) {
				return value == null;
			}
		});
		JSONArray jsonArray = JSONArray.fromObject(result, config);

		return jsonArray.toString();
	}

	@RequestMapping("/header.do")
	public String header(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_HEADER;
	}

	@RequestMapping("/welcome.do")
	public String welcome(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_WELCOME;
	}

	private boolean isRootMenu(Menu menu) {
		return menu.getId().length() == 3;
	}

	private Menu getRootMenuByLeafMenu(Menu menu, Map<String, Menu> allMenuMap) {
		if (isRootMenu(menu)) {
			return menu;
		}

		if (allMenuMap.containsKey(menu.getParent_id())) {
			Menu parent = allMenuMap.get(menu.getParent_id());
			parent.addChild(menu);
			if (isRootMenu(parent)) {
				return parent;
			} else {
				return getRootMenuByLeafMenu(parent, allMenuMap);
			}
		} else {
			return null;
		}
	}

	@RequestMapping("/updateUserPasswordWindow.do")
	public String updateUserPasswordWindow(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_UPDATEPASSWORD;
	}

	@ResponseBody
	@RequestMapping("/updateUserPassword.do")
	public Object updateUserPassword(@RequestParam("password") String password,
			@RequestParam("confirmPassword") String confirmPassword, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> result = new HashMap<String, String>();

		if (!StringUtils.hasText(password)) {
			result.put(RET_CODE_PARAM, RET_INTERROR);
			result.put(RET_ERROR_MSG, "密码不能为空！");
			return result;
		}

		if (!password.equals(confirmPassword)) {
			result.put(RET_CODE_PARAM, RET_INTERROR);
			result.put(RET_ERROR_MSG, "两次输入密码不一致！");
			return result;
		}

		com.yuanshanbao.ms.model.admin.User user = new com.yuanshanbao.ms.model.admin.User();

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			UserDetails userDetail = (UserDetails) principal;
			user.setUsername(userDetail.getUsername());
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			user.setPassword(encoder.encodePassword(password, null));
			if (userService.updateUserPwd(user)) {
				result.put(RET_CODE_PARAM, RET_SUCCESS);
				result.put(RET_HTML, getSuccessHtml("更新密码成功"));
				return result;
			} else {
				result.put(RET_CODE_PARAM, RET_FAILURE);
				return result;
			}
		}

		return result;
	}

	@RequestMapping("/icon.do")
	public void icon(@RequestParam("name") String name, HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		byte[] bytes = null;

		bytes = iconCacheService.query(name);

		if (bytes != null && bytes.length > 0) {
			response.getOutputStream().write(bytes);
			response.getOutputStream().flush();
		} else {
			Blob icon = iconService.queryIconByName(name);

			if (icon != null) {
				bytes = new byte[(int) icon.length()];
				icon.getBinaryStream().read(bytes);
				iconCacheService.insert(name, bytes);
				response.getOutputStream().write(bytes);
				response.getOutputStream().flush();
			}
		}
	}
}
