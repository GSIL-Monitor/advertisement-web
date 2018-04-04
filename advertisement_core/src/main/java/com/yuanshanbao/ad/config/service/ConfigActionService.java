package com.yuanshanbao.ad.config.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.ad.config.model.ConfigAction;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface ConfigActionService {

	public void insertConfigAction(ConfigAction configAction);

	public void updateConfigAction(ConfigAction configAction);

	public void deleteConfigAction(ConfigAction configAction);

	public List<ConfigAction> selectConfigAction(ConfigAction configAction, PageBounds pageBounds);

	public Map<Long, List<ConfigAction>> selectConfigActionByFunctionIds(List<Long> functionIds);

	public ConfigAction selectConfigActionById(Long configActionId);

}
