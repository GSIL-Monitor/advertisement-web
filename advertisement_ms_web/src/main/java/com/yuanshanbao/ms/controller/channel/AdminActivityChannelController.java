package com.yuanshanbao.ms.controller.channel;

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
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.channel.model.Channel;
import com.yuanshanbao.dsp.channel.model.ChannelType;
import com.yuanshanbao.dsp.channel.service.ChannelService;
import com.yuanshanbao.dsp.config.model.Config;
import com.yuanshanbao.dsp.config.model.ConfigCategory;
import com.yuanshanbao.dsp.config.model.Function;
import com.yuanshanbao.dsp.config.service.ConfigService;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.ms.controller.common.AdminServerController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/activity/channel")
public class AdminActivityChannelController extends PaginationController {
	private static final String PAGE_LIST = "advertisement/channel/listChannel";

	private static final String PAGE_INSERT = "advertisement/channel/insertChannel";

	private static final String PAGE_UPDATE = "advertisement/channel/updateChannel";

	private static final String PAGE_VIEW = "advertisement/channel/viewChannel";

	private static final String PAGE_CONFIG_INSERT = "advertisement/channel/config/insertConfig";

	@Autowired
	private ChannelService channelService;

	@Autowired
	private ConfigService configService;

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
		try {
			channel.setProjectId(getProjectId(request));
			channelService.insertChannel(channel);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("channel insert function ", e2);
		}
		return result;
	}

	private void setProperty(HttpServletRequest request) {
		request.setAttribute("typeList", ChannelType.getCodeDescriptionMap().entrySet());
		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(Channel channel, HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		List<Channel> list = channelService.selectChannels(channel, new PageBounds());
		if (list != null && list.size() > 0) {
			channel = list.get(0);
		}
		request.setAttribute("channel", channel);
		setProperty(request);
		return PAGE_UPDATE;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object update(String range, Channel channel, HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			channelService.updateChannel(channel);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("channel update function ", e2);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/delete.do")
	public Object delete(String range, Long channelId, HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Channel channel = new Channel();
			channel.setChannelId(channelId);
			channel.setStatus(CommonStatus.OFFLINE);
			channelService.updateChannel(channel);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("channel delete function ", e2);
		}
		return result;
	}

	@RequestMapping("/config/insertWindow.do")
	public String insertWindow(HttpServletRequest request, Long activityId, String channel,
			HttpServletResponse response, ModelMap modelMap) {
		Map<Integer, ConfigCategory> map = configService.getConfigCategoryList(activityId, channel);
		request.setAttribute("configCategoryList", map.values());
		request.setAttribute("activityId", activityId.toString());
		request.setAttribute("channel", channel);
		return PAGE_CONFIG_INSERT;
	}

	@ResponseBody
	@RequestMapping("/config/insert.do")
	public Object insert(HttpServletRequest request, Function function, String channel, Long activityId,
			HttpServletResponse response, ModelMap modelMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (function == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			configService.updateActivityConfig(request, activityId, channel);
			Config config = new Config();
			config.setStatus(CommonStatus.ONLINE);
			List<Config> configsList = configService.selectConfig(config, new PageBounds());
			// ConfigManager.refreshConfig(null, null, null, configsList, null,
			// null);
			AdminServerController.refreshConfirm();
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("error :", e2);
		}

		return result;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/config/query.do")
	public Object query(String range, Config config, HttpServletRequest request, HttpServletResponse response) {
		Object object = configService.selectConfig(config, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}
}
