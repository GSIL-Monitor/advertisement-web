package com.yuanshanbao.dsp.withdrawdeposit.service;

import com.yuanshanbao.dsp.withdrawdeposit.dao.WithdrawDepositDao;
import com.yuanshanbao.dsp.withdrawdeposit.model.WithdrawDeposit;
import com.yuanshanbao.paginator.domain.PageBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/10/26.
 */
@Service
public class WithdrawDepositServiceImpl implements WithdrawDepositService {
    @Autowired
    private WithdrawDepositDao withdrawDepositDao;
    @Override
    public List<WithdrawDeposit> selectWithdrawDeposits(WithdrawDeposit withdrawDeposit, PageBounds pageBounds) {
        return withdrawDepositDao.selectWithdrawDeposits(withdrawDeposit,pageBounds);
    }

    @Override
    public int insertWithdrawDeposit(WithdrawDeposit message) {
        return 0;
    }

    @Override
    public int deleteWithdrawDeposit(Long id) {
        return 0;
    }

    @Override
    public int updateWithdrawDeposit(WithdrawDeposit withdrawDeposit) {
        return 0;
    }
}
