package com.yuanshanbao.ms.controller.config;

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
import com.yuanshanbao.dsp.config.model.ConfigAction;
import com.yuanshanbao.dsp.config.model.Function;
import com.yuanshanbao.dsp.config.service.ConfigActionService;
import com.yuanshanbao.dsp.config.service.ConfigService;
import com.yuanshanbao.dsp.config.service.FunctionService;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/config/action")
public class AdminConfigActionController extends PaginationController {

	private static final String PAGE_LIST = "advertisement/config/action/listConfigAction";

	private static final String PAGE_INSERT = "advertisement/config/action/insertConfigAction";

	private static final String PAGE_UPDATE = "advertisement/config/action/updateConfigAction";

	private static final String PAGE_VIEW = "advertisement/config/action/viewConfigAction";

	@Autowired
	private ConfigService configService;
	
	@Autowired
	private ConfigActionService configActionService;
	
	@Autowired
	private FunctionService functionService;


	@RequestMapping("/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, ConfigAction configAction,
			HttpServletRequest request, HttpServletResponse response) {
		Object object = configActionService.selectConfigAction(configAction,
				getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/insertWindow.do")
	public String insertWindow(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		setStatus(request);
		return PAGE_INSERT;
	}
	
	private void setStatus(HttpServletRequest request) {
		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap()
				.entrySet());
		Function function = new Function();
		List<Function> functionList = functionService.selectFunctions(function, new PageBounds());
		request.setAttribute("functionList", functionList);
	}

	@ResponseBody
	@RequestMapping("/insert.do")
	public Object insert(ConfigAction configAction,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			
			validateParameters(configAction);;
			configActionService.insertConfigAction(configAction);
			
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(),
					e.getMessage());
		}

		return result;
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(HttpServletRequest request,
			HttpServletResponse response, Long configActionId) {
		ConfigAction configAction = configActionService.selectConfigActionById(configActionId);
		setStatus(request);
		request.setAttribute("itemEdit", configAction);
		return PAGE_UPDATE;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object update(ConfigAction configAction, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			if (configAction == null 
					|| configAction.getConfigActionId() == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			validateParameters(configAction);
			configActionService.updateConfigAction(configAction);

			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(),
					e.getMessage());
		}

		return result;
	}
	
	@ResponseBody
	@RequestMapping("/delete.do")
	public Object delete(Long configActionId, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			
			if (configActionId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			ConfigAction configAction = new ConfigAction();
			configAction.setStatus(CommonStatus.OFFLINE);
			configAction.setConfigActionId(configActionId);
			configActionService.updateConfigAction(configAction);

			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(),
					e.getMessage());
		}

		return result;
	}

	@RequestMapping("/view.do")
	public String view(Long configActionId, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		ConfigAction configAction = configActionService.selectConfigActionById(configActionId);
		request.setAttribute("itemEdit", configAction);
		return PAGE_VIEW;
	}

}
