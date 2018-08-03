package com.yuanshanbao.dsp.apply.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yuanshanbao.dsp.apply.dao.ApplySequenceDao;
import com.yuanshanbao.dsp.common.constant.CommonConstant;

@Service
public class ApplyIdServiceImpl implements ApplyIdService {

	@Autowired
	private ApplySequenceDao applySequenceDao;

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public String generateApplyId(String userId) {
		long seq = applySequenceDao.getApplySequence();
		String id = seq + CommonConstant.getShardSuffixByUserId(userId);
		return id;
	}
}
