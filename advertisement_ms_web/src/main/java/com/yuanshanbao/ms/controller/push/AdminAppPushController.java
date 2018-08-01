package com.yuanshanbao.ms.controller.push;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.CommonUtil;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.common.constant.CommonConstant;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.IniBean;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.product.service.ProductService;
import com.yuanshanbao.dsp.push.model.AppPush;
import com.yuanshanbao.dsp.push.service.AppPushService;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.dsp.user.service.UserService;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/appPush")
public class AdminAppPushController extends PaginationController {

	private static final String PAGE_LIST = "advertisement/push/listAppPush";

	private static final String PAGE_INSERT = "advertisement/push/insertAppPush";

	private static final String PAGE_UPDATE = "advertisement/push/updateAppPush";

	private static final String PAGE_VIEW = "advertisement/push/viewAppPush";

	public static final String TEST_APPPUSH_KEY = "test_app_push";

	@Autowired
	UserService userService;

	@Autowired
	AppPushService appPushService;

	@Autowired
	ProductService productService;

	@RequestMapping("/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, AppPush appPush, HttpServletRequest request, HttpServletResponse response) {
		Object object = appPushService.selectAppPush(appPush, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/insertWindow.do")
	public String insertWindow(Integer type, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());
		request.setAttribute("type", type);
		return PAGE_INSERT;
	}

	@ResponseBody
	@RequestMapping("/insert.do")
	public Object insert(AppPush appPush, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			validateParameters(appPush);
			setProperty(appPush);
			appPushService.insertAppPush(appPush);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("appPush insert function - upload image error", e2);
		}

		return result;
	}

	private void setProperty(AppPush appPush) {
		User user = null;
		if (appPush != null && ValidateUtil.isPhoneNo(appPush.getUserId())) {
			user = userService.selectUserByMobile(appPush.getUserId());
			if (user == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			appPush.setUserId(user.getUserId() + "");
		}
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(HttpServletRequest request, HttpServletResponse response, AppPush appPush) {
		List<AppPush> list = appPushService.selectAppPush(appPush, new PageBounds());
		if (list != null && list.size() >= 0) {
			appPush = list.get(0);
		}
		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());
		request.setAttribute("itemEdit", appPush);
		return PAGE_UPDATE;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object update(AppPush appPush, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			validateParameters(appPush);
			setProperty(appPush);
			appPushService.updateAppPush(appPush);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("appPush update function - upload image error", e2);
		}

		return result;
	}

	@ResponseBody
	@RequestMapping("/delete.do")
	public Object delete(Long pushId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {

			if (pushId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			AppPush appPush = new AppPush();
			appPush.setStatus(CommonStatus.OFFLINE);
			appPush.setPushId(pushId);
			appPushService.updateAppPush(appPush);

			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}

	@RequestMapping("/view.do")
	public String view(AppPush appPush, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		List<AppPush> list = appPushService.selectAppPush(appPush, new PageBounds());
		if (list != null && list.size() >= 0) {
			appPush = list.get(0);
		}
		request.setAttribute("itemEdit", appPush);
		return PAGE_VIEW;
	}

	@ResponseBody
	@RequestMapping("/push.do")
	public Object push(Long pushId, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			AppPush appPush = appPushService.selectAppPush(pushId);
			appPush.setSound("default");
			setProperty(appPush);
			String bindAila = appPush.getUserId();
			if (StringUtils.isNotBlank(bindAila)) {
				appPush.setUserId(CommonUtil.getBindPrefix() + bindAila);
			}
			appPushService.sendPush(appPush);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("", e);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/testPush.do")
	public Object testPush(Long pushId, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String[] mobiles = IniBean.getIniValue(TEST_APPPUSH_KEY, "18810503302").split(
					CommonConstant.COMMA_SPLIT_STR);
			if (mobiles.length > 0) {
				for (String mobile : mobiles) {
					AppPush appPush = appPushService.selectAppPush(pushId);
					appPush.setUserId(mobile);
					appPush.setSound("default");
					setProperty(appPush);
					String bindAila = appPush.getUserId();
					if (StringUtils.isNotBlank(bindAila)) {
						appPush.setUserId(CommonUtil.getBindPrefix() + bindAila);
					}
					appPushService.sendPush(appPush);
				}
			}
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("", e);
		}
		return result;
	}

}
