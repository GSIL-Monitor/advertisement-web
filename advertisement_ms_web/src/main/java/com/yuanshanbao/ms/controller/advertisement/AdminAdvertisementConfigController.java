package com.yuanshanbao.ms.controller.advertisement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.yuanshanbao.ad.advertisement.model.AdvertisementConfig;
import com.yuanshanbao.ad.advertisement.model.AdvertisementPosition;
import com.yuanshanbao.ad.advertisement.service.AdvertisementCategoryService;
import com.yuanshanbao.ad.advertisement.service.AdvertisementService;
import com.yuanshanbao.ad.advertisement.service.AdvertiserService;
import com.yuanshanbao.ad.app.model.App;
import com.yuanshanbao.ad.app.model.AppType;
import com.yuanshanbao.ad.config.ConfigManager;
import com.yuanshanbao.ad.config.model.Function;
import com.yuanshanbao.ad.config.service.FunctionService;
import com.yuanshanbao.ad.core.InterfaceRetCode;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/adConfig")
public class AdminAdvertisementConfigController extends PaginationController {

	private static final String PAGE_CONFIG = "advertisement/advertisement/config/configAdvertisement";

	private static final String PAGE_LIST = "advertisement/advertisement/config/listApp";

	private static final String PAGE_INSERT = "advertisement/advertisement/config/insertAdvertisement";

	private static final String PAGE_UPDATE = "advertisement/advertisement/config/updateAdvertisement";

	private static final String PAGE_VIEW = "advertisement/advertisement/config/viewAdvertisement";

	@Autowired
	private AdvertisementService advertService;

	@Autowired
	private AdvertiserService advertiserService;

	@Autowired
	private FunctionService functionService;

	@Autowired
	private AdvertisementCategoryService advertisementCategoryService;

	@RequestMapping("/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_LIST;
	}

	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, HttpServletRequest request, HttpServletResponse response) {
		PageList<App> pageList = new PageList<App>();
		Long count = 1L;
		for (Entry<String, String> entry : AppType.getCodeDescriptionMap().entrySet()) {
			App app = new App();
			app.setAppId(count);
			app.setAppKey(entry.getKey());
			app.setAppName(entry.getValue());
			count++;
			pageList.add(app);
		}
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/configWindow.do")
	public String configWindow(String appKey, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		List<AdvertisementConfig> list = new ArrayList<AdvertisementConfig>();
		for (String section : AdvertisementPosition.getSectionMap().values()) {
			AdvertisementConfig config = new AdvertisementConfig();
			config.setName(AdvertisementPosition.getDescription(section));
			List<String> keys = new ArrayList<String>();
			for (String configStr : AdvertisementPosition.getConfigMap().values()) {
				keys.add(section + configStr);
			}
			keys.add(section + "config");
			config.setFunctions(new ArrayList<Function>(functionService.selectFunctionByKeys(keys).values()));
			list.add(config);
		}
		request.setAttribute("configList", list);
		request.setAttribute("categoryLists", ConfigManager.getCategoryMap());
		return PAGE_CONFIG;
	}

	@ResponseBody
	@RequestMapping("/config.do")
	public Object config(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Function> functions = functionService.selectFunctionsByCategory(7);
			for (Function function : functions) {
				String params = request.getParameter(function.getKey());
				if (!params.equals(function.getDefaultAction())) {
					function.setDefaultAction(params);
					functionService.updateFunction(function);
				}
			}
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("", e);
		}
		return result;
	}

}
