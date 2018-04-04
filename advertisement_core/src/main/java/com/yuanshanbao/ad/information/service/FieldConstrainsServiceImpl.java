package com.yuanshanbao.ad.information.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.ad.information.dao.FieldConstrainsDao;
import com.yuanshanbao.ad.information.model.FieldConstrains;
import com.yuanshanbao.ad.information.model.InformationField;

@Service
public class FieldConstrainsServiceImpl implements FieldConstrainsService {

	@Autowired
	private FieldConstrainsDao fieldConstrainsDao;

	@Override
	public List<FieldConstrains> selectFieldConstrains(FieldConstrains fieldConstrains, PageBounds pageBounds) {
		return setProperty(fieldConstrainsDao.selectFieldConstrains(fieldConstrains, pageBounds));
	}

	private List<FieldConstrains> setProperty(List<FieldConstrains> list) {
		return list;
	}

	@Override
	public void insertFieldConstrains(FieldConstrains fieldConstrains) {
		int result = -1;

		result = fieldConstrainsDao.insertFieldConstrains(fieldConstrains);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteFieldConstrains(Long constrainsId) {
		int result = -1;

		result = fieldConstrainsDao.deleteFieldConstrains(constrainsId);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateFieldConstrains(FieldConstrains fieldConstrains) {
		int result = -1;

		result = fieldConstrainsDao.updateFieldConstrains(fieldConstrains);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public Map<Long, FieldConstrains> selectFieldConstrainsByIds(List<Long> constrainsIdList) {
		Map<Long, FieldConstrains> map = new HashMap<Long, FieldConstrains>();
		if (constrainsIdList == null || constrainsIdList.size() == 0) {
			return map;
		}
		List<FieldConstrains> list = fieldConstrainsDao.selectFieldConstrains(constrainsIdList);
		for (FieldConstrains FieldConstrains : list) {
			map.put(FieldConstrains.getConstrainsId(), FieldConstrains);
		}
		return map;
	}

	@Override
	public FieldConstrains selectFieldConstrains(Long constainsId) {
		FieldConstrains fieldConstrains = new FieldConstrains();
		fieldConstrains.setConstrainsId(constainsId);
		List<FieldConstrains> list = selectFieldConstrains(fieldConstrains, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void checkField(List<FieldConstrains> fieldConstrains, InformationField field, Map<String, String> parameterMap) {
		String value = parameterMap.get(field.getFieldName());
		for (FieldConstrains constant : fieldConstrains) {
			if (!constant.check(value)) {
				throw new BusinessException(field.getName() + constant.getDescription() + constant.getValue(),
						ComRetCode.WRONG_PARAMETER);
			}
		}
	}
}
