package com.yuanshanbao.ad.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.ad.base.dao.BaseDaoImpl;
import com.yuanshanbao.ad.common.model.Alarm;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class AlarmDaoImpl extends BaseDaoImpl implements AlarmDao {
	@Override
	public List<Alarm> selectAlarms(Alarm alarm, PageBounds pageBounds) {
		return getSqlSession().selectList("alarm.selectAlarm", alarm, pageBounds);
	}

	@Override
	public int insertAlarm(Alarm alarm) {
		return getSqlSession().insert("alarm.insertAlarm", alarm);
	}

	@Override
	public int deleteAlarm(Alarm alarm) {
		return getSqlSession().delete("alarm.deleteAlarm", alarm);
	}

	@Override
	public int updateAlarm(Alarm alarm) {
		return getSqlSession().update("alarm.updateAlarm", alarm);
	}

}
