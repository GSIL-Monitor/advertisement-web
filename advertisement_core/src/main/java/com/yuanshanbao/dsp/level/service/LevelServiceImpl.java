package com.yuanshanbao.dsp.level.service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.agency.model.Agency;
import com.yuanshanbao.dsp.level.dao.LevelDao;
import com.yuanshanbao.dsp.level.model.Level;
import com.yuanshanbao.paginator.domain.PageBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/10/30.
 */
@Service
public class LevelServiceImpl  implements  LevelService{
    @Autowired
    private LevelDao levelDao;
    @Override
    public List<Level> selectLevels(Level level, PageBounds pageBounds) {
        return null;
    }

    @Override
    public Level selectLevel(Long id) {
        if (id == null){
            throw  new BusinessException(ComRetCode.NOT_LOGIN);
        }
        return levelDao.selectLevel(id);
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
