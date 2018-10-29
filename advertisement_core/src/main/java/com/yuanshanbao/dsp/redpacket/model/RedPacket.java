package com.yuanshanbao.dsp.redpacket.model;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class RedPacket {

    private Long redPacketId;
    private Long userId;
    private Long strategyId;
    private BigDecimal balance;
    private Timestamp startTime;
    private Timestamp endTime;
    private Integer status;
    private Timestamp createTime;
    private Timestamp updateTime;

    private RedPacketStrategy strategy;
    private List<Integer> statusList;

    public Long getRedPacketId() {
        return redPacketId;
    }

    public void setRedPacketId(Long redPacketId) {
        this.redPacketId = redPacketId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public String getName() {
        if (strategy != null) {
            return strategy.getName();
        }
        return null;
    }

    public String getDescription() {
        if (strategy != null) {
            return strategy.getDescription();
        }
        return null;
    }

    public BigDecimal getAmount() {
        if (strategy != null) {
            return strategy.getAmount();
        }
        return null;
    }

    public BigDecimal getCondition() {
        if (strategy != null) {
            return strategy.getCondition();
        }
        return null;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getType() {
        if (strategy != null) {
            return strategy.getType();
        }
        return null;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getCreateTime() {
        return createTime;
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

    public RedPacketStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(RedPacketStrategy strategy) {
        this.strategy = strategy;
    }


    public List<Integer> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Integer> statusList) {
        this.statusList = statusList;
    }

}
