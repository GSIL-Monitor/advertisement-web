package com.yuanshanbao.dsp.withdrawdeposit.dao;

import com.yuanshanbao.dsp.withdrawdeposit.model.WithdrawDeposit;
import com.yuanshanbao.paginator.domain.PageBounds;

import java.util.List;

/**
 * Created by Administrator on 2018/10/26.
 */
public interface WithdrawDepositDao {
    public List<WithdrawDeposit> selectWithdrawDeposits(WithdrawDeposit withdrawDeposit, PageBounds pageBounds);

    public int insertWithdrawDeposit(WithdrawDeposit message);

    public int deleteWithdrawDeposit(Long id);

    public int updateWithdrawDeposit(WithdrawDeposit withdrawDeposit);
}
