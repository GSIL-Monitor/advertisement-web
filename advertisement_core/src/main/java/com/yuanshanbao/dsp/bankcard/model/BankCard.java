package com.yuanshanbao.dsp.bankcard.model;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.dsp.agency.model.Agency;
import com.yuanshanbao.dsp.agency.model.vo.AgencyStatus;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2018/10/23.
 */
public class BankCard {
    private Long bankId;
    private Long advertiserId;
    private String name;
    private String mobile;
    private String bankName;
    private Integer status;
    private Timestamp createTime;
    private Timestamp updateTime;

    public BankCard(){
    }
    public BankCard(Long bankId, Long advertiserId, String name, String mobile, String bankName, Integer status, Timestamp createTime, Timestamp updateTime) {
        this.bankId = bankId;
        this.advertiserId = advertiserId;
        this.name = name;
        this.mobile = mobile;
        this.bankName = bankName;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public Long getAdvertiserId() {
        return advertiserId;
    }

    public void setAdvertiserId(Long advertiserId) {
        this.advertiserId = advertiserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getStatus() {
        return status;
    }
    public String getStatusValue(){
        return AgencyStatus.getDescription(status);
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public String getCreateTimeValue(){
        return DateUtils.format(createTime,DateUtils.DEFAULT_DATE_FORMAT);
    }
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
