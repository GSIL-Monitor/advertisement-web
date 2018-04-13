package com.yuanshanbao.ms.controller.admin;

import java.util.ArrayList;
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

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.FileUtil;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.ms.model.admin.Group;
import com.yuanshanbao.ms.model.admin.Menu;
import com.yuanshanbao.ms.model.admin.MenuCategory;
import com.yuanshanbao.ms.model.admin.Right;
import com.yuanshanbao.ms.model.admin.User;
import com.yuanshanbao.ms.service.admin.AdminUserService;
import com.yuanshanbao.ms.service.admin.GroupService;
import com.yuanshanbao.ms.service.admin.MenuService;
import com.yuanshanbao.ms.service.admin.RightService;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.project.model.Project;
import com.yuanshanbao.dsp.project.service.ProjectService;

@Controller
@RequestMapping("/admin/user")
public class AdminUserController extends PaginationController {

	private static final String PAGE_MGRUSER = "admin/system/user/adminUser";

	private static final String PAGE_INSERTUSER = "admin/system/user/insertUser";

	private static final String PAGE_EDITUSERGROUP = "admin/system/user/updateUserGroup";

	private static final String PAGE_EDITUSER = "admin/system/user/updateUser";

	private static final String PAGE_CHANGEPWD = "admin/system/user/updatePassword";

	private static final String PAGE_VIEWRIGHTSMENUS = "admin/system/user/queryUserRightsAndMenus";

	private static final String PAGE_UPDATE_USERIDS = "admin/system/user/updateUserIds";

	@Autowired
	private AdminUserService userService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private RightService rightService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private ProjectService projectService;

