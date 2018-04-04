package com.yuanshanbao.ad.common.dao;

import java.util.List;

import com.yuanshanbao.ad.common.model.Entrance;
import com.yuanshanbao.paginator.domain.PageBounds;

/**
 * Created by Ynght on 2016/12/9.
 */
public interface EntranceDao {
    List<Entrance> getAllEntrances();

    public int insert(Entrance entrance);

    public int update(Entrance entrance);
    
    List<Entrance> selectEntrances(Entrance entrance, PageBounds pageBounds);
}
