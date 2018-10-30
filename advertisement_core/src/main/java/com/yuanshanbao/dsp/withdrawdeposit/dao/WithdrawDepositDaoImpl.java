package com.yuanshanbao.dsp.withdrawdeposit.dao;

import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.withdrawdeposit.model.WithdrawDeposit;
import com.yuanshanbao.paginator.domain.PageBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/10/26.
 */
@Repository
public class WithdrawDepositDaoImpl extends BaseDaoImpl implements WithdrawDepositDao {

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
