package com.yuanshanbao.ms.controller.function;

import java.util.HashMap;
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
import com.yuanshanbao.dsp.config.model.Function;
import com.yuanshanbao.dsp.config.service.FunctionService;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/function")
public class AdminFunctionController extends PaginationController {

	private static final String PAGE_LIST = "insurance/function/listFunction";

	private static final String PAGE_INSERT = "insurance/function/insertFunction";

	private static final String PAGE_UPDATE = "insurance/function/updateFunction";

	private static final String PAGE_VIEW = "insurance/function/viewFunction";

	@Autowired
	private FunctionService functionService;
	

	@RequestMapping("/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, Function function, HttpServletRequest request,
			HttpServletResponse response) {
		Object object = functionService.selectFunctions(function,
				getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/insertWindow.do")
	public String insertWindow(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap()
				.entrySet());
		return PAGE_INSERT;
	}

	@ResponseBody
	@RequestMapping("/insert.do")
	public Object insert(Function function,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			validateParameters(function);
			function.setStatus(CommonStatus.ONLINE);
			functionService.insertFunction(function);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(),
					e.getMessage());
		}

		return result;
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(HttpServletRequest request, Long functionId,
			HttpServletResponse response) {
		Function function = functionService.selectFunctionById(functionId);
		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap()
				.entrySet());
		request.setAttribute("itemEdit", function);
		return PAGE_UPDATE;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object update(Function function, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			if (function == null 
					|| function.getFunctionId() == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			validateParameters(function);
			functionService.updateFunction(function);

			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(),
					e.getMessage());
		}

		return result;
	}
	
	@ResponseBody
	@RequestMapping("/delete.do")
	public Object delete(Long functionId, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			
			if (functionId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			Function function = new Function();
			function.setStatus(CommonStatus.OFFLINE);
			function.setFunctionId(functionId);
			functionService.updateFunction(function);

			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(),
					e.getMessage());
		}

		return result;
	}

	@RequestMapping("/view.do")
	public String view(Long functionId, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		Function function = functionService.selectFunctionById(functionId);
		request.setAttribute("itemEdit", function);
		return PAGE_VIEW;
	}

}
