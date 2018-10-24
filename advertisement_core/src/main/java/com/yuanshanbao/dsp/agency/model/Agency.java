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
    private BigDecimal commission;
    private Integer status;

    public Agency(){}

    public Agency(Long inviteUserId, Long userId, String agencyName, String userName, Timestamp inviteTime, BigDecimal commission, Integer status) {
        this.inviteUserId = inviteUserId;
        this.userId = userId;
        this.agencyName = agencyName;
        this.userName = userName;
        this.inviteTime = inviteTime;
        this.commission = commission;
        this.status = status;
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

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

