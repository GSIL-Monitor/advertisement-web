package com.yuanshanbao.ad.controller.channel;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.JSPHelper;
import com.yuanshanbao.common.util.JacksonUtil;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.UserAgentUtils;
import com.yuanshanbao.ad.channel.model.ChannelPromotionInfo;
import com.yuanshanbao.ad.channel.model.PromotionStatus;
import com.yuanshanbao.ad.channel.service.ChannelPromotionInfoService;
import com.yuanshanbao.ad.common.constant.CommonConstant;
import com.yuanshanbao.ad.config.ConfigConstants;
import com.yuanshanbao.ad.config.ConfigManager;
import com.yuanshanbao.ad.core.InterfaceRetCode;

@Controller
@RequestMapping({ "/channel", "/i/channel" })
public class ChannelController {

	@Autowired
	ChannelPromotionInfoService promotionInfoService;

	@ResponseBody
	@RequestMapping("/clickapi")
	public Object clickapi(ChannelPromotionInfo promotionInfo, HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String userAgent = request.getHeader("User-Agent");
			String agent = request.getParameter("agent");
			String ip = JSPHelper.getRemoteAddr(request);
			if (StringUtils.isBlank(promotionInfo.getPartnerId())) {
				String uuid = promotionInfo.getUuid();
				String clickid = promotionInfo.getClickid();
				String subchannel = promotionInfo.getSubchannel();
				if (StringUtils.isNotBlank(uuid) || StringUtils.isNotBlank(clickid)
						|| StringUtils.isNotBlank(subchannel)) {
					promotionInfo.setPartnerId(uuid + CommonConstant.COMMON_COLON_STR + clickid
							+ CommonConstant.COMMON_COLON_STR + subchannel);
				}
			}
			promotionInfo.setUserAgent(userAgent);
			promotionInfo.setIp(ip);
			promotionInfo.setChannel(agent);
			promotionInfo.setStatus(PromotionStatus.INACTIVE);
			getUserAgent(userAgent, promotionInfo);
			if (!JacksonUtil.obj2json(promotionInfo).contains("${")) {
				promotionInfoService.insertOrUpdateChannelPromotionInfo(promotionInfo);
			}
			String url = ConfigManager.getConfigValue(null, promotionInfo.getChannel(), null,
					ConfigConstants.PARTNER_CLICKAPI_REDIRECT_URL_CONFIG);
			resultMap.put("url", url);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[clickapi error:]", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	private void getUserAgent(String userAgent, ChannelPromotionInfo promotionInfo) {
		boolean isMobile = UserAgentUtils.isMobile(userAgent);
		if (isMobile) {
			String deviceType = UserAgentUtils.getDeviceType(userAgent);
			String systemName = UserAgentUtils.getSystemName(userAgent);
			String systemVersion = UserAgentUtils.getSystemVersion(userAgent);
			if(StringUtils.isNotBlank(deviceType)){
				promotionInfo.setDeviceType(deviceType);
				promotionInfo.setSystemName(systemName);
				promotionInfo.setSystemVersion(systemVersion);
			}
		}
	}

	@RequestMapping("/clicker")
	public String clicker(ChannelPromotionInfo promotionInfo, HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String userAgent = request.getHeader("User-Agent");
			String agent = request.getParameter("agent");
			String ip = JSPHelper.getRemoteAddr(request);
			if (StringUtils.isBlank(promotionInfo.getPartnerId())) {
				String uuid = promotionInfo.getUuid();
				String clickid = promotionInfo.getClickid();
				String subchannel = promotionInfo.getSubchannel();
				if (StringUtils.isNotBlank(uuid) || StringUtils.isNotBlank(clickid)
						|| StringUtils.isNotBlank(subchannel)) {
					promotionInfo.setPartnerId(uuid + CommonConstant.COMMON_COLON_STR + clickid
							+ CommonConstant.COMMON_COLON_STR + subchannel);
				}
			}
			promotionInfo.setUserAgent(userAgent);
			promotionInfo.setIp(ip);
			promotionInfo.setChannel(agent);
			promotionInfo.setStatus(PromotionStatus.INACTIVE);
			/*if (!JacksonUtil.obj2json(promotionInfo).contains("${")) {
				promotionInfoService.insertOrUpdateChannelPromotionInfo(promotionInfo);
			}*/
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[clicker error:]", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		String url = ConfigManager.getConfigValue(null, promotionInfo.getChannel(), null,
				ConfigConstants.PARTNER_CLICKAPI_REDIRECT_URL_CONFIG);
		return "redirect:" + url;
	}
}
