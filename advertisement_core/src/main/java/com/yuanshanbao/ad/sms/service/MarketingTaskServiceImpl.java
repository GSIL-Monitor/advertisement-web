package com.yuanshanbao.ad.sms.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.HttpUtil;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.PropertyUtil;
import com.yuanshanbao.common.util.UploadUtils;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.ad.cache.IniCache;
import com.yuanshanbao.ad.core.CommonStatus;
import com.yuanshanbao.ad.location.model.MobileLocation;
import com.yuanshanbao.ad.location.service.LocationService;
import com.yuanshanbao.ad.location.service.MobileLocationService;
import com.yuanshanbao.ad.sms.dao.MarketingTaskDao;
import com.yuanshanbao.ad.sms.model.MarketingSms;
import com.yuanshanbao.ad.sms.model.MarketingTask;
import com.yuanshanbao.ad.sms.model.MarketingTaskType;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class MarketingTaskServiceImpl implements MarketingTaskService {

	public static String OSS_HOST_FILES = PropertyUtil.getProperty("oss.host.files");

	@Autowired
	private MarketingSmsService marketingSmsService;

	@Autowired
	private LocationService locationService;

	@Autowired
	private MobileLocationService mobileService;

	@Autowired
	private MarketingTaskDao marketingTaskDao;

	@Override
	public void insertMarketingTask(MarketingTask marketingTask) {

		int result = -1;

		result = marketingTaskDao.insertMarketingTask(marketingTask);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public void updateMarketingTask(MarketingTask marketingTask) {
		int result = -1;

		result = marketingTaskDao.updateMarketingTask(marketingTask);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public void deleteMarketingTask(MarketingTask marketingTask) {
		int result = -1;

		result = marketingTaskDao.deleteMarketingTask(marketingTask);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public List<MarketingTask> selectMarketingTask(MarketingTask marketingTask, PageBounds pageBounds) {
		return marketingTaskDao.selectMarketingTask(marketingTask, pageBounds);
	}

	@Override
	public Map<Long, MarketingTask> selectMarketingTaskByIds(List<Long> marketingTaskIds) {
		Map<Long, MarketingTask> map = new HashMap<Long, MarketingTask>();
		if (marketingTaskIds == null || marketingTaskIds.size() == 0) {
			return map;
		}
		List<MarketingTask> list = marketingTaskDao.selectMarketingTaskByIds(marketingTaskIds);
		for (MarketingTask marketingTask : list) {
			map.put(marketingTask.getMarketingTaskId(), marketingTask);
		}
		return map;
	}

	@Override
	public void executeMarketingTask(Long marketingTaskId) throws Exception {
		try {
			if (marketingTaskId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}

			MarketingTask marketingTask = selectMarketingTask(marketingTaskId);
			if (marketingTask == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			if (MarketingTaskType.RUNNING.equals(marketingTask.getStatus())) {
				throw new BusinessException(ComRetCode.SUCCESS_DUPLICATED);
			}
			if (StringUtils.isNotBlank(marketingTask.getDownloadUrl())) {
				throw new BusinessException(ComRetCode.SUCCESS_DUPLICATED);
			}

			Integer limit = marketingTask.getExportCount();
			String[] type = marketingTask.getDownloadMobileType().split(",");
			Map<String, Object> searchMarketingSmsMap = new HashMap<String, Object>();
			List<String> mobiles = getUploadFile(marketingTask.getUploadUrl());
			String rejectCity = marketingTask.getRejectCity();
			String rejectProvince = marketingTask.getRejectProvince();
			if (mobiles == null || mobiles.size() == 0) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}

			initSearchMarketingSmsMap(searchMarketingSmsMap);

			marketingTask.setStatus(MarketingTaskType.RUNNING);
			updateMarketingTask(marketingTask);

			int index = 0;
			String result = "";
			List<String> mobileList = new ArrayList<String>();
			List<String> tempList = new ArrayList<String>();
			LoggerUtil.info("[marketing task begin] marketingTaskId={}", marketingTaskId);
			for (String mobile : mobiles) {
				if (StringUtils.isBlank(mobile)) {
					continue;
				}
				if (!ValidateUtil.isNumber(mobile)) {
					continue;
				}

				mobileList.add(mobile);
				if (mobileList.size() == 10000) {
					List<String> prefixMobileList = getMobilePrefix(mobileList);
					Map<String, MobileLocation> map = mobileService.selectMobileLocationByPrefixs(prefixMobileList);
					searchMarketingSmsMap.put("list", mobileList);
					Map<String, List<MarketingSms>> marketingSmsMap = marketingSmsService
							.selectMarketingSmsByMobiles(searchMarketingSmsMap);
					for (String element : mobileList) {
						List<MarketingSms> marketingSmsList = marketingSmsMap.get(element);
						if (marketingSmsList != null && marketingSmsList.size() > 0) {
							continue;
						}

						String prefix = element.substring(0, 7);
						MobileLocation mo = map.get(prefix);
						if (mo == null || StringUtils.isBlank(mo.getOperator())) {
							continue;
						}

						if (isVerifyLocationCode(rejectCity, rejectProvince, mo)) {
							continue;
						}
						if (iscontainsOperator(type, mo.getOperator())) {
							if (index == limit) {
								break;
							}
							if (index % 100 == 0) {
								updateMarketingTaskProgress(marketingTaskId, index, limit);
							}
							result += element + "\r\n";
							tempList.add(element);
							index++;
						}
					}
					mobileList = new ArrayList<String>();
				}
			}

			if (mobileList.size() > 0 && index < limit) {
				List<String> prefixMobileList = getMobilePrefix(mobileList);
				Map<String, MobileLocation> map = mobileService.selectMobileLocationByPrefixs(prefixMobileList);
				searchMarketingSmsMap.put("list", mobileList);
				Map<String, List<MarketingSms>> marketingSmsMap = marketingSmsService
						.selectMarketingSmsByMobiles(searchMarketingSmsMap);
				for (String ele : mobileList) {
					List<MarketingSms> marketingSmsList = marketingSmsMap.get(ele);
					if (marketingSmsList != null && marketingSmsList.size() > 0) {
						continue;
					}

					String prefix = ele.substring(0, 7);
					MobileLocation mo = map.get(prefix);
					if (mo == null || StringUtils.isBlank(mo.getOperator())) {
						continue;
					}

					if (isVerifyLocationCode(rejectCity, rejectProvince, mo)) {
						continue;
					}
					if (iscontainsOperator(type, mo.getOperator())) {
						if (index == limit) {
							break;
						}
						if (index % 100 == 0) {
							updateMarketingTaskProgress(marketingTaskId, index, limit);
						}
						result += ele + "\r\n";
						tempList.add(ele);
						index++;
					}
				}
			}

			if (tempList == null || tempList.size() == 0) {
				throw new BusinessException(ComRetCode.MARKETING_SMS_NOT_VERIFY_ERROR);
			}
			updateMarketingTaskProgress(marketingTaskId, tempList.size(), limit);
			InputStream is = new ByteArrayInputStream(result.getBytes());
			String uploadPath = "form/" + marketingTask.getProductName() + "_" + marketingTask.getSmsPlatform() + "_"
					+ DateUtils.format(marketingTask.getSendTime(), "yyyy-MM-dd_HH点") + "_" + index + "手机号" + "_"
					+ new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ".txt";
			UploadUtils.uploadFiles(is, result.length(), uploadPath);
			insertMarketingSms(tempList, marketingTask.getMarketingTaskId());
			String path = OSS_HOST_FILES + "/" + uploadPath;
			LoggerUtil.info("[marketing success] marketingTaskId={}, productName={}, smsPlatform={}, path={}",
					marketingTask.getMarketingTaskId(), marketingTask.getProductName(), marketingTask.getSmsPlatform(),
					path);
			marketingTask.setDownloadUrl(path);
			marketingTask.setStatus(MarketingTaskType.END);
			updateMarketingTask(marketingTask);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e2) {
			LoggerUtil.error("[marketing error]", e2);
			throw e2;
		}
	}

	private void updateMarketingTaskProgress(Long marketingTaskId, int index, Integer limit) {
		MarketingTask marketingTask = new MarketingTask();
		marketingTask.setMarketingTaskId(marketingTaskId);
		marketingTask.setProgress(index + "/" + limit);
		updateMarketingTask(marketingTask);
	}

	private void initSearchMarketingSmsMap(Map<String, Object> searchMarketingSmsMap) {
		try {
			String value = IniCache.getIniValue("marketing_sms_repeat_days", "90");
			if (!ValidateUtil.isNumber(value)) {
				throw new BusinessException("ini时间区域错误", ComRetCode.WRONG_PARAMETER);
			}
			Date date = new Date();
			int region = Integer.valueOf(value);
			searchMarketingSmsMap.put("createTimeStart", DateUtils.addDays(date, -region));
			searchMarketingSmsMap.put("createTimeEnd", DateUtils.format(date, "yyyy-MM-dd HH:mm:ss"));
		} catch (Exception e) {
			LoggerUtil.error("marketing init error", e);
		}
	}

	public void insertMarketingSms(List<String> mobileList, Long marketingTaskId) {
		if (marketingTaskId == null) {
			throw new BusinessException(ComRetCode.FAIL);
		}
		if (mobileList == null || mobileList.size() == 0) {
			throw new BusinessException(ComRetCode.MARKETING_SMS_NOT_VERIFY_ERROR);
		}

		for (String mobile : mobileList) {
			try {
				MarketingSms marketingSms = new MarketingSms();
				marketingSms.setMobile(mobile);
				marketingSms.setMarketingTaskId(marketingTaskId);
				marketingSms.setStatus(CommonStatus.ONLINE);
				marketingSmsService.insertMarketingSms(marketingSms);
			} catch (Exception e2) {
				LoggerUtil.error("[marketing duplicate] mobile=" + mobile + ", marketingTaskId=" + marketingTaskId, e2);
			}
		}
		// ThreadPool.getInstance().exec(new Runnable() {
		// @Override
		// public void run() {
		// try {
		//
		// } catch (Exception e) {
		// LoggerUtil.error("[marketing insert error]", e);
		// }
		// }
		// });
	}

	public static boolean iscontainsOperator(String[] channels, String param) {
		if (StringUtils.isBlank(param)) {
			return false;
		}
		for (String element : channels) {
			if (element.contains(param)) {
				return true;
			}
		}
		return false;
	}

	private boolean isVerifyLocationCode(String rejectCity, String rejectProvince, MobileLocation mo) {
		boolean provinceResult = isVerify(true, rejectProvince, mo.getLocationCode());
		if (provinceResult) {
			return true;
		}
		boolean cityResult = isVerify(false, rejectCity, mo.getLocationCode());
		if (cityResult) {
			return true;
		}
		return false;
	}

	private boolean isVerify(boolean isProvince, String condition, String locationCode) {
		if (StringUtils.isBlank(condition)) {
			return false;
		}
		if (StringUtils.isBlank(locationCode)) {
			return true;
		}
		String[] arrays = condition.split(",");
		if (arrays == null || arrays.length == 0) {
			return false;
		}
		for (String element : arrays) {
			if (StringUtils.isBlank(element)) {
				continue;
			}
			if (isProvince) {
				if (element.length() < 3) {
					continue;
				}
				String prefix = element.substring(0, 2);
				if (locationCode.startsWith(prefix)) {
					return true;
				}
			} else {
				if (locationCode.equals(element)) {
					return true;
				}
			}
		}
		return false;
	}

	private List<String> getMobilePrefix(List<String> mobileList) {
		List<String> prefixList = new ArrayList<String>();
		for (String element : mobileList) {
			prefixList.add(element.substring(0, 7));
		}
		return prefixList;
	}

	private List<String> getUploadFile(String uploadFile) throws Exception {
		List<String> uploadList = new ArrayList<String>();
		try {
			String content = HttpUtil.sendGetRequest(uploadFile);
			for (String param : content.split("\r\n")) {
				if (StringUtils.isBlank(param)) {
					continue;
				}
				if (!ValidateUtil.isNumber(param)) {
					continue;
				}
				uploadList.add(param);
			}
			return uploadList;
		} catch (Exception e) {
			LoggerUtil.error("[marketing task]", e);
			throw e;
		}
	}

	@Override
	public MarketingTask selectMarketingTask(Long marketingTaskId) {
		if (marketingTaskId == null) {
			return null;
		}
		MarketingTask marketingTask = new MarketingTask();
		marketingTask.setMarketingTaskId(marketingTaskId);
		List<MarketingTask> marketingTaskList = selectMarketingTask(marketingTask, new PageBounds());
		if (marketingTaskList.size() > 0) {
			return marketingTaskList.get(0);
		}
		return null;
	}

	public static void main(String[] args) {
		MarketingTask marketingTask = new MarketingTask();
		marketingTask.setProductName("兴贷");
		marketingTask.setSendTime(new Timestamp(new Date().getTime()));
		marketingTask.setExportCount(20000);
		marketingTask.setSmsPlatform("创蓝");
		String uploadPath = "form/" + marketingTask.getProductName() + "_" + marketingTask.getSmsPlatform() + "_"
				+ DateUtils.format(marketingTask.getSendTime(), "yyyy-MM-dd_HH点") + "_"
				+ marketingTask.getExportCount() + "手机号" + "_"
				+ new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ".txt";
		System.out.println(uploadPath);
	}
}
