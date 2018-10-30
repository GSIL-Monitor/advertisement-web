package com.yuanshanbao.dsp.level.service;

import com.yuanshanbao.dsp.agency.model.Agency;
import com.yuanshanbao.dsp.level.model.Level;
import com.yuanshanbao.paginator.domain.PageBounds;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/10/30.
 */
@Service
public class LevelServiceImpl  implements  LevelService{
    @Override
    public List<Level> selectLevels(Level level, PageBounds pageBounds) {
        return null;
    }

    @Override
    public Agency selectLevel(Long id) {
        return null;
    }

    @Override
    public int insertLevel(Level level) {
        return 0;
    }

    @Override
    public int deleteLevel(Long Id) {
        return 0;
    }

    @Override
    public int updateLevel(Level level) {
        return 0;
    }

    @Override
    public List<Agency> selectLevelsByIds(List<Long> IdList) {
        return null;
    }
}
