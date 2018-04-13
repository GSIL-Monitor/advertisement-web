package com.yuanshanbao.dsp.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.JSPHelper;
import com.yuanshanbao.dsp.app.model.AppInfo;
import com.yuanshanbao.dsp.app.service.AppService;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.IniBean;
import com.yuanshanbao.dsp.core.InterfaceRetCode;

@Controller
@RequestMapping("/i/common")
public class AppCommonController extends BaseController {

	public static final String MOBILE_OS_IOS = "IOS";
	public static final String MOBILE_OS_ANDROID = "ANDROID";

	public static final String IOS_DOWNLOAD_URL_KEY = "app_ios_download_url";
	public static final String ANDROID_DOWNLOAD_URL_KEY = "app_android_download_url";
	public static final String WEIXIN_DOWNLOAD_URL_KEY = "app_weixin_download_url";

	public static final String VERSION = "version";// 最新版本号
	public static final String MINORVER = "minorVersion";// 最低版本号
	public static final String INVER = "internalVersion";// 内部版本号
	public static final String DESC = "description"; // 版本更新的描述,描述以“|”分割，以表示1,2,3更新提示
	public static final String DOWNLOAD_URL = "downloadUrl";// 客户端下载wap地址
	public static final String SERVER_SYS_TIME = "serverSysTime";// 服务器当前系统时间。格式：当前系统时间的毫秒数，例如：1323075982564

	@Resource
	private AppService appService;

	/**
	 * App初始化接口
	 * 
	 * @param appInfo
	 * @return
	 */
	@RequestMapping("/initial")
	@ResponseBody
	public Map<String, String> initial(HttpServletRequest request, AppInfo appInfo) {
		Map<String, String> ret = new HashMap<String, String>();
		appService.initialAppInfo(appInfo);
		appInfo.setIp(JSPHelper.getRemoteAddr(request));
		ret.put("appId", appInfo.getAppId());
		ret.put("key", appInfo.getKey());
		InterfaceRetCode.setAppCodeDesc(ret, ComRetCode.SUCCESS);
		return ret;
	}

	@RequestMapping("/checkAppUpdate")
	@ResponseBody
	protected Map<String, String> serviecs(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			String mobileOsType = request.getParameter("mobile_os_type");

			if (!isValidOsType(mobileOsType)) {
				InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.WRONG_PARAMETER);
				return resultMap;
			}

			String version = "";
			String minorVer = "";
			String inver = "";
			String desc = "";
			String wapUrl = "";

			if (MOBILE_OS_ANDROID.equalsIgnoreCase(mobileOsType)) {
				version = IniBean.getIniValue("app_android_version", "1.0.1");
				minorVer = IniBean.getIniValue("app_android_minorVer", "1.0.1");
				inver = IniBean.getIniValue("app_android_inver", "1.0.1");
				desc = IniBean.getIniValue("app_android_desc", "兴贷Android客户端");
				wapUrl = IniBean.getIniValue(ANDROID_DOWNLOAD_URL_KEY,
						"https://advertisementtech.oss-cn-beijing.aliyuncs.com/app/xingdai/xingdai-1.0.1.apk");
				if (StringUtils.isNotBlank(wapUrl) && wapUrl.contains("%s")) {
					wapUrl = wapUrl.replace("%s", "");
				}
			} else if (MOBILE_OS_IOS.equalsIgnoreCase(mobileOsType)) {
				version = IniBean.getIniValue("app_iPhone_version", "1.0");
				minorVer = IniBean.getIniValue("app_iPhone_minorVer", "1.0");
				inver = IniBean.getIniValue("app_iPhone_inver", "1.0");
				desc = IniBean.getIniValue("app_iPhone_desc", "红红彩IOS客户端");
				wapUrl = IniBean.getIniValue(IOS_DOWNLOAD_URL_KEY, "https://itunes.apple.com/cn/app/");
			}

			resultMap.put(VERSION, version);
			resultMap.put(MINORVER, minorVer);
			resultMap.put(INVER, inver);
			resultMap.put(DESC, desc);
			resultMap.put(DOWNLOAD_URL, wapUrl);
			resultMap.put(SERVER_SYS_TIME, String.valueOf(System.currentTimeMillis()));

			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);

			return resultMap;
		} catch (Exception e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			return resultMap;
		}
	}

	private boolean isValidOsType(String mobileOsType) {

		if (MOBILE_OS_ANDROID.equalsIgnoreCase(mobileOsType) || MOBILE_OS_IOS.equalsIgnoreCase(mobileOsType)) {
			return true;
		}

		return false;
	}
}