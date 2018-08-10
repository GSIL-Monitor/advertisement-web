package com.yuanshanbao.dsp.controller.web.education;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.information.model.Information;

@RequestMapping({ "/ed/education/common", "/m/ed/education/common" })
@Controller
public class CommonEducationController extends BaseInformationController {

	@RequestMapping("/index")
	@ResponseBody
	public Object index(HttpServletRequest request, ModelMap modelMap, String channel, String activityKey) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// String redirectUrl = forceRedirectUrl(request, channel,
			// activityKey);
			// if (StringUtils.isNotBlank(redirectUrl)) {
			// return redirect(redirectUrl);
			// }
			setChannelVariables(request, resultMap, activityKey);
			modelMap.put("activityPath", "ed/vipkid/mfl");
			// modelMap.put("appliedCount",
			// activityService.getActivityCount(activityKey));
			// modelMap.put("reverseCount", getReverseShowCount(channel,
			// activityKey));
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (Exception e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			LoggerUtil.error("[educationIndex: error]", e);
		}
		return resultMap;
	}

	@RequestMapping("/commit")
	@ResponseBody
	public Object commit(HttpServletRequest request, Information information, String channel, String activityKey) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			commit(request, resultMap, information, activityKey, MERCHANT_VIPKID);
		} catch (BusinessException e) {
			if (e.getReturnCode() == ComRetCode.ORDER_DELIVER_ERROR
					|| e.getReturnCode() == ComRetCode.ORDER_MOBILE_HAS_EXIST_ERROR
					|| e.getReturnCode() == ComRetCode.FAIL) {
				InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
				return resultMap;
			}
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			LoggerUtil.error("[vipkid Commit: error]", e);
		}
		return resultMap;
	}
}
