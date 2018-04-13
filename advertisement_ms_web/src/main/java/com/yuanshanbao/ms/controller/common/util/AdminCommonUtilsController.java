package com.yuanshanbao.ms.controller.common.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.HttpUtil;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.PropertyUtil;
import com.yuanshanbao.common.util.UploadUtils;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.ms.controller.base.PaginationController;

@Controller
@RequestMapping("/admin/common")
public class AdminCommonUtilsController extends PaginationController {

	private static final String PAGE_URL_SWITCH = "advertisement/common/url/switchTransform";

	private static final String PAGE_UPLOAD_FILE = "advertisement/common/file/uploadFile";

	private static final String PAGE_REDEEM_FILE = "advertisement/common/redeem_code/redeemFile";

	private static final String SINA_TRANSFORM_BASE_URL = "http://api.t.sina.com.cn/short_url/shorten.json?source=2815391962&url_long=";

	public static String OSS_HOST_FILES = PropertyUtil.getProperty("oss.host.files");

	public static String HOST_NAME = PropertyUtil.getProperty("host.web.open");

	@RequestMapping("/url/transformWindow.do")
	public String insertWindow(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		return PAGE_URL_SWITCH;
	}

	@ResponseBody
	@RequestMapping("/url/transform.do")
	public Object insert(String needTransformUrl, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			if (StringUtils.isBlank(needTransformUrl)) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			needTransformUrl = URLEncoder.encode(needTransformUrl, "utf-8");

			String content = HttpUtil.forward(request, SINA_TRANSFORM_BASE_URL + needTransformUrl);
			if (StringUtils.isBlank(content) || content.indexOf("url_short") < 0) {
				throw new BusinessException(ComRetCode.FAIL);
			}
			String shortUrl = content.substring(content.indexOf(":") + 2, content.indexOf(",") - 1);

			result.put("path", shortUrl);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.WRONG_PARAMETER);
			LoggerUtil.error("parse url error:", e2);
		}
		return result;
	}

	@RequestMapping("/file/uploadFileWindow.do")
	public String uploadFileWindow(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		return PAGE_UPLOAD_FILE;
	}

	@ResponseBody
	@RequestMapping("/file/upload.do")
	public Object upload(@RequestParam("file") MultipartFile data, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			if (data.isEmpty()) {
				throw new BusinessException(ComRetCode.FAIL);
			}
			String path = UploadUtils.uploadFile(data, UploadUtils.FTP_COMMON_FILE);
			result.put("path", path);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.WRONG_PARAMETER);
			LoggerUtil.error("parse url error:", e2);
		}

		return result;
	}

	@RequestMapping("/file/redeemWindow.do")
	public String inviteWindow(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		return PAGE_REDEEM_FILE;
	}

	@ResponseBody
	@RequestMapping("/file/redeem.do")
	public Object invite(Integer number, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			if (number == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			Boolean isNumber = null;
			String content = "";
			for (int i = 0; i < number; i++) {
				String code = "";
				for (int j = 0; j < 6; j++) {
					if (isNumber != null && isNumber) {
						code += (int) (Math.random() * 10);
					} else {
						if (Math.random() > 0.5) {
							code += (int) (Math.random() * 10);
						} else {
							code += (char) ('a' + (int) (Math.random() * 26));
						}
					}
				}
				content += code + "\r\n";
			}

			InputStream is = new ByteArrayInputStream(content.getBytes());
			String uploadPath = "form/" + "兑换码" + "_" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
					+ ".txt";
			UploadUtils.uploadFiles(is, content.length(), uploadPath);
			String invitePath = OSS_HOST_FILES + "/" + uploadPath;
			result.put("path", invitePath);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.WRONG_PARAMETER);
			LoggerUtil.error("invite code error:", e2);
		}

		return result;
	}

	private String getParam(String key, boolean isRequired) {
		String param = "";
		if (StringUtils.isBlank(key) && isRequired) {
			LoggerUtil.info("[transfer txt] must inert param was null");
			throw new BusinessException(ComRetCode.FAIL);
		}
		if (StringUtils.isNotBlank(key)) {
			param += key + ",";
		} else {
			param += ",";
		}
		return param;
	}

	/**
	 * 获取map形式的数据 key-value都空不存储 当前列的标题行为空，不存储
	 * 
	 * @param dataList
	 * @param titleList
	 * @param rows
	 */
	private void getExcelMap(List<List<String>> dataList, List<Map<String, String>> rows) {
		List<String> titleList = new ArrayList<String>();
		Map<String, String> map = null;
		for (int i = 0; i < dataList.size(); i++) {
			List<String> rowList = dataList.get(i);
			if (i < 1) {
				// titleList = rowList; //引用传递，錯誤
				// 赋值传递title
				for (String param : rowList) {
					titleList.add(param);
				}
				continue;
			}
			map = new HashMap<String, String>();
			for (int j = 0; j < rowList.size(); j++) {
				if (j >= titleList.size()) {
					break;
				}
				String key = titleList.get(j);
				String value = rowList.get(j);
				if (StringUtils.isBlank(key) && StringUtils.isBlank(value)) {
					continue;
				}
				map.put(key, value);
			}
			rows.add(map);
		}
	}
}
