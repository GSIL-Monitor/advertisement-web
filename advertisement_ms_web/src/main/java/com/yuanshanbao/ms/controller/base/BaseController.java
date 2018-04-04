package com.yuanshanbao.ms.controller.base;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yuanshanbao.ad.common.constant.ConstantsManager;
import com.yuanshanbao.ad.project.service.ProjectService;
import com.yuanshanbao.common.constant.SystemConstants;
import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.validator.util.ValidatorModel;
import com.yuanshanbao.common.validator.util.ValidatorUtils;
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
}
