package com.yuanshanbao.dsp.controller.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.common.model.Feedback;
import com.yuanshanbao.dsp.common.service.FeedbackService;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.dsp.weixin.service.WeixinService;

@Controller
@RequestMapping({ "/common/feedback", "/i/common/feedback" })
public class FeedbackController extends BaseController {

	@Autowired
	private FeedbackService feedbackService;

	@Autowired
	private WeixinService weixinService;

	@RequestMapping("/index")
	public String index(HttpServletRequest request, ModelMap modelMap) {
		try {
			User user = getSessionUser(request);
			modelMap.put("contact", user == null);
			InterfaceRetCode.setAppCodeDesc(modelMap, ComRetCode.SUCCESS);
			return getFtlPath(request, "/common/feedback");
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(modelMap, e.getReturnCode(), e.getMessage());
			return getFtlPath(request, "/common/error");
		} catch (Exception e) {
			LoggerUtil.error("", e);
			InterfaceRetCode.setAppCodeDesc(modelMap, ComRetCode.FAIL);
			return getFtlPath(request, "/common/error");
		}
	}

	@ResponseBody
	@RequestMapping("/commit")
	public Object addFavorite(HttpServletRequest request, Feedback feedback) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		User user = getSessionUser(request);
		try {
			if (user != null) {
				feedback.setUserId(user.getUserId() + "");
			}
			feedback.setStatus(CommonStatus.ONLINE);
			feedbackService.insertFeedback(feedback);
			weixinService.sendFeedbackAlert(feedback);
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
