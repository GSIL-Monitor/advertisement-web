package com.yuanshanbao.dsp.creative.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.creative.dao.CreativeDao;
import com.yuanshanbao.dsp.creative.model.Creative;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

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

	@Override
	public List<Creative> selectCreativesByIds(Long advertiserId, String creativeIds, Boolean isSelect,
			PageBounds pageBounds) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(creativeIds)) {
			parameters.put("creativeIdList", Arrays.asList(creativeIds.split(",")));
		} else if (isSelect != null && isSelect) {
			return new PageList<Creative>();
		}
		parameters.put("advertiserId", advertiserId);
		if (isSelect != null && isSelect) {
			parameters.put("isSelect", isSelect);
		}
		parameters.put("status", CommonStatus.ONLINE);
		return setProperty(creativeDao.selectCreativesByIds(parameters, pageBounds));

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
