package com.yuanshanbao.ad.common.service;

import java.util.List;

import com.yuanshanbao.ad.common.model.Entrance;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface EntranceService {

	public void insertEntrance(Entrance entrance);
	
	public void updateEntrance(Entrance entrance);
	
	public List<Entrance> selectEntrance(Entrance entrance, PageBounds pageBounds);
	
}
