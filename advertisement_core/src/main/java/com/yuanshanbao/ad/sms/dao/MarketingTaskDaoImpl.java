package com.yuanshanbao.ad.sms.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.ad.base.dao.BaseDaoImpl;
import com.yuanshanbao.ad.sms.model.MarketingTask;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class MarketingTaskDaoImpl extends BaseDaoImpl implements MarketingTaskDao {

	@Override
	public int insertMarketingTask(MarketingTask marketingTask) {
		return getSqlSession().insert("marketingTask.insertMarketingTask", marketingTask);
	}

	@Override
	public int updateMarketingTask(MarketingTask marketingTask) {
		return getSqlSession().update("marketingTask.updateMarketingTask", marketingTask);
	}

	@Override
	public int deleteMarketingTask(MarketingTask marketingTask) {
		return getSqlSession().delete("marketingTask.deleteMarketingTask", marketingTask);
	}

	@Override
	public List<MarketingTask> selectMarketingTask(MarketingTask marketingTask, PageBounds pageBounds) {
		return getSqlSession().selectList("marketingTask.selectMarketingTask", marketingTask, pageBounds);
	}

	@Override
	public List<MarketingTask> selectMarketingTaskByIds(List<Long> marketingTaskIds) {
		if (marketingTaskIds == null || marketingTaskIds.size() == 0) {
			return new ArrayList<MarketingTask>();
		}
		return getSqlSession().selectList("marketingTask.selectMarketingTaskByIds", marketingTaskIds);
	}

}
