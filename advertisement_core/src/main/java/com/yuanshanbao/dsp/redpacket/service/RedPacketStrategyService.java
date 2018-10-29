package com.yuanshanbao.dsp.redpacket.service;


import com.yuanshanbao.dsp.redpacket.model.RedPacketStrategy;
import com.yuanshanbao.paginator.domain.PageBounds;

import java.util.List;
import java.util.Map;

public interface RedPacketStrategyService {

    public List<RedPacketStrategy> selectRedPacketStrategys(RedPacketStrategy redPacketStrategy, PageBounds pageBounds);

    public RedPacketStrategy selectRedPacketStrategy(Long strategyId);

    public void insertRedPacketStrategy(RedPacketStrategy redPacketStrategy);

    public void deleteRedPacketStrategy(Long strategyId);

    public void deleteRedPacketStrategy(RedPacketStrategy redPacketStrategy);

    public void updateRedPacketStrategy(RedPacketStrategy redPacketStrategy);

    public void insertOrUpdateRedPacketStrategy(RedPacketStrategy redPacketStrategy);

    public Map<Long, RedPacketStrategy> selectRedPacketStrategy(List<Long> strategyIdList);

}
