package com.yuanshanbao.ms.controller.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.HttpUtil;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.thread.ThreadPool;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.common.constant.DspConstantsManager;
import com.yuanshanbao.dsp.core.IniBean;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.ms.controller.base.PaginationController;

@Controller
@RequestMapping("/admin/server")
public class AdminServerController extends PaginationController {

	@RequestMapping("/config.do")
	public String adminUsers(HttpServletRequest request, HttpServletResponse response) {
		return "common/server";
	}

	@ResponseBody
	@RequestMapping("/refresh.do")
	public Object refresh(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> result = new HashMap<String, String>();
		refresh("server_ips_online");
		InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		return result;
	}

	@ResponseBody
	@RequestMapping("/refreshMs.do")
	public Object refreshMs(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> result = new HashMap<String, String>();
		// ConstantsManager.refresh();
		InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		return result;
	}

	@ResponseBody
	@RequestMapping("/refreshConfirm.do")
	public Object refreshConfirm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> result = new HashMap<String, String>();
		refresh("server_ips_confirm");
		InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		return result;
	}

	@ResponseBody
	@RequestMapping("/refreshDspConfirm.do")
	public Object refreshDsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> result = new HashMap<String, String>();
		refreshDsp("server_ips_confirm");
		InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		return result;
	}

	@ResponseBody
	@RequestMapping("/refreshDsp.do")
	public Object refreshDspOnline(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> result = new HashMap<String, String>();
		refreshDsp("server_ips_online");
		InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		return result;
	}

	public static void refreshDsp(String key) throws Exception {
		String serverIps = IniBean.getIniValue(key);
		String[] segs = serverIps.split(",");
		for (String ip : segs) {
			if (!ip.contains(":")) {
				ip = ip + ":8080";
			}
			HttpUtil.sendGetRequest("http://" + ip + "/internal/server/refreshDspConstants.html");
		}
		DspConstantsManager.refresh();
	}

	public static void refresh(String key) throws Exception {
		String serverIps = IniBean.getIniValue(key);
		String[] segs = serverIps.split(",");
		for (String ip : segs) {
			if (!ip.contains(":")) {
				ip = ip + ":8080";
			}
			HttpUtil.sendGetRequest("http://" + ip + "/internal/server/refreshConstants.html");
		}
		ConstantsManager.refresh();
	}

	public static void refreshConfirm() throws Exception {
		refresh("server_ips_confirm");
	}

	public static void refreshOnline() throws Exception {
		try {
			ThreadPool.getInstance().exec(new Runnable() {
				@Override
				public void run() {
					try {
						refresh("server_ips_online");
					} catch (Exception e) {
						LoggerUtil.error("[refreshOnline]", e);
					}
				}
			});

		} catch (Exception e) {
			LoggerUtil.error("[refreshOnline]", e);
		}
	}

}
