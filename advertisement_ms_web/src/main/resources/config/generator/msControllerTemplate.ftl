package com.yuanshanbao.ms.controller.broker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;
import com.yuanshanbao.ad.core.CommonStatus;
import com.yuanshanbao.ad.core.InterfaceRetCode;
import ${defaultTemplate.ftlPath}.BrokerDepartment;
import ${defaultTemplate.servicePath};


@Controller
@RequestMapping("/admin/${defaultTemplate.ftlPath}")
public class Admin${defaultTemplate.entityName}Controller extends PaginationController {

	private static final String PAGE_LIST = "advertisement/${defaultTemplate.ftlPath}/list${defaultTemplate.entityName}";

	private static final String PAGE_INSERT = "advertisement/${defaultTemplate.ftlPath}/insert${defaultTemplate.entityName}";

	private static final String PAGE_UPDATE = "advertisement/${defaultTemplate.ftlPath}/update${defaultTemplate.entityName}";

	private static final String PAGE_VIEW = "advertisement/${defaultTemplate.ftlPath}/view${defaultTemplate.entityName}";

	@Autowired
	private ${defaultTemplate.serviceName} ${defaultTemplate.serviceViable};

	@RequestMapping("/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, ${defaultTemplate.entityName} ${defaultTemplate.entityViable}, Integer type, HttpServletRequest request,
			HttpServletResponse response) {
		Object object = ${defaultTemplate.serviceViable}.select${defaultTemplate.entityName}s(${defaultTemplate.entityViable}, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/insertWindow.do")
	public String insertWindow(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());
		return PAGE_INSERT;
	}

	@ResponseBody
	@RequestMapping("/insert.do")
	public Object insert(${defaultTemplate.entityName} ${defaultTemplate.entityViable}, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			validateParameters(${defaultTemplate.entityViable});
			${defaultTemplate.serviceViable}.insert${defaultTemplate.entityName}(${defaultTemplate.entityViable});
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}

	@ResponseBody
	@RequestMapping("/delete.do")
	public Object delete(Long ${defaultTemplate.entityViable}Id, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			if (${defaultTemplate.entityViable}Id == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			${defaultTemplate.entityName} ${defaultTemplate.entityViable} = new ${defaultTemplate.entityName}();
			${defaultTemplate.entityViable}.setStatus(CommonStatus.OFFLINE);
			${defaultTemplate.entityViable}.set${defaultTemplate.entityName}Id(${defaultTemplate.entityViable}Id);
			${defaultTemplate.serviceViable}.update${defaultTemplate.entityName}(${defaultTemplate.entityViable});

			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(HttpServletRequest request, HttpServletResponse response, ${defaultTemplate.entityName} ${defaultTemplate.entityViable}) {
		List<${defaultTemplate.entityName}> list = ${defaultTemplate.serviceViable}.select${defaultTemplate.entityName}s(${defaultTemplate.entityViable}, new PageBounds());
		if (list != null && list.size() > 0) {
			${defaultTemplate.entityViable} = list.get(0);
		}

		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());
		request.setAttribute("itemEdit", ${defaultTemplate.entityViable});
		return PAGE_UPDATE;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object update(${defaultTemplate.entityName} ${defaultTemplate.entityViable}, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			validateParameters(${defaultTemplate.entityViable});
			${defaultTemplate.serviceViable}.update${defaultTemplate.entityName}(${defaultTemplate.entityViable});
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}
		return result;
	}

	@RequestMapping("/view.do")
	public String view(${defaultTemplate.entityName} ${defaultTemplate.entityViable}, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		List<${defaultTemplate.entityName}> list = ${defaultTemplate.serviceViable}.select${defaultTemplate.entityName}s(${defaultTemplate.entityViable}, new PageBounds());
		if (list != null && list.size() > 0) {
			${defaultTemplate.entityViable} = list.get(0);
		}
		request.setAttribute("itemEdit", ${defaultTemplate.entityViable});
		return PAGE_VIEW;
	}

}
