package com.yuanshanbao.dsp.agency.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.dsp.agency.model.Agency;
import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.paginator.domain.PageBounds;

/**
 * Created by Administrator on 2018/10/22.
 */
@Repository
public class AgencyDaoImpl extends BaseDaoImpl implements AgencyDao {

	@Override
	public List<Agency> selectAgencys(Agency agency, PageBounds pageBounds) {
		return getSqlSession().selectList("agency.selectAgencys", agency, pageBounds);
	}

	@Override
	public List<Agency> selectAgency(Long userId) {
		return getSqlSession().selectList("agency.selectAgencyById", userId);
	}

	@Override
	public int insertAgency(Agency agency) {
		return getSqlSession().insert("agency.insertAgency", agency);
	}

	@Override
	public int deleteAgency(String Id) {
		return 0;
	}

	@Override
	public int updateAgency(Agency agency) {
		agency.setUpdateTime(DateUtils.getCurrentTime());
		return getSqlSession().update("agency.updateAgency", agency);
	}

	@Override
	public List<Agency> selectAgencys(List<Long> IdList) {
		return null;
	}

	@Override
	public BigDecimal getAgencyBrokerage(Long inviteUserId) {
		return getSqlSession().selectOne("agency.getAgencyBrokerage", inviteUserId);
	}

	@Override
	public int selectAgencyByInviteId(Long inviteId) {
		return getSqlSession().selectOne("agency.selectAgencysByInviteUserId", inviteId);
	}

	@Override
	public List<Agency> selectAgencysByInviteUserIds(List<Long> inviteUserIds) {
		if (inviteUserIds == null || inviteUserIds.size() == 0) {
			return new ArrayList<Agency>();
		}
		return getSqlSession().selectList("agency.selectAgencyByIds", inviteUserIds);
	}

	@Override
	public BigDecimal getSumBrokerage(List<Long> inviteUserIds) {
		if (inviteUserIds.size() == 0) {
			return null;
		}
		return getSqlSession().selectOne("agency.getSumBrokerage", inviteUserIds);
	}

	@Override
	public int selectAgencyByInviteIdCount(Long inviteUserId) {
		return getSqlSession().selectOne("agency.selectAgencyByInviteIdCount", inviteUserId);
	}

	@Override
	public int updateBankTime(Agency agency) {
		agency.setUpdateTime(DateUtils.getCurrentTime());
		return getSqlSession().update("agency.updateBankTime", agency);
	}

	@Override
	public BigDecimal queryVIPAgenctSumBrokerage(Long inviteUserId) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("agency.getVIPAgenctSumBrokerage", inviteUserId);
	}

	@Override
	public BigDecimal settledTwoAgencyBrokerages(List<Long> twoInviteUserIds) {
		if (twoInviteUserIds.size() == 0) {
			return null;
		}
		return getSqlSession().selectOne("agency.settledTwoAgencyBrokerages", twoInviteUserIds);
	}

	@Override
	public BigDecimal settledBrokerageAndTimeCompare(List<Long> twoInviteUserIds) {
		if (twoInviteUserIds.size() == 0) {
			return null;
		}
		return getSqlSession().selectOne("agency.settledBrokerageAndTimeCompare", twoInviteUserIds);
	}

}
