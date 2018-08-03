package com.yuanshanbao.dsp.config.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yuanshanbao.dsp.config.model.Config;
import com.yuanshanbao.dsp.config.model.ConfigCategory;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface ConfigService {

	public void insertConfig(Config config);

	public void updateConfig(Config config);

	public void deleteConfig(Long configId);

	public Config selectConfigById(Long configId);

	public List<Config> selectConfig(Config config, PageBounds pageBounds);

	public List<Config> selectConfigByIds(List<Long> idsList);

	public void insertOrUpdateConfig(Config config);

	public void updateActivityConfig(HttpServletRequest request, Long activityId, String channel, String appKey);

	public Map<Integer, ConfigCategory> getConfigCategoryList(Long activityId, String channel, String appKey);

	public Map<Long, String> selectConfigByActivityAndFunctionIds(Long activityId, List<Long> functionIds);

	public Map<Long, String> selectConfigsDivideByChannel(Long activityId, List<Long> functionIds, String channel);

	public Map<Integer, ConfigCategory> getConfigCategoryList(Long activityId, String channel);

	public void updateActivityConfig(HttpServletRequest request, Long activityId, String channel);

}
