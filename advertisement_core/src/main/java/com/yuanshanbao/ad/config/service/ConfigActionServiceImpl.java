package com.yuanshanbao.ad.config.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.ad.config.dao.ConfigActionDao;
import com.yuanshanbao.ad.config.model.ConfigAction;
import com.yuanshanbao.ad.config.model.Function;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class ConfigActionServiceImpl implements ConfigActionService {

	@Autowired
	private ConfigActionDao configActionDao;

	@Autowired
	private FunctionService functionService;

	@Override
	public void insertConfigAction(ConfigAction configAction) {

		int result = -1;

		result = configActionDao.insertConfigAction(configAction);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public void updateConfigAction(ConfigAction configAction) {

		int result = -1;

		result = configActionDao.updateConfigAction(configAction);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public void deleteConfigAction(ConfigAction configAction) {

		int result = -1;

		if (configAction == null || configAction.getConfigActionId() == null) {
			throw new BusinessException(ComRetCode.WRONG_PARAMETER);
		}

		result = configActionDao.deleteConfigAction(configAction);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public List<ConfigAction> selectConfigAction(ConfigAction configAction, PageBounds pageBounds) {
		return setProperty(configActionDao.selectConfigActions(configAction, pageBounds));
	}

	private List<ConfigAction> setProperty(List<ConfigAction> list) {
		List<Long> functionIds = new ArrayList<Long>();
		for (ConfigAction ca : list) {
			functionIds.add(ca.getFunctionId());
		}
		Map<Long, Function> functionMap = functionService.selectFunctionByIds(functionIds);
		for (ConfigAction ca : list) {
			ca.setFunction(functionMap.get(ca.getFunctionId()));
		}
		return list;
	}

	@Override
	public Map<Long, List<ConfigAction>> selectConfigActionByFunctionIds(List<Long> functionIds) {
		List<ConfigAction> list = configActionDao.selectConfigActionByFunctionIds(functionIds);
		Map<Long, List<ConfigAction>> map = new HashMap<Long, List<ConfigAction>>();

		for (ConfigAction ac : list) {
			List<ConfigAction> exist = map.get(ac.getFunctionId());
			if (exist == null) {
				exist = new ArrayList<ConfigAction>();
				map.put(ac.getFunctionId(), exist);
			}
			exist.add(ac);
		}

		return map;
	}

	@Override
	public ConfigAction selectConfigActionById(Long configActionId) {
		ConfigAction configAction = new ConfigAction();
		configAction.setConfigActionId(configActionId);
		List<ConfigAction> list = selectConfigAction(configAction, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}

		return null;
	}

}
