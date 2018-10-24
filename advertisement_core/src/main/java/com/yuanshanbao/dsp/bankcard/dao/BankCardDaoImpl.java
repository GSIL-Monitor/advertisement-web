package com.yuanshanbao.dsp.bankcard.dao;

import com.yuanshanbao.dsp.bankcard.model.BankCard;
import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.paginator.domain.PageBounds;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2018/10/23.
 */
@Repository
public class BankCardDaoImpl extends BaseDaoImpl implements BankCardDao{
    @Override
    public List<BankCard> selectBankCards(BankCard bankCard, PageBounds pageBounds) {
        return getSqlSession().selectList("bankCard.selectBankCards",bankCard,pageBounds);
    }

    @Override
    public List<BankCard> selectUserApplys(BankCard bankCard, PageBounds pageBounds) {
        return null;
    }

    @Override
    public int insertBankCard(BankCard bankCard) {
        return getSqlSession().insert("bankCard.insetBankCard",bankCard);
    }
}
