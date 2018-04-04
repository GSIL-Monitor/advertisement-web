package com.yuanshanbao.ad.controller.internal;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.ad.common.constant.ConstantsManager;
import com.yuanshanbao.ad.controller.base.BaseController;
import com.yuanshanbao.ad.core.IniBean;
import com.yuanshanbao.ad.core.InterfaceRetCode;

@Controller
@RequestMapping("/internal/server")
public class ServerController extends BaseController {
	
	@Resource(name="iniBean")
	private IniBean iniBean;

	@ResponseBody
	@RequestMapping("/refreshConstants")
	public Object refreshConfig(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			ConstantsManager.refresh();
			iniBean.refresh();
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			LoggerUtil.error("[FavoriteController-add: error]", e);
		}
		return resultMap;
	}
}
