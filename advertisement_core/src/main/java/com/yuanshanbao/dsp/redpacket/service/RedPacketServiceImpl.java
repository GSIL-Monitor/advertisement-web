package com.yuanshanbao.dsp.redpacket.service;


import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.redpacket.dao.RedPacketDao;
import com.yuanshanbao.dsp.redpacket.model.RedPacket;
import com.yuanshanbao.dsp.redpacket.model.RedPacketStrategy;
import com.yuanshanbao.paginator.domain.PageBounds;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RedPacketServiceImpl implements RedPacketService {

    @Autowired
    private RedPacketDao redPacketDao;

    @Autowired
    private RedPacketStrategyService strategyService;

    @Override
    public List<RedPacket> selectRedPackets(RedPacket redPacket, PageBounds pageBounds) {
        return setProperties(redPacketDao.selectRedPackets(redPacket, pageBounds));
    }

    private List<RedPacket> setProperties(List<RedPacket> redPacketList) {
        List<Long> strategyIdList = new ArrayList<Long>();
        for (RedPacket redPacket : redPacketList) {
            strategyIdList.add(redPacket.getStrategyId());
        }
        Map<Long, RedPacketStrategy> map = strategyService.selectRedPacketStrategy(strategyIdList);
        for (RedPacket redPacket : redPacketList) {
            redPacket.setStrategy(map.get(redPacket.getStrategyId()));
        }
        return redPacketList;
    }

    @Override
    public RedPacket selectRedPacket(Long userId, Long redPacketId) {
        if (redPacketId == null) {
            return null;
        }
        RedPacket redPacket = new RedPacket();
        redPacket.setUserId(userId);
        redPacket.setRedPacketId(redPacketId);
        List<RedPacket> list = selectRedPackets(redPacket, new PageBounds());
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<RedPacket> selectAvailableRedPackets(String userId) {
        if (StringUtils.isBlank(userId)) {
            return new ArrayList<RedPacket>();
        }
        RedPacket param = new RedPacket();
        param.setUserId(Long.parseLong(userId));
        return selectRedPackets(param, new PageBounds());
    }

    @Override
    public void insertRedPacket(RedPacket redPacket) {
        int result = -1;

        result = redPacketDao.insertRedPacket(redPacket);

        if (result < 0) {
            throw new BusinessException(ComRetCode.FAIL);
        }
    }

    @Override
    public void deleteRedPacket(Long redPacketId) {
        RedPacket redPacket = new RedPacket();
        redPacket.setRedPacketId(redPacketId);
        deleteRedPacket(redPacket);
    }

    @Override
    public void deleteRedPacket(RedPacket redPacket) {
        int result = -1;

        result = redPacketDao.deleteRedPacket(redPacket);

        if (result < 0) {
            throw new BusinessException(ComRetCode.FAIL);
        }
    }

    @Override
    public void updateRedPacket(RedPacket redPacket) {
        int result = -1;

        result = redPacketDao.updateRedPacket(redPacket);

        if (result < 0) {
            throw new BusinessException(ComRetCode.FAIL);
        }
    }

    /**
     * @param redPacket
     * @return
     * @throws IOException
     */
    @Override
    public void insertOrUpdateRedPacket(RedPacket redPacket) {
        if (redPacket.getRedPacketId() == null) {
            insertRedPacket(redPacket);
        } else {
            updateRedPacket(redPacket);
        }
    }

    @Override
    public BigDecimal checkRedPacketAmount(String userId, Long gameId, Long redPacketId, BigDecimal orderAmount) {
        if (redPacketId == null) {
            return BigDecimal.ZERO;
        }
        RedPacket redPacket = selectRedPacket(Long.parseLong(userId), redPacketId);
        checkRedPacketAvailable(redPacket, gameId, orderAmount);
        if (redPacket.getBalance().compareTo(orderAmount) < 0) {
            return redPacket.getBalance();
        } else {
            return orderAmount;
        }
    }

    @Override
    public void checkRedPacketAvailable(RedPacket redPacket, Long gameId, BigDecimal orderAmount) {

    }

    @Override
    public int selectShowRedPacketCount(String userId) {
        return redPacketDao.selectShowRedPacketCount(userId);
    }

}
