package com.yuanshanbao.dsp.creative.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class CreativeSize {
	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();
	public static final int SIZE_640110 = 1;
	public static final String SIZE_640110_DESCRIPTION = "640*100";
	public static final int SIZE_600500 = 2;
	public static final String SIZE_600500_DESCRIPTION = "600*500";
	public static final int SIZE_640320 = 3;
	public static final String SIZE_640320_DESCRIPTION = "640*320";
	public static final int SIZE_72890 = 4;
	public static final String SIZE_72890_DESCRIPTION = "728*90";
	public static final int SIZE_32050 = 5;
	public static final String SIZE_32050_DESCRIPTION = "320*50";
	public static final int SIZE_46860 = 6;
	public static final String SIZE_46860_DESCRIPTION = "468*60";
	public static final int SIZE_64050 = 7;
	public static final String SIZE_64050_DESCRIPTION = "640*50";
	public static final int SIZE_400300 = 8;
	public static final String SIZE_400300_DESCRIPTION = "400*300";
	public static final int SIZE_250300 = 9;
	public static final String SIZE_250300_DESCRIPTION = "250*300";
	public static final int SIZE_600300 = 10;
	public static final String SIZE_600300_DESCRIPTION = "600*300";
	public static final int SIZE_640400 = 11;
	public static final String SIZE_640400_DESCRIPTION = "640*400";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(SIZE_640110, SIZE_640110_DESCRIPTION);
		codeDescriptionMap.put(SIZE_600500, SIZE_600500_DESCRIPTION);
		codeDescriptionMap.put(SIZE_640320, SIZE_640320_DESCRIPTION);
		codeDescriptionMap.put(SIZE_72890, SIZE_72890_DESCRIPTION);
		codeDescriptionMap.put(SIZE_32050, SIZE_32050_DESCRIPTION);
		codeDescriptionMap.put(SIZE_46860, SIZE_46860_DESCRIPTION);
		codeDescriptionMap.put(SIZE_64050, SIZE_64050_DESCRIPTION);
		codeDescriptionMap.put(SIZE_400300, SIZE_400300_DESCRIPTION);
		codeDescriptionMap.put(SIZE_250300, SIZE_250300_DESCRIPTION);
		codeDescriptionMap.put(SIZE_600300, SIZE_600300_DESCRIPTION);
		codeDescriptionMap.put(SIZE_640400, SIZE_640400_DESCRIPTION);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
