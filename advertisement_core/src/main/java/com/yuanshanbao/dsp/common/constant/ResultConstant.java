package com.yuanshanbao.dsp.common.constant;

/**
 * Created by Singal
 */
public class ResultConstant {
    public static final int SUCCESS = 0;
    public static final int ERROR = -1;
    public static final int TOKEN_ERROR = -2;//未登录认证
    public static final int SIGN_ERROR = -3;//验签有误
    public static final int PERIOD_CHANGED = -4; //选择期次已经过期
    //拆单返回
    public static final int ERROR_TIME = -1;
    public static final int ERROR_REPEAT = -2;
    public static final int ERROR_FOLLOW_BUY = -3;

    public static final String SUCCESS_CODE = "0";
    public static final String NEED_RELOGIN = "2007";
    public static final String TOKEN_ISSUE = "2003";
    public static final String PERIOD_CHANGED_MSG = "periodChanged";
}
