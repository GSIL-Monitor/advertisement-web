package com.yuanshanbao.ad.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yuanshanbao.ad.common.constant.CommonConstant;
import com.yuanshanbao.ad.user.dao.UserSequenceDao;

@Service
public class UserIdServiceImpl implements UserIdService {

	@Autowired
	private UserSequenceDao userSequenceDao;

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public String generateUserId(String userId) {
		long seq = userSequenceDao.getUserSequence();
		String id = seq + CommonConstant.getShardSuffixByUserId(userId);
		return id;
	}
}
