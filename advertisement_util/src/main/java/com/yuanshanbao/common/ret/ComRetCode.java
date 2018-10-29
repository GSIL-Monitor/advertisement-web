package com.yuanshanbao.common.ret;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import com.yuanshanbao.common.util.LoggerUtil;

public class ComRetCode {
	/* 新接口返回属性 */
	public static final String RET_CODE = "retCode";
	public static final String RET_DESC = "retDesc";
	public static final String RET_FIELD = "retField";
	public static final String RET = "ret";

	public static final String PAGINTOR = "paginator";
	public static final String PAGINTOR_ITEM_LIST = "pageItemList";

	/**
	 * 200 成功
	 */
	public static final int SUCCESS = 200;
	public static final String SUCCESS_DESC = "操作成功";

	/** #20 通用错误 **/

	/* 201请不要重复提交请求 */
	public static final int SUCCESS_DUPLICATED = 201;
	public static final String SUCCESS_DUPLICATED_DESC = "请不要重复提交请求";

	/* 202网络异常，请稍后再试！ */
	public static final int FAIL = 202;
	public static final String FAIL_DESC = "网络异常，请稍后再试！";

	/* 205系统错误 */
	public static final int COMMON_FAIL = 205;
	public static final String COMMON_FAIL_DESC = "系统错误";

	/* 203网络异常，请稍后再试！ */
	public static final int FAIL_PARTNER = 203;
	public static final String FAIL_PARTNER_DESC = "网络异常，请稍后再试！";

	/* 204网络异常，请稍后再试！ */
	public static final int FAIL_DB = 204;
	public static final String FAIL_DB_DESC = "网络异常，请稍后再试！";

	/** #21 登录相关错误 **/

	/* 211用户已存在。 */
	public static final int USER_EXIST = 211;
	public static final String USER_EXIST_DESC = "用户已存在。";

	/* 212用户不存在。 */
	public static final int USER_NOT_EXIST = 212;
	public static final String USER_NOT_EXIST_DESC = "用户不存在。";

	/* 213用户名或密码错误。 */
	public static final int LOGIN_FAIL = 213;
	public static final String LOGIN_FAIL_DESC = "用户名或密码错误。";

	/* 214帐号未登录，请登录后再操作 */
	public static final int NOT_LOGIN = 214;
	public static final String NOT_LOGIN_DESC = "帐号未登录，请登录后再操作";

	/* 215登录已超时 */
	public static final int LOGIN_TIMEOUT = 215;
	public static final String LOGIN_TIMEOUT_DESC = "登录已超时";

	/* 216登录状态已失效，请重新登录 */
	public static final int TOKEN_INVALID = 216;
	public static final String TOKEN_INVALID_DESC = "登录状态已失效，请重新登录";

	/* 217您的帐号已锁定，请联系客服解除 */
	public static final int USER_LOCKED = 217;
	public static final String USER_LOCKED_DESC = "您的帐号已锁定，请联系客服解除";

	/* 218邮箱已存在，请重新填写 */
	public static final int USER_EMAIL_EXIST = 218;
	public static final String USER_EMAIL_EXIST_DESC = "邮箱已存在，请重新填写";

	/* 219你还没有填写昵称，请填写后操作 */
	public static final int USER_NOT_COMPLETE_INFO_ERROR = 219;
	public static final String USER_NOT_COMPLETE_INFO_ERROR_DESC = "你还没有填写昵称，请填写后操作";

	/* 220微信登录失败。 */
	public static final int WEIXIN_LOGIN_FAIL = 220;
	public static final String WEIXIN_LOGIN_FAIL_DESC = "微信登录失败。";

	/** #26 流程错误 **/

	/* 261手机验证已超时，请重新验证 */
	public static final int VERIFY_MOBILE_TIMEOUT = 261;
	public static final String VERIFY_MOBILE_TIMEOUT_DESC = "手机验证已超时，请重新验证";

	/* 262订单编号错误 */
	public static final int PARAM_SERIALNO_WRONG = 262;
	public static final String PARAM_SERIALNO_WRONG_DESC = "订单编号错误";

	/* 263请填写正确的短信验证码 */
	public static final int SMS_CODE_WRONG = 263;
	public static final String SMS_CODE_WRONG_DESC = "请填写正确的短信验证码";

	/* 264资质审核中，暂时无法修改信息 */
	public static final int BROKER_AUDITING = 264;
	public static final String BROKER_AUDITING_DESC = "资质审核中，暂时无法修改信息";

	/** #30 通用格式错误 **/

	/* 301请求参数错误，请稍后重试 */
	public static final int WRONG_PARAMETER = 301;
	public static final String WRONG_PARAMETER_DESC = "请求参数错误，请稍后重试";

	/* 302IP校验失败 */
	public static final int IP_NOT_ALLOW = 302;
	public static final String IP_NOT_ALLOW_DESC = "IP校验失败";

	/* 303您填写的身份证号已经在近期领取过赠险 */
	public static final int ORDER_IDENTITY_CARD_HAS_EXIST_ERROR = 303;
	public static final String ORDER_IDENTITY_CARD_HAS_EXIST_ERROR_DESC = "您填写的身份证号已经在近期领取过赠险";

