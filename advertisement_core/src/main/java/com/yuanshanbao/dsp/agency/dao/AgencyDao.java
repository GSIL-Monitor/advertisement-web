package com.yuanshanbao.dsp.agency.dao;

import java.math.BigDecimal;
import java.util.List;

import com.yuanshanbao.dsp.agency.model.Agency;
import com.yuanshanbao.paginator.domain.PageBounds;

/**
 * Created by Administrator on 2018/10/22.
 */
public interface AgencyDao {
	public List<Agency> selectAgencys(Agency agency, PageBounds pageBounds);

	public List<Agency> selectAgency(Long userId);

	public int insertAgency(Agency agency);

	public int deleteAgency(String Id);

	public int updateAgency(Agency agency);

	public List<Agency> selectAgencys(List<Long> IdList);

	public BigDecimal getAgencyBrokerage(Long inviteUserId);

	public int selectAgencyByInviteId(Long inviteUserId);

	public List<Agency> selectAgencysByInviteUserIds(List<Long> inviteUserIds);

	public BigDecimal getSumBrokerage(List<Long> inviteUserIds);

	public int selectAgencyByInviteIdCount(Long inviteUserId);

	public int updateBankTime(Agency agency);

	public BigDecimal queryVIPAgenctSumBrokerage(Long inviteUserId);

	public BigDecimal settledTwoAgencyBrokerages(List<Long> twoInviteUserIds);

	public BigDecimal settledBrokerageAndTimeCompare(List<Long> twoInviteUserIds);
}
