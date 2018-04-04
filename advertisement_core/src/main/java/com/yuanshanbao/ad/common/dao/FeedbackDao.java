package com.yuanshanbao.ad.common.dao;

import java.util.List;

import com.yuanshanbao.ad.common.model.Feedback;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface FeedbackDao {

	public List<Feedback> selectFeedbacks(Feedback feedback, PageBounds pageBounds);

	public int insertFeedback(Feedback feedback);

	public int deleteFeedback(Feedback feedback);

	public int updateFeedback(Feedback feedback);
}
