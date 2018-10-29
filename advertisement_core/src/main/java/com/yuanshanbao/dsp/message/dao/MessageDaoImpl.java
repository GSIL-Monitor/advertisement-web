package com.yuanshanbao.dsp.message.dao;

import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.merchant.model.Merchant;
import com.yuanshanbao.paginator.domain.PageBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/10/26.
 */
@Repository
public class MessageDaoImpl extends BaseDaoImpl implements MessageDao{
    @Override
    public List<Merchant> selectMerchants(Merchant merchant, PageBounds pageBounds) {
        return null;
    }

    @Override
    public int insertMerchant(Merchant merchant) {
        return 0;
    }

    @Override
    public int deleteMerchant(Long merchantId) {
        return 0;
    }

    @Override
    public int updateMerchant(Merchant merchant) {
        return 0;
    }
}
