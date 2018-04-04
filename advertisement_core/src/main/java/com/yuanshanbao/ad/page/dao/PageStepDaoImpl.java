package com.yuanshanbao.ad.page.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.ad.base.dao.BaseDaoImpl;
import com.yuanshanbao.ad.page.dao.PageStepDao;
import com.yuanshanbao.ad.page.model.PageStep;

/**
 * 
 * @description
 *
 * @author
 */
@Repository
public class PageStepDaoImpl extends BaseDaoImpl implements PageStepDao {

	@Override
	public int insertPageStep(PageStep pageStep) {
		return getSqlSession().insert("pageStep.insertPageStep", pageStep);
	}

	@Override
	public int updatePageStep(PageStep pageStep) {
		return getSqlSession().update("pageStep.updatePageStep", pageStep);
	}

	@Override
	public int deletePageStep(PageStep pageStep) {
		return getSqlSession().delete("pageStep.deletePageStep", pageStep);
	}

	@Override
	public List<PageStep> selectPageSteps(PageStep pageStep, PageBounds pageBounds) {
		return getSqlSession().selectList("pageStep.selectPageSteps", pageStep, pageBounds);
	}

	@Override
	public List<PageStep> selectPageStepByIds(List<Long> pageStepIds) {
		if (pageStepIds == null || pageStepIds.size() == 0) {
			return new ArrayList<PageStep>();
		}
		return getSqlSession().selectList("pageStep.selectPageStepByIds", pageStepIds);
	}
}