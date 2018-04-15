package com.yuanshanbao.dsp.position.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.position.dao.PositionDao;
import com.yuanshanbao.dsp.position.model.Position;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class PositionServiceImpl implements PositionService {

	@Autowired
	private PositionDao positionDao;

	@Override
	public void insertPosition(Position position) {
		int result = -1;

		result = positionDao.insertPosition(position);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updatePosition(Position position) {
		int result = -1;

		result = positionDao.updatePosition(position);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deletePosition(Position position) {
		int result = -1;

		result = positionDao.deletePosition(position);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public List<Position> selectPosition(Position position, PageBounds pageBounds) {
		return positionDao.selectPosition(position, pageBounds);
	}

	@Override
	public Position selectPosition(Long positionId) {
		if (positionId == null) {
			return null;
		}
		Position position = new Position();
		position.setPositionId(positionId);
		List<Position> list = selectPosition(position, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<Long, Position> selectPositionByIds(List<Long> positionIds) {
		Map<Long, Position> map = new HashMap<Long, Position>();
		if (positionIds == null || positionIds.size() == 0) {
			return map;
		}
		List<Position> list = positionDao.selectPositionByIds(positionIds);
		for (Position ad : list) {
			map.put(ad.getPositionId(), ad);
		}
		return map;
	}

	@Override
	public List<Position> selectPositionByProjectId(Long projectId) {
		if (projectId == null) {
			return new ArrayList<Position>();
		}
		Position position = new Position();
		position.setProjectId(projectId);
		return selectPosition(position, new PageBounds());
	}

}
