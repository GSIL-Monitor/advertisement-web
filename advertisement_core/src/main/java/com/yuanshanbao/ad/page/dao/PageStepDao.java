package com.yuanshanbao.ad.page.dao;

import java.util.List;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.ad.page.model.PageStep;

/**
 * 
 * @description
 *
 * @author
 */
public interface PageStepDao {

	public int insertPageStep(PageStep pageStep);

	public int updatePageStep(PageStep pageStep);

	public int deletePageStep(PageStep pageStep);

	public List<PageStep> selectPageSteps(PageStep pageStep, PageBounds pageBounds);

	public List<PageStep> selectPageStepByIds(List<Long> pageStepIds);
}