package com.yuanshanbao.ms.controller.advertisement;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.FileUtil;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.advertisement.model.Advertisement;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementStrategy;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementStrategyType;
import com.yuanshanbao.dsp.advertisement.service.AdvertisementService;
import com.yuanshanbao.dsp.advertisement.service.AdvertisementStrategyService;
import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.config.model.Function;
import com.yuanshanbao.dsp.config.service.FunctionService;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.location.model.Location;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.ms.controller.common.AdminServerController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/strategy")
public class AdminAdvertisementStrategyController extends PaginationController {

	private static final String PAGE_LIST = "advertisement/strategy/listStrategy";

	private static final String PAGE_INSERT = "advertisement/strategy/insertStrategy";

	private static final String PAGE_UPDATE = "advertisement/strategy/updateStrategy";

	private static final String PAGE_VIEW = "advertisement/strategy/viewStrategy";
	private static final String PAGE_STRATEGY = "advertisement/strategy/strategy";
	private static final String PAGE_PLAN_STRATEGY_LIST = "advertisement/strategy/listPlanStrategy";

	@Autowired
	private AdvertisementStrategyService strategyService;

	@Autowired
	private AdvertisementService advertService;

	@Autowired
	private FunctionService functionService;

	@RequestMapping("/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		Advertiser advertiser = getBindAdvertiserByUser();
		if (advertiser != null) {
			request.setAttribute("advertiserId", advertiser.getAdvertiserId());
		}
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, AdvertisementStrategy strategy, HttpServletRequest request,
			HttpServletResponse response) {
		Object object = strategyService.selectAdvertisementStrategy(strategy, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/insertWindow.do")
	public String insertWindow(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		setProperty(request);
		return PAGE_INSERT;
	}

	private void setProperty(HttpServletRequest request) {

		Advertisement advertisement = new Advertisement();
		advertisement.setStatus(CommonStatus.ONLINE);
		List<Advertisement> list = advertService.selectAdvertisement(advertisement, new PageBounds());
		Function function = new Function();
		function.setStatus(CommonStatus.ONLINE);
		List<Function> functions = functionService.selectFunctions(function, new PageBounds());

		request.setAttribute("functionList", functions);
		request.setAttribute("advertList", list);
		request.setAttribute("typeList", AdvertisementStrategyType.getTypeDescriptionMap().entrySet());
		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());

	}

	@ResponseBody
	@RequestMapping("/insert.do")
	public Object insert(@RequestParam(value = "data", required = false) MultipartFile data,
			AdvertisementStrategy strategy, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			if (!data.isEmpty()) {
				queryLocation(data, strategy);
			}
			validateParameters(strategy);
			strategyService.insertAdvertisementStrategy(strategy);
			AdminServerController.refreshConfirm();
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[AdminStrategy]", e);
		}

		return result;
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(HttpServletRequest request, HttpServletResponse response, AdvertisementStrategy strategy) {
		List<AdvertisementStrategy> list = strategyService.selectAdvertisementStrategy(strategy, new PageBounds());
		if (list != null && list.size() > 0) {
			strategy = list.get(0);
		}
		setProperty(request);
		request.setAttribute("itemEdit", strategy);
		return PAGE_UPDATE;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object update(@RequestParam(value = "data", required = false) MultipartFile data,
			AdvertisementStrategy strategy, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			if (!data.isEmpty()) {
				queryLocation(data, strategy);
			}
			validateParameters(strategy);
			strategyService.updateAdvertisementStrategy(strategy);
			AdminServerController.refreshConfirm();
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[AdminStrategy]", e);
		}

		return result;
	}

	@ResponseBody
	@RequestMapping("/delete.do")
	public Object delete(Long strategyId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {

			if (strategyId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			AdvertisementStrategy strategy = new AdvertisementStrategy();
			strategy.setStatus(CommonStatus.OFFLINE);
			strategy.setAdvertisementStrategyId(strategyId);
			strategyService.updateAdvertisementStrategy(strategy);

			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}

	@RequestMapping("/view.do")
	public String view(AdvertisementStrategy strategy, ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		List<AdvertisementStrategy> list = strategyService.selectAdvertisementStrategy(strategy, new PageBounds());
		if (list != null && list.size() > 0) {
			strategy = list.get(0);
		}
		request.setAttribute("itemEdit", strategy);
		return PAGE_VIEW;
	}

	public void queryLocation(@RequestParam("data") MultipartFile data, AdvertisementStrategy strategy) {
		if (!data.getOriginalFilename().endsWith("txt")) {
			throw new BusinessException("只支持txt文件");
		}
		try {
			List<String> locationNames = FileUtil.read(data);
			String locationCode = "";
			for (String locationName : locationNames) {
				Location location = ConstantsManager.getLocationByName(locationName);
				if (location == null) {
					throw new BusinessException(locationName + "城市格式错误");
				}
				locationCode = locationCode + location.getCode() + ",";
			}
			strategy.setValue(locationCode.substring(0, locationCode.lastIndexOf(",")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/strategyWindow.do")
	public String strategyWindow(Long planId, ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		Advertiser advertiser = getBindAdvertiserByUser();
		if (advertiser != null) {
			request.setAttribute("advertiserId", advertiser.getAdvertiserId());
		}
		Map<String, String> map = ConfigManager.getStrategyMap(planId);
		request.setAttribute("strategyValue", map);
		request.setAttribute("planId", planId);
		return PAGE_STRATEGY;
	}

	@ResponseBody
	@RequestMapping("/updateProbabilityStrategy.do")
	public Object strategy(Long probabilityId, Long advertiserId, Function function, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (function == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}

			strategyService.updateProbabilityStrategy(request, probabilityId, advertiserId);
			// AdminServerController.refreshConfirm();
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("", e);
		}
		return result;
	}

	@RequestMapping("/strategyList.do")
	public String strategyList(Long probabilityId, ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		Advertiser advertiser = getBindAdvertiserByUser();
		if (advertiser != null) {
			request.setAttribute("advertiserId", advertiser.getAdvertiserId());
		}
		Map<String, String> map = ConfigManager.getStrategyMap(probabilityId);
		request.setAttribute("strategyValue", map);
		request.setAttribute("probabilityId", probabilityId);
		return PAGE_PLAN_STRATEGY_LIST;
	}

	@ResponseBody
	@RequestMapping("/strategyQuery.do")
	public Object strategyQuery(AdvertisementStrategy strategy, ModelMap modelMap, HttpServletRequest request,
			String range, HttpServletResponse response) {
		Object object = strategyService.selectPlanStrategy(strategy, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

}
