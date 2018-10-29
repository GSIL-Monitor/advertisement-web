package com.yuanshanbao.dsp.redpacket.dao;

import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.redpacket.model.RedPacketSequence;
import org.springframework.stereotype.Repository;

/**
 * Created by Kyle Zhang on 2016/10/24.
 */
@Repository
public class RedPacketSequenceDaoImpl extends BaseDaoImpl implements RedPacketSequenceDao {

    @Override
    public long generateRedPacketSeq() {
        RedPacketSequence redPacketSequence = new RedPacketSequence();
        redPacketSequence.setStub("a");
        getSqlSession().insert("FollowSequence.insertFollowSeq", redPacketSequence);
        Long followSeq = redPacketSequence.getRedPacketSeq();
        return followSeq;
    }
}
