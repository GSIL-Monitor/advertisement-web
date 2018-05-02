package com.yuanshanbao.ms.controller.advertisement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.advertisement.model.Advertisement;
import com.yuanshanbao.dsp.advertisement.service.AdvertisementService;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.position.model.Position;
import com.yuanshanbao.dsp.project.model.Project;
import com.yuanshanbao.dsp.project.service.ProjectService;

@RequestMapping({ "/index" })
@Controller
public class IndexAdvertisementController extends BaseController {
	@Autowired
	private AdvertisementService advertisementService;
	@Autowired
	private ProjectService projectService;

	// 请求广告接口
	@RequestMapping("/{projectKey}/advertisement")
	@ResponseBody
	public Object getAdvertisements(HttpServletRequest request, HttpServletResponse response, String userId,
			String appId, String moblileLocation, String gender, String age, String deviceType, String deviceId, String mac,
			String coordinate, String positionKey, @PathVariable("projectKey") String projectKey) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			Project project = ConstantsManager.getProjectByKey(projectKey);
			if (project != null) {
				Position position = ConstantsManager.getPositionByKey(project.getProjectId(), positionKey);
				if (position != null) {
					List<Advertisement> resultAdList = advertisementService.getAdvertisement(project.getProjectId(),
							position.getPositionId());
					resultMap.put("advertisementList", resultAdList);
				}	
			}
			
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);

		} catch (BusinessException e) {
			InterfaceRetCode.setSpecAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());

		} catch (Exception e) {
			LoggerUtil.error("[advertisement index]: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}

		return resultMap;		
	}

	// 广告点击
	@RequestMapping("/{projectKey}/adclick")
	@ResponseBody
	public Object adClick(HttpServletRequest request, HttpServletResponse response, String userId,
			String advertisementId, String adPosition) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			if (ValidateUtil.isNumber(advertisementId)) {
				advertisementService.increaseAdvertisementCount(Long.parseLong(advertisementId));
			}
			
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);

		} catch (BusinessException e) {
			InterfaceRetCode.setSpecAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());

		} catch (Exception e) {
			LoggerUtil.error("[adClick index]: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}

		return resultMap;
	}

}
