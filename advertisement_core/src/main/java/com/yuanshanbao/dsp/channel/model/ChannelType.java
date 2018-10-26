package com.yuanshanbao.dsp.channel.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ChannelType {

	protected static Map<String, String> codeDescriptionMap = new LinkedHashMap<String, String>();

	protected static Map<Integer, String> typeDescriptionMap = new LinkedHashMap<Integer, String>();

	protected static Map<Integer, String> deliverTypeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final String ZHIFU = "zhifu";
	public static final String ZHIFU_DESCRIPTION = "金融支付类";
	public static final String ZIXUN = "zixun";
	public static final String ZIXUN_DESCRIPTION = "新闻资讯类";
	public static final String CHEZHU = "chezhu";
	public static final String CHEZHU_DESCRIPTION = "车主类";

	public static final Integer TUSHU = 1;
	public static final String TUSHU_DESCRIPTION = "图书";
	public static final Integer SHANGWU = 1;
	public static final String TUSHU_DESCRIPTION = "商务";
	public static final Integer YULE = 1;
	public static final String TUSHU_DESCRIPTION = "商品指南";
	public static final Integer JINRONG = 1;
	public static final String TUSHU_DESCRIPTION = "教育";
	public static final Integer MEISHI = 1;
	public static final String TUSHU_DESCRIPTION = "金融/理财";
	public static final Integer YOUXI = 1;
	public static final String TUSHU_DESCRIPTION = "美食佳饮";
	public static final Integer JIANKANG = 1;
	public static final String TUSHU_DESCRIPTION = "游戏";
	public static final Integer SHENGHUO = 1;
	public static final String TUSHU_DESCRIPTION = "健康健美";
	public static final Integer BAOKAN = 1;
	public static final String TUSHU_DESCRIPTION = "生活";
	public static final Integer YILIAO = 1;
	public static final String TUSHU_DESCRIPTION = "报刊杂志";
	public static final Integer YINYUE = 1;
	public static final String TUSHU_DESCRIPTION = "医疗";
	public static final Integer DAOHANG = 1;
	public static final String TUSHU_DESCRIPTION = "音乐";
	public static final Integer XINWEN = 1;
	public static final String TUSHU_DESCRIPTION = "导航";
	public static final Integer SHEYING = 1;
	public static final String TUSHU_DESCRIPTION = "新闻";
	public static final Integer XIAOLV = 1;
	public static final String TUSHU_DESCRIPTION = "摄影与录像";
	public static final Integer GOUWU = 1;
	public static final String TUSHU_DESCRIPTION = "效率";
	public static final Integer SHEJIAO = 1;
	public static final String TUSHU_DESCRIPTION = "参考";
	public static final Integer DAOHANG = 1;
	public static final String TUSHU_DESCRIPTION = "购物";
	public static final Integer DAOHANG = 1;
	public static final String TUSHU_DESCRIPTION = "社交";
	public static final Integer DAOHANG = 1;
	public static final String TUSHU_DESCRIPTION = "体育";
	public static final Integer DAOHANG = 1;
	public static final String TUSHU_DESCRIPTION = "贴纸";
	public static final Integer DAOHANG = 1;
	public static final String TUSHU_DESCRIPTION = "旅游";
	public static final Integer DAOHANG = 1;
	public static final String TUSHU_DESCRIPTION = "工具";
	public static final Integer DAOHANG = 1;
	public static final String TUSHU_DESCRIPTION = "天气";
	public static final Integer DAOHANG = 1;
	public static final String TUSHU_DESCRIPTION = "视频";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(ZHIFU, ZHIFU_DESCRIPTION);
		codeDescriptionMap.put(ZIXUN, ZIXUN_DESCRIPTION);
		codeDescriptionMap.put(CHEZHU, CHEZHU_DESCRIPTION);

		typeDescriptionMap.put(TOMMOROW_SHOW_AND_NOT_CONFIRM, TOMMOROW_SHOW_AND_NOT_CONFIRM_DESCRIPTION);
		typeDescriptionMap.put(SHOW_DATA_ALWAYS, SHOW_DATA_ALWAYS_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<String, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}

	public static String getType(String description) {
		for (Entry<String, String> entry : codeDescriptionMap.entrySet()) {
			if (entry.getValue().equals(description)) {
				return entry.getKey();
			}
		}
		return null;
	}

	public static String getShowTypeDescription(Integer code) {
		return typeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getShowTypeDescriptionMap() {
		return typeDescriptionMap;
	}

	public static String getDeliverTypeDescription(Integer code) {
		return deliverTypeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getDeliverTypeDescriptionMap() {
		return deliverTypeDescriptionMap;
	}
}
