package com.yuanshanbao.dsp.position.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.position.model.Position;
import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class PositionDaoImpl extends BaseDaoImpl implements PositionDao {

	@Override
	public int insertPosition(Position position) {
		return getSqlSession().insert("position.insertPosition", position);
	}

	@Override
	public int updatePosition(Position position) {
		return getSqlSession().update("position.updatePosition", position);
	}

	@Override
	public int deletePosition(Position position) {
		return getSqlSession().delete("position.deletePosition", position);
	}

	@Override
	public List<Position> selectPosition(Position position, PageBounds pageBounds) {
		return getSqlSession().selectList("position.selectPosition", position, pageBounds);
	}

	@Override
	public List<Position> selectPositionByIds(List<Long> positionIds) {
		if (positionIds == null || positionIds.size() == 0) {
			return new ArrayList<Position>();
		}

		return getSqlSession().selectList("position.selectPositionByIds", positionIds);
	}

}
