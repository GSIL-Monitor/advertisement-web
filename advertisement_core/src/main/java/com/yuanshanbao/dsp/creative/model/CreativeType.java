package com.yuanshanbao.dsp.creative.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class CreativeType {
	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int MOBILE_BANNER = 1;
	public static final String MOBILE_BANNER_DESCRIPTION = "移动横幅";
	public static final int MOBILE_INDEX = 2;
	public static final String MOBILE_INDEX_DESCRIPTION = "移动开屏";
	public static final int MOBILE_INDEXORINSERT = 3;
	public static final String MOBILE_INDEXORINSERT_DESCRIPTION = "移动开屏或移动插屏";
	public static final int MOBILE_WALL = 4;
	public static final String MOBILE_WALL_DESCRIPTION = "移动积分墙";
	public static final int MOBILE_VIDEO = 5;
	public static final String MOBILE_VIDEO_DESCRIPTION = "移动视频贴片";
	public static final int MOBILE_VIDEO_PAUSE = 6;
	public static final String MOBILE_VIDEO_PAUSE_DESCRIPTION = "移动视频暂停";
	public static final int MOBILE_FEEDS = 7;
	public static final String MOBILE_FEEDS_DESCRIPTION = "移动feeds信息流";
	public static final int MOBILE_FEEDS_MUL = 8;
	public static final String MOBILE_FEEDS_MUL_DESCRIPTION = "移动feeds信息流多图";
	public static final int MOBILE_MOTIVATE = 9;
	public static final String MOBILE_MOTIVATE_DESCRIPTION = "移动激励广告";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(MOBILE_BANNER, MOBILE_BANNER_DESCRIPTION);
		codeDescriptionMap.put(MOBILE_INDEX, MOBILE_INDEX_DESCRIPTION);
		codeDescriptionMap.put(MOBILE_INDEXORINSERT, MOBILE_INDEXORINSERT_DESCRIPTION);
		codeDescriptionMap.put(MOBILE_WALL, MOBILE_WALL_DESCRIPTION);
		codeDescriptionMap.put(MOBILE_VIDEO, MOBILE_VIDEO_DESCRIPTION);
		codeDescriptionMap.put(MOBILE_VIDEO_PAUSE, MOBILE_VIDEO_PAUSE_DESCRIPTION);
		codeDescriptionMap.put(MOBILE_FEEDS, MOBILE_FEEDS_DESCRIPTION);
		codeDescriptionMap.put(MOBILE_FEEDS_MUL, MOBILE_FEEDS_MUL_DESCRIPTION);
		codeDescriptionMap.put(MOBILE_MOTIVATE, MOBILE_MOTIVATE_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
