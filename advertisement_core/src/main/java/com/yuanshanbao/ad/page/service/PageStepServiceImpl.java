package com.yuanshanbao.ad.page.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.paginator.domain.PageBounds;
import org.springframework.stereotype.Service;
import com.yuanshanbao.ad.page.dao.PageStepDao;
import com.yuanshanbao.ad.page.model.PageStep;
import com.yuanshanbao.ad.page.service.PageStepService;

/**
 * @description
 * @author
 */
@Service
public class PageStepServiceImpl implements PageStepService {

	// ~ fields =======================================================
	@Autowired
	private PageStepDao pageStepDao;

	// ~ Method =======================================================

	@Override
	public void insertPageStep(PageStep pageStep) {

		int result = -1;

		result = pageStepDao.insertPageStep(pageStep);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updatePageStep(PageStep pageStep) {

		int result = -1;

		result = pageStepDao.updatePageStep(pageStep);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deletePageStep(PageStep pageStep) {

		int result = -1;

		result = pageStepDao.deletePageStep(pageStep);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public List<PageStep> selectPageSteps(PageStep pageStep, PageBounds pageBounds) {
		return pageStepDao.selectPageSteps(pageStep, pageBounds);
	}

	@Override
	public Map<Long, PageStep> selectPageStepByIds(List<Long> pageStepIds) {
		Map<Long, PageStep> map = new HashMap<Long, PageStep>();
		if (pageStepIds == null || pageStepIds.size() == 0) {
			return map;
		}
		List<PageStep> list = pageStepDao.selectPageStepByIds(pageStepIds);
		for (PageStep pageStep : list) {
			map.put(pageStep.getPageStepId(), pageStep);
		}
		return map;
	}

	@Override
	public PageStep selectPageStep(Long pageStepId) {
		PageStep pageStep = new PageStep();
		if (pageStepId == null) {
			return null;
		}
		pageStep.setPageStepId(pageStepId);
		List<PageStep> list = selectPageSteps(pageStep, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertOrUpdatePageStep(PageStep pageStep) {
		if (pageStep.getPageStepId() == null) {
			insertPageStep(pageStep);
		} else {
			updatePageStep(pageStep);
		}
	}

}