	/** #31 输入格式错误 **/

	/* 311原密码错误。 */
	public static final int OLD_PASSWORD_ERROR = 311;
	public static final String OLD_PASSWORD_ERROR_DESC = "原密码错误。";

	/* 312密码格式错误 */
	public static final int WRONG_PASSOWRD = 312;
	public static final String WRONG_PASSOWRD_DESC = "密码格式错误";

	/* 313请您填写正确的手机号 */
	public static final int WRONG_MOBILE = 313;
	public static final String WRONG_MOBILE_DESC = "请您填写正确的手机号";

	/* 314请您填写正确的姓名 */
	public static final int NAME_FORMAT_ERROR = 314;
	public static final String NAME_FORMAT_ERROR_DESC = "请您填写正确的姓名";

	/* 315请您填写正确的手机号 */
	public static final int MOBILE_FORMAT_ERROR = 315;
	public static final String MOBILE_FORMAT_ERROR_DESC = "请您填写正确的手机号";

	/* 316请您填写正确的身份证号 */
	public static final int IDNO_FORMAT_ERROR = 316;
	public static final String IDNO_FORMAT_ERROR_DESC = "请您填写正确的身份证号";

	/* 317请您填写正确的邮箱 */
	public static final int EMAIL_FORMAT_ERROR = 317;
	public static final String EMAIL_FORMAT_ERROR_DESC = "请您填写正确的邮箱";

	/* 337请您填写正确的生日 */
	public static final int BIRTHDAY_FORMAT_ERROR = 337;
	public static final String BIRTHDAY_FORMAT_ERROR_DESC = "请您填写正确的生日";

	/* 338请您选择性别 */
	public static final int GENDER_FORMAT_ERROR = 338;
	public static final String GENDER_FORMAT_ERROR_DESC = "请您选择性别";

	/* 339您填写的姓名中包含敏感词 */
	public static final int NAME_MINGAN_ERROR = 339;
	public static final String NAME_MINGAN_ERROR_DESC = "您填写的姓名中包含敏感词";

	/* 318您填写的邀请码不正确，请核实后填写 */
	public static final int REGISTER_INVITE_CODE_ERROR = 318;
	public static final String REGISTER_INVITE_CODE_ERROR_DESC = "您填写的邀请码不正确，请核实后填写";

	/* 319您填写的邀请码已使用 */
	public static final int REGISTER_INVITE_CODE_USED_ERROR = 319;
	public static final String REGISTER_INVITE_CODE_USED_ERROR_DESC = "您填写的邀请码已使用";

	/* 320上传头像图片错误 */
	public static final int UPLOAD_AVATAR_ERROR = 320;
	public static final String UPLOAD_AVATAR_ERROR_DESC = "上传头像图片错误";

	/* 322上传图片错误 */
	public static final int UPLOAD_IMAGE_ERROR = 322;
	public static final String UPLOAD_IMAGE_ERROR_DESC = "上传图片错误";

	/* 321邮件验证链接已过期 */
	public static final int EMAIL_VERIFY_ERROR = 321;
	public static final String EMAIL_VERIFY_ERROR_DESC = "邮件验证链接已过期";

	/* 323活动不存在 */
	public static final int ACTIVITY_NOT_EXIST_ERROR = 323;
	public static final String ACTIVITY_NOT_EXIST_ERROR_DESC = "活动不存在";

	/** #40 贷款产品相关错误 **/

	/* 419产品不存在 */
	public static final int PRODUCT_NOT_EXIST_ERROR = 419;
	public static final String PRODUCT_NOT_EXIST_ERROR_DESC = "产品不存在";

	/* 420评论不存在 */
	public static final int COMMENT_NOT_EXIST_ERROR = 420;
	public static final String COMMENT_NOT_EXIST_ERROR_DESC = "评论不存在";

	/* 406订单不存在 */
	public static final int ORDER_NOT_EXIST_ERROR = 406;
	public static final String ORDER_NOT_EXIST_ERROR_DESC = "订单不存在";

	/* 401没有该操作的权限 */
	public static final int AUTHORITY_NOT_EXIST_ERROR = 401;
	public static final String AUTHORITY_NOT_EXIST_ERROR_DESC = "没有该操作的权限";

	/* 403产品版本未开放 */
	public static final int PRODUCT_VERSION_NOT_OPEN_ERROR = 403;
	public static final String PRODUCT_VERSION_NOT_OPEN_ERROR_DESC = "产品版本未开放";

	/* 402产品攻略不存在 */
	public static final int PRODUCT_STRATEGY_NOT_EXIST_ERROR = 402;
	public static final String PRODUCT_STRATEGY_NOT_EXIST_ERROR_DESC = "产品攻略不存在";

	/* 405请您填写正确的过期时间 */
	public static final int APP_PUSH_EXPIRETIME_NOT_EXIST_ERROR = 405;
	public static final String APP_PUSH_EXPIRETIME_NOT_EXIST_ERROR_DESC = "请您填写正确的过期时间";

