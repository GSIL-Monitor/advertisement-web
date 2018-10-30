package com.yuanshanbao.dsp.agency.model;

import java.io.Serializable;
import java.math.BigDecimal;
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

    public Agency(){}

    public Agency(Long inviteUserId, Long userId, String agencyName, String userName, Timestamp inviteTime, BigDecimal brokerage, Integer status) {
        this.inviteUserId = inviteUserId;
        this.userId = userId;
        this.agencyName = agencyName;
        this.userName = userName;
        this.inviteTime = inviteTime;
        this.brokerage = brokerage;
        this.status = status;
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

    public BigDecimal getBrokerage() {
        return brokerage;
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

    public Timestamp getInviteTime() {
        return inviteTime;
    }

    public void setInviteTime(Timestamp inviteTime) {
        this.inviteTime = inviteTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

