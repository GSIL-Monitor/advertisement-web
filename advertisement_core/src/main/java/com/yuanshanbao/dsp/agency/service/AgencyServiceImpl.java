package com.yuanshanbao.dsp.agency.service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.agency.dao.AgencyDao;
import com.yuanshanbao.dsp.agency.model.Agency;
import com.yuanshanbao.dsp.agency.model.vo.AgencyVo;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.dsp.user.model.UserLevel;
import com.yuanshanbao.dsp.user.service.UserService;
import com.yuanshanbao.paginator.domain.PageBounds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Created by Administrator on 2018/10/22.
 */
@Service
public class AgencyServiceImpl implements AgencyService {

    @Autowired
    private AgencyDao agencyDao;

    @Autowired
    private UserService userService;

    @Override
    public List<Agency> selectAgencys(Agency agency, PageBounds pageBounds) {
        return agencyDao.selectAgencys(agency, pageBounds);
    }

    @Override
    public List<Agency>  selectAgency(Long userId) {
        if (userId == null) {
            throw new BusinessException(ComRetCode.FAIL);
        }
        return agencyDao.selectAgency(userId);
    }


    @Override
    public int insertAgency(Agency agency) {
        return agencyDao.insertAgency(agency);
    }

    @Override
    public int deleteAgency(String Id) {
        return 0;
    }

    @Override
    public int updateAgency(Agency agency) {
        if (agency == null && agency.getUserId() == null) {
            throw new BusinessException(ComRetCode.FAIL);
        }
        return agencyDao.updateAgency(agency);

    }

    @Override
    public int updateBankTime(Agency agency) {
        int result = -1;
        result = agencyDao.updateBankTime(agency);
        if (result < 0) {
            throw new BusinessException(ComRetCode.FAIL);
        }
        return result;
    }

    @Override
    public List<Agency> selectAgencys(List<Long> IdList) {
        return null;
    }

    @Override
    public BigDecimal getAgencyBrokerage(Long inviteUserId) {


        if (inviteUserId == null && inviteUserId == 0)
            throw new BusinessException();

        return agencyDao.getAgencyBrokerage(inviteUserId);
    }

    @Override
    public int selectAgencyByInviteId(Long inviteUserId) {
       return  agencyDao.selectAgencyByInviteId(inviteUserId);
    }

    @Override
    public int selectAgencyByInviteIdCount(Long inviteUserId) {
        return agencyDao.selectAgencyByInviteIdCount(inviteUserId);
    }

    @Override
    public List<AgencyVo> getAgencyInfos(User user,Agency agency,PageBounds pageBounds) {

        List<AgencyVo> agencyVoList = new LinkedList<>();
        try{
            agency.setInviteUserId(user.getUserId());
            List<Agency> agencyList = agencyDao.selectAgencys(agency, pageBounds);

            for (Iterator iterator = agencyList.iterator(); iterator.hasNext(); ) {
                Agency agen = (Agency) iterator.next();
                if (agen.getProductId() != null) {
                    iterator.remove();
                }
            }
            if (agencyList.size() != 0){
                for (Agency agen : agencyList) {
                    User agencyUser =  userService.selectUserById(agen.getUserId());
                    User inviteUser = userService.selectUserById(agen.getInviteUserId());

                    AgencyVo agencyVo = new AgencyVo();
                    agencyVo.setUserId(agen.getUserId());
                    agencyVo.setInviteTime(agen.getInviteTimeValue());
                    agencyVo.setAgencyName(agencyUser.getNickName());
                    agencyVo.setUserLevel(agencyUser.getLevelValue());
                    if (inviteUser != null) {
                        agencyVo.setInviteUserLevel(inviteUser.getLevelValue());
                    }else {
                        agencyVo.setInviteUserLevel(UserLevel.NULL_DESCRIPTION);
                    }
                    agencyVoList.add(agencyVo);
                    LoggerUtil.info("agencyVo success" +agen.getUserId());
                }
            }else {
                LoggerUtil.info("agencyVo error" + agencyList );
                return agencyVoList;

            }
        } catch (Exception e) {
            LoggerUtil.error("[productList error:]" + agencyVoList);
        }


        return agencyVoList;
    }

