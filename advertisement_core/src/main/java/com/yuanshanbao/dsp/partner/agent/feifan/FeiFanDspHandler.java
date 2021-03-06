package com.yuanshanbao.dsp.partner.agent.feifan;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yuanshanbao.common.exception.AppException;
import com.yuanshanbao.common.util.HttpUtil;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.MD5Util;
import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.material.model.Material;
import com.yuanshanbao.dsp.material.model.MaterialStatus;
import com.yuanshanbao.dsp.partner.agent.AbstractDspHandler;

public class FeiFanDspHandler extends AbstractDspHandler {

	public final static String DOMAIN = "http://api.route.f2time.com";
	public final static String CREATE_ADVERTISER = "/ampAdvertisersDsp/add";
	public final static String UPDATE_ADVERTISER = "/ampAdvertisersDsp/update";
	public final static String CHECK_ADVERTISER_STATUS = "/ampAdvertisersDsp/checkAdvertisersStatus";
	public final static String CREATE_MATERIAL = "/ampEntityDsp/create";
	public final static String UPDATE_MATERIAL = "/ampEntityDsp/update";
	public final static String CHECK_MATERIAL_STATUS = "/ampEntityDsp/checkEntityState";
	public final static String DSP_ID = "yuanshan";
	public final static String DSP_TOKEN = "8d0129f8385741fe9c8556a90724729f";
	public final static String DEFAULT_CHARSET = "utf-8";

	@Override
	public Advertiser creatAdvertiser(Advertiser advertiser) {
		SortedMap<Object, Object> paramMap = new TreeMap<Object, Object>();
		paramMap.put("realName", advertiser.getCompanyName());
		paramMap.put("businesslicense", advertiser.getBusinessPicture());
		paramMap.put("adxId", advertiser.getAdxId());
		paramMap.put("token", DSP_TOKEN);
		paramMap.put("dspId", DSP_ID);
		String content = getRequestContent(paramMap);
		// String result = HttpUtil.sendGetRequest(url);
		try {
			String result = HttpUtil.sendPostRequest(DOMAIN + CREATE_ADVERTISER, content, DEFAULT_CHARSET);
			LoggerUtil.info("feifan createAdvertiser" + result);
			JSONObject jsonObject = JSONObject.parseObject(result);
			if (jsonObject.get("code").toString().equals("A000000")) {
				JSONObject data = (JSONObject) jsonObject.get("data");
				if (data.get("state") != null && data.getInteger("state") == 1) {
					advertiser.setCustomerKey(data.get("customerKey").toString());
					return advertiser;
				}
			}
		} catch (Exception e) {
			LoggerUtil.error("feifan creatAdvertiser error", e);
		}

		return null;
	}

