package com.yuanshanbao.ad.app.dao;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.ad.app.model.AppInfo;
import com.yuanshanbao.ad.base.dao.BaseDaoImpl;

@Repository
public class AppDaoImpl extends BaseDaoImpl implements AppDao {

	@Override
	public void initialAppInfo(AppInfo appInfo) {
		getSqlSession().insert("App.insertAppInfo", appInfo);
	}

}