package com.yuanshanbao.dsp.sms.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.dsp.sms.model.MarketingTask;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface MarketingTaskService {

	public void insertMarketingTask(MarketingTask marketingTask);

	public void updateMarketingTask(MarketingTask marketingTask);

	public void deleteMarketingTask(MarketingTask marketingTask);

	public List<MarketingTask> selectMarketingTask(MarketingTask marketingTask, PageBounds pageBounds);

	public Map<Long, MarketingTask> selectMarketingTaskByIds(List<Long> marketingTaskIds);

	public void executeMarketingTask(Long marketingTaskId) throws Exception;

	public MarketingTask selectMarketingTask(Long marketingTaskId);

}
