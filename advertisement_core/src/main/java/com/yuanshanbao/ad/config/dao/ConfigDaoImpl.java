package com.yuanshanbao.ad.config.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.ad.base.dao.BaseDaoImpl;
import com.yuanshanbao.ad.config.model.Config;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class ConfigDaoImpl extends BaseDaoImpl implements ConfigDao {

	@Override
	public int insertConfig(Config config) {
		return getSqlSession().insert("config.insertConfig", config);
	}

	@Override
	public int updateConfig(Config config) {
		return getSqlSession().update("config.updateConfig", config);
	}

	@Override
	public int deleteConfig(Config config) {
		return getSqlSession().delete("config.deleteConfig", config);
	}

	@Override
	public List<Config> selectConfigs(Config config, PageBounds pageBounds) {
		return getSqlSession().selectList("config.selectConfigs", config, pageBounds);
	}

	@Override
	public List<Config> selectConfigByIds(List<Long> idsList) {
		if (idsList == null || idsList.size() == 0) {
			return new ArrayList<Config>();
		}

		return getSqlSession().selectList("config.selectConfigByIds", idsList);
	}

	@Override
	public List<Config> selectConfigByActivityIdAndFunctionIds(Map<String, Object> map) {
		if (map == null || map.size() == 0) {
			return new ArrayList<Config>();
		}
		return getSqlSession().selectList("config.selectConfigByActivityIdAndFunctionIds", map);
	}

	@Override
	public List<Config> selectConfigsDivideByChannel(Map<String, Object> map) {
		if (map == null || map.size() == 0) {
			return new ArrayList<Config>();
		}
		return getSqlSession().selectList("config.selectConfigByMap", map);
	}

}
