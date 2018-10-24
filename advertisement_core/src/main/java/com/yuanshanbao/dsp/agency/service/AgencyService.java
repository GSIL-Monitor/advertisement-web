package com.yuanshanbao.dsp.agency.service;

import com.yuanshanbao.dsp.agency.model.Agency;
import com.yuanshanbao.dsp.apply.model.Apply;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.paginator.domain.PageBounds;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Administrator on 2018/10/22.
 */
public interface AgencyService {
    public List<Agency> selectAgencys(Agency agency,PageBounds pageBounds);

    public Agency selectAgency(Long inviteId);

    public int insertAgency(Agency agency);

    public int deleteAgency(String Id);

    public int updateAgency(Agency agency);

    public List<Agency> selectAgencys(List<Long> IdList);

    /**
     * 获得一级直推总佣金
     * @param agency
     * @return
     */
    BigDecimal getAgencyCommission(Long  inviteUserId);

    List<Agency> selectAgencyByInviteId(Long inviteId);
}
