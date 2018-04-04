package com.yuanshanbao.ad.project.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.ad.project.dao.ProjectDao;
import com.yuanshanbao.ad.project.model.Project;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectDao projectDao;

	@Override
	public void insertProject(Project project) {

		int result = -1;

		result = projectDao.insertProject(project);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public void updateProject(Project project) {

		int result = -1;

		result = projectDao.updateProject(project);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public void deleteProject(Project project) {

		int result = -1;

		result = projectDao.deleteProject(project);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public List<Project> selectProjects(Project project, PageBounds pageBounds) {
		return projectDao.selectProjects(project, pageBounds);
	}

	@Override
	public Map<Long, Project> selectProjectByIds(List<Long> projectIds) {
		Map<Long, Project> map = new HashMap<Long, Project>();
		if (projectIds == null || projectIds.size() == 0) {
			return map;
		}
		List<Project> projectList = projectDao.selectProjectByIds(projectIds);
		for (Project project : projectList) {
			if (project == null) {
				continue;
			}
			map.put(project.getProjectId(), project);
		}
		return map;
	}

	@Override
	public Project selectProject(String projectKey) {
		if (StringUtils.isBlank(projectKey)) {
			return null;
		}
		Project project = new Project();
		project.setProjectKey(projectKey);
		List<Project> list = selectProjects(project, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Project selectProject(Long projectId) {
		if (projectId == null) {
			return null;
		}
		Project project = new Project();
		project.setProjectId(projectId);
		List<Project> list = selectProjects(project, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
