package com.yuanshanbao.ms.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuanshanbao.common.constant.SessionConstants;
import com.yuanshanbao.common.util.CookieUtils;
import com.yuanshanbao.dsp.project.model.Project;
import com.yuanshanbao.dsp.project.service.ProjectService;

@Controller
public class AdminIndexController {
	private static final String PAGE_LOGIN = "login";

	@Autowired
	private ProjectService projectService;

	@RequestMapping({ "/index.do", "/redirect.do" })
	public String index(HttpServletRequest request, HttpServletResponse response, String error) {
		String projectKey = CookieUtils.getCookieValue(request, "project_key");
		if ("true".equals(error)) {
			request.setAttribute("msg", request.getSession().getAttribute("msg"));
		}
		request.getSession().removeAttribute("msg");
		if (StringUtils.isNotBlank(projectKey)) {
			return PAGE_LOGIN + "/" + projectKey;
		} else {
			// return "redirect:/404.htm";
			return null;
		}
	}

	@RequestMapping("/{projectKey}/index.do")
	public String projectIndex(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("projectKey") String projectKey) {
		Project project = projectService.selectProject(projectKey);
		if (project != null) {
			request.getSession().setAttribute(SessionConstants.SESSION_PROJECT_ID, project.getProjectId());
			request.getSession().setAttribute(SessionConstants.SESSION_PROJECT_KEY, project.getProjectKey());
			CookieUtils.setPersistCookieValue(response, "project_key", projectKey);
			return PAGE_LOGIN + "/" + projectKey;
		} else {
			return "redirect:/404.htm";
		}
	}
}
