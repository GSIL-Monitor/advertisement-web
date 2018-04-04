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
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.ad.activity.model.Activity;
import com.yuanshanbao.ad.activity.service.ActivityService;
import com.yuanshanbao.ad.channel.model.Channel;
import com.yuanshanbao.ad.channel.service.ChannelService;
import com.yuanshanbao.ad.config.model.Config;
import com.yuanshanbao.ad.config.model.Function;
import com.yuanshanbao.ad.config.service.ConfigService;
import com.yuanshanbao.ad.config.service.FunctionService;
import com.yuanshanbao.ad.core.CommonStatus;
import com.yuanshanbao.ad.core.InterfaceRetCode;
import com.yuanshanbao.ad.merchant.model.Merchant;
import com.yuanshanbao.ad.merchant.service.MerchantService;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.ms.controller.common.AdminServerController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/config")
public class AdminConfigController extends PaginationController {

	private static final String PAGE_LIST = "insurance/config/listConfig";

	private static final String PAGE_INSERT = "insurance/config/insertConfig";

	private static final String PAGE_UPDATE = "insurance/config/updateConfig";

	private static final String PAGE_VIEW = "insurance/config/viewConfig";

	@Autowired
	private ConfigService configService;

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private ActivityService activityService;

	@Autowired
	private ChannelService channelService;

	@Autowired
	private FunctionService functionService;

	@RequestMapping("/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, Config config, HttpServletRequest request, HttpServletResponse response) {
		Object object = configService.selectConfig(config, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/insertWindow.do")
	public String insertWindow(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		setStatus(request);
		return PAGE_INSERT;
	}

	private void setStatus(HttpServletRequest request) {
		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());
		Merchant merchant = new Merchant();
		List<Merchant> merList = merchantService.selectMerchants(merchant, new PageBounds());
		Activity activity = new Activity();
		List<Activity> activityList = activityService.selectActivitys(activity, new PageBounds());
		Channel channel = new Channel();
		List<Channel> channelList = channelService.selectChannels(channel, new PageBounds());
		Function function = new Function();
		List<Function> functionList = functionService.selectFunctions(function, new PageBounds());

		request.setAttribute("merchantList", merList);
		request.setAttribute("activityList", activityList);
		request.setAttribute("channelList", channelList);
		request.setAttribute("functionList", functionList);
	}

	@ResponseBody
	@RequestMapping("/insert.do")
	public Object insert(Config config, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {

			validateParameters(config);
			configService.insertConfig(config);
			AdminServerController.refreshConfirm();
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("", e);
		}

		return result;
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(HttpServletRequest request, HttpServletResponse response, Long configId) {
		Config config = configService.selectConfigById(configId);
		setStatus(request);
		request.setAttribute("itemEdit", config);
		return PAGE_UPDATE;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object update(Config config, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			if (config == null || config.getConfigId() == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			validateParameters(config);
			configService.updateConfig(config);
			AdminServerController.refreshConfirm();
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("", e);
		}

		return result;
	}

	@ResponseBody
	@RequestMapping("/delete.do")
	public Object delete(Long configId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {

			if (configId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			Config config = new Config();
			config.setStatus(CommonStatus.OFFLINE);
			config.setConfigId(configId);
			configService.updateConfig(config);
			AdminServerController.refreshConfirm();
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("", e);
		}

		return result;
	}

	@RequestMapping("/view.do")
	public String view(Long configId, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		Config config = configService.selectConfigById(configId);
		request.setAttribute("itemEdit", config);
		return PAGE_VIEW;
	}

}
