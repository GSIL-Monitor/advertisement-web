package com.yuanshanbao.dsp.controller.weixin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.weixin.service.WeixinService;

@Controller
@RequestMapping("/i/share")
public class WeixinShareController {

	@Autowired
	private WeixinService weixinService;

	@Autowired
	private RedisService redisService;

	@ResponseBody
	@RequestMapping("/get/shareParams")
	public Map<String, String> getShare(HttpServletRequest request, String url) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String accessToken = null;
			String jsapi_ticket = null;

			accessToken = weixinService.getAccessToken();
			if (StringUtils.isBlank(accessToken)) {
				LoggerUtil.error(" weiXinShare:  accessToken ={}", accessToken);
			}
			jsapi_ticket = weixinService.getJSAPITicket(accessToken);// jsapi_ticket
			if (StringUtils.isBlank(jsapi_ticket)) {
				accessToken = weixinService.getAccessToken();
				jsapi_ticket = weixinService.getJSAPITicket(accessToken);
			}
			String timestamp = Long.toString(System.currentTimeMillis() / 1000);// 时间戳
			String nonceStr = UUID.randomUUID().toString();
			String signature = ShareSignUtil.getSignature(jsapi_ticket, nonceStr, timestamp, url);// 验证签名
			map.put("url", url);
			map.put("jsapi_ticket", jsapi_ticket);
			map.put("nonceStr", nonceStr);
			map.put("timestamp", timestamp);
			map.put("signature", signature);
			map.put("appid", weixinService.getAppId(WeixinService.CONFIG_SERVICE));
			InterfaceRetCode.setAppCodeDesc(map, ComRetCode.SUCCESS);

		} catch (Exception e) {
			e.printStackTrace();
			LoggerUtil.error("[getShare error:]", e);
			InterfaceRetCode.setAppCodeDesc(map, ComRetCode.FAIL);
		}
		return map;
	}

}
