package com.yuanshanbao.dsp.controller.index;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.InterfaceRetCode;

@RequestMapping({ "/index", "/i/index" })
@Controller
public class IndexController extends BaseController {

	// 客户端首页
	@RequestMapping("/home")
	@ResponseBody
	public Object index(HttpServletRequest request,
			HttpServletResponse response, String activityKey,
			PageBounds pageBounds, Integer client) {
		Map<String, Object> resultMap = new HashMap<>();
		try {

			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setSpecAppCodeDesc(resultMap, e.getReturnCode(),
					e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[home index]: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

}
