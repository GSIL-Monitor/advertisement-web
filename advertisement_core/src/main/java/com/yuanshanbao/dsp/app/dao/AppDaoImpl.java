package com.yuanshanbao.dsp.app.dao;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.app.model.AppInfo;
import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;

@Repository
public class AppDaoImpl extends BaseDaoImpl implements AppDao {

	@Override
	public void initialAppInfo(AppInfo appInfo) {
		getSqlSession().insert("App.insertAppInfo", appInfo);
	}

}