	/* 407锁库存失败 */
	public static final int ORDER_LOCK_STOCK_FAIL_ = 407;
	public static final String ORDER_LOCK_STOCK_FAIL_ERROR = "锁库存失败";

	/* 410你不符合领取该产品的资格 */
	public static final int ORDER_DELIVER_ERROR = 410;
	public static final String ORDER_DELIVER_ERROR_DESC = "你不符合领取该产品的资格";

	/* 411您的基本信息不完全 */
	public static final int INFORMATION_NOT_COMPLETE = 411;
	public static final String INFORMATION_NOT_COMPLETE_DESC = "您的基本信息不完全";

	/* 409提交信息不能为空 */
	public static final int INFORMATION_EMPTY_ERROR = 409;
	public static final String INFORMATION_EMPTY_ERROR_DESC = "提交信息不能为空";

	/* 408您已经领取过该产品 */
	public static final int APPLY_EXIST_ERROR = 408;
	public static final String APPLY_EXIST_ERROR_DESC = "您已经领取过该产品";

	/* 404无符合营销手机号 */
	public static final int MARKETING_SMS_NOT_VERIFY_ERROR = 404;
	public static final String MARKETING_SMS_NOT_VERIFY_ERROR_DESC = "无符合营销手机号";

	/** #80 次数限制错误 **/

	/* 501您获取短信验证码次数过多，请明天再试 */
	public static final int SMS_SEND_TIME_LIMIT_OVER = 501;
	public static final String SMS_SEND_TIME_LIMIT_OVER_DESC = "您获取短信验证码次数过多，请明天再试";

	/* 502您输入短信验证码错误次数过多，请明天再试 */
	public static final int SMS_VERIFY_TIME_LIMIT_OVER = 502;
	public static final String SMS_VERIFY_TIME_LIMIT_OVER_DESC = "您输入短信验证码错误次数过多，请明天再试";

	/* 503您输入密码错误次数过多，请明天再试 */
	public static final int PWD_LOGIN_FAIL_TIME_LIMIT_OVER = 503;
	public static final String PWD_LOGIN_FAIL_TIME_LIMIT_OVER_DESC = "您输入密码错误次数过多，请明天再试";

	/* 801抽奖机会已用完 */
	public static final int GAME_NO_PRIZE_ERROR = 801;
	public static final String GAME_NO_PRIZE_ERROR_DESC = "抽奖机会已用完";

	/* 805活动组合进行跳转 */
	public static final int GAME_NO_PRIZE_AND_JUMP = 805;
	public static final String GAME_NO_PRIZE_AND_JUMP_DESC = "活动组合进行跳转";

	/* 802请联系管理员配置不校验手机号 */
	public static final int CONFIG_NO_SMS_FORMAT_ERROR = 802;
	public static final String CONFIG_NO_SMS_FORMAT_ERROR_DESC = "请联系管理员配置不校验手机号";

	/* 803您填写的手机号已经在近期领取过赠险 */
	public static final int ORDER_MOBILE_HAS_EXIST_ERROR = 803;
	public static final String ORDER_MOBILE_HAS_EXIST_ERROR_DESC = "您填写的手机号已经在近期领取过赠险";


	/* 804,没有银行卡 */
	public static final int NO_BANK_CARD = 804;
	public static final String NO_BANK_CARD_DESC = "您还没有添加银行卡";

	/* 805,没有实名认证 */
	public static final int NO_IDENTITY = 805;
	public static final String NO_IDENTITY_DESC = "您还没有实名认证";

	/* 806,验证上次真实姓名 */
	public static final int NO_USERNAME = 806;
	public static final String NO_USERNAME_DESC = "姓名与上一次不一致";

	/* 807,验证上次真实姓名 */
	public static final int NO_AGENCY = 807;
	public static final String NO_AGENCY_DESC = "抱歉，还没有代理人";

	/* 808,银行卡格式错误 */
	public static final int BANK_CAED_ERRO = 808;
	public static final String BANK_CAED_ERRO_DESC = "请输入正确的银行卡号";


	/* 809,提交的意见不能为空 */
	public static final int NO_OPIOION = 809;
	public static final String NO_OPIOION_DESC = "内容不能为空";

	public static Map<Integer, String> retCodeMap = new LinkedHashMap<Integer, String>();

	static {
		try {
			Field[] fields = ComRetCode.class.getFields();
			String descriptionEnd = "_DESC";
			for (Field field : fields) {
				String name = field.getName();
				if (name.endsWith(descriptionEnd)) {
					Field keyField = ComRetCode.class.getField(name.substring(0,
							name.length() - descriptionEnd.length()));
					Object key = keyField.get(null);
					String value = field.get(null).toString();
					if (key instanceof Integer) {
						String existValue = retCodeMap.get(key);
						if (existValue != null) {
							LoggerUtil.error("Duplicated ComRetCode", new IllegalArgumentException());
						} else {
							retCodeMap.put((Integer) key, value);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String message(int value) {
		String msg = retCodeMap.get(value);
		if (msg == null) {
			msg = FAIL_DESC;
		}
		return msg;
	}
}
