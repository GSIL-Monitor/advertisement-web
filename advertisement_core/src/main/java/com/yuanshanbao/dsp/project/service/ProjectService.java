package com.yuanshanbao.dsp.project.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.dsp.project.model.Project;

public interface ProjectService {

	public void insertProject(Project project);

	public void updateProject(Project project);

	public void deleteProject(Project project);

	public List<Project> selectProjects(Project project, PageBounds pageBounds);

	public Map<Long, Project> selectProjectByIds(List<Long> projectIds);

	public Project selectProject(String projectKey);
	
	public Project selectProject(Long projectId);

}
