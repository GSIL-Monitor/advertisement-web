package com.yuanshanbao.dsp.agency.dao;

import com.yuanshanbao.dsp.agency.model.Agency;
import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.paginator.domain.PageBounds;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2018/10/22.
 */
@Repository
public class AgencyDaoImpl extends BaseDaoImpl implements AgencyDao {

    @Override
    public List<Agency> selectAgencys(Agency agency, PageBounds pageBounds) {
        return getSqlSession().selectList("agency.selectAgencys" , agency,pageBounds);
    }

    @Override
    public Agency selectAgency(Long inviteId) {
        return getSqlSession().selectOne("agency.selectAgency", inviteId);
    }

    @Override
    public int insertAgency(Agency agency) {
        return getSqlSession().insert("agency.insertAgency",agency);
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
    public BigDecimal getAgencyCommission(Long inviteUserId ) {
        return getSqlSession().selectOne("agency.getSumCommission",inviteUserId);
    }

    @Override
    public List<Agency> selectAgencysByInviteId(Long inviteId) {
        return getSqlSession().selectList("agency.selectAgencysByInviteId",inviteId);
    }
}
