package com.yuanshanbao.dsp.user.dao;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.user.model.UserSequence;
import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;

@Repository
public class UserSequenceDaoImpl extends BaseDaoImpl implements UserSequenceDao {

	@Override
	public Long getUserSequence() {
		UserSequence userSequence = new UserSequence();
		userSequence.setStub("a");
		getSqlSession().insert("UserSequence.insertUserSeq", userSequence);
		Long userSeq = userSequence.getUserSeq();
		return userSeq;
	}
}
