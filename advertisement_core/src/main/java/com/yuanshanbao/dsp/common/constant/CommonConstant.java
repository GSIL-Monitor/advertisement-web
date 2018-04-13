package com.yuanshanbao.dsp.common.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.PropertyUtil;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.cache.IniCache;

public class CommonConstant {
	public static final ThreadLocal<HttpServletRequest> requestTL = new ThreadLocal<>(); // 保存request的threadlocal
	public static final ThreadLocal<HttpServletResponse> responseTL = new ThreadLocal<>(); // 保存response的threadlocal
	public static final ThreadLocal<HttpSession> sessionTL = new ThreadLocal<>(); // 保存session的threadlocal

	public final static String APPLICATION_TYPE_WAP = "wap";
	public final static String APPLICATION_TYPE_IOS = "iOS";
	public final static String APPLICATION_TYPE_ANDROID = "android";

	public final static String SPACE_SPLIT_STR = " ";
	public final static String PERCENT_SPLIT_STR = "%";
	public final static String COMMON_SPLIT_STR = "_";
	public final static String COMMON_DASH_STR = "-";
	public final static String COMMA_SPLIT_STR = ",";
	public final static String SEMICOLON_SPLIT_STR = ";";
	public final static String COMMON_VERTICAL_STR = "|";
	public final static String URL_SPLIT_STR = "/";
	public final static String DOUBLE_SLASH_STR = "//";
	public final static String POUND_SPLIT_STR = "#";
	public final static String COMMON_ESCAPE_STR = "\\";
	public final static String COMMON_AT_STR = "@";
	public final static String COMMON_DOLLAR_STR = "$";
	public final static String COMMON_WAVE_STR = "~";
	public final static String COMMON_STAR_STR = "*";
	public final static String COMMON_COLON_STR = ":";
	public final static String COMMON_DOT_STR = ".";
	public final static String COMMON_EQUAL_STR = "=";
	public final static String COMMON_AND_STR = "&";
	public final static String UP_ARROW_STR = "^";
	public final static String COMMON_BRACKET_LEFT = "(";
	public final static String COMMON_BRACKET_RIGHT = ")";
	public final static String DOUBLE_DASH_STR = "--";
	public static final String REAL_NAME = PropertyUtil.getProperty("realName");
	public static final String ID_CARD = PropertyUtil.getProperty("idCard");
	public static final String PHONE = PropertyUtil.getProperty("phoneNumber");
	public static final int ORDER_ID_LENGTH = 23;
	public static final int FOLLOW_ID_LENGTH = 18;
	public static final int ZERO = 0;
	public static final int ONE = 1;
	public static final int BAK_INSERT_ERROR = -1;
	public static final int BAK_DEL_ERROR = -2;
	public static final String PAY_PRIVATE_KEY = "advertisement.pri_key";
	public static final String PAY_PUBLIC_KEY = "payment.pub_key";
	public static final String advertisement_HOST = PropertyUtil.getProperty("advertisement.host");

	public static final String AUDIT_SALES_AMOUNT = "sales";
	public static final String AUDIT_SALES_TICKETS = "salenum";
	public static final String AUDIT_AWARD_TICKETS = "awardnum";

	public static final String PAYMENT_PLATFROM_NAME = "cp";

	public static final String THIRD_CPS_MZ = "meizu";

	public static final String COMMON_GAME_STOP_ALERT_20_CLOCK = "20:00:00";
	public static final String COMMON_GAME_STOP_ALERT_12_CLOCK = "12:00:00";

	// 分表@TableShard 属性值
	public static final String USER_SHARD_TYPE = "userShardType";
	public static final String USER_SHARD_BY_ID = "userId";
	public static final String USER_SHARD_BY_MOBILE = "mobile";
	public static final String USER_TABLE_NAME = "tb_user_info";
	public static final String USER_BASE_INFO_TABLE_NAME = "tb_user_base_info";

