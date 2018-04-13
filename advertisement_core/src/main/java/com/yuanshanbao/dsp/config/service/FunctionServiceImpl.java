package com.yuanshanbao.dsp.config.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.config.dao.FunctionDao;
import com.yuanshanbao.dsp.config.model.ConfigAction;
import com.yuanshanbao.dsp.config.model.Function;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class FunctionServiceImpl implements FunctionService {

	@Autowired
	private FunctionDao functionDao;

	@Autowired
	private ConfigActionService configActionService;

	@Override
	public void insertFunction(Function function) {

		int result = -1;

		result = functionDao.insertFunction(function);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public void updateFunction(Function function) {

		int result = -1;

		result = functionDao.updateFunction(function);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteFunction(Function function) {

		int result = -1;

		if (function == null || function.getFunctionId() == null) {
			throw new BusinessException(ComRetCode.WRONG_PARAMETER);
		}

		result = functionDao.deleteFunction(function);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public List<Function> selectFunctions(Function function, PageBounds pageBounds) {
		return functionDao.selectFunctions(function, pageBounds);
	}

	@Override
	public List<Function> selectFunctionsByCategory(Integer category) {
		Function function = new Function();
		function.setCategory(category);
		return functionDao.selectFunctions(function, new PageBounds());
	}
	
	@Override
	public Map<Long, Function> selectFunctionByIds(List<Long> idsList) {

		List<Function> list = functionDao.selectFunctionByIds(idsList);

		Map<Long, Function> map = new HashMap<Long, Function>();

		for (Function function : list) {
			map.put(function.getFunctionId(), function);
		}

		return map;
	}

	@Override
	public Function selectFunctionById(Long functionId) {
		Function function = new Function();
		function.setFunctionId(functionId);
		List<Function> list = selectFunctions(function, new PageBounds());
		if (list != null && list.size() >= 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Function> selectFunctionByKeys(List<String> keys) {
		List<Function> list = functionDao.selectFunctionByKeys(keys);
		List<Long> functionIds = new ArrayList<Long>();
		for (Function fc : list) {
			functionIds.add(fc.getFunctionId());
		}
		Map<Long, List<ConfigAction>> configActionMap = configActionService
				.selectConfigActionByFunctionIds(functionIds);
		Map<String, Function> map = new HashMap<String, Function>();
		for (Function f : list) {
			f.setConfigActionList(configActionMap.get(f.getFunctionId())); // 添加configAction
			map.put(f.getKey(), f);
		}
		return map;
	}

}
