package com.yuanshanbao.dsp.location.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.HttpsUtil;
import com.yuanshanbao.common.util.JacksonUtil;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.location.dao.IpLocationDao;
import com.yuanshanbao.dsp.location.model.IpLocation;
import com.yuanshanbao.dsp.location.model.Location;
import com.yuanshanbao.dsp.location.model.LocationType;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class IpLocationServiceImpl implements IpLocationService {

	private static final String ALIYUN_IP_LOCATION_HOST = "https://dm-81.data.aliyun.com";
	private static final String ALIYUN_IP_LOCATION_PATH = "/rest/160601/ip/getIpInfo.json";
	private static final String ALIYUN_IP_LOCATION_KEY = "d13e5b1909d34a7483098e2bc428a29a";

	private static final String YIYUAN_IP_LOCATION_HOST = "https://ali-ip.showapi.com";
	private static final String YIYUAN_IP_LOCATION_PATH = "/ip";

	@Autowired
	IpLocationDao ipLocationDao;

	@Override
	public List<IpLocation> selectIpLocations(IpLocation ipLocation, PageBounds pageBounds) {
		return ipLocationDao.selectIpLocations(ipLocation, pageBounds);
	}

	@Override
	public IpLocation selectIpLocation(Long ipLocationId) {
		IpLocation ipLocation = new IpLocation();
		if (ipLocationId == null) {
			return null;
		}
		ipLocation.setIpLocationId(ipLocationId);
		List<IpLocation> list = selectIpLocations(ipLocation, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public IpLocation selectIpLocation(String prefix) {
		IpLocation ipLocation = new IpLocation();
		if (StringUtils.isBlank(prefix)) {
			return null;
		}
		ipLocation.setPrefix(prefix);
		List<IpLocation> list = selectIpLocations(ipLocation, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertIpLocation(IpLocation ipLocation) {
		int result = -1;

		result = ipLocationDao.insertIpLocation(ipLocation);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public void deleteIpLocation(Long ipLocationId) {
		int result = -1;

		result = ipLocationDao.deleteIpLocation(ipLocationId);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateIpLocation(IpLocation ipLocation) {
		int result = -1;

		result = ipLocationDao.updateIpLocation(ipLocation);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void insertOrUpdateIpLocation(IpLocation ipLocation) {
		if (ipLocation.getIpLocationId() == null) {
			insertIpLocation(ipLocation);
		} else {
			updateIpLocation(ipLocation);
		}
	}

	@Override
	public Map<Long, IpLocation> selectIpLocations(List<Long> ipLocationIdList) {
		Map<Long, IpLocation> map = new HashMap<Long, IpLocation>();
		if (ipLocationIdList == null || ipLocationIdList.size() == 0) {
			return map;
		}
		List<IpLocation> list = ipLocationDao.selectIpLocations(ipLocationIdList);
		for (IpLocation ipLocation : list) {
			map.put(ipLocation.getIpLocationId(), ipLocation);
		}
		return map;
	}

	@Override
	public Location queryIpLocation(String ipAddress) {
		String prefix = ipAddress.substring(0, ipAddress.lastIndexOf("."));
		IpLocation ipLocation = selectIpLocation(prefix);
		Location location = null;
		if (ipLocation != null) {
			location = ConstantsManager.getLocationByCode(ipLocation.getLocationCode());
		} else {
			location = queryYiYuanInterface(ipAddress);
			if (location == null) {
				location = queryAliyunInterface(ipAddress);
			}
			// location = queryAliyunInterface(ipAddress);
		}
		return location;
	}

	private Location queryYiYuanInterface(String ipAddress) {
		try {
			Map<String, Object> resultMap = doGetIpInterface(ipAddress, "YIYUAN");
			Object obj = resultMap.get("showapi_res_body");
			if (obj instanceof Map) {
				String operator = (String) ((Map) obj).get("country") + ((Map) obj).get("isp");
				String cityCode = (String) ((Map) obj).get("city_code");
				Location location = null;
				if (StringUtils.isNotBlank(cityCode)) {
					location = ConstantsManager.getLocationByCode(cityCode);
				}
				if (location != null) {
					IpLocation ipLocation = new IpLocation();
					ipLocation.setPrefix(ipAddress.substring(0, ipAddress.lastIndexOf(".")));
					ipLocation.setLocationCode(location.getCode());
					ipLocation.setOperator(operator);
					ipLocation.setStatus(CommonStatus.ONLINE);
					insertIpLocation(ipLocation);
				} else {
					LoggerUtil.info("[queryYiYuanInterface]city not exist: cityCode={}, ipAddress={}, response={}",
							cityCode, ipAddress, JacksonUtil.obj2json(resultMap));
				}
				return location;
			}
		} catch (Exception e) {
			LoggerUtil.error("[queryYiYuanIpError]", e);
		}
		return null;
	}

	private Location queryAliyunInterface(String ipAddress) {
		Map<String, String> querys = new HashMap<String, String>();
		Map<String, String> header = new HashMap<String, String>();
		header.put("Authorization", "APPCODE " + ALIYUN_IP_LOCATION_KEY);
		querys.put("ip", ipAddress);
		try {
			HttpResponse response = HttpsUtil.doGet(ALIYUN_IP_LOCATION_HOST, ALIYUN_IP_LOCATION_PATH, "GET", header,
					querys);
			Map<String, Object> resultMap = JacksonUtil.json2map(EntityUtils.toString(response.getEntity()));
			Object obj = resultMap.get("data");
			if (obj instanceof Map) {
				String cityName = (String) ((Map) obj).get("city");
				String operator = (String) ((Map) obj).get("country") + ((Map) obj).get("isp");
				Location location = null;
				for (Location l : ConstantsManager.getLocationMap().values()) {
					if ((l.getType() == LocationType.PROVINCE || l.getType() == LocationType.CITY)
							&& StringUtils.isNotBlank(cityName) && l.getName().contains(cityName)) {
						location = l;
					}
				}

				if (location != null) {
					IpLocation ipLocation = new IpLocation();
					ipLocation.setPrefix(ipAddress.substring(0, ipAddress.lastIndexOf(".")));
					ipLocation.setLocationCode(location.getCode());
					ipLocation.setOperator(operator);
					ipLocation.setStatus(CommonStatus.ONLINE);
					insertIpLocation(ipLocation);
				} else {
					LoggerUtil.info("[city not exist:" + cityName + ", ipAddress=" + ipAddress + "]");
				}
				return location;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		Map<String, String> querys = new HashMap<String, String>();
		Map<String, String> header = new HashMap<String, String>();
		header.put("Authorization", "APPCODE " + ALIYUN_IP_LOCATION_KEY);
		querys.put("ip", "127.0.0.1");
		try {
			HttpResponse response = HttpsUtil.doGet(ALIYUN_IP_LOCATION_HOST, ALIYUN_IP_LOCATION_PATH, "GET", header,
					querys);
			Map<String, Object> resultMap = JacksonUtil.json2map(EntityUtils.toString(response.getEntity()));
			Object obj = resultMap.get("data");
			System.out.println(obj.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param ip
	 * @param type
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> doGetIpInterface(String ipAddress, String type) throws Exception {
		Map<String, String> querys = new HashMap<String, String>();
		Map<String, String> header = new HashMap<String, String>();
		header.put("Authorization", "APPCODE " + ALIYUN_IP_LOCATION_KEY);
		querys.put("ip", ipAddress);
		String host = ALIYUN_IP_LOCATION_HOST;
		String path = ALIYUN_IP_LOCATION_PATH;
		if ("YIYUAN".equals(type)) {
			host = YIYUAN_IP_LOCATION_HOST;
			path = YIYUAN_IP_LOCATION_PATH;
		}
		HttpResponse response = HttpsUtil.doGet(host, path, "GET", header, querys);
		return JacksonUtil.json2map(EntityUtils.toString(response.getEntity()));
	}

}
