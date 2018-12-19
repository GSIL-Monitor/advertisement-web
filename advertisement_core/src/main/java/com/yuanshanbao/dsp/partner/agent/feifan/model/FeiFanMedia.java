package com.yuanshanbao.dsp.partner.agent.feifan.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class FeiFanMedia {
	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();
	protected static Map<Integer, String> keyDescriptionMap = new LinkedHashMap<Integer, String>();

	public static final int YOUKU = 60;
	public static final String YOUKU_DESCRIPTION = "优酷";
	public static final String YOUKU_KEY = "feifan_youku";
	public static final int AIQIYI = 62;
	public static final String AIQIYI_DESCRIPTION = "爱奇艺";
	public static final String AIQIYI_KEY = "feifan_aqy";
	public static final int QICHEZHIJIA = 185;
	public static final String QICHEZHIJIA_DESCRIPTION = "汽车之家";
	public static final String QICHEZHIJIA_KEY = "feifan_qczj";
	public static final int MOJITIANQI = 184;
	public static final String MOJITIANQI_DESCRIPTION = "墨迹天气";
	public static final String MOJITIANQI_KEY = "feifan_mjtq";
	public static final int ZHANGMENG = 121;
	public static final String ZHANGMENG_DESCRIPTION = "掌盟";
	public static final String ZHANGMENG_KEY = "feifan_zhangmeng";

	static {
		initCodeDescriptionMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(YOUKU, YOUKU_DESCRIPTION);
		codeDescriptionMap.put(AIQIYI, AIQIYI_DESCRIPTION);
		codeDescriptionMap.put(QICHEZHIJIA, QICHEZHIJIA_DESCRIPTION);
		codeDescriptionMap.put(MOJITIANQI, MOJITIANQI_DESCRIPTION);
		codeDescriptionMap.put(ZHANGMENG, ZHANGMENG_DESCRIPTION);

		keyDescriptionMap.put(YOUKU, YOUKU_KEY);
		keyDescriptionMap.put(AIQIYI, AIQIYI_KEY);
		keyDescriptionMap.put(QICHEZHIJIA, QICHEZHIJIA_KEY);
		keyDescriptionMap.put(MOJITIANQI, MOJITIANQI_KEY);
		keyDescriptionMap.put(ZHANGMENG, ZHANGMENG_KEY);
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static String getKey(Integer code) {
		return keyDescriptionMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}
}
