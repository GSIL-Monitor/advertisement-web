package com.yuanshanbao.ad.project.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.ad.base.dao.BaseDaoImpl;
import com.yuanshanbao.ad.project.model.Project;

@Repository
public class ProjectDaoImpl extends BaseDaoImpl implements ProjectDao {

	@Override
	public int insertProject(Project project) {
		return getSqlSession().insert("project.insertProject", project);
	}

	@Override
	public int updateProject(Project project) {
		return getSqlSession().update("project.updateProject", project);
	}

	@Override
	public int deleteProject(Project project) {
		return getSqlSession().delete("project.deleteProject", project);
	}

	@Override
	public List<Project> selectProjects(Project project, PageBounds pageBounds) {
		return getSqlSession().selectList("project.selectProjects", project,
				pageBounds);
	}

	@Override
	public List<Project> selectProjectByIds(List<Long> projectIds) {
		if (projectIds == null || projectIds.size() == 0) {
			return new ArrayList<Project>();
		}
		return getSqlSession().selectList("project.selectProjectByIds",
				projectIds);
	}

}
