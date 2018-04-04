package com.yuanshanbao.ad.sms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.PropertyUtil;
import com.yuanshanbao.ad.location.service.LocationService;
import com.yuanshanbao.ad.location.service.MobileLocationService;
import com.yuanshanbao.ad.sms.dao.MarketingSmsDao;
import com.yuanshanbao.ad.sms.model.MarketingSms;
import com.yuanshanbao.ad.sms.model.MarketingTask;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class MarketingSmsServiceImpl implements MarketingSmsService {

	public static String OSS_HOST_FILES = PropertyUtil.getProperty("oss.host.files");

	@Autowired
	private MarketingSmsDao marketingSmsDao;

	@Autowired
	private LocationService locationService;

	@Autowired
	private MobileLocationService mobileService;

	@Autowired
	private MarketingTaskService marketingTaskService;

	@Override
	public void insertMarketingSms(MarketingSms marketingSms) {

		int result = -1;

		result = marketingSmsDao.insertMarketingSms(marketingSms);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public void updateMarketingSms(MarketingSms marketingSms) {
		int result = -1;

		result = marketingSmsDao.updateMarketingSms(marketingSms);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteMarketingSms(MarketingSms marketingSms) {
		int result = -1;

		result = marketingSmsDao.deleteMarketingSms(marketingSms);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public List<MarketingSms> selectMarketingSms(MarketingSms marketingSms, PageBounds pageBounds) {
		return setProperty(marketingSmsDao.selectMarketingSms(marketingSms, pageBounds));
	}

	private List<MarketingSms> setProperty(List<MarketingSms> marketingSmsList) {
		List<Long> marketingTaskIds = new ArrayList<Long>();
		for (MarketingSms marketingSms : marketingSmsList) {
			marketingTaskIds.add(marketingSms.getMarketingTaskId());
		}
		Map<Long, MarketingTask> taskMap = marketingTaskService.selectMarketingTaskByIds(marketingTaskIds);
		for (MarketingSms marketingSms : marketingSmsList) {
			marketingSms.setMarketingTask(taskMap.get(marketingSms.getMarketingTaskId()));
		}
		return marketingSmsList;
	}

	@Override
	public Map<String, List<MarketingSms>> selectMarketingSmsByMobiles(List<String> mobiles) {
		Map<String, List<MarketingSms>> map = new HashMap<String, List<MarketingSms>>();
		if (mobiles == null || mobiles.size() == 0) {
			return map;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("list", mobiles);
		return selectMarketingSmsByMobiles(paramMap);
	}

	@Override
	public Map<String, List<MarketingSms>> selectMarketingSmsByMobiles(Map<String, Object> map) {
		Map<String, List<MarketingSms>> smsMap = new HashMap<String, List<MarketingSms>>();
		if (map == null || map.size() == 0) {
			return smsMap;
		}
		List<MarketingSms> list = marketingSmsDao.selectMarketingSmsByMobile(map);
		for (MarketingSms marketingSms : list) {
			List<MarketingSms> smsList = smsMap.get(marketingSms.getMobile());
			if (smsList == null) {
				smsList = new ArrayList<MarketingSms>();
				smsMap.put(marketingSms.getMobile(), smsList);
			}
			smsList.add(marketingSms);
		}

		return smsMap;
	}

}
