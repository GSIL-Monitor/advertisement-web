package com.yuanshanbao.dsp.common.constant;

/**
 * Created by Ynght on 2017/1/9.
 */
public class ConfigConstant {

	// 分表@TableShard 属性值
	public static final String ORDER_SHARD_TYPE = "orderShardType";
	public static final String ORDER_SHARD_BY = "periodId";
	public static final String ORDER_TABLE_NAME = "tb_order";

	public static final String INDEX_ORDER_SHARD_TYPE = "indexOrderShardType";
	public static final String INDEX_ORDER_SHARD_BY = "userId";
	public static final String INDEX_ORDER_TABLE_NAME = "tb_index_order";

	public static final String GAME_PERIOD_SHARD_TYPE = "gamePeriodShardType";
	public static final String GAME_PERIOD_SHARD_BY = "gameId";
	public static final String GAME_PERIOD_TABLE_NAME = "tb_game_period";

	public static final String FOLLOW_BUY_SHARD_TYPE = "followBuyShardType";
	public static final String FOLLOW_BUY_SHARD_BY = "userId";
	public static final String FOLLOW_BUY_TABLE_NAME = "tb_follow_buy";

	public static final String RAW_NUMBER_SHARD_TYPE = "rawNumberShardType";
	public static final String RAW_NUMBER_SHARD_BY = "periodId";
	public static final String RAW_NUMBER_TABLE_NAME = "tb_raw_number";

	public static final String TICKET_SHARD_TYPE = "ticketShardType";
	public static final String TICKET_SHARD_BY = "periodId";
	public static final String TICKET_TABLE_NAME = "tb_ticket";

	public static final String SUB_TICKET_SHARD_TYPE = "subTicketShardType";
	public static final String SUB_TICKET_SHARD_BY = "periodId";
	public static final String SUB_TICKET_TABLE_NAME = "tb_sub_ticket";

	public static final String MAPPING_MATCH_SHARD_TYPE = "mappingMatchShardType";
	public static final String MAPPING_MATCH_SHARD_BY = "periodId";
	public static final String MAPPING_MATCH_TABLE_NAME = "tb_mapping_match";

	public static final String MAPPING_ORDER_SHARD_TYPE = "mappingOrderShardType";
	public static final String MAPPING_ORDER_SHARD_BY = "periodId";
	public static final String MAPPING_ORDER_TABLE_NAME = "tb_order,tb_mapping_match";

	public static final String RED_PACKET_SHARD_TYPE = "redPacketShardType";
	public static final String RED_PACKET_SHARD_BY = "userId";
	public static final String RED_PACKET_TABLE_NAME = "tb_red_packet";

	/* meizu send notify url */
	public final static String MEIZU_SEND_URL = "http://life-ns.meizu.com/notify/v2/lotteryball/callback.do";

	/* meizu params md5 key */
	public final static String MEIZU_MD5_KEY = "LlNblAdLEJ9i3f43ii4fGFc1hMCe3DL5";

	/* hecai wsdl url */
	public final static String HECAI_WSDL_URL = "http://gp.he-lot.cn:8989/newif/services/Iprotocol?wsdl";
	public final static String HECAI_COMMON_WSDL_URL = "http://jc.he-lot.cn:8080/newif/services/Iprotocol?wsdl";

	/* 测试环境没有给ticketid 落地票号 */
	public final static Boolean HECAI_CHECK_TICKET_NO = Boolean.TRUE;
	public final static String HLJ_PROTOCOL_VERSION = "1500";
}
