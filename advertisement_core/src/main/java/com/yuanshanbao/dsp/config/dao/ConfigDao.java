package com.yuanshanbao.dsp.config.dao;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.dsp.config.model.Config;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface ConfigDao {

	public int insertConfig(Config config);

	public int updateConfig(Config config);

	public int deleteConfig(Config config);

	public List<Config> selectConfigs(Config config, PageBounds pageBounds);

	public List<Config> selectConfigByIds(List<Long> idsList);

	public List<Config> selectConfigByActivityIdAndFunctionIds(Map<String, Object> map);

	public List<Config> selectConfigsDivideByChannel(Map<String, Object> map);

}
