package com.yuanshanbao.dsp.common.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.common.dao.AlarmDao;
import com.yuanshanbao.dsp.common.model.Alarm;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class AlarmServiceImpl implements AlarmService {

	@Autowired
	private AlarmDao alarmDao;

	@Override
	public List<Alarm> selectAlarms(Alarm alarm, PageBounds pageBounds) {
		return alarmDao.selectAlarms(alarm, pageBounds);
	}

	@Override
	public List<Alarm> selectAlarmsByType(Integer type) {
		Alarm alarm = new Alarm();
		alarm.setType(type);
		return alarmDao.selectAlarms(alarm, new PageBounds());
	}

	@Override
	public Alarm selectAlarm(Long alarmId) {
		Alarm alarm = new Alarm();
		alarm.setAlarmId(alarmId);
		List<Alarm> list = selectAlarms(alarm, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertAlarm(Alarm alarm) {
		int result = -1;

		result = alarmDao.insertAlarm(alarm);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteAlarm(Long alarmId) {
		Alarm alarm = new Alarm();
		alarm.setAlarmId(alarmId);
		deleteAlarm(alarm);
	}

	@Override
	public void deleteAlarm(Alarm alarm) {
		int result = -1;

		result = alarmDao.deleteAlarm(alarm);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateAlarm(Alarm alarm) {
		int result = -1;

		result = alarmDao.updateAlarm(alarm);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	/**
	 * @param alarm
	 * @return
	 * @throws IOException
	 */
	@Override
	public void insertOrUpdateAlarm(Alarm alarm) {
		if (alarm.getAlarmId() == null) {
			insertAlarm(alarm);
		} else {
			updateAlarm(alarm);
		}
	}

	@Override
	public boolean hasRight(User user, int type) {
		Alarm alarm = new Alarm();
		if (user == null) {
			return false;
		}
		alarm.setMobile(user.getMobile());
		alarm.setType(type);
		List<Alarm> list = selectAlarms(alarm, new PageBounds());
		return list != null && list.size() > 0;
	}

}
