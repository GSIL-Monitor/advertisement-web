package com.yuanshanbao.ms.controller.channel;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.channel.model.Channel;
import com.yuanshanbao.dsp.channel.service.ChannelService;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/activity/channel")
public class AdminActivityChannelController extends PaginationController {
	private static final String PAGE_LIST = "advertisement/channel/listChannel";

	private static final String PAGE_INSERT = "advertisement/channel/insertChannel";

	private static final String PAGE_UPDATE = "advertisement/channel/updateChannel";

	private static final String PAGE_VIEW = "advertisement/channel/viewChannel";

	@Autowired
	private ChannelService channelService;

	@RequestMapping("/list.do")
	public String list(String activityId, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		request.setAttribute("activityId", activityId);
		return PAGE_LIST;
	}

	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, Channel channel, HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		Object object = channelService.selectChannels(channel, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/insertWindow.do")
	public String insertWindow(String activityId, HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		request.setAttribute("activityId", activityId);
		setProperty(request);
		return PAGE_INSERT;
	}

	@ResponseBody
	@RequestMapping("/insert.do")
	public Object insert(String range, Channel channel, HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		channelService.insertChannel(channel);
		InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		return result;
	}

	private void setProperty(HttpServletRequest request) {
	}
}
