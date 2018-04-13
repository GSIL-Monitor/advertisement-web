package com.yuanshanbao.dsp.push.dao;

import java.util.List;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.dsp.push.model.AppPush;

public interface AppPushDao {
	public int insertAppPush(AppPush appPush);

	public int updateAppPush(AppPush appPush);

	public int deleteAppPush(AppPush appPush);

	public List<AppPush> selectAppPush(AppPush appPush, PageBounds pageBounds);

	public List<AppPush> selectAppPushByIds(List<Long> appPushIds);
}
