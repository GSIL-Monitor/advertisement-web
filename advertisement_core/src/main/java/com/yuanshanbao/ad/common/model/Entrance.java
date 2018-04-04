package com.yuanshanbao.ad.common.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.yuanshanbao.ad.common.constant.CommonConstant;

/**
 * Created by Ynght on 2016/12/9.
 */
public class Entrance {
	public static final int POSITION_TYPE_HOT_GAME = 0;
	public static final int POSITION_TYPE_GAMES = 1;
	public static final int POSITION_TYPE_ATTENTION = 2;
	public static final int POSITION_TYPE_AUTH = 3;
	
	public static final int TIPS_TYPE_GRAY = 1;
	public static final int TIPS_TYPE_RED = 2;
	public static final int TIPS_TYPE_RED_BACKGROUND = 3;


	public static final String WAP_CONTENT_KEY = "wap";
	public static final String APP_CONTENT_KEY = "app";

	private Long entranceId;
	private String imgUrl;
	private String title;
	private String tips;
	private Integer tipsType;
	private Integer positionType;
	private String contentUrl;
	private Integer weight;
	private Integer isDel;
	private String remark;
	private Integer ifShowTipsStyle;// 0不显示样式1显示

	public Entrance() {

	}

	public Entrance(Long entranceId, String imgUrl, String title, String tips, Integer tipsType, Integer positionType,
			String contentUrl, Integer weight, Integer isDel, String remark) {
		this.entranceId = entranceId;
		this.imgUrl = imgUrl;
		this.title = title;
		this.tips = tips;
		this.tipsType = tipsType;
		this.positionType = positionType;
		this.contentUrl = contentUrl;
		this.weight = weight;
		this.isDel = isDel;
		this.remark = remark;
		this.ifShowTipsStyle = getIfShowStypeByRemark("tips");
	}

	public static Map<String, String> getEntranceNameMap(String entranceName) {
		String[] nameStr = entranceName.split(CommonConstant.COMMON_ESCAPE_STR + CommonConstant.COMMA_SPLIT_STR);
		Map<String, String> entranceNameMap = new HashMap<>();
		for (String name : nameStr) {
			String start = name.substring(0, name.indexOf(CommonConstant.COMMON_COLON_STR));
			switch (Integer.valueOf(start)) {
			case Entrance.POSITION_TYPE_GAMES:
				entranceNameMap.put("gamesEntranceCn",
						name.substring(name.indexOf(CommonConstant.COMMON_COLON_STR) + 1));
				break;
			case Entrance.POSITION_TYPE_ATTENTION:
				entranceNameMap.put("attentionEntranceCn",
						name.substring(name.indexOf(CommonConstant.COMMON_COLON_STR) + 1));
				break;
			default:
				break;
			}
		}
		return entranceNameMap;
	}

	public Long getEntranceId() {
		return entranceId;
	}

	public void setEntranceId(Long entranceId) {
		this.entranceId = entranceId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public Integer getTipsType() {
		return tipsType;
	}

	public void setTipsType(Integer tipsType) {
		this.tipsType = tipsType;
	}

	public Integer getPositionType() {
		return positionType;
	}

	public void setPositionType(Integer positionType) {
		this.positionType = positionType;
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getIfShowTipsStyle() {
		return ifShowTipsStyle;
	}

	public void setIfShowTipsStyle(Integer ifShowTipsStyle) {
		this.ifShowTipsStyle = ifShowTipsStyle;
	}

	public Integer getIfShowStypeByRemark(String key) {
		if (StringUtils.isBlank(this.remark)) {
			return 0;
		}
		Integer value = JSONObject.parseObject(this.remark).getInteger(key);
		if (value == null) {
			return 0;
		}
		return value;
	}
}
