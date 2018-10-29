package com.yuanshanbao.dsp.redpacket.dao;


import com.yuanshanbao.dsp.base.shard.TableShard;
import com.yuanshanbao.dsp.common.constant.ConfigConstant;
import com.yuanshanbao.dsp.redpacket.model.RedPacket;
import com.yuanshanbao.paginator.domain.PageBounds;

import java.util.List;

@TableShard(tableName = ConfigConstant.RED_PACKET_TABLE_NAME, shardType = ConfigConstant.RED_PACKET_SHARD_TYPE, shardBy = ConfigConstant.RED_PACKET_SHARD_BY)
public interface RedPacketDao {

    public List<RedPacket> selectRedPackets(RedPacket redPacket, PageBounds pageBounds);

    public int insertRedPacket(RedPacket redPacket);

    public int deleteRedPacket(RedPacket redPacket);

    public int updateRedPacket(RedPacket redPacket);

    public RedPacket selectRedPacketById(Long redPacketId, String userId, Boolean isLock);

    public int selectShowRedPacketCount(String userId);

}
