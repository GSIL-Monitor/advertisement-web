package com.yuanshanbao.dsp.redpacket.dao;


import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.redpacket.model.RedPacket;
import com.yuanshanbao.paginator.domain.PageBounds;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RedPacketDaoImpl extends BaseDaoImpl implements RedPacketDao {

    @Override
    public List<RedPacket> selectRedPackets(RedPacket redPacket, PageBounds pageBounds) {
        return getSqlSession().selectList("RedPacket.selectRedPacket", redPacket, pageBounds);
    }

    @Override
    public int insertRedPacket(RedPacket redPacket) {
        return getSqlSession().insert("RedPacket.insertRedPacket", redPacket);
    }

    @Override
    public int deleteRedPacket(RedPacket redPacket) {
        return getSqlSession().delete("RedPacket.deleteRedPacket", redPacket);
    }

    @Override
    public int updateRedPacket(RedPacket redPacket) {
        return getSqlSession().update("RedPacket.updateRedPacket", redPacket);
    }

    @Override
    public RedPacket selectRedPacketById(Long redPacketId, String userId, Boolean isLock) {
        Map<String, Object> params = new HashMap<>();
        params.put("redPacketId", redPacketId);
        params.put("userId", userId);
        params.put("isLock", isLock);
        return getSqlSession().selectOne("RedPacket.selectRedPacketById", params);
    }

    @Override
    public int selectShowRedPacketCount(String userId) {
        return getSqlSession().selectOne("RedPacket.selectShowRedPacketCount", userId);
    }

}