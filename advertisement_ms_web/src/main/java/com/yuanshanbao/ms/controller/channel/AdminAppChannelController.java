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
import com.yuanshanbao.common.util.UploadUtils;
import com.yuanshanbao.dsp.app.model.AppType;
import com.yuanshanbao.dsp.channel.model.Channel;
import com.yuanshanbao.dsp.channel.model.ChannelType;
import com.yuanshanbao.dsp.channel.service.ChannelService;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.ms.controller.common.AdminServerController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/app/channel")
public class AdminAppChannelController extends PaginationController {

	private static final String PAGE_LIST = "advertisement/app/channel/listChannel";

	private static final String PAGE_INSERT = "advertisement/app/channel/insertChannel";

	private static final String PAGE_UPDATE = "advertisement/app/channel/updateChannel";

	private static final String PAGE_VIEW = "advertisement/app/channel/viewChannel";

	@Autowired
	private ChannelService channelService;

	@RequestMapping("/list.do")
	public String list(String appId, HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("appId", appId);
		request.setAttribute("appName", AppType.getDescription(AppType.getkey(Long.parseLong(appId))));
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
	public String insertWindow(String appId, HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		request.setAttribute("appId", appId);
		setProperty(request);
		return PAGE_INSERT;
	}
	
	private void setProperty(HttpServletRequest request) {
		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());
		request.setAttribute("showTypeList", ChannelType.getShowTypeDescriptionMap().entrySet());
		request.setAttribute("deliverTypeList", ChannelType.getDeliverTypeDescriptionMap().entrySet());
		request.setAttribute("appList", AppType.getDescriptionMap().entrySet());
	}

	@ResponseBody
	@RequestMapping("/insert.do")
	public Object insert(Channel channel, @RequestParam("image") MultipartFile file,
			@RequestParam(value = "iosFile", required = false) MultipartFile iosFile,
			@RequestParam(value = "androidFile", required = false) MultipartFile androidFile,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			if (ConfigManager.getChannel(channel.getKey()) != null) {
				throw new BusinessException(ComRetCode.SUCCESS_DUPLICATED);
			}
			if (!file.isEmpty()) {
				channel.setImageUrl(UploadUtils.uploadFile(file, UploadUtils.FTP_CHANNEL_IMAGE));
			}
			setAppUrl(iosFile, androidFile, channel);

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

	private void setAppUrl(MultipartFile iosFile, MultipartFile androidFile, Channel channel) throws IOException {
		if (channel.getAppId() == null) {
			throw new BusinessException(ComRetCode.WRONG_PARAMETER);
		}
		String appName = "xingdai";
		if (AppType.RUIDAI_ID.equals(channel.getAppId())) {
			appName = "ruidai";
		}
		String path = "app/" + appName + "/" + iosFile.getName();
		if (!iosFile.isEmpty()) {
			UploadUtils.uploadFiles(iosFile.getInputStream(), iosFile.getSize(), path);
		}
		if (!androidFile.isEmpty()) {
			path = "app/" + appName + "/" + androidFile.getOriginalFilename();
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
	public Object update(Channel channel, @RequestParam(value = "image", required = false) MultipartFile file, HttpServletRequest request,
			@RequestParam(value = "iosFile", required = false) MultipartFile iosFile,
			@RequestParam(value = "androidFile", required = false) MultipartFile androidFile,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {

			if (!file.isEmpty()) {
				channel.setImageUrl(UploadUtils.uploadFile(file, UploadUtils.FTP_CHANNEL_IMAGE));
			}
			if (StringUtils.isNotBlank(channel.getKey())) {
				channel.setKey(channel.getKey().trim());
			}
			setAppUrl(iosFile, androidFile, channel);

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

}
