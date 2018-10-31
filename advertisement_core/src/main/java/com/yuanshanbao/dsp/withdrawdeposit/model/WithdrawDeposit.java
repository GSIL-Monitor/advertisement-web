package com.yuanshanbao.dsp.withdrawdeposit.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2018/10/26.
 */
public class WithdrawDeposit {
    private Long withdrawId;
    private Long userId;
    private String description;
    private Timestamp createTime;
    private Long productId;
    private BigDecimal money;


    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getWithdrawId() {
        return withdrawId;
    }

    public void setWithdrawId(Long withdrawId) {
        withdrawId = withdrawId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
