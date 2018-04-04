package com.yuanshanbao.ms.base;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baidu.ueditor.ConfigManager;
import com.baidu.ueditor.define.ActionMap;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import com.baidu.ueditor.hunter.FileManager;
import com.baidu.ueditor.hunter.ImageHunter;
import com.baidu.ueditor.upload.Uploader;

@Controller
@RequestMapping("/ueditor")
public class UEditorController {

	@RequestMapping("/control")
	public void control(HttpServletRequest request, HttpServletResponse response) {
		ConfigManager configManager = ConfigManager.getInstance(request.getServletContext().getRealPath("/"),
				request.getContextPath(), request.getRequestURI());
		try {
			response.getWriter().write(exec(request, response, configManager));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String exec(HttpServletRequest request, HttpServletResponse response, ConfigManager configManager)
			throws Exception {

		String callbackName = request.getParameter("callback");

		if (callbackName != null) {

			if (!validCallbackName(callbackName)) {
				return new BaseState(false, AppInfo.ILLEGAL).toJSONString();
			}

			return callbackName + "(" + this.invoke(request, response, configManager) + ");";

		} else {
			return this.invoke(request, response, configManager);
		}

	}

	public String invoke(HttpServletRequest request, HttpServletResponse response, ConfigManager configManager)
			throws Exception {
		String actionType = request.getParameter("action");
		if (actionType == null || !ActionMap.mapping.containsKey(actionType)) {
			return new BaseState(false, AppInfo.INVALID_ACTION).toJSONString();
		}

		if (configManager == null || !configManager.valid()) {
			return new BaseState(false, AppInfo.CONFIG_ERROR).toJSONString();
		}

		State state = null;

		int actionCode = ActionMap.getType(actionType);

		Map<String, Object> conf = null;

		switch (actionCode) {

		case ActionMap.CONFIG:
			return configManager.getAllConfig().toString();

		case ActionMap.UPLOAD_IMAGE:
		case ActionMap.UPLOAD_SCRAWL:
		case ActionMap.UPLOAD_VIDEO:
		case ActionMap.UPLOAD_FILE:
			conf = configManager.getConfig(actionCode);
			state = new Uploader(request, conf).doExec();
			break;

		case ActionMap.CATCH_IMAGE:
			conf = configManager.getConfig(actionCode);
			String[] list = request.getParameterValues((String) conf.get("fieldName"));
			state = new ImageHunter(conf).capture(list);
			break;

		case ActionMap.LIST_IMAGE:
		case ActionMap.LIST_FILE:
			conf = configManager.getConfig(actionCode);
			int start = this.getStartIndex(request);
			state = new FileManager(conf).listFile(start);
			break;

		}

		return state.toJSONString();

	}

	public int getStartIndex(HttpServletRequest request) {

		String start = request.getParameter("start");

		try {
			return Integer.parseInt(start);
		} catch (Exception e) {
			return 0;
		}

	}

	/**
	 * callback参数验证
	 */
	public boolean validCallbackName(String name) {

		if (name.matches("^[a-zA-Z_]+[\\w0-9_]*$")) {
			return true;
		}

		return false;

	}
}
