package com.yuanshanbao.dsp.redpacket.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class RedPacketStatus {

    public static final int INIT = 0;
    public static final String INIT_DESCRIPTION = "待派发";
    public static final int AVAILABLE = 1;
    public static final String AVAILABLE_DESCRIPTION = "可用";
    public static final int RUNOUT = 2;
    public static final String RUNOUT_DESCRIPTION = "已用完";
    public static final int OVERDUE = 3;
    public static final String OVERDUE_DESCRIPTION = "已过期";
    public static final int DELETE = -1;
    public static final String DELETE_DESCRIPTION = "下线";
    protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();

    static {
        initCodeDescriptionMap();
    }

    public static void initCodeDescriptionMap() {
        codeDescriptionMap.put(INIT, INIT_DESCRIPTION);
        codeDescriptionMap.put(AVAILABLE, AVAILABLE_DESCRIPTION);
        codeDescriptionMap.put(RUNOUT, RUNOUT_DESCRIPTION);
        codeDescriptionMap.put(OVERDUE, OVERDUE_DESCRIPTION);
        codeDescriptionMap.put(DELETE, DELETE_DESCRIPTION);
    }

    public static String getDescription(Integer code) {
        return codeDescriptionMap.get(code);
    }

    public static Map<Integer, String> getCodeDescriptionMap() {
        return codeDescriptionMap;
    }
}
