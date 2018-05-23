package com.yuanshanbao.ms.controller.activity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/activity/combine")
public class AdminActivityCombineController {
	private static final String PAGE_LIST = "advertisement/activity/listActivity";

	private static final String PAGE_INSERT = "advertisement/activity/insertActivity";

	private static final String PAGE_UPDATE = "advertisement/activity/updateActivity";

	@RequestMapping("/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("isSimple", "0");
		return PAGE_LIST;
	}
}
