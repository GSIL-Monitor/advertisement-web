package com.yuanshanbao.dsp.position.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.dsp.position.model.Position;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface PositionService {
	public void insertPosition(Position position);

	public void updatePosition(Position position);

	public void deletePosition(Position position);

	public List<Position> selectPosition(Position position, PageBounds pageBounds);

	public Position selectPosition(Long positionId);

	public Map<Long, Position> selectPositionByIds(List<Long> positionIds);

	public List<Position> selectPositionByProjectId(Long projectId);

}
