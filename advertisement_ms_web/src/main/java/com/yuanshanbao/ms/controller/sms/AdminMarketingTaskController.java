package com.yuanshanbao.ms.controller.sms;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.UploadUtils;
import com.yuanshanbao.ad.core.CommonStatus;
import com.yuanshanbao.ad.core.InterfaceRetCode;
import com.yuanshanbao.ad.sms.model.MarketingTask;
import com.yuanshanbao.ad.sms.model.MarketingTaskType;
import com.yuanshanbao.ad.sms.service.MarketingSmsService;
import com.yuanshanbao.ad.sms.service.MarketingTaskService;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/marketing/task")
public class AdminMarketingTaskController extends PaginationController {

	public static final String PAGE_LIST = "advertisement/marketing/task/listMarketingTask";

	private static final String PAGE_INSERT = "advertisement/marketing/task/insertMarketingTask";

	private static final String PAGE_UPDATE = "advertisement/marketing/task/updateMarketingTask";

	private static final String PAGE_VIEW = "advertisement/marketing/task/viewMarketingTask";

	@Autowired
	private MarketingSmsService marketingSmsService;

	@Autowired
	private MarketingTaskService marketingTaskService;

	@RequestMapping("/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, MarketingTask marketingTask, HttpServletRequest request,
			HttpServletResponse response) {
		Object object = marketingTaskService.selectMarketingTask(marketingTask, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/insertWindow.do")
	public String insertWindow(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		request.setAttribute("statusList", MarketingTaskType.getStatusDescriptionMap().entrySet());
		return PAGE_INSERT;
	}

	@ResponseBody
	@RequestMapping("/insert.do")
	public Object insert(MarketingTask marketingTask, @RequestParam("file") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (file.isEmpty()) {
				throw new BusinessException(ComRetCode.UPLOAD_AVATAR_ERROR);
			}
			if (StringUtils.isBlank(marketingTask.getSendTimeStr())) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			if (marketingTask.getExportCount() == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}

			marketingTask
					.setSendTime(DateUtils.formatToTimestamp(marketingTask.getSendTimeStr(), "yyyy-MM-dd HH:mm:ss"));
			marketingTask.setUploadUrl(UploadUtils.uploadFile(file, UploadUtils.FTP_COMMON_FILE));
			marketingTask.setStatus(CommonStatus.ONLINE);
			validateParameters(marketingTask);
			marketingTaskService.insertMarketingTask(marketingTask);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("[ms insert marketing]", e2);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.FAIL);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/delete.do")
	public Object delete(Long marketingTaskId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			if (marketingTaskId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			MarketingTask marketingTask = new MarketingTask();
			marketingTask.setStatus(CommonStatus.OFFLINE);
			marketingTask.setMarketingTaskId(marketingTaskId);
			marketingTaskService.updateMarketingTask(marketingTask);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(HttpServletRequest request, HttpServletResponse response, MarketingTask marketingTask) {
		List<MarketingTask> list = marketingTaskService.selectMarketingTask(marketingTask, new PageBounds());
		if (list != null && list.size() > 0) {
			marketingTask = list.get(0);
		}

		request.setAttribute("statusList", MarketingTaskType.getStatusDescriptionMap().entrySet());
		request.setAttribute("itemEdit", marketingTask);
		return PAGE_UPDATE;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object update(MarketingTask marketingTask,
			@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (StringUtils.isBlank(marketingTask.getDownloadMobileType())) {
				throw new BusinessException(ComRetCode.UPLOAD_AVATAR_ERROR);
			}
			if (StringUtils.isBlank(marketingTask.getSendTimeStr())) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			if (marketingTask.getExportCount() == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			marketingTask
					.setSendTime(DateUtils.formatToTimestamp(marketingTask.getSendTimeStr(), "yyyy-MM-dd HH:mm:ss"));
			if (!file.isEmpty()) {
				marketingTask.setUploadUrl(UploadUtils.uploadFile(file, UploadUtils.FTP_COMMON_FILE));
			}

			marketingTask.setStatus(CommonStatus.ONLINE);
			validateParameters(marketingTask);
			marketingTaskService.updateMarketingTask(marketingTask);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("[ms update marketingTask]", e2);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.FAIL);
		}

		return result;
	}

	@RequestMapping("/view.do")
	public String view(MarketingTask marketingTask, ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		List<MarketingTask> list = marketingTaskService.selectMarketingTask(marketingTask, new PageBounds());
		if (list != null && list.size() > 0) {
			marketingTask = list.get(0);
		}
		request.setAttribute("itemEdit", marketingTask);
		return PAGE_VIEW;
	}

	@ResponseBody
	@RequestMapping("/execute.do")
	public Object search(Long marketingTaskId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			marketingTaskService.executeMarketingTask(marketingTaskId);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("[marketingTask error]", e2);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.FAIL);
		}

		return result;
	}

}