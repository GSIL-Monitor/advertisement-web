package com.yuanshanbao.dsp.bankcard.service;

import com.yuanshanbao.dsp.bankcard.model.BankCard;
import com.yuanshanbao.paginator.domain.PageBounds;

import java.util.List;

/**
 * Created by Administrator on 2018/10/23.
 */
public interface BankCardService {

    public List<BankCard> selectBankCards(BankCard bankCard, PageBounds pageBounds);

    public List<BankCard> selectUserApplys(BankCard bankCard, PageBounds pageBounds);

    public void insertBankCard(BankCard bankCard);

}
