package com.yuanshanbao.ms.controller.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;
import com.yuanshanbao.ad.core.CommonStatus;
import com.yuanshanbao.ad.core.InterfaceRetCode;
import com.yuanshanbao.ad.project.model.Project;
import com.yuanshanbao.ad.project.service.ProjectService;

@Controller
@RequestMapping("/admin/project")
public class AdminProjectController extends PaginationController {

	private static final String PAGE_LIST = "advertisement/project/listProject";

	private static final String PAGE_INSERT = "advertisement/project/insertProject";

	private static final String PAGE_UPDATE = "advertisement/project/updateProject";

	private static final String PAGE_VIEW = "advertisement/project/viewProject";

	@Autowired
	private ProjectService projectService;

	@RequestMapping("/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, Project project, HttpServletRequest request, HttpServletResponse response) {
		Object object = projectService.selectProjects(project, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/insertWindow.do")
	public String insertWindow(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		setProperty(request);
		return PAGE_INSERT;
	}

	private void setProperty(HttpServletRequest request) {
		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());
		request.setAttribute("typeList", null);
	}

	@ResponseBody
	@RequestMapping("/insert.do")
	public Object insert(Project project, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			validateParameters(project);
			projectService.insertProject(project);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}

	@ResponseBody
	@RequestMapping("/delete.do")
	public Object delete(Long projectId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			if (projectId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			Project project = new Project();
			project.setProjectId(projectId);
			projectService.deleteProject(project);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(HttpServletRequest request, HttpServletResponse response, Project project) {
		List<Project> list = projectService.selectProjects(project, new PageBounds());
		if (list != null && list.size() > 0) {
			project = list.get(0);
		}

		setProperty(request);
		request.setAttribute("itemEdit", project);
		return PAGE_UPDATE;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object update(Project project, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			validateParameters(project);
			projectService.updateProject(project);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}
		return result;
	}

	@RequestMapping("/view.do")
	public String view(Project project, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		List<Project> list = projectService.selectProjects(project, new PageBounds());
		if (list != null && list.size() > 0) {
			project = list.get(0);
		}
		request.setAttribute("itemEdit", project);
		return PAGE_VIEW;
	}

}
