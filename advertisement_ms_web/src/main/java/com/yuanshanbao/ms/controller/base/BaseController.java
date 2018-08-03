package com.yuanshanbao.ms.controller.base;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yuanshanbao.common.constant.SystemConstants;
import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.util.CommonUtil;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.RequestUtil;
import com.yuanshanbao.common.validator.util.ValidatorModel;
import com.yuanshanbao.common.validator.util.ValidatorUtils;
import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.advertiser.service.AdvertiserService;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.project.service.ProjectService;
import com.yuanshanbao.ms.model.admin.User;
import com.yuanshanbao.ms.service.admin.AdminUserService;
import com.yuanshanbao.paginator.domain.PageBounds;

public class BaseController {
	protected static final String RET_CODE_PARAM = "retCode";

	protected static final String RET_HTML = "html";

	protected static final String RET_ERROR_MSG = "error_msg";

	protected static final String RET_SUCCESS = "200";

	protected static final String RET_FAILURE = "500";

	protected static final String RET_INTERROR = "501";

	protected static final String RET_DELERROR = "501";

	protected static final String RET_CHECK_ERROR = "501";

	protected static final String HTML_SUCCESS = SystemConstants.HTML_SUCCESS;

	protected static final String HTML_FAILURE = SystemConstants.HTML_FAILURE;

	@Autowired
	protected AdminUserService userService;

	@Autowired
	protected ProjectService projectService;

	@Autowired
	protected AdvertiserService advertiserService;

	protected String getSuccessHtml(String msg) {
		return SystemConstants.HTML_SUCCESS.replace("#msg#", msg);
	}

	protected String getFailureHtml(String msg) {
		return SystemConstants.HTML_FAILURE.replace("#msg#", msg);
	}

	protected User getCurrentUser() {
		User user = null;

		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (obj != null) {
			user = (User) obj;
		}
		User query = new User();
		query.setUsername(user.getUsername());
		List<User> userList = userService.queryUsers(query, new PageBounds());
		if (userList.size() > 0) {
			return userList.get(0);
		}
		return user;
	}

	protected void validateParameters(Object object) {
		ValidatorModel model = null;
		try {
			model = ValidatorUtils.validate(object);
		} catch (Exception e) {
			LoggerUtil.error("[Error validate parameters: ]", e);
		}
		if (model != null) {
			throw new BusinessException(model.getField().getName(), model.getMessageCode());
		}
	}

	protected Long getProjectId(HttpServletRequest request) {
		return ConstantsManager.getProjectId(projectService, request);
	}

	protected Advertiser getBindAdvertiserByUser() {
		User user = null;

		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (obj == null || !(obj instanceof User)) {
			if ("dev".equals(CommonUtil.getEnvironment())) {
				Advertiser advertiser = new Advertiser();
				advertiser.setBindUserName("admin");
				advertiser.setProjectId(1L);
				List<Advertiser> advertiserList = advertiserService.selectAdvertiser(advertiser, new PageBounds());
				if (advertiserList != null && advertiserList.size() > 0) {
					return advertiserList.get(0);
				}
			}
			return null;
		}
		user = (User) obj;
		Advertiser advertiser = new Advertiser();
		advertiser.setBindUserName(user.getUsername());
		advertiser.setProjectId(user.getProjectId());
		List<Advertiser> advertiserList = advertiserService.selectAdvertiser(advertiser, new PageBounds());
		if (advertiserList != null && advertiserList.size() > 0) {
			return advertiserList.get(0);
		}
		return null;
	}

	protected String redirect(String url) {
		if (!url.contains(".")) {
			url = url + ".html";
		}
		return "redirect:" + url;
	}

	protected String getFtlPath(HttpServletRequest request, String ftl) {
		setHost(request);
		if (isMobile(request)) {
			return "/wap" + ftl;
		}
		return "/web" + ftl;
	}

	protected boolean isMobile(HttpServletRequest request) {
		if (RequestUtil.isFromWeixin(request)) {
			request.setAttribute("isFromWeixin", "true");
		}
		if (CommonUtil.isMobile(request)) {
			request.setAttribute("isMobilePage", "true");
			return true;
		}
		return false;
	}

	private void setHost(HttpServletRequest request) {
		try {
			String host = request.getHeader("Host");
			if (StringUtils.isNotBlank(host) && host.contains("yuanshanbao")) {
				request.setAttribute("isYuanshanbao", "true");
			} else if (StringUtils.isNotBlank(host) && host.contains("dachuanbao")) {
				request.setAttribute("isDachuanbao", "true");
			} else if (StringUtils.isNotBlank(host) && host.contains("yuanshanbx.com")) {
				request.setAttribute("isYuanshanbx", "true");
			} else if (StringUtils.isNotBlank(host) && host.contains("huhabao.com")) {
				request.setAttribute("isHuhabao", "true");
			} else if (StringUtils.isNotBlank(host) && host.contains("ruidaiwang.cn")) {
				request.setAttribute("isRuidaiwang", "true");
			}
			String isHttps = request.getHeader("IsHttps");
			if ("false".equals(isHttps)) {
				request.setAttribute("isHttps", "false");
			} else {
				request.setAttribute("isHttps", "true");
			}
		} catch (Exception e) {
			LoggerUtil.error("[setHost]", e);
		}
	}
}
