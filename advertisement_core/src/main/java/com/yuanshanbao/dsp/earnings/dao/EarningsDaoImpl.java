package com.yuanshanbao.dsp.earnings.dao;

import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.earnings.model.Earnings;
import com.yuanshanbao.paginator.domain.PageBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/10/26.
 */
@Repository
public class EarningsDaoImpl extends BaseDaoImpl implements EarningsDao {


    @Override
    public List<Earnings> selectEarnings(Earnings earnings, PageBounds pageBounds) {
        return getSqlSession().selectList("earnings.selectEarnings",earnings,pageBounds);
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
        return getSqlSession().selectOne("earnings.getCountProductId",userId);
    }
}
