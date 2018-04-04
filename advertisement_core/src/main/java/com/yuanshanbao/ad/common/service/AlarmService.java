package com.yuanshanbao.ad.common.service;

import java.util.List;

import com.yuanshanbao.ad.common.model.Alarm;
import com.yuanshanbao.ad.user.model.User;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface AlarmService {

	public List<Alarm> selectAlarms(Alarm alarm, PageBounds pageBounds);

	public List<Alarm> selectAlarmsByType(Integer type);

	public Alarm selectAlarm(Long alarmId);

	public void insertAlarm(Alarm alarm);

	public void deleteAlarm(Long alarmId);

	public void deleteAlarm(Alarm alarm);

	public void updateAlarm(Alarm alarm);

	public void insertOrUpdateAlarm(Alarm alarm);

	public boolean hasRight(User user, int jobVerify);

}
