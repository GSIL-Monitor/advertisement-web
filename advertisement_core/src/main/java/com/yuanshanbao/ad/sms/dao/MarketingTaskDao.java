package com.yuanshanbao.ad.sms.dao;

import java.util.List;

import com.yuanshanbao.ad.sms.model.MarketingTask;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface MarketingTaskDao {

	public int insertMarketingTask(MarketingTask marketingTask);

	public int updateMarketingTask(MarketingTask marketingTask);

	public int deleteMarketingTask(MarketingTask marketingTask);

	public List<MarketingTask> selectMarketingTask(MarketingTask marketingTask, PageBounds pageBounds);
	
	public List<MarketingTask> selectMarketingTaskByIds(List<Long> marketingTaskIds);

}
