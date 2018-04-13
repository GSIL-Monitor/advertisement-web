package com.yuanshanbao.dsp.information.dao;

import java.util.List;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.dsp.information.model.FieldConstrains;

public interface FieldConstrainsDao {

	public List<FieldConstrains> selectFieldConstrains(FieldConstrains fieldConstrains, PageBounds pageBounds);

	public int insertFieldConstrains(FieldConstrains fieldConstrains);

	public int deleteFieldConstrains(Long constrainsId);

	public int updateFieldConstrains(FieldConstrains fieldConstrains);

	public List<FieldConstrains> selectFieldConstrains(List<Long> constrainsIdList);
}
