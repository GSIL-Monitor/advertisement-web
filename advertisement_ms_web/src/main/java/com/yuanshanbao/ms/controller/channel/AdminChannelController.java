package com.yuanshanbao.ms.controller.channel;

import java.io.IOException;
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
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.PropertyUtil;
import com.yuanshanbao.common.util.UploadUtils;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.ms.controller.common.AdminServerController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;
import com.yuanshanbao.ad.activity.model.Activity;
import com.yuanshanbao.ad.activity.service.ActivityService;
import com.yuanshanbao.ad.app.model.AppType;
import com.yuanshanbao.ad.channel.model.Channel;
import com.yuanshanbao.ad.channel.model.ChannelProcedure;
import com.yuanshanbao.ad.channel.model.ChannelType;
import com.yuanshanbao.ad.channel.service.ChannelProcedureService;
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

@Controller
@RequestMapping("/admin/channel")
public class AdminChannelController extends PaginationController {

	private static final String PAGE_LIST = "advertisement/channel/listChannel";

	private static final String PAGE_INSERT = "advertisement/channel/insertChannel";

	private static final String PAGE_UPDATE = "advertisement/channel/updateChannel";

	private static final String PAGE_VIEW = "advertisement/channel/viewChannel";

	private static final String PAGE_CONFIG_LIST = "advertisement/channel/config/listConfig";

	private static final String PAGE_CONFIG_INSERT = "advertisement/channel/config/insertConfig";

	private static final String PAGE_PROCEDURE_LIST = "advertisement/channel/listChannelProcedure";

	private static final String PAGE_PROCEDURE_INSERT = "advertisement/channel/insertChannelProcedure";

	private static final String PAGE_PROCEDURE_UPDATE = "advertisement/channel/updateChannelProcedure";

	public static String OSS_HOST_FILES = PropertyUtil.getProperty("oss.host.files");

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private ActivityService activityService;

	@Autowired
	private ChannelService channelService;

	@Autowired
	private ChannelProcedureService channelProcedureService;

	@Autowired
	private FunctionService functionService;

	@Autowired
	private ConfigService configService;

	@Autowired
	private ConfigActionService configActionService;

	@RequestMapping("/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, Channel channel, HttpServletRequest request, HttpServletResponse response) {
		Object object = channelService.selectChannels(channel, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/insertWindow.do")
	public String insertWindow(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		setProperty(request);
		return PAGE_INSERT;
	}

	private void setProperty(HttpServletRequest request) {
		Activity activity = new Activity();
		activity.setStatus(CommonStatus.ONLINE);
		List<Activity> activityList = activityService.selectActivitys(activity, new PageBounds());
		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());
		request.setAttribute("showTypeList", ChannelType.getShowTypeDescriptionMap().entrySet());
		request.setAttribute("deliverTypeList", ChannelType.getDeliverTypeDescriptionMap().entrySet());
		request.setAttribute("appList", AppType.getDescriptionMap().entrySet());
		request.setAttribute("activityList", activityList);
	}

	@ResponseBody
	@RequestMapping("/insert.do")
	public Object insert(Channel channel, @RequestParam(value = "image", required = false) MultipartFile file,
			@RequestParam(value = "androidFile", required = false) MultipartFile androidFile,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			if (ConfigManager.getChannel(channel.getKey()) != null) {
				throw new BusinessException(ComRetCode.SUCCESS_DUPLICATED);
			}
			// if (!file.isEmpty()) {
			// channel.setImageUrl(UploadUtils.uploadFile(file,
			// UploadUtils.FTP_CHANNEL_IMAGE));
			// }
			setAppUrl(androidFile, channel);

			if (StringUtils.isNotBlank(channel.getKey())) {
				channel.setKey(channel.getKey().trim());
			}
			validateParameters(channel);
			channelService.insertChannel(channel);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("upload image error", e2);
		}

		return result;
	}

