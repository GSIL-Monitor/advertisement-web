package com.yuanshanbao.dsp.push.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.push.model.AppPush;

@Repository
public class AppPushDaoImpl extends BaseDaoImpl implements AppPushDao {

	@Override
	public int insertAppPush(AppPush appPush) {
		return getSqlSession().insert("appPush.insertAppPush", appPush);
	}

	@Override
	public int updateAppPush(AppPush appPush) {
		return getSqlSession().update("appPush.updateAppPush", appPush);
	}

	@Override
	public int deleteAppPush(AppPush appPush) {
		return getSqlSession().delete("appPush.deleteAppPush", appPush);
	}

	@Override
	public List<AppPush> selectAppPush(AppPush appPush, PageBounds pageBounds) {
		return getSqlSession().selectList("appPush.selectAppPush", appPush, pageBounds);
	}

	@Override
	public List<AppPush> selectAppPushByIds(List<Long> appPushIds) {
		if (appPushIds == null || appPushIds.size() == 0) {
			return new ArrayList<AppPush>();
		}

		return getSqlSession().selectList("appPush.selectAppPushByIds", appPushIds);
	}

}
