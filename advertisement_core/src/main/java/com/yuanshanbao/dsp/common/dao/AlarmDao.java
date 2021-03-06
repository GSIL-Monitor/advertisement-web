package com.yuanshanbao.dsp.common.dao;

import java.util.List;

import com.yuanshanbao.dsp.common.model.Alarm;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface AlarmDao {

	public List<Alarm> selectAlarms(Alarm alarm, PageBounds pageBounds);

	public int insertAlarm(Alarm alarm);

	public int deleteAlarm(Alarm alarm);

	public int updateAlarm(Alarm alarm);
}
