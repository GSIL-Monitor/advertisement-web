package com.yuanshanbao.dsp.apply.dao;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.apply.model.ApplySequence;
import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;

@Repository
public class ApplySequenceDaoImpl extends BaseDaoImpl implements ApplySequenceDao {

	@Override
	public Long getApplySequence() {
		ApplySequence applySequence = new ApplySequence();
		applySequence.setStub("a");
		getSqlSession().insert("ApplySequence.insertApplySeq", applySequence);
		Long applySeq = applySequence.getApplySeq();
		return applySeq;
	}
}
