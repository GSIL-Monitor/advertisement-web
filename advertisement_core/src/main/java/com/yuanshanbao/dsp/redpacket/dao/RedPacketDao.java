package com.yuanshanbao.dsp.redpacket.dao;

import java.util.List;

import com.yuanshanbao.dsp.redpacket.model.RedPacket;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface RedPacketDao {

	public List<RedPacket> selectRedPackets(RedPacket redPacket, PageBounds pageBounds);

	public int insertRedPacket(RedPacket redPacket);

	public int deleteRedPacket(RedPacket redPacket);

	public int updateRedPacket(RedPacket redPacket);

	public RedPacket selectRedPacketById(Long redPacketId, String userId, Boolean isLock);

	public int selectShowRedPacketCount(String userId);

}
