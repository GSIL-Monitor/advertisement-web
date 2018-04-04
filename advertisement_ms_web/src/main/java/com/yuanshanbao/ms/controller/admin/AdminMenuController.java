package com.yuanshanbao.ms.controller.admin;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.ms.model.admin.Menu;
import com.yuanshanbao.ms.model.admin.MenuCategory;
import com.yuanshanbao.ms.model.admin.Right;
import com.yuanshanbao.ms.service.admin.AdminUserService;
import com.yuanshanbao.ms.service.admin.GroupService;
import com.yuanshanbao.ms.service.admin.IconService;
import com.yuanshanbao.ms.service.admin.MenuCategoryService;
import com.yuanshanbao.ms.service.admin.MenuService;
import com.yuanshanbao.ms.service.admin.RightService;
import com.yuanshanbao.ms.service.cache.IconCacheService;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/menu")
public class AdminMenuController extends PaginationController {
	private static final String PAGE_MGRMENU = "admin/system/menu/adminMenu";

	private static final String PAGE_INSERTDMENU = "admin/system/menu/insertDMenu";

	private static final String PAGE_INSERTFMENU = "admin/system/menu/insertFMenu";

	private static final String PAGE_UPDATEICON = "admin/system/menu/updateMenuIcon";

	@Autowired
	private MenuService menuService;

	@Autowired
	private AdminUserService userService;

	@Autowired
	private RightService rightService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private MenuCategoryService mcService;

	@Autowired
	private IconService iconService;

	@Autowired
	private IconCacheService iconCacheService;

	@RequestMapping("/adminMenus.do")
	public String adminMenus(HttpServletRequest request, HttpServletResponse response) {
		List<MenuCategory> categorys = mcService.queryMenuCategorys(new MenuCategory(), new PageBounds());
		request.setAttribute("categorys", categorys);
		if (categorys != null && categorys.size() > 0) {
			request.setAttribute("defaultCategoryId", categorys.get(0).getId());
		}
		return PAGE_MGRMENU;
	}

	@ResponseBody
	@RequestMapping(value = "/queryMenus.do")
	public Object queryMenu(String range, @RequestParam(value = "categoryid") String categoryId,
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "dir", required = false) String dir, HttpServletRequest request,
			HttpServletResponse response) {
		Menu categoryMenu = new Menu();
		categoryMenu.setCategory_id(categoryId);
		List<Menu> result = menuService.queryMenus(categoryMenu, getPageBounds(request));

		Set<Menu> menuSet = new LinkedHashSet<Menu>();

		Map<String, Menu> menuMap = new LinkedHashMap<String, Menu>();

		if (result != null) {
			for (Menu m : result) {
				if (m.getRight_id() == null) {
					m.setRight_id("");
				}

				if (m.getIcon_name() == null) {
					m.setIcon_name("");
				}
				menuMap.put(m.getId(), m);
			}
		}

		for (Map.Entry<String, Menu> entry : menuMap.entrySet()) {
			if (menuMap.containsKey(entry.getValue().getParent_id())) {
				Menu parent = menuMap.get(entry.getValue().getParent_id());
				parent.addChild(entry.getValue());
			}
		}

		for (Map.Entry<String, Menu> entry : menuMap.entrySet()) {
			if (isRootMenu(entry.getValue())) {
				menuSet.add(entry.getValue());
			}
		}

		JsonConfig config = new JsonConfig();
		config.setJsonPropertyFilter(new PropertyFilter() {
			@Override
			public boolean apply(Object source, String name, Object value) {
				return value == null;
			}
		});
		JSONArray jsonArray = JSONArray.fromObject(menuSet, config);

		return setPageInfo(request, response, (PageList) result);
	}

	@RequestMapping("/insertDMenuWindow.do")
	public String insertDMenuWindow(@RequestParam(value = "categoryid", required = true) String categoryId,
			String parentId, HttpServletRequest request,
			HttpServletResponse response) {
		MenuCategory mc = new MenuCategory();
		mc.setId(categoryId);
		List<MenuCategory> mcList = mcService.queryMenuCategorys(mc, new PageBounds());
		if (mcList != null && mcList.size() > 0) {
			mc = mcList.get(0);
		}
		request.setAttribute("categoryId", categoryId);
		request.setAttribute("categoryName", mc.getName());
		request.setAttribute("parentId", parentId);
		return PAGE_INSERTDMENU;
	}

	@RequestMapping("/insertFMenuWindow.do")
	public String insertFMenuWindow(@RequestParam(value = "categoryid", required = true) String categoryId,
			String parentId, HttpServletRequest request,
			HttpServletResponse response) {
		MenuCategory mc = new MenuCategory();
		mc.setId(categoryId);
		List<MenuCategory> mcList = mcService.queryMenuCategorys(mc, new PageBounds());
		if (mcList != null && mcList.size() > 0) {
			mc = mcList.get(0);
		}
		request.setAttribute("categoryId", categoryId);
		request.setAttribute("categoryName", mc.getName());
		List<Right> rights = rightService.queryAvailableMenuRights();
		request.setAttribute("rights", rights);
		request.setAttribute("parentId", parentId);
		return PAGE_INSERTFMENU;
	}

	@ResponseBody
	@RequestMapping("/insertMenu.do")
	public Object insertMenu(@Valid Menu menu, BindingResult br, HttpServletRequest request,
			HttpServletResponse response) {
		String sblingId = menuService.queryLastSblingId(menu.getParent_id());

		String menuId = menuService.getNextMenuId(sblingId);

		menu.setId(menuId);

		if (menu.getRight_id() == null) {
			menu.setRight_id("");
		}

		Map<String, String> result = new HashMap<String, String>();

		if (br.hasErrors()) {
			result.put(RET_CODE_PARAM, RET_CHECK_ERROR);
			result.put(RET_ERROR_MSG, br.getAllErrors().get(0).getDefaultMessage());
			return result;
		}

		if (menuService.insertMenu(menu)) {
			result.put(RET_CODE_PARAM, RET_SUCCESS);
			result.put(RET_HTML, getSuccessHtml("添加菜单成功！"));
		} else {
			result.put(RET_CODE_PARAM, RET_FAILURE);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/deleteMenu.do")
	public Object deleteMenu(Menu menu, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> result = new HashMap<String, String>();

		if (menuService.deleteMenu(menu)) {
			result.put(RET_CODE_PARAM, RET_SUCCESS);
		} else {
			result.put(RET_CODE_PARAM, RET_FAILURE);
		}
		return result;
	}

	@RequestMapping("/updateIconWindow.do")
	public String updateIconWindow(@RequestParam(value = "id", required = true) String id, HttpServletRequest request,
			HttpServletResponse response) {
		List<String> iconNames = iconService.queryIconNames();

		request.setAttribute("id", id);
		request.setAttribute("iconNames", iconNames);

		return PAGE_UPDATEICON;
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

	@ResponseBody
	@RequestMapping("/updateIcon.do")
	public Object updateIcon(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "icon_name", required = true) String icon_name, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> result = new HashMap<String, String>();

		if (menuService.updateMenuIcon(id, icon_name)) {
			result.put(RET_CODE_PARAM, RET_SUCCESS);
			result.put(RET_HTML, getSuccessHtml("变更菜单图标成功"));
		} else {
			result.put(RET_CODE_PARAM, RET_FAILURE);
		}

		return result;
	}

	private boolean isRootMenu(Menu menu) {
		return menu.getId().length() == 3;
	}
}
