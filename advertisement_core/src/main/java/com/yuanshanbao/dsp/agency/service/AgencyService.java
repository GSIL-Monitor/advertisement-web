package com.yuanshanbao.dsp.agency.service;

import com.yuanshanbao.dsp.agency.model.Agency;
import com.yuanshanbao.dsp.agency.model.vo.AgencyVo;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.paginator.domain.PageBounds;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/22.
 */
public interface AgencyService {
    public List<Agency> selectAgencys(Agency agency,PageBounds pageBounds);

    public List<Agency>  selectAgency(Long userId);

    public int insertAgency(Agency agency);

    public int deleteAgency(String Id);

    public int updateAgency(Agency agency);


    public int updateBankTime(Agency agency);

    public List<Agency> selectAgencys(List<Long> IdList);


    BigDecimal getAgencyBrokerage(Long inviteUserId);




    /**
     * 获得一级直推总佣金
     * @param
     * @return
     */
    public BigDecimal getBrokerages(Agency agency,User user, PageBounds pageBounds);

    /**
     *获得直推办卡成功人数
     * @param inviteId
     * @return
     */
    public int selectAgencyByInviteId(Long inviteId);

    /**
     * 获得直推人为经理等级人数
     * @param
     * @return
     */
    public int selectAgencyByInviteIdCount(Long inviteUserId);

    /**
     * 我的代理集合
     * @param user
     * @return
     */
    List<AgencyVo> getAgencyInfos(User user, Agency agency, PageBounds pageBounds);

    public List<AgencyVo> getAgencyListVo(List<Agency> twoAgencyList,User user);


}
