package com.yuanshanbao.dsp.redpacket.service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.redpacket.dao.RedPacketStrategyDao;
import com.yuanshanbao.dsp.redpacket.model.RedPacketStrategy;
import com.yuanshanbao.paginator.domain.PageBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RedPacketStrategyServiceImpl implements RedPacketStrategyService {

    @Autowired
    private RedPacketStrategyDao redPacketStrategyDao;

    @Override
    public List<RedPacketStrategy> selectRedPacketStrategys(RedPacketStrategy redPacketStrategy, PageBounds pageBounds) {
        return setProperties(redPacketStrategyDao.selectRedPacketStrategys(redPacketStrategy, pageBounds));
    }

    private List<RedPacketStrategy> setProperties(List<RedPacketStrategy> redPacketStrategyList) {
        return null;
    }

    @Override
    public RedPacketStrategy selectRedPacketStrategy(Long strategyId) {
        RedPacketStrategy redPacketStrategy = new RedPacketStrategy();
        redPacketStrategy.setStrategyId(strategyId);
        List<RedPacketStrategy> list = selectRedPacketStrategys(redPacketStrategy, new PageBounds());
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public void insertRedPacketStrategy(RedPacketStrategy redPacketStrategy) {
        int result = -1;

        result = redPacketStrategyDao.insertRedPacketStrategy(redPacketStrategy);

        if (result < 0) {
            throw new BusinessException(ComRetCode.FAIL);
        }
    }

    @Override
    public void deleteRedPacketStrategy(Long strategyId) {
        RedPacketStrategy redPacketStrategy = new RedPacketStrategy();
        redPacketStrategy.setStrategyId(strategyId);
        deleteRedPacketStrategy(redPacketStrategy);
    }

    @Override
    public void deleteRedPacketStrategy(RedPacketStrategy redPacketStrategy) {
        int result = -1;

        result = redPacketStrategyDao.deleteRedPacketStrategy(redPacketStrategy);

        if (result < 0) {
            throw new BusinessException(ComRetCode.FAIL);
        }
    }

    @Override
    public void updateRedPacketStrategy(RedPacketStrategy redPacketStrategy) {
        int result = -1;

        result = redPacketStrategyDao.updateRedPacketStrategy(redPacketStrategy);

        if (result < 0) {
            throw new BusinessException(ComRetCode.FAIL);
        }
    }

    /**
     * @param redPacketStrategy
     * @return
     * @throws IOException
     */
    @Override
    public void insertOrUpdateRedPacketStrategy(RedPacketStrategy redPacketStrategy) {
        if (redPacketStrategy.getStrategyId() == null) {
            insertRedPacketStrategy(redPacketStrategy);
        } else {
            updateRedPacketStrategy(redPacketStrategy);
        }
    }

    @Override
    public Map<Long, RedPacketStrategy> selectRedPacketStrategy(List<Long> strategyIdList) {
        Map<Long, RedPacketStrategy> map = new HashMap<Long, RedPacketStrategy>();
        if (strategyIdList == null || strategyIdList.size() == 0) {
            return map;
        }
        List<RedPacketStrategy> list = setProperties(redPacketStrategyDao.selectRedPacketStrategyByIds(strategyIdList));
        for (RedPacketStrategy strategy : list) {
            map.put(strategy.getStrategyId(), strategy);
        }
        return map;
    }

}
