package com.yuanshanbao.dsp.information.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.information.model.FieldConstrains;

@Repository
public class FieldConstrainsDaoImpl extends BaseDaoImpl implements FieldConstrainsDao {
	@Override
	public List<FieldConstrains> selectFieldConstrains(FieldConstrains fieldConstrains, PageBounds pageBounds) {
		List<FieldConstrains> list = getSqlSession().selectList("FieldConstrains.selectFieldConstrains",
				fieldConstrains, pageBounds);
		return list;
	}

	@Override
	public int insertFieldConstrains(FieldConstrains fieldConstrains) {
		return getSqlSession().insert("fieldConstrains.insertFieldConstrains", fieldConstrains);
	}

	@Override
	public int deleteFieldConstrains(Long constrainsId) {
		return getSqlSession().delete("fieldConstrains.deleteFieldConstrains", constrainsId);
	}

	@Override
	public int updateFieldConstrains(FieldConstrains fieldConstrains) {
		return getSqlSession().update("fieldConstrains.updateFieldConstrains", fieldConstrains);
	}

	@Override
	public List<FieldConstrains> selectFieldConstrains(List<Long> constrainsIdList) {
		if (constrainsIdList == null || constrainsIdList.size() == 0) {
			return new ArrayList<FieldConstrains>();
		}
		return getSqlSession().selectList("fieldConstrains.selectFieldConstrainsByIds", constrainsIdList);
	}

}