	@Override
	public void updateAdvertiser(Advertiser advertiser) {
		SortedMap<Object, Object> paramMap = new TreeMap<Object, Object>();
		paramMap.put("customerKey", advertiser.getCustomerKey());
		paramMap.put("adxId", advertiser.getAdxId());
		paramMap.put("token", DSP_TOKEN);
		paramMap.put("dspId", DSP_ID);
		String key = getMD5Key(paramMap);
		paramMap.put("key", key);
		String params = getParams(paramMap);
		String url = DOMAIN + UPDATE_ADVERTISER + "?" + params;
		try {
			String result = HttpUtil.sendGetRequest(url);
			LoggerUtil.info("feifan updateAdvertiser" + result);
			JSONObject jsonObject = JSONObject.parseObject(result);
			if (jsonObject.get("code").toString().equals("A000000")) {
				JSONObject data = (JSONObject) jsonObject.get("data");
				if (data.getInteger("state") == 1) {
					// success
				}
			}
		} catch (IOException | AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void checkAdvertisersStatus(Advertiser advertiser) {
		SortedMap<Object, Object> paramMap = new TreeMap<Object, Object>();
		paramMap.put("customerKey", advertiser.getCustomerKey());
		paramMap.put("adxId", advertiser.getAdxId());
		paramMap.put("token", DSP_TOKEN);
		paramMap.put("dspId", DSP_ID);
		String content = getRequestContent(paramMap);
		try {
			String result = HttpUtil.sendPostRequest(DOMAIN + CHECK_ADVERTISER_STATUS, content, DEFAULT_CHARSET);
			LoggerUtil.info("feifan checkAdvertisersStatus" + result);
			JSONObject jsonObject = JSONObject.parseObject(result);
			if (jsonObject.getString("code").equals("A000000")) {
				JSONObject data = (JSONObject) jsonObject.get("data");
				JSONArray adxArrary = data.getJSONArray("adxAuditState");
				if (adxArrary != null && adxArrary.size() > 0) {
					boolean check = true;
					for (int i = 0; i < adxArrary.size(); i++) {
						JSONObject adxAudit = adxArrary.getJSONObject(i);
						if (adxAudit.getInteger("state") != 1) {
							check = false;
							break;
						}
					}
					if (check) {
						advertiser.setStatus(CommonStatus.ONLINE);
					}
				}
			}
		} catch (Exception e) {
			LoggerUtil.error("feifan checkAdvertiser error", e);
		}
	}

	@Override
	public Material createMaterial(Material material, Advertiser advertiser) {
		SortedMap<Object, Object> paramMap = new TreeMap<Object, Object>();
		paramMap.put("entityName", material.getName());
		paramMap.put("extId", material.getMaterialId());
		paramMap.put("entityUrl", material.getImageUrl());
		paramMap.put("landUrl", material.getLink());
		paramMap.put("width", material.getWidth());
		paramMap.put("height", material.getHeight());
		paramMap.put("entityType", 36);
		paramMap.put("media", material.getAdxId());
		paramMap.put("customerKey", advertiser.getCustomerKey());
		paramMap.put("token", DSP_TOKEN);
		paramMap.put("dspId", DSP_ID);
		String content = getRequestContent(paramMap);
		try {
			String result = HttpUtil.sendPostRequest(DOMAIN + CREATE_MATERIAL, content, DEFAULT_CHARSET);
			LoggerUtil.info("feifan createMaterial" + result);
			JSONObject jsonObject = JSONObject.parseObject(result);
			if (jsonObject.getString("code").equals("A000000")) {
				JSONObject data = (JSONObject) jsonObject.get("data");
				if (data.getInteger("state") == 1) {
					// success
					return material;
				}
			}
		} catch (Exception e) {
			LoggerUtil.error("feifan creatMaterial error", e);
		}
		return null;
	}

	@Override
	public void updateMaterial(Material material, Advertiser advertiser) {
		SortedMap<Object, Object> paramMap = new TreeMap<Object, Object>();
		paramMap.put("entityName", material.getName());
		paramMap.put("landUrl", material.getLink());
		paramMap.put("width", material.getLink());
		paramMap.put("height", material.getLink());
		paramMap.put("entityType", material.getLink());
		paramMap.put("media", material.getLink());
		paramMap.put("token", DSP_TOKEN);
		paramMap.put("dspId", DSP_ID);

		String content = getRequestContent(paramMap);
		try {
			String result = HttpUtil.sendPostRequest(DOMAIN + UPDATE_MATERIAL, content, DEFAULT_CHARSET);
			JSONObject jsonObject = JSONObject.parseObject(result);
			if (jsonObject.getString("code").equals("A000000")) {
				JSONObject data = (JSONObject) jsonObject.get("data");
				if (data.getInteger("state") == 1) {
					// success
				}
			}
		} catch (Exception e) {
			LoggerUtil.error("feifan updateMaterial error", e);
		}
	}

	@Override
	public void checkMaterialStatus(Material material, Advertiser advertiser) {
		SortedMap<Object, Object> paramMap = new TreeMap<Object, Object>();
		paramMap.put("extId", material.getMaterialId());
		paramMap.put("customerKey", material.getName());
		paramMap.put("token", DSP_TOKEN);
		paramMap.put("dspId", DSP_ID);

		String content = getRequestContent(paramMap);
		try {
			String result = HttpUtil.sendPostRequest(DOMAIN + UPDATE_MATERIAL, content, DEFAULT_CHARSET);
			JSONObject jsonObject = JSONObject.parseObject(result);
			if (jsonObject.get("code").toString().equals("A000000")) {
				JSONObject data = (JSONObject) jsonObject.get("data");
				if (data.getInteger("state") == MaterialStatus.ONLINE) {
					material.setStatus(MaterialStatus.ONLINE);
				} else if (data.getInteger("state") == MaterialStatus.DENIED) {
					material.setStatus(MaterialStatus.DENIED);
				} else if (data.getInteger("state") == MaterialStatus.UNDERREVIEWED) {
					material.setStatus(MaterialStatus.UNDERREVIEWED);
				}
			}
		} catch (Exception e) {
			LoggerUtil.error("checkMaterialStatus error", e);
		}
	}

	private String getRequestContent(SortedMap<Object, Object> paramMap) {
		String key = getMD5Key(paramMap);
		String params = getParams(paramMap);
		StringBuffer sb = new StringBuffer();
		sb.append(params);
		sb.append("&");
		sb.append("key=");
		sb.append(key);
		return sb.toString();
	}

	public static void main(String[] args) throws MalformedURLException, IOException, AppException {
		SortedMap<Object, Object> paramMap = new TreeMap<Object, Object>();
		paramMap.put("extId", "23");
		paramMap.put("customerKey", "b8a1df91cb4847c7");
		paramMap.put("token", DSP_TOKEN);
		paramMap.put("dspId", DSP_ID);
		// String content = getRequestContent(paramMap);
		// String result = HttpUtil.sendPostRequest(DOMAIN +
		// CHECK_MATERIAL_STATUS, content, DEFAULT_CHARSET);
		// System.err.println(result);
		// String url = getRequestContent(paramMap);
		// System.err.println(url);
		// // String result = HttpUtil.sendGetRequest(url);
		// String result = HttpUtil
		// .sendPostRequest(
		// "http://api.route.f2time.com/ampAdvertisersDsp/checkAdvertisersStatus",
		// "adxId=60,62,184,185,121&customerKey=44bcdfd96048474f&dspId=yuanshan&token=8d0129f8385741fe9c8556a90724729f&key=05e59a90b0f499da60f0af258d6c7daebaa615",
		// "utf-8");
		// System.err.println(result);
	}

	private static String getParams(SortedMap<Object, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<Object, Object> entry : parameters.entrySet()) {
			sb.append(entry.getKey() + "=" + entry.getValue());
			sb.append("&");
		}
		String s = sb.toString();
		if (s.endsWith("&")) {
			s = StringUtils.substringBeforeLast(s, "&");
		}
		return s;
	}

	private static String getMD5Key(SortedMap<Object, Object> parameters) {
		String random = "baa615";
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet(); // 所有参与传参的参数按照accsii排序（升序）
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			// 空值不传递，不参与签名组串
			if (null != v && !"".equals(v)) {
				sb.append(k + "=" + v);
			}
		}
		// MD5加密,结果转换为大写字符
		String sign = MD5Util.get(sb.toString());
		return sign + random;

	}

}
