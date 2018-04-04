package com.yuanshanbao.ad.config.dao;

import java.util.List;

import com.yuanshanbao.ad.config.model.ConfigAction;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface ConfigActionDao {

	public int insertConfigAction(ConfigAction configAction);

	public int updateConfigAction(ConfigAction configAction);

	public int deleteConfigAction(ConfigAction configAction);

	public List<ConfigAction> selectConfigActions(ConfigAction configAction, PageBounds pageBounds);

	public List<ConfigAction> selectConfigActionByFunctionIds(List<Long> functionIds);

}
