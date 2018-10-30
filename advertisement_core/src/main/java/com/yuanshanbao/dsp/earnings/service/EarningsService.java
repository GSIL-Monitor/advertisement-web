package com.yuanshanbao.dsp.earnings.service;

import com.yuanshanbao.dsp.earnings.model.Earnings;
import com.yuanshanbao.paginator.domain.PageBounds;

import java.util.List;

/**
 * Created by Administrator on 2018/10/29.
 */
public interface EarningsService {
    public List<Earnings> selectEarnings(Earnings earnings, PageBounds pageBounds);

    public int insertEarnings(Earnings message);

    public int deleteEarnings(Long id);

    public int updateEarnings(Earnings withdrawDeposit);

    int selectCountProuctIds(Long userId);
}
