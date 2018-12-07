package com.yuanshanbao.dsp.controller.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.common.service.AlarmService;
import com.yuanshanbao.dsp.common.service.AlarmType;
import com.yuanshanbao.dsp.common.service.ServerLogService;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.dsp.weixin.service.WeixinService;

@Controller
@RequestMapping({ "/admin", "/m/admin", "/i/admin" })
public class AdminController extends BaseController {

	@Autowired
	private AlarmService alarmService;

	@Autowired
	private WeixinService weixinService;

	@Autowired
	private ServerLogService serverLogService;

	@RequestMapping("/serverLog")
	public Object serverLog(HttpServletRequest request, ModelMap modelMap, Long logId) {
		User user = getSessionUser(request);
		try {
			if (!alarmService.hasRight(user, AlarmType.SERVER)) {
				throw new BusinessException(ComRetCode.NOT_LOGIN);
			}
			modelMap.put("serverLog", serverLogService.selectServerLog(logId));
			InterfaceRetCode.setAppCodeDesc(modelMap, ComRetCode.SUCCESS);
			return getFtlPath(request, "/internal/admin/serverLog");
		} catch (BusinessException e) {
			LoggerUtil.error("[ServerLog]", e);
			InterfaceRetCode.setAppCodeDesc(modelMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			InterfaceRetCode.setAppCodeDesc(modelMap, ComRetCode.FAIL);
			LoggerUtil.error("[ServerLog]", e);
		}
		return getFtlPath(request, "/common/error");
	}
}
