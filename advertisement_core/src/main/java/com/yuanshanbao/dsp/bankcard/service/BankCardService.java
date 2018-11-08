package com.yuanshanbao.dsp.bankcard.service;

import java.util.List;

import com.yuanshanbao.dsp.bankcard.model.BankCard;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.paginator.domain.PageBounds;

/**
 * Created by Administrator on 2018/10/23.
 */
public interface BankCardService {

	public List<BankCard> selectBankCards(BankCard bankCard, PageBounds pageBounds);

	public List<BankCard> selectUserApplys(BankCard bankCard, PageBounds pageBounds);

	public void insertBankCard(BankCard bankCard);

	public void getApplyBankCardInfo(User user, Long productId, String userName, String mobile);
}
