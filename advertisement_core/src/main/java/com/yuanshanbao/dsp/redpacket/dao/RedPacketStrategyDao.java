package com.yuanshanbao.dsp.redpacket.dao;


import com.yuanshanbao.dsp.redpacket.model.RedPacketStrategy;
import com.yuanshanbao.paginator.domain.PageBounds;

import java.util.List;

public interface RedPacketStrategyDao {

    public List<RedPacketStrategy> selectRedPacketStrategys(RedPacketStrategy redPacketStrategy, PageBounds pageBounds);

    public int insertRedPacketStrategy(RedPacketStrategy redPacketStrategy);

    public int deleteRedPacketStrategy(RedPacketStrategy redPacketStrategy);

    public int updateRedPacketStrategy(RedPacketStrategy redPacketStrategy);

    public List<RedPacketStrategy> selectRedPacketStrategyByIds(List<Long> strategyIdList);

}
