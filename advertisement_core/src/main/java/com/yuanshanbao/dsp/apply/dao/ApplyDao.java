package com.yuanshanbao.dsp.apply.dao;

import java.util.List;

import com.yuanshanbao.dsp.apply.model.Apply;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface ApplyDao {

	public List<Apply> selectApplys(Apply apply, PageBounds pageBounds);

	public List<Apply> selectUserApplys(Apply apply, PageBounds pageBounds);

	public int insertApply(Apply apply);

	public int deleteApply(String applyId);

	public int updateApply(Apply apply);

	public List<Apply> selectApplys(List<Long> applyIdList);
}
