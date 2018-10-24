package com.yuanshanbao.dsp.strategy.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/10/15.
 */
public class Strategy implements Serializable {
    private  String strategyId;
    private  String name;
    private  String stratTime;
    private  String stopTime;
    private  String type;
    private BigDecimal budget;
    private String bid;

    public String getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStratTime() {
        return stratTime;
    }

    public void setStratTime(String stratTime) {
        this.stratTime = stratTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    @Override
    public String toString() {
        return "Strategy{" +
                "strategyId='" + strategyId + '\'' +
                ", name='" + name + '\'' +
                ", stratTime='" + stratTime + '\'' +
                ", stopTime='" + stopTime + '\'' +
                ", type='" + type + '\'' +
                ", budget=" + budget +
                ", bid='" + bid + '\'' +
                '}';
    }
}
