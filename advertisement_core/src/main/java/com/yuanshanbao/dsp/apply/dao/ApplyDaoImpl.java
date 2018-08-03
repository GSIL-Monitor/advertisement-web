package com.yuanshanbao.dsp.apply.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.apply.model.Apply;
import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class ApplyDaoImpl extends BaseDaoImpl implements ApplyDao {
	@Override
	public List<Apply> selectApplys(Apply apply, PageBounds pageBounds) {
		return getSqlSession().selectList("apply.selectApply", apply, pageBounds);
	}

	@Override
	public int insertApply(Apply apply) {
		return getSqlSession().insert("apply.insertApply", apply);
	}

	@Override
	public int deleteApply(String applyId) {
		return getSqlSession().delete("apply.deleteApply", applyId);
	}

	@Override
	public int updateApply(Apply apply) {
		return getSqlSession().update("apply.updateApply", apply);
	}

	@Override
	public List<Apply> selectApplys(List<Long> applyIdList) {
		if (applyIdList == null || applyIdList.size() == 0) {
			return new ArrayList<Apply>();
		}
		return getSqlSession().selectList("apply.selectApplyByIds", applyIdList);
	}

	@Override
	public List<Apply> selectUserApplys(Apply apply, PageBounds pageBounds) {
		return getSqlSession().selectList("apply.selectUserApply", apply, pageBounds);
	}

}
