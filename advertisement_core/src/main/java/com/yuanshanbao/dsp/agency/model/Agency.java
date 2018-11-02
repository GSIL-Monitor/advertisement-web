package com.yuanshanbao.dsp.agency.model;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.dsp.agency.model.vo.AgencyStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2018/10/22.
 */
public class Agency implements Serializable  {
    private static final long serialVersionUID = 4165445835251317169L;
    private Long inviteUserId;
    private Long userId;
    private String agencyName;
    private String userName;
    private Timestamp inviteTime;
    private BigDecimal brokerage;
    private Integer status;                //0:待审核 1:审核通过 2:审核未通过
    private Long productId;
    private String productName;
    private Timestamp createTime;

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }




    public void setBrokerage(BigDecimal brokerage) {
        this.brokerage = brokerage;
    }

    public Long getInviteUserId() {
        return inviteUserId;
    }

    public void setInviteUserId(Long inviteUserId) {
        this.inviteUserId = inviteUserId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setInviteTime(Timestamp inviteTime) {
        this.inviteTime = inviteTime;
    }

    public  String getInviteTimeValue(){
        return DateUtils.format(inviteTime,DateUtils.DEFAULT_DATE_FORMAT);
    }


    public String getStatusValue(){
            return AgencyStatus.getDescription(status);
    }
    public BigDecimal getBrokerageValue() {
        return brokerage.setScale(2, RoundingMode.HALF_UP);
    }


    public void setStatus(Integer status) {
        this.status = status;
    }
}

