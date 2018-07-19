package com.yuanshanbao.dsp.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.AES;
import com.yuanshanbao.common.util.CommonUtil;
import com.yuanshanbao.common.util.JacksonUtil;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.MD5Util;
import com.yuanshanbao.dsp.app.dao.AppDao;
import com.yuanshanbao.dsp.app.model.AppInfo;
import com.yuanshanbao.dsp.channel.model.Channel;
import com.yuanshanbao.dsp.channel.model.ChannelPromotionInfo;
import com.yuanshanbao.dsp.channel.model.PromotionStatus;
import com.yuanshanbao.dsp.channel.service.ChannelPromotionInfoService;
import com.yuanshanbao.dsp.channel.service.ChannelService;
import com.yuanshanbao.dsp.common.constant.CommonConstant;
import com.yuanshanbao.dsp.core.IniBean;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class AppServiceImpl implements AppService {

	private static final String restrictedIdfa = "00000000-0000-0000-0000-000000000000";

	@Resource
	private AppDao appDao;

	@Resource
	private ChannelPromotionInfoService promotionInfoService;

	@Resource
	private ChannelService channelService;

	@Override
	public void initialAppInfo(AppInfo appInfo) {
		generateAppId(appInfo);
		appDao.initialAppInfo(appInfo);
	}

	public void generateAppId(AppInfo appInfo) {
		String key = getPrivateKey();
		String keySource = CommonUtil.getRandomID();
		keySource += CommonConstant.COMMON_COLON_STR + appInfo.getSystemName() + appInfo.getProductVersion();
		keySource += CommonConstant.COMMON_COLON_STR
				+ (StringUtils.isNotBlank(appInfo.getAppKey()) ? appInfo.getAppKey() : "");
		keySource += CommonConstant.COMMON_COLON_STR
				+ (StringUtils.isNotBlank(appInfo.getSourceChannel()) ? appInfo.getSourceChannel() : "");
		try {
			String appId = AES.encrypt(keySource, key);
			appInfo.setAppId(appId);
			appInfo.setKey(MD5Util.getAESKey(keySource));
			activeDeliver(appInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String decryptAppKey(String appId) {
		try {
			String keySource = AES.decrypt(appId, getPrivateKey());
			return MD5Util.getAESKey(keySource);
		} catch (Exception e) {
			LoggerUtil.error("decryptAppKey", e);
		}
		return null;
	}

	public boolean activeDeliver(AppInfo appInfo) {
		boolean gorderResult = true;
		if (appInfo == null || StringUtils.isBlank(appInfo.getSystemName())) {
			throw new BusinessException();
		}
		ChannelPromotionInfo promotionInfo = new ChannelPromotionInfo();
		if (appInfo.getUniqueId().equals(restrictedIdfa)) {
			LoggerUtil.order("未授权的IDFA或者IMEI, appInfo={}", JacksonUtil.obj2json(appInfo));
			return false;
		}

		if (appInfo.getSystemName().equals("iOS")) {
			promotionInfo.setIdfa(appInfo.getUniqueId());
		} else if (appInfo.getSystemName().equals("android")) {
			promotionInfo.setImei(appInfo.getUniqueId());
		} else {
			throw new BusinessException();
		}
		List<ChannelPromotionInfo> list = promotionInfoService.selectChannelPromotionInfos(promotionInfo,
				new PageBounds());
		if (list.size() > 0) {
			promotionInfo = list.get(0);
		} else {
			// idfa为空，使用IP+UA
			promotionInfo = new ChannelPromotionInfo();
			promotionInfo.setIp(appInfo.getIp());
			promotionInfo.setDeviceType(appInfo.getDeviceType());
			promotionInfo.setSystemName(appInfo.getSystemName());
			promotionInfo.setSystemVersion(appInfo.getSystemVersion());
			list = promotionInfoService.selectChannelPromotionInfos(promotionInfo, new PageBounds());
			if (list.size() > 0) {
				promotionInfo = list.get(0);
			} else {
				LoggerUtil.order("未找到匹配的promotionInfo, appInfo={}", JacksonUtil.obj2json(appInfo));
				return false;
			}
		}

		try {
			boolean result;
			Channel channel = promotionInfo.getChannelObject();
			Double bonus = channel.getBonus();
			if (bonus == null) {
				bonus = 1D;
			}
			boolean withhold = Math.random() < bonus;
			if (channel != null && promotionInfo.getStatus() != null
					&& promotionInfo.getStatus().equals(PromotionStatus.INACTIVE)) {
				if (withhold) {
					LoggerUtil.order("开始激活反馈, appInfo={}", JacksonUtil.obj2json(appInfo));
					String deliverOrderUrl = promotionInfo.getChannelObject().getDeliverOrderUrl();
					if (StringUtils.isNotBlank(deliverOrderUrl)) {
						// IDeliverNotify notify = (IDeliverNotify)
						// Class.forName(
						// deliverOrderUrl).newInstance();
						// result = notify.sendActive(promotionInfo);
						result = true;
					} else {
						result = false;
					}
					if (result) {
						LoggerUtil.order("成功激活反馈, result={},appInfo={}", result, JacksonUtil.obj2json(appInfo));
					} else {
						gorderResult = false;
					}
					if (gorderResult) {
						// 更新promotionInfo状态为已发送
						promotionInfo.setStatus(PromotionStatus.CALLBACK);
					} else {
						promotionInfo.setStatus(PromotionStatus.CALLBACK_FAILED);
					}
				} else {
					promotionInfo.setStatus(PromotionStatus.WITHHOLD);
				}
				promotionInfoService.updateChannelPromotionInfo(promotionInfo);
			}
		} catch (Exception e) {
			LoggerUtil.error("activeDeliver", e);
			throw new BusinessException(ComRetCode.COMMON_FAIL);
		}
		return gorderResult;
	}

	@Override
	public Map<String, String> decryptParameters(String appId, String params) {
		Map<String, String> result = new HashMap<String, String>();
		String decryptParams = null;
		try {
			if (appId == null) {
				decryptParams = AES.decrypt(params);
			} else {
				String key = decryptAppKey(appId);
				if (key != null) {
					decryptParams = AES.decrypt(params, key);
				}
			}
			String[] segs = decryptParams.split("&");
			for (String seg : segs) {
				String[] param = seg.split("=");
				if (param.length >= 2) {
					result.put(param[0], param[1]);
				}
			}
		} catch (Exception e) {
			LoggerUtil.error("decryptAppKey", e);
		}
		return result;
	}

	/**
	 * 获取密钥
	 * 
	 * @param url
	 * @return
	 */
	public String getPrivateKey() {
		String default_key = "e4503d4d8b8033a20ad88471819de38f";// 默认配置

		// 获取密钥
		String priKey = IniBean.getIniValue("app_encrypt_key");
		if (StringUtils.isBlank(priKey)) {
			priKey = default_key;
		}
		return priKey;
	}

	@Override
	public String getApplicationType(String appId) {
		try {
			if (StringUtils.isBlank(appId)) {
				return null;
			}
			String keySource = AES.decrypt(appId, getPrivateKey());
			if (StringUtils.isBlank(keySource)) {
				return null;
			}
			String[] segs = keySource.split(CommonConstant.COMMON_ESCAPE_STR + CommonConstant.COMMON_COLON_STR);
			if (segs.length > 1) {
				return segs[1];
			}
		} catch (Exception e) {
			LoggerUtil.error("[getApplicationType]", e);
		}
		return null;
	}

	@Override
	public String getAppKey(String appId) {
		try {
			if (StringUtils.isBlank(appId)) {
				return null;
			}
			String keySource = AES.decrypt(appId, getPrivateKey());
			if (StringUtils.isBlank(keySource)) {
				return null;
			}
			String[] segs = keySource.split(CommonConstant.COMMON_ESCAPE_STR + CommonConstant.COMMON_COLON_STR);
			if (segs.length > 2) {
				return segs[2];
			}
		} catch (Exception e) {
			LoggerUtil.error("[getAppKey]", e);
		}
		return null;
	}

	@Override
	public String getAppChannel(String appId) {
		try {
			if (StringUtils.isBlank(appId)) {
				return null;
			}
			String keySource = AES.decrypt(appId, getPrivateKey());
			if (StringUtils.isBlank(keySource)) {
				return null;
			}
			String[] segs = keySource.split(CommonConstant.COMMON_ESCAPE_STR + CommonConstant.COMMON_COLON_STR);
			if (segs.length > 3) {
				return segs[3];
			}
		} catch (Exception e) {
			LoggerUtil.error("[getAppChannel]", e);
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		/*
		 * String result = AES.encrypt("mobile=18810503302&smsCode=1128",
		 * "485978f55c45a542c228438616aa601a"); System.out.println(result);
		 * result = AES.decrypt(
		 * "0A5CF8E1E911FDB81E9A29010768DE0FFADED225A6B186225B9CA1C586EFB6C4BEB57AD65D9CA7528372447B9E4282A7"
		 * , "NULL"); System.out.println(result); String[] segs =
		 * result.split(CommonConstant.COMMON_ESCAPE_STR +
		 * CommonConstant.COMMON_COLON_STR); if (segs.length == 2) { String
		 * invalidProductVersion = "iOS1.0"; if
		 * (invalidProductVersion.contains(segs[1])) { throw new
		 * BusinessException(ComRetCode.PRODUCT_VERSION_NOT_OPEN_ERROR); } }
		 * System.out.println(result);
		 */
		String appId = "E30CD99EE8D8CE5B9C6057EDCE7C34602DB0E923967716ED6D9F2F8C70CFE0FB0461677A3C8C32D781B52043C592B9FFA4FCA2513D102B5BB4D7AF1127C40816EC5F33C12CC6D0C7D9106BFD74CE30E5DC89599762DA5D317D76EC751F90A719EEC995B6A09566CA83CCECD01DDB8FE0";
		String keySource = AES.decrypt(appId, "e4503d4d8b8033a20ad88471819de38f");
		System.out.println(keySource);
		if (StringUtils.isBlank(keySource)) {
		}
		String[] segs = keySource.split(CommonConstant.COMMON_ESCAPE_STR + CommonConstant.COMMON_COLON_STR);
		if (segs.length > 2) {
			System.out.println(segs[3]);
		}
	}
}
