package com.yuanshanbao.dsp.bankcard.model;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2018/10/23.
 */
public class BankCard {
    private Long userId;
    private String cardName;
    private String userName;
    private String type;
    private String cardNumber;
    private Timestamp createTime;

    public BankCard(){}

    public BankCard(Long userId, String cardName, String userName, String type, String cardNumber, Timestamp createTime) {
        this.userId = userId;
        this.cardName = cardName;
        this.userName = userName;
        this.type = type;
        this.cardNumber = cardNumber;
        this.createTime = createTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
