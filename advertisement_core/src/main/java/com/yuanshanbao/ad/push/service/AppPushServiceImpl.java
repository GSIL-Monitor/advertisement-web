package com.yuanshanbao.ad.push.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.uitls.AppConditions;
import com.gexin.rp.sdk.base.uitls.AppConditions.OptType;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.AbstractTemplate;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.ad.app.model.AppType;
import com.yuanshanbao.ad.push.dao.AppPushDao;
import com.yuanshanbao.ad.push.model.AppPush;
import com.yuanshanbao.ad.push.model.AppPushType;
import com.yuanshanbao.ad.user.service.UserService;

@Service
public class AppPushServiceImpl implements AppPushService {

	/*
	 * 兴贷
	 */
	//	private static String appId = "EhN1A77ifT83pzUpSAFSP4";
	//	private static String appKey = "WKlRUVyHsJ9RLULXQE7nd1";
	//	private static String masterSecret = "qJZEgii8Uq93YTmHwVcIs1";

	/*
	 * 兴贷正式
	 */
	//	private static String appId = "xCXzJNjH0A73GWTyJacdx1";
	//	private static String appKey = "P5IfkWEcBw8rWrXaEtVYe5";
	//	private static String masterSecret = "kWnsCLRgAg8bpCQoDO2PC7";

	/*
	 * 瑞贷
	 */
	//	private static String appId = "zLO5RizGGn6qMgSdyWfVd1"; 
	//	private static String appKey = "BTUH5HHB6Q9CwO8GChcrC7"; 
	//	private static String masterSecret = "yO5PR5qeDsAF0TchDX2t96";
	
	/*
	 * 保险经理人
	 */
	private static String appId = "BbsBYs230z6fW0b7WnV341";
	private static String appKey = "PNTgLxY43V6jX63A6JA3b3";
	private static String masterSecret = "CVVH4hdAMY5dUs00nsSfA2";

	private static String URL = "http://sdk.open.api.igexin.com/apiex.htm";
	private static String defaultLogo = "http://yuanshanbao.oss-cn-beijing.aliyuncs.com/file/ms/1504504844597_8133.png";

	@Autowired
	private AppPushDao appPushDao;

	@Autowired
	private UserService userService;

