package com.yuanshanbao.dsp.common.service;

import java.util.List;

import com.yuanshanbao.dsp.common.model.Alarm;
import com.yuanshanbao.dsp.user.model.User;
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
