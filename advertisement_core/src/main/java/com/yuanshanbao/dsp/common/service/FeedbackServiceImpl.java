package com.yuanshanbao.dsp.common.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.common.dao.FeedbackDao;
import com.yuanshanbao.dsp.common.model.Feedback;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	private FeedbackDao feedbackDao;

	@Override
	public List<Feedback> selectFeedbacks(Feedback feedback, PageBounds pageBounds) {
		return feedbackDao.selectFeedbacks(feedback, pageBounds);
	}

	@Override
	public Feedback selectFeedback(Long feedbackId) {
		if (feedbackId == null) {
			return null;
		}
		Feedback feedback = new Feedback();
		feedback.setFeedbackId(feedbackId);
		List<Feedback> list = selectFeedbacks(feedback, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertFeedback(Feedback feedback) {
		int result = -1;

		result = feedbackDao.insertFeedback(feedback);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteFeedback(Long feedbackId) {
		Feedback feedback = new Feedback();
		feedback.setFeedbackId(feedbackId);
		deleteFeedback(feedback);
	}

	@Override
	public void deleteFeedback(Feedback feedback) {
		int result = -1;

		result = feedbackDao.deleteFeedback(feedback);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateFeedback(Feedback feedback) {
		int result = -1;

		result = feedbackDao.updateFeedback(feedback);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	/**
	 * @param feedback
	 * @return
	 * @throws IOException
	 */
	@Override
	public void insertOrUpdateFeedback(Feedback feedback) {
		if (feedback.getFeedbackId() == null) {
			insertFeedback(feedback);
		} else {
			updateFeedback(feedback);
		}
	}

}
