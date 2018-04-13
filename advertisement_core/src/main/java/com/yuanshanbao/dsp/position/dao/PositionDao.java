package com.yuanshanbao.dsp.position.dao;

import java.util.List;

import com.yuanshanbao.dsp.position.model.Position;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface PositionDao {
	public int insertPosition(Position position);

	public int updatePosition(Position position);

	public int deletePosition(Position position);

	public List<Position> selectPosition(Position position, PageBounds pageBounds);

	public List<Position> selectPositionByIds(List<Long> positionIds);
}
