package com.yuanshanbao.dsp.location.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.HttpUtil;
import com.yuanshanbao.common.util.JacksonUtil;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.PropertyUtil;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.location.dao.MobileLocationDao;
import com.yuanshanbao.dsp.location.model.Location;
import com.yuanshanbao.dsp.location.model.LocationType;
import com.yuanshanbao.dsp.location.model.MobileLocation;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class MobileLocationServiceImpl implements MobileLocationService {

	private static final String MOBILE_LOCATION_URL = PropertyUtil.getProperty("interfaces.mobile.location");
	private static final String ALIYUN_MOBILE_LOCATION_URL = "http://ali-mobile.showapi.com/6-1?num=";
	private static final String ALIYUN_MOBILE_LOCATION_KEY = "d13e5b1909d34a7483098e2bc428a29a";

	@Autowired
	private MobileLocationDao mobileLocationDao;

	@Override
	public List<MobileLocation> selectMobileLocations(MobileLocation mobileLocation, PageBounds pageBounds) {
		return mobileLocationDao.selectMobileLocations(mobileLocation, pageBounds);
	}

	@Override
	public MobileLocation selectMobileLocation(Long mobileLocationId) {
		MobileLocation mobileLocation = new MobileLocation();
		if (mobileLocationId == null) {
			return null;
		}
		mobileLocation.setMobileLocationId(mobileLocationId);
		List<MobileLocation> list = selectMobileLocations(mobileLocation, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertMobileLocation(MobileLocation mobileLocation) {
		int result = -1;

		result = mobileLocationDao.insertMobileLocation(mobileLocation);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteMobileLocation(Long mobileLocationId) {
		int result = -1;

		result = mobileLocationDao.deleteMobileLocation(mobileLocationId);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateMobileLocation(MobileLocation mobileLocation) {
		int result = -1;

		result = mobileLocationDao.updateMobileLocation(mobileLocation);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	/**
	 * @param mobileLocation
	 * @return
	 * @throws IOException
	 */
	@Override
	public void insertOrUpdateMobileLocation(MobileLocation mobileLocation) {
		if (mobileLocation.getMobileLocationId() == null) {
			insertMobileLocation(mobileLocation);
		} else {
			updateMobileLocation(mobileLocation);
		}
	}

	@Override
	public Map<Long, MobileLocation> selectMobileLocations(List<Long> mobileLocationIdList) {
		Map<Long, MobileLocation> map = new HashMap<Long, MobileLocation>();
		if (mobileLocationIdList == null || mobileLocationIdList.size() == 0) {
			return map;
		}
		List<MobileLocation> list = mobileLocationDao.selectMobileLocations(mobileLocationIdList);
		for (MobileLocation mobileLocation : list) {
			map.put(mobileLocation.getMobileLocationId(), mobileLocation);
		}
		return map;
	}

	@Override
	public MobileLocation selectMobileLocation(String prefix) {
		MobileLocation mobileLocation = new MobileLocation();
		if (StringUtils.isBlank(prefix)) {
			return null;
		}
		mobileLocation.setPrefix(prefix);
		List<MobileLocation> list = selectMobileLocations(mobileLocation, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Location queryMobileLocation(String mobile) {
		String prefix = mobile.substring(0, 7);
		MobileLocation mobileLocation = selectMobileLocation(prefix);
		Location location = null;
		if (mobileLocation != null) {
			location = ConstantsManager.getLocationByCode(mobileLocation.getLocationCode());
		} else {
			location = queryRemoteMobileLocation(mobile);
		}
		return location;
	}

	private Location queryRemoteMobileLocation(String mobile) {
		Location location = queryK780Interface(mobile);
		if (location == null) {
			location = queryAliyunInterface(mobile);
		}
		return location;
	}

	private Location queryK780Interface(String mobile) {
		try {
			String result = HttpUtil.sendGetRequest(MOBILE_LOCATION_URL + mobile);
			Map<String, Object> resultMap = JacksonUtil.json2map(result);
			Object obj = resultMap.get("result");
			if (obj instanceof Map) {
				String fullName = (String) ((Map) obj).get("style_citynm");
				String operator = (String) ((Map) obj).get("operators");
				if (StringUtils.isNotBlank(operator)) {
					operator = operator.replaceAll("中国", "");
				}
				if (StringUtils.isBlank(fullName)) {
					return null;
				}
				String[] segs = fullName.split(",");
				String cityName = segs[segs.length - 1];
				Location location = ConstantsManager.getLocationByName(cityName);
				MobileLocation mobileLocation = new MobileLocation();
				mobileLocation.setPrefix(mobile.substring(0, 7));
				mobileLocation.setLocationCode(location.getCode());
				mobileLocation.setOperator(operator);
				mobileLocation.setStatus(CommonStatus.ONLINE);
				insertMobileLocation(mobileLocation);
				return location;
			}
		} catch (Exception e) {
			LoggerUtil.error("[queryK780Interface]", e);
		}
		return null;
	}

	private Location queryAliyunInterface(String mobile) {
		try {
			Map<String, String> header = new HashMap<String, String>();
			header.put("Authorization", "APPCODE " + ALIYUN_MOBILE_LOCATION_KEY);
			String result = HttpUtil.sendGetRequest(ALIYUN_MOBILE_LOCATION_URL + mobile, header);
			Map<String, Object> resultMap = JacksonUtil.json2map(result);
			Object obj = resultMap.get("showapi_res_body");
			if (obj instanceof Map) {
				String operator = (String) ((Map) obj).get("name");
				String cityName = (String) ((Map) obj).get("city");
				if (StringUtils.isBlank(cityName)) {
					cityName = (String) ((Map) obj).get("prov");
				}
				if (StringUtils.isNotBlank(operator)) {
					operator = operator.replaceAll("中国", "");
				}
				Location location = null;
				for (Location l : ConstantsManager.getLocationMap().values()) {
					if ((l.getType() == LocationType.PROVINCE || l.getType() == LocationType.CITY)
							&& l.getName().contains(cityName)) {
						if (location != null) {
							LoggerUtil.info("[city duplicate:" + cityName + ", mobile=" + mobile + "]");
						}
						location = l;
					}
				}
				if (location != null) {
					MobileLocation mobileLocation = new MobileLocation();
					mobileLocation.setPrefix(mobile.substring(0, 7));
					mobileLocation.setLocationCode(location.getCode());
					mobileLocation.setOperator(operator);
					mobileLocation.setStatus(CommonStatus.ONLINE);
					insertMobileLocation(mobileLocation);
					return location;
				} else {
					LoggerUtil.info("[city not exist:" + cityName + ", mobile=" + mobile + "]");
				}
			}
		} catch (Exception e) {
			LoggerUtil.error("[queryAliyunInterface]", e);
		}
		return null;
	}

	@Override
	public Map<String, MobileLocation> selectMobileLocationByPrefixs(List<String> prefixs) {
		Map<String, MobileLocation> map = new HashMap<String, MobileLocation>();
		if (prefixs == null || prefixs.size() == 0) {
			return map;
		}
		List<MobileLocation> list = mobileLocationDao.selectMobileLocationByPrefixs(prefixs);
		for (MobileLocation mobileLocation : list) {
			map.put(mobileLocation.getPrefix(), mobileLocation);
		}
		return map;
	}
}
