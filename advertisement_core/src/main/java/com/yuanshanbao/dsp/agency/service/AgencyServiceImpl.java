package com.yuanshanbao.dsp.agency.service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.StringUtil;
import com.yuanshanbao.dsp.agency.dao.AgencyDao;
import com.yuanshanbao.dsp.agency.model.Agency;
import com.yuanshanbao.dsp.agency.model.vo.AgencyStatus;
import com.yuanshanbao.dsp.agency.model.vo.AgencyVo;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.dsp.user.model.UserLevel;
import com.yuanshanbao.dsp.user.service.UserService;
import com.yuanshanbao.paginator.domain.PageBounds;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public Agency selectAgency(String userId) {
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
                    AgencyVo agencyVo = new AgencyVo();
                    agencyVo.setUserId(agen.getUserId());
                    agencyVo.setInviteTime(agen.getInviteTimeValue());
                    agencyVo.setAgencyName(agencyUser.getNickName());
                    agencyVo.setUserLevel(agencyUser.getLevelValue());
                    agencyVo.setInviteUserLevel(userService.selectUserById(agen.getInviteUserId()).getLevelValue());
                    agencyVoList.add(agencyVo);
                    LoggerUtil.info("agencyVo success" +agencyVoList);
            }
        }else {
            LoggerUtil.info("agencyVo error" + agencyList );
           return agencyVoList;

        }

        return agencyVoList;
    }

    @Override
    public BigDecimal getBrokerages(Agency agency, PageBounds pageBounds) {
        return getSumBrokerage(agency,pageBounds);
    }



    public BigDecimal getSumBrokerage(Agency agency, PageBounds pageBounds) {
        List<Agency> oneAgencyList = agencyDao.selectAgencys(agency, pageBounds);

        List<Long> oneInviteUserIds = new ArrayList<>();
        List<Long> twoInviteUserIds = new ArrayList<>();
        BigDecimal oneAgencyBrokerage = BigDecimal.valueOf(0);
        BigDecimal twoAgencyBrokerage = BigDecimal.valueOf(0);
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
            for (Agency agencyIds : twoAgencyList) {
                twoInviteUserIds.add(Long.valueOf(agencyIds.getUserId()));
            }
        }
        if (twoInviteUserIds.size() != 0){
            twoAgencyBrokerage = agencyDao.getSumBrokerage(twoInviteUserIds);
            if (twoAgencyBrokerage == null){
                twoAgencyBrokerage = BigDecimal.valueOf(0);
            }
        }
        if (oneAgencyBrokerage == null) {
        	oneAgencyBrokerage = BigDecimal.ZERO;
        }
        if (twoAgencyBrokerage == null) {
        	twoAgencyBrokerage = BigDecimal.ZERO;
        }
        BigDecimal sumAgencyBrokerage = oneAgencyBrokerage.add(twoAgencyBrokerage);
        return sumAgencyBrokerage;
    }


}
