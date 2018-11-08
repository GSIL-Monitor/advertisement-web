package com.yuanshanbao.dsp.agency.dao;

import com.yuanshanbao.dsp.agency.model.Agency;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.paginator.domain.PageBounds;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2018/10/22.
 */
public interface AgencyDao {
    public List<Agency> selectAgencys(Agency agency, PageBounds pageBounds);

    Agency selectAgency(String  userId);

    public int insertAgency(Agency agency);

    public int deleteAgency(String Id);

    public int updateAgency(Agency agency);

    public List<Agency> selectAgencys(List<Long> IdList);

    BigDecimal getAgencyBrokerage(Long inviteUserId);

    List<Agency> selectAgencysByInviteId(Long inviteId);


    /**
     * ids
     * @return
     * @param inviteUserIds
     */
    List<Agency> selectAgencysByInviteUserIds(List<Long> inviteUserIds);

    BigDecimal getSumBrokerage(List<Long> inviteUserIds);
}
