package com.yuanshanbao.dsp.message.dao;

import com.yuanshanbao.dsp.merchant.model.Merchant;
import com.yuanshanbao.paginator.domain.PageBounds;

import java.util.List;

/**
 * Created by Administrator on 2018/10/26.
 */
public interface MessageDao {
    public List<Merchant> selectMerchants(Merchant merchant, PageBounds pageBounds);

    public int insertMerchant(Merchant merchant);

    public int deleteMerchant(Long merchantId);

    public int updateMerchant(Merchant merchant);
}
