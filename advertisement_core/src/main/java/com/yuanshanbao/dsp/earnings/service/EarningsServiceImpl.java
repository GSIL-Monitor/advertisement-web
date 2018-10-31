package com.yuanshanbao.dsp.earnings.service;

import com.yuanshanbao.dsp.earnings.dao.EarningsDao;
import com.yuanshanbao.dsp.earnings.model.Earnings;
import com.yuanshanbao.paginator.domain.PageBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/10/29.
 */
@Service
public class EarningsServiceImpl implements EarningsService{
    @Autowired
    private EarningsDao earningsDao;
    @Override
    public List<Earnings> selectEarnings(Earnings earnings, PageBounds pageBounds) {
        return earningsDao.selectEarnings(earnings, pageBounds);
    }

    @Override
    public int insertEarnings(Earnings message) {
        return 0;
    }

    @Override
    public int deleteEarnings(Long id) {
        return 0;
    }

    @Override
    public int updateEarnings(Earnings withdrawDeposit) {
        return 0;
    }

    @Override
    public int selectCountProuctIds(Long userId) {
        if (userId == 0) {
            return 0;
        }
        return earningsDao.selectCountProuctIds(userId);
    }
}