	@RequestMapping("/adminUsers.do")
	public String adminUsers(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_MGRUSER;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/queryUsers.do")
	public Object queryUsers(String range, User user, HttpServletRequest request, HttpServletResponse response) {
		// User currentUser = getCurrentUser();
		// user.setUser_level(currentUser.getUser_level());
		// user.setGroups(currentUser.getGroups());
		Object result = userService.queryUsers(user, getPageBounds(range, request));
		PageList pageList = (PageList) result;
		return setPageInfo(request, response, pageList);
	}

	@ResponseBody
	@RequestMapping("/deleteUser.do")
	public Object deleteUser(User user, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> result = new HashMap<String, String>();
		String userName = request.getParameter("username") == null ? "" : request.getParameter("username");
		if (userService.deleteUser(userName)) {
			result.put(RET_CODE_PARAM, RET_SUCCESS);
		} else {
			result.put(RET_CODE_PARAM, RET_FAILURE);
		}
		return result;
	}

	@RequestMapping("/insertUserWindow.do")
	public String insertUserWindow(HttpServletRequest request, HttpServletResponse response) {
		setProperties(request);
		return PAGE_INSERTUSER;
	}

	private void setProperties(HttpServletRequest request) {
		Project project = new Project();
		project.setStatus(CommonStatus.ONLINE);
		List<Project> projectList = projectService.selectProjects(project, new PageBounds());
		request.setAttribute("projectList", projectList);
	}

	@ResponseBody
	@RequestMapping("/insertUser.do")
	public Object insertUser(User user, BindingResult br, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> result = new HashMap<String, String>();

		if (br.hasErrors()) {
			result.put(RET_CODE_PARAM, RET_INTERROR);
			result.put(RET_ERROR_MSG, br.getAllErrors().get(0).getDefaultMessage());
			return result;
		}
		if (userService.isUserExits(user.getName())) {
			result.put(RET_CODE_PARAM, RET_INTERROR);
			result.put(RET_ERROR_MSG, "该用户已经存在，请重新输入用户名！");
			return result;
		}

		User curUser = getCurrentUser();

		if (!curUser.getUser_level().equals("1")) {
			user.setUser_level("3");
		}

		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		user.setPassword(encoder.encodePassword(user.getPassword(), null));
		if (userService.insertUser(user)) {
			result.put(RET_CODE_PARAM, RET_SUCCESS);
			result.put(RET_HTML, getSuccessHtml("添加用户成功"));
		}

		return result;
	}

	@RequestMapping("/updateUserWindow.do")
	public String updateUserWindow(HttpServletRequest request, HttpServletResponse response, User user) {
		List<User> users = userService.queryUsers(user, new PageBounds());

		if (users != null && users.size() > 0) {
			user = users.get(0);
		}

		request.setAttribute("userEdit", user);
		setProperties(request);
		return PAGE_EDITUSER;
	}

	@ResponseBody
	@RequestMapping("/updateUser.do")
	public Object updateUser(@Valid User user, BindingResult br) {
		Map<String, String> result = new HashMap<String, String>();

		if (br.hasErrors()) {
			if (br.getFieldErrors().size() == 1 && br.getFieldErrors().get(0).getField().equals("password")) {

			} else {
				result.put(RET_CODE_PARAM, RET_INTERROR);
				result.put(RET_ERROR_MSG, br.getAllErrors().get(0).getDefaultMessage());
				return result;
			}
		}

		if (userService.updateUser(user)) {
			result.put(RET_CODE_PARAM, RET_SUCCESS);
			result.put(RET_HTML, getSuccessHtml("更新成功"));
		}
		return result;
	}

	@RequestMapping("/updatePasswordWindow.do")
	public String updatePasswordWindow(HttpServletRequest request, HttpServletResponse response, User user) {
		List<User> users = userService.queryUsers(user, new PageBounds());

		if (users != null && users.size() > 0) {
			user = users.get(0);
		}

		request.setAttribute("userEdit", user);
		return PAGE_CHANGEPWD;
	}

	@ResponseBody
	@RequestMapping("/updatePassword.do")
	public Object updatePassword(User user, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> result = new HashMap<String, String>();

		String password = request.getParameter("password");

		String confirmPassword = request.getParameter("confirmPassword");

		if (StringUtils.hasText(password) && StringUtils.hasText(confirmPassword)) {
			if (!password.equals(confirmPassword)) {
				result.put(RET_CODE_PARAM, RET_INTERROR);
				result.put(RET_ERROR_MSG, "两次输入密码不一致！");
				return result;
			}
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			user.setPassword(encoder.encodePassword(user.getPassword(), null));
			userService.updateUserPwd(user);
			result.put(RET_CODE_PARAM, RET_SUCCESS);
			result.put(RET_HTML, getSuccessHtml("更新用户密码成功"));
		} else {
			result.put(RET_CODE_PARAM, RET_INTERROR);
			result.put(RET_ERROR_MSG, "新添密码不能为空！");
			return result;
		}

		return result;
	}

	@RequestMapping("/updateUserGroupsWindow.do")
	public String editUserGroup(@RequestParam("username") String username, HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("username", username);
		return PAGE_EDITUSERGROUP;
	}

	@ResponseBody
	@RequestMapping("/queryGroups.do")
	public Object queryGroups(HttpServletRequest request, HttpServletResponse response) {
		Group group = new Group();

		User user = getCurrentUser();

		if (user.getUser_level().equals("2")) {
			group.setQueryScope(user.getGroups());
		}

		List<Group> result = groupService.queryGroups(group, getPageBounds(request));

		Set<Group> groups = new LinkedHashSet<Group>();

		Map<String, Group> groupMap = new LinkedHashMap<String, Group>();

		if (result != null && result.size() > 0) {
			for (Group g : result) {
				groupMap.put(g.getId(), g);
			}

			for (Map.Entry<String, Group> groupEntry : groupMap.entrySet()) {
				if (groupMap.containsKey(groupEntry.getValue().getParent_id())) {
					Group parent = groupMap.get(groupEntry.getValue().getParent_id());
					parent.addChild(groupEntry.getValue());
				}
			}

			for (Map.Entry<String, Group> groupEntry : groupMap.entrySet()) {
				if (isRootGroup(groupEntry.getValue())) {
					groups.add(groupEntry.getValue());
				}
			}
		}

		JsonConfig config = new JsonConfig();
		config.setJsonPropertyFilter(new PropertyFilter() {
			@Override
			public boolean apply(Object source, String name, Object value) {
				return value == null;
			}
		});
		JSONArray jsonArray = JSONArray.fromObject(groups, config);

		return setPageInfo(request, response, (PageList) result);
	}

	@ResponseBody
	@RequestMapping("/queryGroupsByUsername.do")
	public Object queryGroupsByUsername(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "flag", required = true) String flag, HttpServletRequest request,
			HttpServletResponse response) {
		List<Group> groups = groupService.queryGroupsByUsername(flag, username, getPageBounds(request));
		return setPageInfo(request, response, (PageList) groups);
	}

	@ResponseBody
	@RequestMapping("/insertUserGroups.do")
	public Object insertUserGroups(@RequestParam("username") String userName,
			@RequestParam("groupIds") String groupIdsStr, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> result = new HashMap<String, String>();

		String[] groupIds = groupIdsStr.split(",");

		if (userName == null || userName.equals("") || groupIds[0].equals("")) {
			result.put(RET_CODE_PARAM, RET_SUCCESS);
			return result;
		}

		if (groupService.insertUserGroups(userName, groupIds)) {
			result.put(RET_CODE_PARAM, RET_SUCCESS);
		} else {
			result.put(RET_CODE_PARAM, RET_FAILURE);
		}

		return result;
	}

	@ResponseBody
	@RequestMapping("/deleteUserGroups.do")
	public Object deleteUserGroups(@RequestParam("username") String username,
			@RequestParam("groupIds") String groupIdsStr, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> result = new HashMap<String, String>();

		String[] groupIds = groupIdsStr.split(",");
		if (username == null || username.equals("") || groupIds[0].equals("")) {
			result.put(RET_CODE_PARAM, RET_FAILURE);
			return result;
		}

		if (groupService.deleteUserGroups(username, groupIds)) {
			result.put(RET_CODE_PARAM, RET_SUCCESS);
		} else {
			result.put(RET_CODE_PARAM, RET_FAILURE);
		}

		return result;
	}

