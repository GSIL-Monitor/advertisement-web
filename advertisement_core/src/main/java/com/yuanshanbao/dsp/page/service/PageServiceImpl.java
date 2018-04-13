package com.yuanshanbao.dsp.page.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.dsp.information.model.InformationField;
import com.yuanshanbao.dsp.information.service.InformationFieldService;
import com.yuanshanbao.dsp.page.dao.PageDao;
import com.yuanshanbao.dsp.page.model.Page;
import com.yuanshanbao.dsp.page.model.PageStep;

/**
 * @description
 * @author
 */
@Service
public class PageServiceImpl implements PageService {

	// ~ fields =======================================================
	@Autowired
	private PageDao pageDao;

	@Autowired
	private InformationFieldService informationFieldService;

	@Autowired
	private PageStepService pageStepService;

	// ~ Method =======================================================

	@Override
	public void insertPage(Page page) {

		int result = -1;

		result = pageDao.insertPage(page);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updatePage(Page page) {

		int result = -1;

		result = pageDao.updatePage(page);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deletePage(Page page) {

		int result = -1;

		result = pageDao.deletePage(page);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public List<Page> selectPages(Page page, PageBounds pageBounds) {
		return setProperty(pageDao.selectPages(page, pageBounds));
	}

	private List<Page> setProperty(List<Page> pageList) {
		for (Page page : pageList) {
			List<Long> stepIdList = page.getStepIds();
			Map<Long, PageStep> map = pageStepService.selectPageStepByIds(stepIdList);
			for (Long stepId : stepIdList) {
				List<Long> fieldIdList = new ArrayList<Long>();
				PageStep pageStep = map.get(stepId);
				if (pageStep != null) {
					fieldIdList.addAll(pageStep.getFieldIds());
					List<InformationField> informationFields = new ArrayList<InformationField>();
					for (Long fieldId : fieldIdList) {
						InformationField informationField = informationFieldService.selectInformationField(fieldId);
						if (informationField == null) {
							continue;
						}
						informationFields.add(informationField);
					}
					pageStep.setFieldList(informationFields);
					page.getPageStepList().add(pageStep);
				}
			}
		}
		return pageList;
	}

	@Override
	public Map<Long, Page> selectPageByIds(List<Long> pageIds) {
		Map<Long, Page> map = new HashMap<Long, Page>();
		if (pageIds == null || pageIds.size() == 0) {
			return map;
		}
		List<Page> list = setProperty(pageDao.selectPageByIds(pageIds));
		for (Page page : list) {
			map.put(page.getPageId(), page);
		}
		return map;
	}

	@Override
	public Page selectPage(Long pageId) {
		Page page = new Page();
		if (pageId == null) {
			return null;
		}
		page.setPageId(pageId);
		List<Page> list = selectPages(page, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertOrUpdatePage(Page page) {
		if (page.getPageId() == null) {
			insertPage(page);
		} else {
			updatePage(page);
		}
	}

}