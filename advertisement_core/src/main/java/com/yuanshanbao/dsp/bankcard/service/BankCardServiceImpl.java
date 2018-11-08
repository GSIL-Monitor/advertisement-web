package com.yuanshanbao.dsp.bankcard.service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.agency.dao.AgencyDao;
import com.yuanshanbao.dsp.agency.model.Agency;
import com.yuanshanbao.dsp.agency.model.vo.AgencyStatus;
import com.yuanshanbao.dsp.bankcard.dao.BankCardDao;
import com.yuanshanbao.dsp.bankcard.model.BankCard;
import com.yuanshanbao.dsp.information.dao.InformationDao;
import com.yuanshanbao.dsp.information.model.Information;
import com.yuanshanbao.dsp.product.dao.ProductDao;
import com.yuanshanbao.dsp.product.model.Product;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.paginator.domain.PageBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2018/10/23.
 */
@Service
public class BankCardServiceImpl implements BankCardService {
    @Autowired
    private BankCardDao bankCardDao;
    @Autowired
    private InformationDao informationDao;
    @Autowired
    private AgencyDao agencyDao;

    @Autowired
    private ProductDao productDao;

    @Override
    public List<BankCard> selectBankCards(BankCard bankCard, PageBounds pageBounds) {
        return null;
    }

    @Override
    public List<BankCard> selectUserApplys(BankCard bankCard, PageBounds pageBounds) {
        return null;
    }

    @Override
    public void insertBankCard(BankCard bankCard) {
        int result = -1;
        result = bankCardDao.insertBankCard(bankCard);
        if (result < 0) {
            throw new BusinessException(ComRetCode.FAIL);
        }
    }

    @Override
    public void getApplyBankCardInfo(Long userId, Long productId, String userName, String mobile) {
        Information queryInfomation = new Information();
        queryInfomation.setName(userName);
        queryInfomation.setMobile(mobile);
        Product product = productDao.selectPrdouctById(productId);
        Agency agencyUser = agencyDao.selectAgency(String.valueOf(userId));
        if (agencyUser != null) {
            //添加用户信息
            Agency agency = new Agency();
            agency.setUserId(userId);
            agency.setInviteUserId(agencyUser.getInviteUserId());
            agency.setMobile(agencyUser.getMobile());
            agency.setStatus(AgencyStatus.ONCHECK);
            agency.setProductName(product.getName());
            agency.setProductId(productId);
            agency.setAgencyName(userName);
            agency.setBrokerage(product.getBrokerage().multiply(BigDecimal.valueOf(0.85)));
            agencyDao.insertAgency(agencyUser);
        }

    }
}
