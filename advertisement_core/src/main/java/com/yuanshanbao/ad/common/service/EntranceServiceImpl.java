package com.yuanshanbao.ad.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.ad.common.dao.EntranceDao;
import com.yuanshanbao.ad.common.model.Entrance;
import com.yuanshanbao.paginator.domain.PageBounds;


@Service
public class EntranceServiceImpl implements EntranceService {

	@Autowired
	private EntranceDao entranceDao;
	
	@Override
	public void insertEntrance(Entrance entrance) {
		
		int result = -1;
		
		result = entranceDao.insert(entrance);
		
		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
		
	}

	@Override
	public void updateEntrance(Entrance entrance) {
		int result = -1;
		
		result = entranceDao.update(entrance);
		
		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public List<Entrance> selectEntrance(Entrance entrance,
			PageBounds pageBounds) {
		return entranceDao.selectEntrances(entrance, pageBounds);
	}

}
