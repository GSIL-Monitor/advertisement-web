package com.yuanshanbao.dsp.message.service;

import com.yuanshanbao.dsp.merchant.model.Merchant;
import com.yuanshanbao.dsp.message.model.Message;
import com.yuanshanbao.paginator.domain.PageBounds;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/10/26.
 */
@Service
public class MessageServiceImpl implements MessageService {


    @Override
    public List<Merchant> selectMerchants(Message message, PageBounds pageBounds) {
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
