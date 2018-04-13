package com.yuanshanbao.dsp.project.dao;

import java.util.List;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.dsp.project.model.Project;

public interface ProjectDao {

	public int insertProject(Project project);

	public int updateProject(Project project);

	public int deleteProject(Project project);

	public List<Project> selectProjects(Project project, PageBounds pageBounds);

	public List<Project> selectProjectByIds(List<Long> projectIds);

}
