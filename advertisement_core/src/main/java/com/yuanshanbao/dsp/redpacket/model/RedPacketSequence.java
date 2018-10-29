package com.yuanshanbao.dsp.redpacket.model;

/**
 * Created by Kyle Zhang on 2016/10/24.
 */
public class RedPacketSequence {
    private long redPacketSeq;
    private String stub;

    public long getRedPacketSeq() {
        return redPacketSeq;
    }

    public void setFollowSeq(long redPacketSeq) {
        this.redPacketSeq = redPacketSeq;
    }

    public String getStub() {
        return stub;
    }

    public void setStub(String stub) {
        this.stub = stub;
    }

}
