package com.yuanshanbao.dsp.controller.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliyun.oss.OSSClient;
import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.RequestUtil;
import com.yuanshanbao.common.util.UploadUtils;
import com.yuanshanbao.dsp.app.model.AppDownload;
import com.yuanshanbao.dsp.channel.model.Channel;
import com.yuanshanbao.dsp.channel.service.ChannelService;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.IniBean;
import com.yuanshanbao.dsp.core.InterfaceRetCode;

@Controller
@RequestMapping({ "/app", "/i/app" })
public class AppDownloadController extends BaseController {

	@Autowired
	ChannelService channelService;

	@ResponseBody
	@RequestMapping("/download")
	public Object download(HttpServletRequest request, String channel) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String url = getUrl(request, channel);
			resultMap.put("downloadUrl", url);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[download error:]", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	protected String getUrl(HttpServletRequest request, String channel) {
		Channel c = null;
		Long appId = 1L;
		if (StringUtils.isNotBlank(channel)) {
			c = channelService.selectChannel(channel);
		}
		if (c != null) {
			appId = c.getAppId();
		}
		String iosDownloadUrl = null, androidDownloadUrl = null;
		if (c != null) {
			iosDownloadUrl = c.getIosDownloadUrl();
			androidDownloadUrl = c.getAndroidDownloadUrl();
		}
		String url;
		if (RequestUtil.isFromIOS(request)) {
			// 原装链接：https://itunes.apple.com/app/id1288922168?l=zh&ls=1&mt=8
			// 加渠道链接：https://itunes.apple.com/app/apple-store/id1288922168?pt=118821288&mt=8
			if (StringUtils.isBlank(iosDownloadUrl)) {
				url = IniBean.getIniValue(new AppDownload().getIosUrl(appId),
						"https://itunes.apple.com/app/apple-store/id1288922168?pt=118821288");
				if (StringUtils.isNotBlank(channel) && !url.contains("ct")) {
					url = url + "&ct=" + channel + "&mt=8";
				}
				if (!url.contains("&mt=8")) {
					url = url + "&mt=8";
				}
			} else {
				url = iosDownloadUrl;
			}

		} else {
			if (RequestUtil.isFromWeixin(request)) {
				url = IniBean.getIniValue(new AppDownload().getWeixinUrl(appId),
						"http://a.app.qq.com/o/simple.jsp?pkgname=com.yuanshanbao.advertisement");
			} else {
				if (StringUtils.isBlank(androidDownloadUrl)) {
					url = IniBean.getIniValue(new AppDownload().getAndroidUrl(appId),
							"https://advertisementtech.oss-cn-beijing.aliyuncs.com/app/xingdai/xingdai-1.0.1%s.apk");
				} else {
					url = androidDownloadUrl;
					return url;
				}
				if (StringUtils.isNotBlank(channel) && isExist(url, channel)) {
					url = String.format(url, "-" + channel);
				} else {
					url = String.format(url, "");
				}
			}
		}
		return url;
	}

	private boolean isExist(String url, String channel) {
		OSSClient client = UploadUtils.getOssUtils().getClient();
		url = String.format(url, "-" + channel).substring(url.indexOf("app"));
		return client.doesObjectExist(UploadUtils.OSS_BUCKET_NAME, url);
	}

}
