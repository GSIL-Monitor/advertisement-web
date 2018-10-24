package com.yuanshanbao.dsp.strategy.service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.strategy.dao.StrategyDao;
import com.yuanshanbao.dsp.strategy.model.Strategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/10/15.
 */
@Service
public class StrategyServiceImpl implements StrategyService {
    @Autowired
    private StrategyDao strategyDao;
    @Override
    public void insertStrategy(Strategy strategy) {
        int result = -1;
        result = strategyDao.insertStrategy(strategy);
        if (result <0){
            throw new BusinessException(ComRetCode.FAIL);
        }
    }
}
