package com.yuanshanbao.dsp.creative.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.creative.model.Creative;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class CreativeDaoImpl extends BaseDaoImpl implements CreativeDao {
	@Override
	public List<Creative> selectCreatives(Creative creative, PageBounds pageBounds) {
		return getSqlSession().selectList("creative.selectCreative", creative, pageBounds);
	}

	@Override
	public int insertCreative(Creative creative) {
		return getSqlSession().insert("creative.insertCreative", creative);
	}

	@Override
	public int deleteCreative(Creative creative) {
		return getSqlSession().delete("creative.deleteCreative", creative);
	}

	@Override
	public int updateCreative(Creative creative) {
		return getSqlSession().update("creative.updateCreative", creative);
	}

}
