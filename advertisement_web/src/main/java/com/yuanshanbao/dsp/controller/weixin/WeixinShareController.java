package com.yuanshanbao.dsp.controller.weixin;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.activity.model.Activity;
import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.product.model.Product;
import com.yuanshanbao.dsp.product.service.ProductService;
import com.yuanshanbao.dsp.weixin.service.WeixinService;
import com.yuanshanbao.paginator.domain.PageBounds;

@Controller
@RequestMapping("/i/share")
public class WeixinShareController {

	@Autowired
	private WeixinService weixinService;

	@Autowired
	private RedisService redisService;

	@Autowired
	private ProductService productService;

	private static final String FENXIANG = "fenxiang";

	@ResponseBody
	@RequestMapping("/get/shareParams")
	public Map<String, String> getShare(HttpServletRequest request, String url) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String accessToken = null;
			String jsapi_ticket = null;
			if (StringUtils.isBlank(url)) {
				url = "https://wz.huhad.com/w/home";
			}
			accessToken = weixinService.getServiceAccessToken();
			if (StringUtils.isBlank(accessToken)) {
				LoggerUtil.error(" weiXinShare:  accessToken ={}", accessToken);
			}
			jsapi_ticket = weixinService.getJSAPITicket(accessToken);// jsapi_ticket
			if (StringUtils.isBlank(jsapi_ticket)) {
				accessToken = weixinService.getServiceAccessToken();
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

			Activity activity = ConfigManager.getActivityByKey(FENXIANG);
			Product product = new Product();
			product.setActivityId(activity.getActivityId());
			List<Product> prList = productService.selectProducts(product, new PageBounds());
			map.put("title", prList.get(0).getTitle());
			map.put("desc", prList.get(0).getDescription());
			map.put("imgUrl", prList.get(0).getImageUrl());
			InterfaceRetCode.setAppCodeDesc(map, ComRetCode.SUCCESS);

		} catch (Exception e) {
			e.printStackTrace();
			LoggerUtil.error("[getShare error:]", e);
			InterfaceRetCode.setAppCodeDesc(map, ComRetCode.FAIL);
		}
		return map;
	}

	@ResponseBody
	@RequestMapping("/wechat")
	public String wexinChat(HttpServletRequest request, HttpServletResponse response) {

		LoggerUtil.error("微信公众号URL测试进入");
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		try {
			if (signature != null && ShareSignUtil.checkSignature(signature, timestamp, nonce)) {
				try {
					Writer print = response.getWriter();
					print.write(echostr);
					print.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			LoggerUtil.error("微信公众号URL测试成功");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}

}
