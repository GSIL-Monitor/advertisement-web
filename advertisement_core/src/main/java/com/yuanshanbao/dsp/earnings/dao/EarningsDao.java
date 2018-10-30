package com.yuanshanbao.dsp.earnings.dao;

import com.yuanshanbao.dsp.earnings.model.Earnings;
import com.yuanshanbao.paginator.domain.PageBounds;

import java.util.List;

/**
 * Created by Administrator on 2018/10/26.
 */
public interface EarningsDao {
    public List<Earnings> selectEarnings(Earnings earnings, PageBounds pageBounds);

    public int insertEarnings(Earnings message);

    public int deleteEarnings(Long id);

    public int updateEarnings(Earnings withdrawDeposit);
}
