package com.yuanshanbao.dsp.withdrawdeposit.service;

import com.yuanshanbao.dsp.withdrawdeposit.model.WithdrawDeposit;
import com.yuanshanbao.paginator.domain.PageBounds;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/10/26.
 */
@Service
public class MessageServiceImpl implements WithdrawDepositService {

    @Override
    public List<WithdrawDeposit> selectWithdrawDeposits(WithdrawDeposit withdrawDeposit, PageBounds pageBounds) {
        return null;
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
