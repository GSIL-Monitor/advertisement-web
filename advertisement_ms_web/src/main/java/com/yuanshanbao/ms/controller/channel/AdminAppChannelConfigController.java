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
import com.yuanshanbao.ad.app.model.AppType;
import com.yuanshanbao.ad.channel.service.ChannelService;
import com.yuanshanbao.ad.config.ConfigManager;
import com.yuanshanbao.ad.config.model.Config;
import com.yuanshanbao.ad.config.model.ConfigCategory;
import com.yuanshanbao.ad.config.model.Function;
import com.yuanshanbao.ad.config.service.ConfigActionService;
import com.yuanshanbao.ad.config.service.ConfigService;
import com.yuanshanbao.ad.config.service.FunctionService;
import com.yuanshanbao.ad.core.CommonStatus;
import com.yuanshanbao.ad.core.InterfaceRetCode;
import com.yuanshanbao.ad.merchant.service.MerchantService;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.ms.controller.common.AdminServerController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/app/channel")
public class AdminAppChannelConfigController extends PaginationController {

	private static final String PAGE_LIST = "advertisement/app/channel/config/listConfig";

	private static final String PAGE_INSERT = "advertisement/app/channel/config/insertConfig";

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private ChannelService channelService;

	@Autowired
	private FunctionService functionService;

	@Autowired
	private ConfigService configService;

	@Autowired
	private ConfigActionService configActionService;

	@RequestMapping("/config/list.do")
	public String list(String appId, String key, HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("appId", appId);
		request.setAttribute("key", key);
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/config/query.do")
	public Object query(String range, Config config, HttpServletRequest request, HttpServletResponse response) {
		Object object = configService.selectConfig(config, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/config/insertWindow.do")
	public String insertWindow(HttpServletRequest request, Long appId, String channel, HttpServletResponse response,
			ModelMap modelMap) {
		Map<Integer, ConfigCategory> map = configService.getConfigCategoryList(null, channel, AppType.getkey(appId));
		request.setAttribute("configCategoryList", map.values());
		request.setAttribute("channel", channel);
		return PAGE_INSERT;
	}

	@ResponseBody
	@RequestMapping("/config/insert.do")
	public Object insert(HttpServletRequest request, Function function, String channel, Long appId,
			HttpServletResponse response, ModelMap modelMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (function == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			configService.updateActivityConfig(request, null, channel, AppType.getkey(appId));
			Config config = new Config();
			config.setStatus(CommonStatus.ONLINE);
			List<Config> configsList = configService.selectConfig(config, new PageBounds());
			ConfigManager.refreshConfig(null, null, null, null, null, configsList, null, null, null);
			AdminServerController.refreshConfirm();
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("error :", e2);
		}

		return result;
	}
}
