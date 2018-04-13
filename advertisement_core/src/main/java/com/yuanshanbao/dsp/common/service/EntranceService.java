package com.yuanshanbao.dsp.common.service;

import java.util.List;

import com.yuanshanbao.dsp.common.model.Entrance;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface EntranceService {

	public void insertEntrance(Entrance entrance);
	
	public void updateEntrance(Entrance entrance);
	
	public List<Entrance> selectEntrance(Entrance entrance, PageBounds pageBounds);
	
}
