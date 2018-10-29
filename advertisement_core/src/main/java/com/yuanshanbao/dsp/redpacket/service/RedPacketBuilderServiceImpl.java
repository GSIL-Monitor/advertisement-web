package com.yuanshanbao.dsp.redpacket.service;


import com.yuanshanbao.common.util.CommonUtil;
import com.yuanshanbao.dsp.redpacket.dao.RedPacketSequenceDao;
import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class RedPacketBuilderServiceImpl implements RedPacketIdBuilderService {

    @Autowired
    RedPacketSequenceDao redPacketSequenceDao;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public long generateRedPacketId(String userId, Date date) {
        long seq = redPacketSequenceDao.generateRedPacketSeq();
        String timePrefix = DateUtil.formatDate(new Date(), DateUtil.PATTERN_RFC1123);
        long id = Long.parseLong(timePrefix + CommonUtil.formatSequence(seq) + userId.substring(userId.length() - 2));
        return id;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public long generateRedPacketId(String userId) {
        return generateRedPacketId(userId, new Date());
    }
}
