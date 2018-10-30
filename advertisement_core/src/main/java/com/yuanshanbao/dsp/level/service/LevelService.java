package com.yuanshanbao.dsp.level.service;

import com.yuanshanbao.dsp.agency.model.Agency;
import com.yuanshanbao.dsp.level.model.Level;
import com.yuanshanbao.paginator.domain.PageBounds;

import java.util.List;

/**
 * Created by Administrator on 2018/10/30.
 */
public interface LevelService {
    public List<Level> selectLevels(Level level, PageBounds pageBounds);

    Agency selectLevel(Long id);

    public int insertLevel(Level level);

    public int deleteLevel(Long Id);

    public int updateLevel(Level level);

    public List<Agency> selectLevelsByIds(List<Long> IdList);
}
