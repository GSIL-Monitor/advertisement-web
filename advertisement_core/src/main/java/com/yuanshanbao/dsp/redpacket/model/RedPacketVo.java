package com.yuanshanbao.dsp.redpacket.model;

import com.yuanshanbao.common.util.CommonUtil;
import com.yuanshanbao.common.util.DateUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class RedPacketVo {

    private Long redPacketId;
    private Long userId;
    private String name;
    private String description;
    private BigDecimal balance;
    private BigDecimal amount;
    private BigDecimal condition;
    private Integer type;
    private Timestamp startTime;
    private Timestamp endTime;
    private Integer status;

    private boolean usable;


    public RedPacketVo() {
        super();
    }

    public RedPacketVo(RedPacket redPacket) {
        this.redPacketId = redPacket.getRedPacketId();
        this.userId = redPacket.getUserId();
        this.name = redPacket.getName();
        this.description = redPacket.getDescription();
        this.balance = redPacket.getBalance();
        this.amount = redPacket.getAmount();
        this.condition = redPacket.getCondition();
        this.type = redPacket.getType();
        this.startTime = redPacket.getStartTime();
        this.endTime = redPacket.getEndTime();
        this.status = redPacket.getStatus();

    }

    public RedPacketVo(RedPacket redPacket, Long gameId, BigDecimal orderAmount, Boolean isFollow) {
        this(redPacket);
        this.usable = checkUsable(redPacket, gameId, orderAmount, isFollow);
    }

    private boolean checkUsable(RedPacket redPacket, Long gameId, BigDecimal orderAmount, Boolean isFollow) {
        if (orderAmount == null) {
            return false;
        }
        if (this.condition != null && this.condition.compareTo(orderAmount) > 0) {
            return false;
        }
        if ((isFollow == null || isFollow) && !this.type.equals(RedPacketType.WITH_FOLLOW)) {
            return false;
        }
        return true;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAmountContent() {
        return CommonUtil.formatIntBigDecimal(amount);
    }

    public BigDecimal getCondition() {
        return condition;
    }

    public void setCondition(BigDecimal condition) {
        this.condition = condition;
    }

    public String getConditionContent() {
        return CommonUtil.formatIntBigDecimal(condition);
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public String getStartTimeContent() {
        return DateUtils.format(startTime);
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getValidTimeContent() {
        int days = DateUtils.getDays(new Date(), endTime);
        if (days > 0) {
            return "还剩" + days + "天到期";
        } else if (days == 0) {
            return "今天到期";
        } else if (balance.compareTo(BigDecimal.ZERO) <= 0) {
            return "已用完";
        } else {
            return "已过期";
        }
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public boolean isUsable() {
        return usable;
    }

    public void setUsable(boolean usable) {
        this.usable = usable;
    }

}
