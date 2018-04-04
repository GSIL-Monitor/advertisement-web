package com.yuanshanbao.ms.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.ms.model.admin.UserMonitorLog;
import com.yuanshanbao.ms.service.monitor.UserMonitorLogService;
import com.yuanshanbao.paginator.domain.Order;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/monitor")
public class AdminUserOptLogController extends PaginationController {

	private static final String PAGE_USER_MONITOR_LOG = "admin/system/userMonitor/userMonitorLog";

	@Autowired
	private UserMonitorLogService userMonitorLogService;

	@RequestMapping("/userMonitorLog.do")
	public String userMonitorLog(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_USER_MONITOR_LOG;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/queryUserMonitorLog.do")
	public Object queryUserMonitorLog(String range, UserMonitorLog userMonitorLog, HttpServletRequest request,
			HttpServletResponse response) {
		PageBounds pageBounds = getPageBounds(range, request);

		if (pageBounds.getOrders() != null) {
			for (Order order : pageBounds.getOrders()) {
				if ("operateIp".equals(order.getProperty())) {
					order.setProperty("operate_ip");
				}

				if ("operateType".equals(order.getProperty())) {
					order.setProperty("operate_type");
				}

				if ("operateTime".equals(order.getProperty())) {
					order.setProperty("operate_time");
				}

				if ("userName".equals(order.getProperty())) {
					order.setProperty("user_name");
				}
			}
		}

		Object result = userMonitorLogService.queryUserMonitorLogs(userMonitorLog, pageBounds);

		PageList pageList = (PageList) result;
		return setPageInfo(request, response, pageList);
	}
}
