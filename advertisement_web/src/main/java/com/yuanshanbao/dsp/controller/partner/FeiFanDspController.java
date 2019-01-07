package com.yuanshanbao.dsp.controller.partner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.advertisement.model.MediaInformation;
import com.yuanshanbao.dsp.advertisement.model.vo.AdvertisementDetails;
import com.yuanshanbao.dsp.channel.model.Channel;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.partner.agent.feifan.model.FeiFanAdvertisement;
import com.yuanshanbao.dsp.partner.agent.feifan.model.FeiFanConnectionType;
import com.yuanshanbao.dsp.partner.agent.feifan.model.FeiFanDevice;
import com.yuanshanbao.dsp.partner.agent.feifan.model.FeiFanMedia;
import com.yuanshanbao.dsp.partner.agent.feifan.model.FeiFanOsType;
import com.yuanshanbao.dsp.partner.agent.feifan.model.FeiFanRequest;
import com.yuanshanbao.dsp.partner.agent.feifan.model.Impression;
import com.yuanshanbao.dsp.probability.service.ProbabilityService;
import com.yuanshanbao.dsp.project.model.Project;

@RequestMapping({ "/feifan" })
@Controller
public class FeiFanDspController {
	private final static String EMPTYINFO = "emptyInfo";
	private final static String SHOW = "https://d.xingdk.com/show";
	private final static String CLICK = "https://d.xingdk.com/click";

	@Autowired
	private ProbabilityService probabilityService;

	// dsp请求广告接口
	@RequestMapping(value = "/content", method = RequestMethod.POST)
	@ResponseBody
	public Object getContent(HttpServletRequest request, HttpServletResponse response,
			@RequestBody FeiFanRequest feifanRequest) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			Project project = ConstantsManager.getProjectByKey("dsp");
			if (project != null) {
				MediaInformation mediaInformation = coverToDspInformation(feifanRequest);
				if (mediaInformation == null) {
					throw new Exception(EMPTYINFO);
				}
				Channel channelObject = ConfigManager.getChannel(mediaInformation.getChannel());
				if (channelObject != null) {
					List<FeiFanAdvertisement> ads = probabilityService.pickFeiFanAd(request, project.getProjectId(),
							channelObject, mediaInformation);
					if (ads.size() == 0) {
						throw new Exception(EMPTYINFO);
					}
					resultMap.put("id", feifanRequest.getId());
					resultMap.put("ads", ads);
				}
			}
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setSpecAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			if (!EMPTYINFO.equals(e.getMessage())) {
				LoggerUtil.error("[advertisement dsp]: ", e);
			}
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.NO_ADVERTISEMENT);
			// 无广告返回204no content
			response.setStatus(204);
		}
		return resultMap;
	}

	private List<FeiFanAdvertisement> coverToFeiFanResponse(FeiFanRequest feifanRequest,
			List<AdvertisementDetails> seatBidList, MediaInformation mediaInformation) {
		List<FeiFanAdvertisement> resultList = new ArrayList<FeiFanAdvertisement>();
		AdvertisementDetails detail = seatBidList.get(0);
		FeiFanAdvertisement advertisement = new FeiFanAdvertisement();
		advertisement.setImpid(feifanRequest.getImp().get(0).getId());
		String param = getCollectUrl(detail, mediaInformation.getChannel());
		advertisement.setPm(SHOW + "?" + param);
		advertisement.setCm(CLICK + "?" + param);
		// TODO 价格
		resultList.add(advertisement);
		return resultList;
	}

	private String getCollectUrl(AdvertisementDetails detail, String channel) {
		StringBuffer sb = new StringBuffer();
		sb.append("pid=");
		sb.append(detail.getpId());
		sb.append("&");
		sb.append("key=");
		sb.append(detail.getKey());
		sb.append("&");
		sb.append("channel=");
		sb.append(channel);
		return sb.toString();
	}

	private MediaInformation coverToDspInformation(FeiFanRequest feifanRequest) {
		MediaInformation mediaInformation = new MediaInformation();
		List<Impression> impList = feifanRequest.getImp();
		FeiFanDevice device = feifanRequest.getDevice();
		if (impList == null || impList.size() == 0 || device == null) {
			return null;
		}
		Impression imp = feifanRequest.getImp().get(0);
		mediaInformation.setImpId(imp.getId());
		mediaInformation.setW(imp.getS1());
		mediaInformation.setH(imp.getS2());
		mediaInformation.setOs(FeiFanOsType.getDescription(device.getOs()));
		mediaInformation.setConnectiontype(FeiFanConnectionType.getDescription(device.getConnectionType()));
		mediaInformation.setIp(device.getIp());
		mediaInformation.setChannel(FeiFanMedia.getKey(feifanRequest.getMid()));
		return mediaInformation;
	}
}