	private void setAppUrl(MultipartFile androidFile, Channel channel) throws IOException {
		if (channel.getAppId() == null) {
			throw new BusinessException(ComRetCode.WRONG_PARAMETER);
		}
		String appName = "xingdai";
		if (AppType.RUIDAI_ID.equals(channel.getAppId())) {
			appName = "ruidai";
		}
		if (!androidFile.isEmpty()) {
			String path = "app/" + appName + "/" + androidFile.getOriginalFilename();
			UploadUtils.uploadFiles(androidFile.getInputStream(), androidFile.getSize(), path);
		}
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(HttpServletRequest request, HttpServletResponse response, Channel channel) {
		List<Channel> list = channelService.selectChannels(channel, new PageBounds());
		if (list != null && list.size() > 0) {
			channel = list.get(0);
		}

		setProperty(request);
		request.setAttribute("itemEdit", channel);
		return PAGE_UPDATE;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object update(Channel channel, @RequestParam(value = "image", required = false) MultipartFile file,
			HttpServletRequest request,
			@RequestParam(value = "androidFile", required = false) MultipartFile androidFile,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {

			// if (!file.isEmpty()) {
			// channel.setImageUrl(UploadUtils.uploadFile(file,
			// UploadUtils.FTP_CHANNEL_IMAGE));
			// }
			if (StringUtils.isNotBlank(channel.getKey())) {
				channel.setKey(channel.getKey().trim());
			}
			setAppUrl(androidFile, channel);

			validateParameters(channel);
			channelService.updateChannel(channel);
			AdminServerController.refreshConfirm();
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("upload image error", e2);
		}

		return result;
	}

	@ResponseBody
	@RequestMapping("/delete.do")
	public Object delete(Long channelId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {

			if (channelId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			Channel channel = new Channel();
			channel.setStatus(CommonStatus.OFFLINE);
			channel.setChannelId(channelId);
			channelService.updateChannel(channel);

			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}

	@RequestMapping("/view.do")
	public String view(Channel channel, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		List<Channel> list = channelService.selectChannels(channel, new PageBounds());
		if (list != null && list.size() > 0) {
			channel = list.get(0);
		}
		request.setAttribute("itemEdit", channel);
		return PAGE_VIEW;
	}

	@RequestMapping("/config/list.do")
	public String list(String activityId, String key, HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("activityId", activityId);
		request.setAttribute("key", key);
		return PAGE_CONFIG_LIST;
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
	public String insertWindow(HttpServletRequest request, Long activityId, String channel, String productKey,
			HttpServletResponse response, ModelMap modelMap) {
		Map<Integer, ConfigCategory> map = configService.getConfigCategoryList(activityId, channel, productKey);
		request.setAttribute("configCategoryList", map.values());
		request.setAttribute("activityId", activityId.toString());
		request.setAttribute("channel", channel);
		return PAGE_CONFIG_INSERT;
	}

	@ResponseBody
	@RequestMapping("/config/insert.do")
	public Object insert(HttpServletRequest request, Function function, String channel, String productKey,
			Long activityId, HttpServletResponse response, ModelMap modelMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (function == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}

			configService.updateActivityConfig(request, activityId, channel, productKey);

			Config config = new Config();
			config.setStatus(CommonStatus.ONLINE);
			List<Config> configsList = configService.selectConfig(config, new PageBounds());
			ConfigManager.refreshConfig(null, null, null, null, null, configsList, null, null, null);
			AdminServerController.refreshConfirm();

			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("error: ", e2);
		}

		return result;
	}

	@RequestMapping("/procedure/list.do")
	public String procedureList(HttpServletRequest request, HttpServletResponse response, Long channelId) {
		Channel channel = channelService.selectChannel(channelId);
		request.setAttribute("channel", channel);
		return PAGE_PROCEDURE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/procedure/query.do")
	public Object queryProcedure(String range, ChannelProcedure channelProcedure, HttpServletRequest request,
			HttpServletResponse response) {
		Object object = channelProcedureService.selectChannelProcedures(channelProcedure, getPageBounds(request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/procedure/insertWindow.do")
	public String insertProcedureWindow(HttpServletRequest request, ChannelProcedure channelProcedure,
			HttpServletResponse response, String channel, ModelMap modelMap) {
		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());
		request.setAttribute("channel", channel);
		return PAGE_PROCEDURE_INSERT;
	}

	@ResponseBody
	@RequestMapping("/procedure/insert.do")
	public Object insertChannelProcedure(ChannelProcedure channelProcedure, MultipartFile image,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			validateParameters(channelProcedure);
			if (image != null) {
				channelProcedure.setImageUrl(UploadUtils.uploadFile(image, "file/channel"));
			}
			channelProcedureService.insertChannelProcedure(channelProcedure);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("advertisement insert function - upload image error", e2);
		}

		return result;
	}

	@RequestMapping("/procedure/updateWindow.do")
	public String updateProcedureWindow(HttpServletRequest request, ChannelProcedure channelProcedure,
			HttpServletResponse response, Long procedureId, ModelMap modelMap) {
		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());
		request.setAttribute("itemEdit", channelProcedureService.selectChannelProcedure(procedureId));
		return PAGE_PROCEDURE_UPDATE;
	}

	@ResponseBody
	@RequestMapping("/procedure/update.do")
	public Object updateChannelProcedure(ChannelProcedure channelProcedure, MultipartFile image,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			validateParameters(channelProcedure);
			if (image != null && !image.isEmpty()) {
				channelProcedure.setImageUrl(UploadUtils.uploadFile(image, "file/channel"));
			}
			channelProcedureService.updateChannelProcedure(channelProcedure);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("advertisement insert function - upload image error", e2);
		}

		return result;
	}

	@ResponseBody
	@RequestMapping("/procedure/delete.do")
	public Object deleteChannelProcedure(Long procedureId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (procedureId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			ChannelProcedure channelProcedure = new ChannelProcedure();
			channelProcedure.setStatus(CommonStatus.OFFLINE);
			channelProcedure.setProcedureId(procedureId);
			channelProcedureService.updateChannelProcedure(channelProcedure);

			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}

}
