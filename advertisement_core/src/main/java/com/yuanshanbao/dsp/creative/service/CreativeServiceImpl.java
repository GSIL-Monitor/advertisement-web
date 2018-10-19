package com.yuanshanbao.dsp.creative.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.creative.dao.CreativeDao;
import com.yuanshanbao.dsp.creative.model.Creative;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class CreativeServiceImpl implements CreativeService {

	@Autowired
	private CreativeDao creativeDao;

	@Override
	public void insertCreative(Creative creative) {
		int result = -1;

		result = creativeDao.insertCreative(creative);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateCreative(Creative creative) {
		int result = -1;

		result = creativeDao.updateCreative(creative);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public void deleteCreative(Creative creative) {
		int result = -1;

		result = creativeDao.deleteCreative(creative);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public List<Creative> selectCreative(Creative creative, PageBounds pageBounds) {
		return setProperty(creativeDao.selectCreatives(creative, pageBounds));

	}

	private List<Creative> setProperty(List<Creative> list) {
		return list;
	}

	@Override
	public Creative selectCreative(Long creativeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Long> selectCreativeIds(Creative creative) {
		List<Long> resultList = new ArrayList<Long>();
		List<Creative> list = selectCreative(creative, new PageBounds());
		for (Creative parma : list) {
			resultList.add(parma.getCreativeId());
		}
		return resultList;
	}

}
