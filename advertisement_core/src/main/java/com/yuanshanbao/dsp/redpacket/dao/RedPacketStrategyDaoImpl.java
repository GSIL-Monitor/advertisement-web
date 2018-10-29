package com.yuanshanbao.dsp.redpacket.dao;


import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.redpacket.model.RedPacketStrategy;
import com.yuanshanbao.paginator.domain.PageBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RedPacketStrategyDaoImpl extends BaseDaoImpl implements RedPacketStrategyDao {

    @Override
    public List<RedPacketStrategy> selectRedPacketStrategys(RedPacketStrategy redPacketStrategy, PageBounds pageBounds) {
        return getSqlSession().selectList("RedPacketStrategy.selectRedPacketStrategy", redPacketStrategy, pageBounds);
    }

    @Override
    public int insertRedPacketStrategy(RedPacketStrategy redPacketStrategy) {
        return getSqlSession().insert("RedPacketStrategy.insertRedPacketStrategy", redPacketStrategy);
    }

    @Override
    public int deleteRedPacketStrategy(RedPacketStrategy redPacketStrategy) {
        return getSqlSession().delete("RedPacketStrategy.deleteRedPacketStrategy", redPacketStrategy);
    }

    @Override
    public int updateRedPacketStrategy(RedPacketStrategy redPacketStrategy) {
        return getSqlSession().update("RedPacketStrategy.updateRedPacketStrategy", redPacketStrategy);
    }

    @Override
    public List<RedPacketStrategy> selectRedPacketStrategyByIds(List<Long> strategyIdList) {
        return getSqlSession().selectList("RedPacketStrategy.selectRedPacketStrategyByIds", strategyIdList);
    }

}