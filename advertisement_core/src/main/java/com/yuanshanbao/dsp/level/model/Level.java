package com.yuanshanbao.dsp.level.model;

import com.yuanshanbao.dsp.agency.model.vo.AgencyStatus;
import com.yuanshanbao.dsp.level.model.vo.LevelStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2018/10/22.
 */
public class Level implements Serializable  {
    private static final long serialVersionUID = 4165445835251317169L;

    private Long userId;
    private String userName;
    private String image;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Integer status;                //1:经理 2:总监 3:执行官
    private Integer countCard;

    public Integer getCountCard() {
        return countCard;
    }

    public void setCountCard(Integer countCard) {
        this.countCard = countCard;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Integer getStatus() {
        return status;
    }
    public String getStatusValue(){
        return LevelStatus.getDescription(status);

    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

