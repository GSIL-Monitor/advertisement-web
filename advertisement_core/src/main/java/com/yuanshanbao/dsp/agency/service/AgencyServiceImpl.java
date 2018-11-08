package com.yuanshanbao.dsp.agency.service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.agency.dao.AgencyDao;
import com.yuanshanbao.dsp.agency.model.Agency;
import com.yuanshanbao.paginator.domain.PageBounds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/10/22.
 */
@Service
public class AgencyServiceImpl implements AgencyService {

    @Autowired
    private AgencyDao agencyDao;

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
        return 0;
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
    public List<Agency> selectAgencyByInviteId(Long inviteId) {
        if (inviteId > 0) {
            agencyDao.selectAgencysByInviteId(inviteId);
        }
        return null;
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
        BigDecimal sumAgencyBrokerage = oneAgencyBrokerage.add(twoAgencyBrokerage);
        return sumAgencyBrokerage;
    }


}
