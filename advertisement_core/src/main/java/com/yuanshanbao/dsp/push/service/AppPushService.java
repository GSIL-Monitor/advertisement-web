package com.yuanshanbao.dsp.push.service;

import java.util.List;
import java.util.Map;

import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.uitls.AppConditions;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.dsp.push.model.AppPush;

public interface AppPushService {

	public void pushToSingle(AppPush appPush, SingleMessage message, Target target, String requestId);

	public void pushToSingle(AppPush appPush, SingleMessage message, Target target);

	public void pushToList(AppPush appPush, ListMessage message, List<Target> targetList, String requestId);

	public void pushToList(AppPush appPush, ListMessage message, List<Target> targetList);

	public void pushToApp(AppPush appPush, AppMessage message, AppConditions condition, List<Target> targetList);

	public void pushToAll(AppPush appPush, AppMessage message, AppConditions condition);

	public void sendPush(AppPush push);

	/* From DB */

	public void insertAppPush(AppPush appPush);

	public void updateAppPush(AppPush appPush);

	public void deleteAppPush(AppPush appPush);

	public List<AppPush> selectAppPush(AppPush appPush, PageBounds pageBounds);

	public AppPush selectAppPush(Long appPushId);

	public Map<Long, AppPush> selectAppPushByIds(List<Long> appPushIds);

}
