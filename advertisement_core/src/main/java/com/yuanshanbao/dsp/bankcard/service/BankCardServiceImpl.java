package com.yuanshanbao.dsp.bankcard.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.agency.model.vo.AgencyVo;
import com.yuanshanbao.dsp.user.model.UserLevel;
import com.yuanshanbao.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.agency.model.Agency;
import com.yuanshanbao.dsp.agency.model.vo.AgencyStatus;
import com.yuanshanbao.dsp.agency.service.AgencyService;
import com.yuanshanbao.dsp.bankcard.dao.BankCardDao;
import com.yuanshanbao.dsp.bankcard.model.BankCard;
import com.yuanshanbao.dsp.information.dao.InformationDao;
import com.yuanshanbao.dsp.product.dao.ProductDao;
import com.yuanshanbao.dsp.product.model.Product;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.dsp.user.service.UserService;
import com.yuanshanbao.paginator.domain.PageBounds;

/**
 * Created by Administrator on 2018/10/23.
 */
@Service
public class BankCardServiceImpl implements BankCardService {

    @Autowired
    private BankCardDao bankCardDao;

    @Autowired
    private UserService userService;

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private ProductDao productDao;

    @Override
    public List<BankCard> selectBankCards(BankCard bankCard, PageBounds pageBounds) {
        return bankCardDao.selectBankCards(bankCard, pageBounds);
    }

    @Override
    public List<BankCard> selectUserApplys(BankCard bankCard, PageBounds pageBounds) {
        return null;
    }

    @Override
    public int insertBankCard(BankCard bankCard) {
        int result = -1;
        result = bankCardDao.insertBankCard(bankCard);
        if (result < 0) {
            throw new BusinessException(ComRetCode.FAIL);
        } else {
            LoggerUtil.info("insertBankCard : number:" + result);
            return result;
        }

    }

    @Override
    public void getApplyBankCardInfo(User user, Long productId, String userName, String mobile) {
        Product product = productDao.selectPrdouctById(productId);
        Agency param = new Agency();
        param.setProductId(productId);
        param.setUserId(user.getUserId());
        List<Agency> agencyList = agencyService.selectAgencys(param, new PageBounds());
        User inviteUser = userService.selectUserById(user.getInviteUserId());
        if (agencyList == null || agencyList.size() == 0) {
            // 添加用户信息
            Agency agency = new Agency();
            agency.setUserId(user.getUserId());
            agency.setInviteUserId(user.getInviteUserId());
            if (inviteUser != null) {
                agency.setAgencyName(inviteUser.getDisplayName());
            }
            agency.setMobile(mobile);
            agency.setStatus(AgencyStatus.ONCHECK);
            agency.setProductName(product.getName());
            agency.setProductId(productId);
            agency.setName(userName);
            if (inviteUser.getLevel() == null) {
            	inviteUser.setLevel(UserLevel.MANAGER);
            }
            if (inviteUser != null && inviteUser.getLevel() == UserLevel.MANAGER){
                agency.setBrokerage(product.getBrokerage().multiply(BigDecimal.valueOf(0.85)));
            }else if (inviteUser != null && inviteUser.getLevel() == UserLevel.MAJORDOMO){
                agency.setBrokerage(product.getBrokerage().multiply(BigDecimal.valueOf(0.9)));
            }else if (inviteUser != null && inviteUser.getLevel() == UserLevel.BAILLIFF){
                agency.setBrokerage(product.getBrokerage());
            }else{
                agency.setBrokerage(product.getBrokerage().multiply(BigDecimal.valueOf(0.85)));
            }
            agencyService.insertAgency(agency);
        } else {
            Agency updateAgency = new Agency();
            updateAgency.setId(agencyList.get(0).getId());
            updateAgency.setUserId(agencyList.get(0).getUserId());
            updateAgency.setProductId(agencyList.get(0).getProductId());
            agencyService.updateAgency(updateAgency);
        }

    }

    @Override
    public List<AgencyVo> getAgencyInfo(List<Agency> agencyList, PageList<BankCard> pageList) {
        List<AgencyVo> oneAgencyVoList = new ArrayList<>();
        for (Agency agen: agencyList){
            for (BankCard page : pageList){
                AgencyVo agencyVo = new AgencyVo();
                if (agen.getHideMobile().equals(page.getMobile())  && agen.getName().equals(page.getName()) && agen.getProductName().equals(page.getBankName())){
                    Long inviteUserId = agen.getInviteUserId();
                    if (inviteUserId != null){
                        agencyVo.setBrokerage(agen.getBrokerage());
                        agencyVo.setName(agen.getName());
                        agencyVo.setProductName(agen.getProductName());
                        agencyVo.setMobile(agen.getMobile());
                        oneAgencyVoList.add(agencyVo);
                    }
                }
            }
        }
        return  oneAgencyVoList;
    }
}
