package com.yuanshanbao.dsp.controller.web.game;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.core.InterfaceRetCode;

@RequestMapping({ "/game/common", "/m/game/common" })
@Controller
public class CommonGameController extends BaseGameController {

	@RequestMapping("/myPrize")
	public String chance(HttpServletRequest request, ModelMap modelMap, String activityKey) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			modelMap.put("myPrizeList", getMyPrizeList(request, activityKey));
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			LoggerUtil.error("[myPrize: error]", e);
		}
		return getFtlPath(request, "/game/common/myPrize/index");
	}
}
