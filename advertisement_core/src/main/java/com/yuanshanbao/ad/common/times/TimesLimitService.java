package com.yuanshanbao.ad.common.times;

public interface TimesLimitService {

	public TimesLimitModel getTimesLimitModel(String type, String key);

	public Integer getTimesLimit(String type, String key);

	public void updateTimesLimit(String type, String key, int newTimes);
}
