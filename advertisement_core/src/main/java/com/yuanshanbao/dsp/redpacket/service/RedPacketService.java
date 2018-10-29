package com.yuanshanbao.dsp.redpacket.service;


import com.yuanshanbao.dsp.redpacket.model.RedPacket;
import com.yuanshanbao.paginator.domain.PageBounds;

import java.math.BigDecimal;
import java.util.List;

public interface RedPacketService {

    public List<RedPacket> selectRedPackets(RedPacket redPacket, PageBounds pageBounds);

    public List<RedPacket> selectAvailableRedPackets(String userId);

    public RedPacket selectRedPacket(Long userId, Long redPacketId);

    public void insertRedPacket(RedPacket redPacket);

    public void deleteRedPacket(Long redPacketId);

    public void deleteRedPacket(RedPacket redPacket);

    public void updateRedPacket(RedPacket redPacket);

    public void insertOrUpdateRedPacket(RedPacket redPacket);

    public BigDecimal checkRedPacketAmount(String userId, Long gameId, Long redPacketId, BigDecimal orderAmount);

    public void checkRedPacketAvailable(RedPacket redPacket, Long gameId, BigDecimal orderAmount);

    public int selectShowRedPacketCount(String userId);

}