	@Override
	public void insertAppPush(AppPush appPush) {
		int result = -1;

		result = appPushDao.insertAppPush(appPush);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateAppPush(AppPush appPush) {
		int result = -1;

		result = appPushDao.updateAppPush(appPush);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteAppPush(AppPush appPush) {
		int result = -1;

		result = appPushDao.deleteAppPush(appPush);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public List<AppPush> selectAppPush(AppPush appPush, PageBounds pageBounds) {
		return setProperty(appPushDao.selectAppPush(appPush, pageBounds));
	}

	private List<AppPush> setProperty(List<AppPush> list) {
		if (list.size() > 0) {
			for (AppPush appPush : list) {
				if (appPush.getUserId() != null) {
					appPush.setUser(userService.selectUserById(appPush.getUserId()));
				}
			}
			return list;
		}
		return null;
	}

	@Override
	public AppPush selectAppPush(Long pushId) {
		if (pushId == null) {
			return null;
		}
		AppPush appPush = new AppPush();
		appPush.setPushId(pushId);
		List<AppPush> list = selectAppPush(appPush, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<Long, AppPush> selectAppPushByIds(List<Long> pushIds) {
		Map<Long, AppPush> map = new HashMap<Long, AppPush>();
		if (pushIds == null || pushIds.size() == 0) {
			return map;
		}
		List<AppPush> list = appPushDao.selectAppPushByIds(pushIds);
		for (AppPush ad : list) {
			map.put(ad.getPushId(), ad);
		}
		return map;
	}

	@Override
	public void pushToSingle(AppPush appPush, SingleMessage message, Target target, String requestId) {

	}

	@Override
	public void pushToSingle(AppPush push, SingleMessage message, Target target) {
		IGtPush igtPush = new IGtPush(URL, push.getAppPushKey(), push.getAppMasterSecret());
		IPushResult ret = igtPush.pushMessageToSingle(message, target);
		LoggerUtil.info("[pushToSingle]" + ret.getResponse().toString());
	}

	@Override
	public void pushToList(AppPush appPush, ListMessage message, List<Target> targetList, String requestId) {

	}

	@Override
	public void pushToList(AppPush appPush, ListMessage message, List<Target> targetList) {

	}

	@Override
	public void pushToApp(AppPush push, AppMessage message, AppConditions condition, List<Target> targetList) {
		IGtPush igtPush = new IGtPush(URL, push.getAppPushKey(), push.getAppMasterSecret());
		List<String> appIds = new ArrayList<String>();
		appIds.add(push.getAppPushId());
		message.setConditions(condition);
		message.setAppIdList(appIds);
		IPushResult ret = igtPush.pushMessageToApp(message, "任务别名_toApp");
		LoggerUtil.info("[pushToApp]" + ret.getResponse().toString());
	}

	@Override
	public void pushToAll(AppPush push, AppMessage message, AppConditions condition) {
		pushToApp(push, message, condition, null);
	}

	public static TransmissionTemplate getTemplate(AppPush push) {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(push.getAppPushId());
		template.setAppkey(push.getAppPushKey());
		template.setTransmissionContent("透传内容");
		template.setTransmissionType(2);
		APNPayload payload = new APNPayload();
		// 在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
		payload.setAutoBadge("+1");
		payload.setContentAvailable(1);
		payload.setSound("default");
		payload.setCategory(push.getUrl());
		if (push.getIsSimple()) {
			// 简单模式APNPayload.SimpleMsg
			payload.setAlertMsg(new APNPayload.SimpleAlertMsg(push.getContent()));
		} else {
			// 字典模式使用APNPayload.DictionaryAlertMsg
			payload.setAlertMsg(getDictionaryAlertMsg(push.getTitle(), push.getContent()));
		}
		// 添加多媒体资源
		if (push != null && push.getMedia() != null) {
			payload.addMultiMedia(push.getMedia());
		}
		template.setAPNInfo(payload);
		return template;
	}

	@Override
	public void sendPush(AppPush push) {

		if (AppPushType.IOS == push.getClient()) {
			TransmissionTemplate template = getTemplate(push);
			setPush(push, template, AppPushType.IOS_PREFIX);
		} else if (AppPushType.ANDROID == push.getClient()) {
			NotificationTemplate template = notificationTemplate(push);
			setPush(push, template, AppPushType.ANDROID_PREFIX);
		} else if (AppPushType.ALL == push.getClient()) {
			TransmissionTemplate template = getTemplate(push);
			setPush(push, template, AppPushType.IOS_PREFIX);
			NotificationTemplate template1 = notificationTemplate(push);
			setPush(push, template1, AppPushType.ANDROID_PREFIX);
		}

	}

	private void setPush(AppPush push, AbstractTemplate template, String prefix) {
		AppConditions cdt = new AppConditions();
		if (StringUtils.isNotBlank(push.getUserId())) {
			List<Target> targetList = new ArrayList<Target>();
			Target target = new Target();
			target.setAppId(push.getAppPushId());
			target.setAlias(prefix + push.getUserId());
			targetList.add(target);
			SingleMessage message = new SingleMessage();
			message.setData(template);
			message.setOffline(push.getIsOffline());
			message.setOfflineExpireTime(push.getExpireTime());
			pushToSingle(push, message, target);
		} else {
			List<String> phoneTypeList = new ArrayList<String>();
			phoneTypeList.add("ANDROID");
			cdt.addCondition(AppConditions.PHONE_TYPE, phoneTypeList, OptType.or);
			AppMessage message = new AppMessage();
			message.setData(template);
			message.setOffline(push.getIsOffline());
			message.setOfflineExpireTime(push.getExpireTime());
			if (prefix.equals(AppPushType.ANDROID_PREFIX)) {
				pushToAll(push, message, cdt);
			} else {
				pushToAll(push, message, null);
			}

		}
	}

	public static void main(String[] args) {
		AppPush push = new AppPush();
		push.setAppPushKey(appKey);
		push.setAppMasterSecret(masterSecret);
		push.setAppPushId(appId);
		push.setTitle("正式环境推送测试");
		push.setContent("正式环境推送测试" + DateUtils.getCurrentTime());
		push.setIsOffline(true);
		push.setIsSimple(false);
		push.setUrl("ruidai://welfareList?categoryId=2");
		push.setSound("default");
		push.setUserId("100000254");
		push.setExpireTime(60 * 1000L);
		push.setClient(AppPushType.ALL);
		push.setType(AppType.XINGDAI_ID);
		AppPushServiceImpl appPushServiceImpl = new AppPushServiceImpl();
		appPushServiceImpl.sendPush(push);
	}

	/*
	 * IOS推送模板
	 */
	private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(String title, String body) {
		APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
		alertMsg.setBody(body);
		alertMsg.setActionLocKey("ActionLockey");
		alertMsg.setLocKey("LocKey");
		alertMsg.addLocArg("loc-args");
		alertMsg.setLaunchImage("launch-image");
		// iOS8.2以上版本支持
		alertMsg.setTitle(title);
		alertMsg.setTitleLocKey("TitleLocKey");
		alertMsg.addTitleLocArg("TitleLocArg");
		return alertMsg;
	}

	public static NotificationTemplate notificationTemplate(AppPush push) {
		NotificationTemplate template = new NotificationTemplate();
		// 设置APPID与APPKEY
		template.setAppId(push.getAppPushId());
		template.setAppkey(push.getAppPushKey());
		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		if (StringUtils.isNotBlank(push.getUrl())) {
			template.setTransmissionType(2);
		} else {
			template.setTransmissionType(1);
		}
		template.setTransmissionContent(push.getUrl());
		// 设置定时展示时间
		// template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
		Style0 style = new Style0();
		// 设置通知栏标题与内容
		style.setTitle(push.getTitle());
		style.setText(push.getContent());
		// 配置通知栏图标
		style.setLogo(defaultLogo);
		// 配置通知栏网络图标
		style.setLogoUrl(defaultLogo);
		// 设置通知是否响铃，震动，或者可清除
		style.setRing(true);
		style.setVibrate(true);
		style.setClearable(true);
		template.setStyle(style);

		return template;
	}

	public static LinkTemplate linkTemplate(AppPush push) {
		LinkTemplate template = new LinkTemplate();
		template.setAppId(push.getAppPushId());
		template.setAppkey(push.getAppPushKey());

		Style0 style = new Style0();
		// 设置通知栏标题与内容
		style.setTitle(push.getTitle());
		style.setText(push.getContent());
		// 配置通知栏图标
		style.setLogo(defaultLogo);
		// 配置通知栏网络图标
		style.setLogoUrl(defaultLogo);
		// 设置通知是否响铃，震动，或者可清除
		style.setRing(true);
		style.setVibrate(true);
		style.setClearable(true);
		template.setStyle(style);
		template.setUrl(push.getUrl());

		return template;
	}

}
