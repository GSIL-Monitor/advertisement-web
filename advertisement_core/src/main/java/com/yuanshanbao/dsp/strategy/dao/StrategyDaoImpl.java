package com.yuanshanbao.dsp.strategy.dao;

import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.strategy.model.Strategy;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018/10/15.
 */
@Repository
public class StrategyDaoImpl extends BaseDaoImpl implements  StrategyDao {
    @Override
    public int insertStrategy(Strategy strategy) {
        return  getSqlSession().insert("Strategy.insertStrategy", strategy );
    }
}
