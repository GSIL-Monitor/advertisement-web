package com.yuanshanbao.dsp.earnings.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2018/10/26.
 */
public class Earnings {
    private Long earningId;
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

    public Long getEarningId() {
        return earningId;
    }

    public void setEarningId(Long earningId) {
        earningId = earningId;
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