    @Override
    public List<AgencyVo> getAgencyListVo(List<Agency> twoAgencyList, User user) {

        List<AgencyVo> agencyVoList = new ArrayList<>();
        for (Agency agen : twoAgencyList) {
            AgencyVo agencyVo = new AgencyVo();
            agencyVo.setName(agen.getName());
            agencyVo.setProductName(agen.getProductName());
            agencyVo.setUpdateTime(agen.getUpdateTimeValue());
            agencyVo.setStatus(agen.getStatusValue());

            if (user.getLevel() != null && user.getLevel() == UserLevel.MANAGER) {
                agencyVo.setBrokerage((agen.getBrokerage().multiply(BigDecimal.valueOf(0.1))).setScale(2, RoundingMode.HALF_UP));
            } else if (user.getLevel() != null && user.getLevel() == UserLevel.MAJORDOMO) {
                agencyVo.setBrokerage((agen.getBrokerage().multiply(BigDecimal.valueOf(0.15))).setScale(2, RoundingMode.HALF_UP));
            } else if (user.getLevel() != null && user.getLevel() == UserLevel.BAILLIFF) {
                agencyVo.setBrokerage((agen.getBrokerage().multiply(BigDecimal.valueOf(0.2))).setScale(2, RoundingMode.HALF_UP));
            } else {
                agencyVo.setBrokerage((agen.getBrokerage().multiply(BigDecimal.valueOf(0.1))).setScale(2, RoundingMode.HALF_UP));
            }
            agencyVoList.add(agencyVo);
        }
        return agencyVoList;
    }



    @Override
    public BigDecimal getBrokerages(Agency agency,User user,PageBounds pageBounds) {
        return getSumBrokerage(agency,pageBounds,user);
    }



    public BigDecimal getSumBrokerage(Agency agency, PageBounds pageBounds,User user) {
        List<Agency> oneAgencyList = agencyDao.selectAgencys(agency, pageBounds);

        List<Long> oneInviteUserIds = new ArrayList<>();
        List<Long> twoInviteUserIds = new ArrayList<>();
        BigDecimal oneAgencyBrokerage = BigDecimal.valueOf(0);
        BigDecimal twoAgencyBrokerage = BigDecimal.valueOf(0);
        BigDecimal brokerage = BigDecimal.valueOf(0);
        for (Agency agencyIds : oneAgencyList) {
            if (oneAgencyList.size() == 0){
                break;
            }
            oneInviteUserIds.add(Long.valueOf(agencyIds.getUserId()));
        }
        if (oneInviteUserIds.size() != 0){
            oneAgencyBrokerage = agencyDao.getSumBrokerage(oneInviteUserIds);
        }
        List<Agency> twoAgencyList = new ArrayList<>();
        for (Agency agen : oneAgencyList) {
            if (oneAgencyList.size() == 0){
                break;
            }
            agency.setInviteUserId(agen.getUserId());
            twoAgencyList = agencyDao.selectAgencys(agency, new PageBounds());
        }
        for (Agency agencyIds : twoAgencyList) {
            twoInviteUserIds.add(Long.valueOf(agencyIds.getUserId()));
        }
        if (twoInviteUserIds.size() != 0){
            twoAgencyBrokerage = agencyDao.getSumBrokerage(twoInviteUserIds);
            if (twoAgencyBrokerage == null) {
                twoAgencyBrokerage = BigDecimal.ZERO;
            }
            if (user!= null &&  user.getLevel() == UserLevel.MANAGER){
                brokerage = twoAgencyBrokerage.multiply(BigDecimal.valueOf(0.1));
            }else if (user != null && user.getLevel() == UserLevel.MAJORDOMO){
                 brokerage = twoAgencyBrokerage.multiply(BigDecimal.valueOf(0.15));
            }else if (user != null && user.getLevel() == UserLevel.BAILLIFF){
                 brokerage = twoAgencyBrokerage.multiply(BigDecimal.valueOf(0.2));
            }else {
                 brokerage = twoAgencyBrokerage.multiply(BigDecimal.valueOf(0.1));
            }

            if (brokerage == null){
                brokerage = BigDecimal.valueOf(0);
            }
        }
        if (oneAgencyBrokerage == null) {
        	oneAgencyBrokerage = BigDecimal.ZERO;
        }
        if (brokerage == null) {
            brokerage = BigDecimal.ZERO;
        }
        BigDecimal sumAgencyBrokerage = oneAgencyBrokerage.add(brokerage);
        return sumAgencyBrokerage;
    }


}
