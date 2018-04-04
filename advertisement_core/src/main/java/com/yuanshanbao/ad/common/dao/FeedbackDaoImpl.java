package com.yuanshanbao.ad.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.ad.base.dao.BaseDaoImpl;
import com.yuanshanbao.ad.common.model.Feedback;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class FeedbackDaoImpl extends BaseDaoImpl implements FeedbackDao {
	@Override
	public List<Feedback> selectFeedbacks(Feedback feedback, PageBounds pageBounds) {
		return getSqlSession().selectList("feedback.selectFeedback", feedback, pageBounds);
	}

	@Override
	public int insertFeedback(Feedback feedback) {
		return getSqlSession().insert("feedback.insertFeedback", feedback);
	}

	@Override
	public int deleteFeedback(Feedback feedback) {
		return getSqlSession().delete("feedback.deleteFeedback", feedback);
	}

	@Override
	public int updateFeedback(Feedback feedback) {
		return getSqlSession().update("feedback.updateFeedback", feedback);
	}

}
