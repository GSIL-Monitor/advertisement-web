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
	public static final Integer SHANGWU = 2;
	public static final String SHANGWU_DESCRIPTION = "商务";
	public static final Integer SHANGPIN = 3;
	public static final String SHANGPIN_DESCRIPTION = "商品指南";
	public static final Integer JIAOYU = 4;
	public static final String JIAOYU_DESCRIPTION = "教育";
	public static final Integer JINRONG = 5;
	public static final String JINRONG_DESCRIPTION = "金融/理财";
	public static final Integer MEISHI = 6;
	public static final String MEISHI_DESCRIPTION = "美食佳饮";
	public static final Integer YOUXI = 7;
	public static final String YOUXI_DESCRIPTION = "游戏";
	public static final Integer JIANKANG = 8;
	public static final String JIANKANG_DESCRIPTION = "健康健美";
	public static final Integer SHENGHUO = 9;
	public static final String SHENGHUO_DESCRIPTION = "生活";
	public static final Integer BAOKAN = 10;
	public static final String BAOKAN_DESCRIPTION = "报刊杂志";
	public static final Integer YILIAO = 11;
	public static final String YILIAO_DESCRIPTION = "医疗";
	public static final Integer YINYUE = 12;
	public static final String YINYUE_DESCRIPTION = "音乐";
	public static final Integer DAOHANG = 13;
	public static final String DAOHANG_DESCRIPTION = "导航";
	public static final Integer XINWEN = 14;
	public static final String XINWEN_DESCRIPTION = "新闻";
	public static final Integer SHEYING = 15;
	public static final String SHEYING_DESCRIPTION = "摄影与录像";
	public static final Integer XIAOLV = 16;
	public static final String XIAOLV_DESCRIPTION = "效率";
	public static final Integer CANKAO = 17;
	public static final String CANKAO_DESCRIPTION = "参考";
	public static final Integer GOUWU = 18;
	public static final String GOUWU_DESCRIPTION = "购物";
	public static final Integer SHEJIAO = 19;
	public static final String SHEJIAO_DESCRIPTION = "社交";
	public static final Integer TIYU = 20;
	public static final String TIYU_DESCRIPTION = "体育";
	public static final Integer TIEZHI = 21;
	public static final String TIEZHI_DESCRIPTION = "贴纸";
	public static final Integer LVYOU = 22;
	public static final String LVYOU_DESCRIPTION = "旅游";
	public static final Integer GONGJU = 23;
	public static final String GONGJU_DESCRIPTION = "工具";
	public static final Integer TIANQI = 24;
	public static final String TIANQI_DESCRIPTION = "天气";
	public static final Integer SHIPIN = 25;
	public static final String SHIPIN_DESCRIPTION = "视频";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(ZHIFU, ZHIFU_DESCRIPTION);
		codeDescriptionMap.put(ZIXUN, ZIXUN_DESCRIPTION);
		codeDescriptionMap.put(CHEZHU, CHEZHU_DESCRIPTION);

		typeDescriptionMap.put(TUSHU, TUSHU_DESCRIPTION);
		typeDescriptionMap.put(SHANGWU, SHANGWU_DESCRIPTION);
		typeDescriptionMap.put(SHANGPIN, SHANGPIN_DESCRIPTION);
		typeDescriptionMap.put(JIAOYU, JIAOYU_DESCRIPTION);
		typeDescriptionMap.put(JINRONG, JINRONG_DESCRIPTION);
		typeDescriptionMap.put(MEISHI, MEISHI_DESCRIPTION);
		typeDescriptionMap.put(YOUXI, YOUXI_DESCRIPTION);
		typeDescriptionMap.put(JIANKANG, JIANKANG_DESCRIPTION);
		typeDescriptionMap.put(SHENGHUO, SHENGHUO_DESCRIPTION);
		typeDescriptionMap.put(BAOKAN, BAOKAN_DESCRIPTION);
		typeDescriptionMap.put(YINYUE, YINYUE_DESCRIPTION);
		typeDescriptionMap.put(DAOHANG, DAOHANG_DESCRIPTION);
		typeDescriptionMap.put(XINWEN, XINWEN_DESCRIPTION);
		typeDescriptionMap.put(SHEYING, SHEYING_DESCRIPTION);
		typeDescriptionMap.put(XIAOLV, XIAOLV_DESCRIPTION);
		typeDescriptionMap.put(CANKAO, CANKAO_DESCRIPTION);
		typeDescriptionMap.put(GOUWU, GOUWU_DESCRIPTION);
		typeDescriptionMap.put(SHEJIAO, SHEJIAO_DESCRIPTION);
		typeDescriptionMap.put(TIYU, TIYU_DESCRIPTION);
		typeDescriptionMap.put(TIEZHI, TIEZHI_DESCRIPTION);
		typeDescriptionMap.put(LVYOU, LVYOU_DESCRIPTION);
		typeDescriptionMap.put(GONGJU, GONGJU_DESCRIPTION);
		typeDescriptionMap.put(TIANQI, TIANQI_DESCRIPTION);
		typeDescriptionMap.put(SHIPIN, SHIPIN_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getTypeDescriptionMap() {
		return typeDescriptionMap;
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
