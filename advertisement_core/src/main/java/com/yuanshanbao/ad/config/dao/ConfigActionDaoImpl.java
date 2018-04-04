package com.yuanshanbao.ad.config.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.ad.base.dao.BaseDaoImpl;
import com.yuanshanbao.ad.config.model.ConfigAction;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class ConfigActionDaoImpl extends BaseDaoImpl implements ConfigActionDao {

	@Override
	public int insertConfigAction(ConfigAction configAction) {
		return getSqlSession().insert("configAction.insertConfigAction", configAction);
	}

	@Override
	public int updateConfigAction(ConfigAction configAction) {
		return getSqlSession().update("configAction.updateConfigAction", configAction);
	}

	@Override
	public int deleteConfigAction(ConfigAction configAction) {
		return getSqlSession().delete("configAction.deleteConfigAction", configAction);
	}

	@Override
	public List<ConfigAction> selectConfigActions(ConfigAction configAction, PageBounds pageBounds) {
		return getSqlSession().selectList("configAction.selectConfigActions", configAction, pageBounds);
	}

	@Override
	public List<ConfigAction> selectConfigActionByFunctionIds(List<Long> functionIds) {
		if (functionIds == null || functionIds.size() == 0) {
			return new ArrayList<ConfigAction>();
		}
		return getSqlSession().selectList("configAction.selectConfigActionByFunctionIds", functionIds);
	}

}
