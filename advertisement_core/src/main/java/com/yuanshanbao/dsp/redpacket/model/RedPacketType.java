package com.yuanshanbao.dsp.redpacket.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class RedPacketType {

    public static final int WITHOUT_FOLLOW = 1;
    public static final String WITHOUT_FOLLOW_DESCRIPTION = "不含追号";
    public static final int WITH_FOLLOW = 2;
    public static final String WITH_FOLLOW_DESCRIPTION = "含追号";
    protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

    static {
        initCodeDescriptionMap();
    }

    public static void initCodeDescriptionMap() {
        codeDescriptionMap.put(WITHOUT_FOLLOW, WITHOUT_FOLLOW_DESCRIPTION);
        codeDescriptionMap.put(WITH_FOLLOW, WITH_FOLLOW_DESCRIPTION);
    }

    public static String getDescription(Integer code) {
        return codeDescriptionMap.get(code);
    }

    public static Map<Integer, String> getCodeDescriptionMap() {
        return codeDescriptionMap;
    }
}