	public static final String INDEX_USER_SHARD_TYPE = "indexShardType";
	public static final String INDEX_USER_SHARD_BY = "openId";
	public static final String INDEX_USER_TABLE_NAME = "tb_index_user";

	public static final String INFORMATION_SHARD_TYPE = "informationShardType";
	public static final String INFORMATION_SHARD_BY = "userId";
	public static final String INFORMATION_TABLE_NAME = "tb_information";

	public static final String EXTEND_INFO_SHARD_TYPE = "extendInfoShardType";
	public static final String EXTEND_INFO_SHARD_BY = "userId";
	public static final String EXTEND_INFO_TABLE_NAME = "tb_extend_info";

	public static final String APPLY_SHARD_TYPE = "applyShardType";
	public static final String APPLY_SHARD_BY = "userId";
	public static final String APPLY_TABLE_NAME = "tb_apply";
	
	public static final String APPLY_INFO_SHARD_TYPE = "applyInfoShardType";
	public static final String APPLY_INFO_SHARD_BY_MOBILE = "mobile";
	public static final String APPLY_INFO_TABLE_NAME = "tb_apply_info";

	// 分隔符
	public static final String SEPARATOR_LINE = System.getProperty("line.separator");
	public static final String SEPARATOR_FILE = System.getProperty("file.separator");

	public final static String BEAN_NAME_GAME_ID = "gameId";
	public final static String BEAN_NAME_TASK_ID = "taskId";
	public final static String BEAN_NAME_PLATFORM_ID = "platformId";
	public final static String[] BEAN_NAME_ALL_ARRAY = new String[] { BEAN_NAME_GAME_ID, BEAN_NAME_TASK_ID,
			BEAN_NAME_PLATFORM_ID };
	public final static String[] BEAN_NAME_GAME_ARRAY = new String[] { BEAN_NAME_GAME_ID };
	public final static String[] BEAN_NAME_GAME_TASK_ARRAY = new String[] { BEAN_NAME_GAME_ID, BEAN_NAME_TASK_ID };
	public final static String BEAN_NAME_WEIGHT = "weight";

	public static final String[] COLORS = { "#809CFF", "#FFB858", "#57C8FF", "#5AE3D6", "#FF894E", "#FF7FC7" };
	
	// tableShard
	public static final String SHARD_BY_USER_ID = "userId";
	public static final String SHARD_BY_GAME_ID = "gameId";
	public static final String SHARD_BY_PERIOD_ID = "periodId";

	public static final String TICKET_PREFIX = DateUtils.getCurrentTime().toString().substring(2, 4);
	public static final int MAX_COMPRESS_TIME = 1000;// Gzip压缩和解压缩时间限制MS
	public final static Map<String, String> SCHEDULE_BEAN_MAP = new HashMap<>();

	public static final int PAGE_RECORD = 20;// 方案/订单列表每页记录数

	public static final String DESK_SERVICE_KEY_WORD = "[ServiceDesk]:";

	// cron间隔最大时间
	public static Long CRON_MAX_MONITOR_INTERVAL = IniCache.getIniLongValue("CronMaxMonitorInterval", 10000L);

	// 监控线程cron时间配置
	public static String CRON_MONITOR = IniCache.getIniValue("CronMonitor", "0/3 * * * * ?");

	public static String generateHandleId(List<Object> params) {
		StringBuffer sb = new StringBuffer();
		params.forEach(sb::append);
		return sb.toString();
	}

	public static String getShardSuffixByUserId(String userId) {
		if (StringUtils.isBlank(userId)) {
			return "00";
		}
		String result = "";
		for (int i = userId.length() - 1; i >= 0; i--) {
			String suffix = String.valueOf(userId.charAt(i));
			if (ValidateUtil.isNumber(suffix)) {
				result = suffix + result;
			}
			if (result.length() == 2) {
				return result;
			}
		}
		return "00";
	}
}