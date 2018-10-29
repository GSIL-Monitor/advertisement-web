package com.yuanshanbao.dsp.redpacket.service;

import java.util.Date;

/**
 * Created by Kyle Zhang on 2016/10/24.
 */
public interface RedPacketIdBuilderService {
    long generateRedPacketId(String userId, Date date);

    long generateRedPacketId(String userId);
}