	@RequestMapping("/queryUserRightsAndMenus.do")
	public String queryUserRightsAndMenus(@RequestParam("username") String uaerName, HttpServletRequest request,
			HttpServletResponse response) {
		List<Menu> menus = menuService.queryMenusByUsername(uaerName, null);

		List<MenuCategory> categorys = new ArrayList<MenuCategory>();

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

		if (categorys != null && categorys.size() > 0) {
			request.setAttribute("defaultCategoryId", categorys.get(0).getId());
		}

		request.setAttribute("categorys", categorys);

		request.setAttribute("userName", uaerName);

		return PAGE_VIEWRIGHTSMENUS;
	}

	@ResponseBody
	@RequestMapping("/queryRightsByUsername.do")
	public Object queryRightsByUsername(@RequestParam("username") String userName, HttpServletRequest request,
			HttpServletResponse response) {
		List<Right> rights = rightService.queryRightsByUsername(userName);
		return rights;
	}

	@ResponseBody
	@RequestMapping("/queryMenusByUsername.do")
	public Object queryMenusByUsername(@RequestParam("username") String userName,
			@RequestParam("categoryid") String categoryId, HttpServletRequest request, HttpServletResponse response) {
		// Menu menu = new Menu();
		// menu.setCategory_id(categoryId);
		List<Menu> allMenus = menuService.queryMenus(new Menu(), new PageBounds());

		Map<String, Menu> allMenuMap = new LinkedHashMap<String, Menu>();

		for (Menu m : allMenus) {
			allMenuMap.put(m.getId(), m);
		}

		List<Menu> menus = menuService.queryMenusByUsername(userName, null);

		Map<String, Menu> userMenuMap = new LinkedHashMap<String, Menu>();

		for (Menu m : menus) {
			Menu rootMenu = getRootMenuByLeafMenu(m, allMenuMap);

			if (rootMenu != null) {
				userMenuMap.put(rootMenu.getId(), rootMenu);
			}
		}

		Set<Menu> userMenus = new LinkedHashSet<Menu>();

		for (Map.Entry<String, Menu> entry : userMenuMap.entrySet()) {
			userMenus.add(entry.getValue());
		}

		JsonConfig config = new JsonConfig();
		config.setJsonPropertyFilter(new PropertyFilter() {
			@Override
			public boolean apply(Object source, String name, Object value) {
				return value == null;
			}
		});
		JSONArray jsonArray = JSONArray.fromObject(userMenus, config);

		return jsonArray.toString();
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

	private boolean isRootGroup(Group group) {
		return group.getId().length() == 3;
	}

	private boolean isRootMenu(Menu menu) {
		return menu.getId().length() == 3;
	}

	@RequestMapping("/updateUserIdsWindow.do")
	public String userId(HttpServletRequest request, HttpServletResponse response) {
		com.yuanshanbao.ms.model.admin.User user = getCurrentUser();
		User u = new User();
		u.setUsername(user.getUsername());
		List<User> userList = userService.queryUsers(u, new PageBounds());
		if (!userList.isEmpty()) {
			user = userList.get(0);
		}
		request.setAttribute("userEdit", user);
		return PAGE_UPDATE_USERIDS;
	}

	@ResponseBody
	@RequestMapping("/updateUserIds.do")
	public Object update(@RequestParam("ids") MultipartFile ids, String username, HttpServletRequest request,
			HttpServletResponse reponse) {
		Map<String, Object> result = new HashMap<>();
		com.yuanshanbao.ms.model.admin.User user = getCurrentUser();
		try {
			String fileName = ids.getOriginalFilename();
			int index = fileName.lastIndexOf(".");
			String suffixName = fileName.substring(index + 1, fileName.length());
			if (!suffixName.equals("txt")) {
				result.put(ComRetCode.RET_CODE, 470);
				result.put(ComRetCode.RET_DESC, "只支持后缀为txt文件");
				return result;
			}
			List<String> idsList = FileUtil.read(ids);

			if (idsList.size() <= 0) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}

			if (user == null || !user.getUsername().equals(username)) {
				throw new BusinessException(ComRetCode.USER_NOT_EXIST);
			}

			user.setBind_user_ids(read(idsList));
			if (userService.updateUser(user)) {
				result.put(RET_CODE_PARAM, RET_SUCCESS);
				result.put(RET_HTML, getSuccessHtml("更新成功"));
			}
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			result.put(RET_CODE_PARAM, RET_INTERROR);
			result.put(RET_ERROR_MSG, "读取文件失败!");
			return result;
		}

		return result;
	}

	private String read(List<String> list) {
		String id = "";
		boolean flag = true;
		for (String str : list) {
			if (StringUtil.isNumeric(str)) {
				if (flag) {
					id += str;
					flag = false;
				} else {
					id += "," + str;
				}

			}
		}
		return id;
	}

}