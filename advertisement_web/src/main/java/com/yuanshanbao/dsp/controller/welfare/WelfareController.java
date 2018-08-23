package com.yuanshanbao.dsp.controller.welfare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.constant.SessionConstants;
import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.activity.model.Activity;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementCategory;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementPosition;
import com.yuanshanbao.dsp.advertisement.model.vo.AdvertisementCategoryVo;
import com.yuanshanbao.dsp.advertisement.model.vo.AdvertisementVo;
import com.yuanshanbao.dsp.cache.IniCache;
import com.yuanshanbao.dsp.config.ConfigConstants;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.product.service.ProductService;

@Controller
@RequestMapping({ "/welfare", "/i/welfare" })
public class WelfareController extends BaseController {

	private static final String EDUCATION_APP = "education_app";

	@Autowired
	private ProductService productService;

	@ResponseBody
	@RequestMapping("/index")
	public Object index(HttpServletRequest request, HttpServletResponse response, String activityKey, Integer client) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String channel = appService.getAppChannel(request.getParameter("appId"));
			// 判断是否是审核版本
			if (isApprovalEdition(request, null)) {
				channel = channel + "_approval";
			}
			request.getSession().setAttribute(activityKey + SessionConstants.SESSION_USER_FROM, channel);
			Activity activity = ConfigManager.getActivityByKey(EDUCATION_APP);
			Long activityId = null;
			if (activity != null) {
				activityId = activity.getActivityId();
			}
			String appKey = getAppKey(request);
			List<AdvertisementVo> welfareList = setAdvertisementLink(AdvertisementPosition.ADVERTISEMENT_WELFARE,
					AdvertisementPosition.WELFARE, channel, appKey, activityId, client);
			List<AdvertisementVo> tagsList = setAdvertisementLink(AdvertisementPosition.ADVERTISEMENT_WELFARE,
					AdvertisementPosition.TAGS, channel, appKey, activityId, client);
			List<AdvertisementCategoryVo> categoryList = new ArrayList<AdvertisementCategoryVo>();
			String ids = ConfigManager.getConfigValue(activityId, channel, appKey,
					ConfigConstants.ADVERTISEMENT_CATEGORY_CONFIG);
			getCategoryList(ids, welfareList, categoryList);
			resultMap.put("categoryList", categoryList);
			resultMap.put("tagsList", tagsList);
			resultMap.put("channel", channel);
			setAdvertisement(client, resultMap, channel, appKey, activityId,
					AdvertisementPosition.ADVERTISEMENT_WELFARE);
			resultMap.put("position", AdvertisementPosition.WELFARE);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[welfareList error:]", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	private void getCategoryList(String ids, List<AdvertisementVo> welfareList,
			List<AdvertisementCategoryVo> categoryList) {
		if (StringUtils.isBlank(ids)) {
			return;
		}
		Set<Long> categorySet = new LinkedHashSet<Long>();
		for (String id : ids.split(",")) {
			if (ValidateUtil.isNumber(id)) {
				categorySet.add(Long.parseLong(id));
			}
		}
		for (Long category : categorySet) {
			AdvertisementCategoryVo vo = new AdvertisementCategoryVo();
			AdvertisementCategory advertisementCategory = ConfigManager.getCategory(category);
			if (advertisementCategory != null) {
				vo = new AdvertisementCategoryVo(advertisementCategory);
			}
			List<AdvertisementVo> advertisements = new ArrayList<AdvertisementVo>();
			for (AdvertisementVo advertisement : welfareList) {
				// 分类福利每个种类只显示4个
				if (category.equals(advertisement.getCategory())
						&& advertisements.size() < IniCache.getIniIntValue("defaultCategoryAdvertisementCount", 4)) {
					advertisement.setCategoryDesc(advertisementCategory.getDescription());
					advertisements.add(advertisement);
				}
			}
			vo.setAdvertisements(advertisements);
			categoryList.add(vo);
		}
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Long categoryId,
			String activityKey, Integer client) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (categoryId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			String channel = appService.getAppChannel(request.getParameter("appId"));
			// 判断是否是审核版本
			if (isApprovalEdition(request, null)) {
				channel = channel + "_approval";
			}
			request.getSession().setAttribute(activityKey + SessionConstants.SESSION_USER_FROM, channel);
			Activity activity = ConfigManager.getActivityByKey(activityKey);
			Long activityId = null;
			if (activity != null) {
				activityId = activity.getActivityId();
			}
			List<AdvertisementVo> welfareList = setAdvertisementLink(AdvertisementPosition.ADVERTISEMENT_WELFARE,
					AdvertisementPosition.WELFARE, channel, getAppKey(request), activityId, client);
			List<AdvertisementVo> advertisements = new ArrayList<AdvertisementVo>();
			AdvertisementCategory advertisementCategory = ConfigManager.getCategory(categoryId);
			for (AdvertisementVo advertisement : welfareList) {
				if (advertisement.getCategory().equals(categoryId)) {
					advertisement.setCategoryDesc(advertisementCategory.getDescription());
					advertisements.add(advertisement);
				}
			}

			resultMap.put("advertisements", advertisements);
			resultMap.put("category", advertisementCategory);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[detail error: " + categoryId + "]", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}
}
