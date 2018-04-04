package com.yuanshanbao.ms.controller.admin;

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
import com.yuanshanbao.ms.filter.security.SysRightsInfo;
import com.yuanshanbao.ms.model.admin.Group;
import com.yuanshanbao.ms.model.admin.SysModule;
import com.yuanshanbao.ms.service.admin.GroupService;
import com.yuanshanbao.ms.service.admin.MenuService;
import com.yuanshanbao.ms.service.admin.RightService;
import com.yuanshanbao.ms.service.admin.SysModuleService;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/group")
public class AdminGroupController extends PaginationController {

	private static final String PAGE_MGRGROUP = "admin/system/group/adminGroup";

	private static final String PAGE_INSERTGROUP = "admin/system/group/insertGroup";

	private static final String PAGE_EDITGROUPMODULES = "admin/system/group/updateGroupModule";

	@Autowired
	private GroupService groupService;

	@Autowired
	private RightService rightService;

	@Autowired
	private MenuService menuService;
	
	@Autowired
	private SysModuleService moduleService;

	@Autowired
	private SysRightsInfo sysRightsInfo;

	@RequestMapping("/adminGroups.do")
	public String adminGroups(HttpServletRequest request,
			HttpServletResponse response) {
		return PAGE_MGRGROUP;
	}

	@ResponseBody
	@RequestMapping("/queryGroups.do")
	public Object queryGroups(Group group, 
			HttpServletRequest request,
			HttpServletResponse response) {
		List<Group> result = groupService.queryGroups(group, getPageBounds(null, request));
		
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
		config.setJsonPropertyFilter(new PropertyFilter()  
		{  
		    @Override  
		    public boolean apply(Object source, String name, Object value)  
		    {  
		        return value == null;  
		    }
		});  
		JSONArray jsonArray = JSONArray.fromObject(result, config);
		
		return setPageInfo(request, response, (PageList) result);
	}

	@RequestMapping("/insertGroupWindow.do")
	public String insertGroupWindow(@RequestParam(value="parentid", required=true) String parentId,
			HttpServletRequest request,
			HttpServletResponse respones) {
		request.setAttribute("parentId", parentId);
		return PAGE_INSERTGROUP;
	}

	@ResponseBody
	@RequestMapping("/insertGroup.do")
	public Object insertGroup(@Valid Group group, BindingResult br,
			HttpServletRequest request, HttpServletResponse respones) {
		Map<String, String> result = new HashMap<String, String>();
		
		if (groupService.isExistGroup(group.getName())) {
			result.put(RET_CODE_PARAM, RET_INTERROR);
			result.put(RET_ERROR_MSG, "已经存在同名分组，请选择其他名称！");
			return result;
		}
		
		String sblingId = groupService.queryLastSblingId(group.getParent_id());
		
		String groupId = groupService.getNextGroupId(sblingId);
		
		group.setId(groupId);
		
		if (br.hasErrors()) {
			result.put(RET_CODE_PARAM, RET_INTERROR);
			result.put(RET_ERROR_MSG, br.getAllErrors().get(0).getDefaultMessage());
			return result;
		}
		if (groupService.insertGroup(group)) {
			result.put(RET_CODE_PARAM, RET_SUCCESS);
			result.put(RET_HTML, getSuccessHtml("添加分组成功"));
		} else {
			result.put(RET_CODE_PARAM, RET_FAILURE);
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/deleteGroup.do")
	public Object deleteGroup(Group group,
			HttpServletRequest request, 
			HttpServletResponse respones) {
		Map<String, String> result = new HashMap<String, String>();
		
		if (groupService.deleteGroup(group)) {
			result.put(RET_CODE_PARAM, RET_SUCCESS);
		} else {
			result.put(RET_CODE_PARAM, RET_FAILURE);
		}
		
		return result;
	}

	@RequestMapping("/updateGroupModules.do")
	public String editGroupAuth(@RequestParam("groupid") String groupId,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("groupId", groupId);
		return PAGE_EDITGROUPMODULES;
	}

	@ResponseBody
	@RequestMapping("/insertGroupModules.do")
	public Object addGroupRights(@RequestParam("groupid") String groupId,
			@RequestParam("moduleIds") String moduleIdsStr,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> result = new HashMap<String, String>();
		
		String[] moduleIds = moduleIdsStr.split(",");

		if (groupService.insertGroupModules(groupId, moduleIds)) {
			result.put(RET_CODE_PARAM, RET_SUCCESS);
		} else {
			result.put(RET_CODE_PARAM, RET_FAILURE);
		}

		sysRightsInfo.reloadRightsMap();
		
		return result;
	}

	@ResponseBody
	@RequestMapping("/deleteGroupModules.do")
	public Object delGroupRights(@RequestParam("groupid") String groupId,
			@RequestParam("moduleIds") String moduleIdsStr,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> result = new HashMap<String, String>();
		
		String[] moduleIds = moduleIdsStr.split(",");

		if (groupService.deleteGroupModules(groupId, moduleIds)) {
			result.put(RET_CODE_PARAM, RET_SUCCESS);
		} else {
			result.put(RET_CODE_PARAM, RET_FAILURE);
		}

		sysRightsInfo.reloadRightsMap();
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/queryModulesByGroupId.do")
	public Object queryModulesByGroupId(@RequestParam("groupid") String groupId,
			@RequestParam("flag") String flag,
			HttpServletRequest request, HttpServletResponse response) {
		List<SysModule> modules = moduleService.queryModulesByGroupId(groupId, flag, getPageBounds(request));
		return setPageInfo(request, response, (PageList) modules);
	}
	
	private boolean isRootGroup(Group group) {
		return group.getId().length() == 3;
	}
}
