package com.yuanshanbao.dsp.common.service;

import java.util.List;

import com.yuanshanbao.dsp.common.model.Feedback;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface FeedbackService {

	public List<Feedback> selectFeedbacks(Feedback feedback, PageBounds pageBounds);

	public Feedback selectFeedback(Long feedbackId);

	public void insertFeedback(Feedback feedback);

	public void deleteFeedback(Long feedbackId);

	public void deleteFeedback(Feedback feedback);

	public void updateFeedback(Feedback feedback);

	public void insertOrUpdateFeedback(Feedback feedback);

}
