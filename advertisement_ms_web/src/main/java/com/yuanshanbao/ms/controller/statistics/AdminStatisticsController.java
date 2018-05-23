package com.yuanshanbao.ms.controller.statistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.ExcelUtil;
import com.yuanshanbao.common.util.JacksonUtil;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.PropertyUtil;
import com.yuanshanbao.dsp.advertisement.service.AdvertisementService;
import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.channel.model.Channel;
import com.yuanshanbao.dsp.channel.service.ChannelService;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.position.service.PositionService;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.dsp.probability.service.ProbabilityService;
import com.yuanshanbao.dsp.statistics.model.AdvertisementStatistics;
import com.yuanshanbao.dsp.statistics.model.Statistics;
import com.yuanshanbao.dsp.statistics.model.StatisticsStatus;
import com.yuanshanbao.dsp.statistics.model.StatisticsType;
import com.yuanshanbao.dsp.statistics.model.SuccessPageClick;
import com.yuanshanbao.dsp.statistics.service.AdvertisementStatisticsService;
import com.yuanshanbao.dsp.statistics.service.StatisticsService;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.ms.model.admin.User;
import com.yuanshanbao.ms.service.admin.AdminUserService;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;
import com.yuanshanbao.paginator.domain.Paginator;

@Controller
@RequestMapping("/admin/statistics")
public class AdminStatisticsController extends PaginationController {

	private static final String PAGE_ORIGIN_LIST = "advertisement/statistics/listOriginStatistics";

	private static final String PAGE_CHANNEL_LIST = "advertisement/statistics/listChannelStatistics";

	private static final String PAGE_CHANNEL_ALL_LIST = "advertisement/statistics/listChannelAllStatistics";

	private static final String PAGE_MERCHANT_LIST = "advertisement/statistics/listMerchantStatistics";

	private static final String PAGE_ADVERTISEMENT_LIST = "advertisement/statistics/listAdvertisementStatistics";

	private static final String PAGE_ADVERTISER_LIST = "advertisement/statistics/listAdvertiserStatistics";

	private static final String PAGE_ADVERTISEMENT_CHANNEL_LIST = "advertisement/statistics/listAdvertisementChannelStatistics";

	private static final String PAGE_CHANNEL_ADVERTISEMENT_LIST = "advertisement/statistics/listChannelAdvertisementStatistics";

	private static final String PAGE_CHANNEL_ADVERTISEMENTS_LIST = "advertisement/statistics/listChannelAdvertisementsStatistics";

	private static final String PAGE_VIEW = "advertisement/statistics/viewStatistics";

	private static final String PAGE_UPDATE = "advertisement/statistics/updateStatistics";

	private static final String PAGE_REALDATA = "advertisement/statistics/calculateadvertisementStatistics";

	private static final String PAGE_CHANNEL = "advertisement/statistics/channelStatistics";

	private static final String PAGE_REALTIME_CHANNEL = "advertisement/statistics/channelRealtimeStatistics";

	private static final String PAGE_DOWNadvertisement_CHANNEL = "advertisement/statistics/downloadChannel";

	private static final String PAGE_RESULT_CLICK_LIST = "advertisement/statistics/resultPageClick";

	private static final String PAGE_MONTH_LIST = "advertisement/statistics/listMonthStatistics";

	public static String OSS_HOST_FILES = PropertyUtil.getProperty("oss.host.files");

	@Autowired
	private StatisticsService statisticsService;

	@Autowired
	protected AdminUserService userService;

	@Autowired
	private AdvertisementService advertisementService;

	@Autowired
	private AdvertisementStatisticsService advertisementStatisticsService;

	@Autowired
	private ChannelService channelService;

	@Autowired
	private PositionService positionService;

	@Autowired
	private ProbabilityService probabilityService;

	/**
	 * 合作方渠道页面窗口
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/channel.do")
	public String channel(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		addDateList(modelMap, 0);
		return PAGE_CHANNEL_LIST;
	}

	@RequestMapping("/channelAll.do")
	public String channelAll(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		addDateList(modelMap, 0);
		return PAGE_CHANNEL_ALL_LIST;
	}

	/**
	 * 合作方渠道页面查询
	 * 
	 * @param statistics
	 * @param request
	 * @param response
	 * @param statisticsDate
	 * @param hasAll
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryChannel.do")
	public Object queryChannel(Statistics statistics, HttpServletRequest request, HttpServletResponse response,
			String statisticsDate, Boolean hasAll) {
		if (hasAll == null) {
			hasAll = false;
		}
		List<String> channelkeysList = getChannelKeys();
		List<Statistics> list = statisticsService.intervalStatistics(channelkeysList, getDateDiff(statisticsDate),
				true, hasAll);
		return setPageInfo(request, response, new PageList<Statistics>(list, new Paginator()));
	}

	@RequestMapping("/merchant.do")
	public String merchant(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		addDateList(modelMap, 0);
		return PAGE_MERCHANT_LIST;
	}

	@ResponseBody
	@RequestMapping("/queryMerchant.do")
	public Object queryMerchant(Statistics statistics, HttpServletRequest request, HttpServletResponse response,
			String statisticsDate) {
		List<Statistics> list = statisticsService.intervalProductStatistics(getDateDiff(statisticsDate), true);
		return setPageInfo(request, response, new PageList<Statistics>(list, new Paginator()));
	}

	@RequestMapping("/advertisement.do")
	public String advertisement(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		modelMap.put("positionList", positionService.selectPositionByProjectId(getProjectId(request)));
		addDateList(modelMap, 0);
		return PAGE_ADVERTISEMENT_LIST;
	}

	@ResponseBody
	@RequestMapping("/queryAdvertisement.do")
	public Object queryAdvertisement(Statistics statistics, HttpServletRequest request, HttpServletResponse response,
			String statisticsDate, Boolean isPv) {
		List<AdvertisementStatistics> list = advertisementStatisticsService.selectAdvertisementStatistic(
				getDateDiff(statisticsDate), isPv, null);
		return setPageInfo(request, response, new PageList<AdvertisementStatistics>(list, new Paginator()));
	}

	@RequestMapping("/advertisementChannel.do")
	public String advertisementChannel(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			Long advertisementId) {
		modelMap.put("positionList", positionService.selectPositionByProjectId(getProjectId(request)));
		modelMap.put("advertisement", ConfigManager.getAdvertisement(advertisementId + ""));
		modelMap.put("advertisementId", advertisementId);
		addDateList(modelMap, 0);
		return PAGE_ADVERTISEMENT_CHANNEL_LIST;
	}

	@ResponseBody
	@RequestMapping("/queryAdvertisementChannel.do")
	public Object queryAdvertisementChannel(Statistics statistics, HttpServletRequest request,
			HttpServletResponse response, String statisticsDate, Boolean isPv, Long advertisementId) {
		List<AdvertisementStatistics> list = advertisementStatisticsService.selectAdvertisementStatistic(
				getDateDiff(statisticsDate), isPv, advertisementId);
		return setPageInfo(request, response, new PageList<AdvertisementStatistics>(list, new Paginator()));
	}

	// 按照渠道查询广告
	@RequestMapping("/channelAdvertisement.do")
	public String channelAdvertisement(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		modelMap.put("positionList", positionService.selectPositionByProjectId(getProjectId(request)));
		addDateList(modelMap, 0);
		return PAGE_CHANNEL_ADVERTISEMENT_LIST;
	}

	@ResponseBody
	@RequestMapping("/queryChannelAdvertisement.do")
	public Object queryChannelAdvertisement(Statistics statistics, HttpServletRequest request,
			HttpServletResponse response, String statisticsDate, Boolean isPv, String channelKey) {
		List<AdvertisementStatistics> list = advertisementStatisticsService.selectChannelAdvertisementStatistic(
				getDateDiff(statisticsDate), isPv, channelKey);
		return setPageInfo(request, response, new PageList<AdvertisementStatistics>(list, new Paginator()));
	}

	@RequestMapping("/channelAdvertisements.do")
	public String channelAdvertisements(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			String channelkey) {
		modelMap.put("positionList", positionService.selectPositionByProjectId(getProjectId(request)));
		modelMap.put("channel", ConfigManager.getChannel(channelkey));
		modelMap.put("channelkey", channelkey);
		addDateList(modelMap, 0);
		return PAGE_CHANNEL_ADVERTISEMENTS_LIST;
	}

	@ResponseBody
	@RequestMapping("/queryChannelAdvertisements.do")
	public Object queryChannelAdvertisements(Statistics statistics, HttpServletRequest request,
			HttpServletResponse response, String statisticsDate, Boolean isPv, String channelKey) {
		List<AdvertisementStatistics> list = advertisementStatisticsService.selectChannelAdvertisementStatistic(
				getDateDiff(statisticsDate), isPv, channelKey);
		return setPageInfo(request, response, new PageList<AdvertisementStatistics>(list, new Paginator()));
	}

	@RequestMapping("/resultPageClick.do")
	public String resultPageClick(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		addDateList(modelMap, 0);
		return PAGE_RESULT_CLICK_LIST;
	}

	@ResponseBody
	@RequestMapping("/queryResultPageClick.do")
	public Object querySuccessPage(HttpServletRequest request, HttpServletResponse response, String statisticsDate) {
		List<SuccessPageClick> list = advertisementStatisticsService.selectSuccessPageClicks(
				getDateDiff(statisticsDate), true);
		Collections.sort(list, new Comparator<SuccessPageClick>() {
			@Override
			public int compare(SuccessPageClick arg0, SuccessPageClick arg1) {
				return arg1.getTotal() - arg0.getTotal();
			}
		});
		return setPageInfo(request, response, new PageList<SuccessPageClick>(list, new Paginator()));
	}

	private void addDateList(ModelMap modelMap, int start) {
		List<String> dateList = new ArrayList<String>();
		String date = DateUtils.format(DateUtils.getCurrentDay(), "yyyy-MM-dd");
		for (int i = start; i < 30; i++) {
			dateList.add(date);
			date = DateUtils.dateDec(date, "D");
		}
		modelMap.put("dateList", dateList);
	}

	private int getDateDiff(String date) {
		Date d = new Date();
		if (StringUtils.isNoneBlank(date)) {
			d = DateUtils.formatToDate(date, "yyyy-MM-dd");
		}
		return DateUtils.getDiffDays(d, new Date());
	}

	/**
	 * 贷款原始统计
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/originStatistics.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_ORIGIN_LIST;
	}

	/**
	 * 贷款原始统计-查询
	 * 
	 * @param range
	 * @param statistics
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/queryOrigin.do")
	public Object query(String range, Statistics statistics, HttpServletRequest request, HttpServletResponse response) {
		statistics.setType(StatisticsType.CHANNEL_ORIGINAL_DATA);
		Object object = statisticsService.selectStatistics(statistics, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	/**
	 * 处理贷款统计页面
	 * 
	 * @param range
	 * @param statistics
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/dealStatistics.do")
	public String insurance(String range, Statistics statistics, HttpServletRequest request,
			HttpServletResponse response) {
		return PAGE_REALDATA;
	}

	/**
	 * 处理贷款统计页面-查询接口
	 * 
	 * @param range
	 * @param statistics
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/dealQuery.do")
	public Object queryReal(String range, Statistics statistics, HttpServletRequest request,
			HttpServletResponse response) {
		statistics.setType(StatisticsType.CHANNEL_WORKING_DATA);// 加工后数据
		Object object = statisticsService.selectStatistics(statistics, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	/**
	 * 处理统计页面会用到修改
	 * 
	 * @param request
	 * @param response
	 * @param statisticsId
	 * @return
	 */
	@RequestMapping("/updateWindow.do")
	public String updateWindow(HttpServletRequest request, HttpServletResponse response, Long statisticsId) {
		Statistics statistics = statisticsService.selectStatisticsById(statisticsId);
		request.setAttribute("itemEdit", statistics);
		return PAGE_UPDATE;
	}

	/**
	 * 运营更新确认数据
	 * 
	 * @param statistics
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/update.do")
	public Object update(Statistics statistics, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			validateParameters(statistics);
			statistics.clearParameter();
			statistics.setStatus(StatisticsStatus.ALREADY);
			statisticsService.updateStatistics(statistics);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}
		return result;
	}

	/**
	 * 渠道统计 合作方用户通过channel查看数据
	 * 
	 * @param range
	 * @param statistics
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/channelStatistics.do")
	public String right(String range, Statistics statistics, ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		formbuilder(range, statistics, modelMap, request, response);
		return PAGE_CHANNEL;
	}

	/**
	 * 合作方渠道每日统计查询
	 *
	 * @param range
	 * @param statisticsDate
	 * @param queryChannel
	 * @param statistics
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/querydata.do")
	public Object rightQuery(String range, String statisticsDate, String queryChannel, Statistics statistics,
			HttpServletRequest request, HttpServletResponse response) {

		statistics.setType(StatisticsType.CHANNEL_WORKING_DATA);// 加工后数据
		statistics.setStatus(StatisticsStatus.ALREADY);

		statistics.setChannelList(getChannelKeys());
		if (!StringUtils.isBlank(statisticsDate)) {
			statistics.setDate(statisticsDate);
		}
		if (!StringUtils.isBlank(queryChannel)) {
			statistics.setChannel(queryChannel);
		}

		List<Statistics> list = statisticsService.selectStatistics(statistics, getPageBounds(range, request));
		Map<String, Channel> channelMap = getChannels();
		List<String> statisticsParam = getKeys();

		List<String> showParam = judgeChannel(statisticsParam, channelMap);

		Paginator paginator = ((PageList) list).getPaginator();
		PageList<Map> tiList = new PageList<Map>();
		if (channelMap.size() > 0) {
			dataProcess(tiList, channelMap, list, showParam);
		}
		tiList.setPaginator(paginator);
		return setPageInfo(request, response, tiList);
	}

	/**
	 * 用户通过channel查看实时数据
	 * 
	 * @param range
	 * @param statistics
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/channelRealtimeStatistics.do")
	public String channelRealTimeInsurance(String range, Statistics statistics, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		formbuilder(range, statistics, modelMap, request, response);
		return PAGE_REALTIME_CHANNEL;
	}

	/**
	 * 查询渠道实时统计数据
	 * 
	 * @param range
	 * @param queryChannel
	 * @param statistics
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/queryRealTimeData.do")
	public Object queryRealTimeData(String range, String queryChannel, Statistics statistics,
			HttpServletRequest request, HttpServletResponse response) {
		PageList<Map> tiList = new PageList<Map>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<String> channelkeysList = getChannelKeys();
			if (StringUtils.isNotBlank(queryChannel)) {
				channelkeysList.clear();
				channelkeysList.add(queryChannel);
			}
			PageList<Statistics> pageList = new PageList<Statistics>();
			List<Statistics> list = statisticsService.getRealtimeStatistics(channelkeysList);

			Map<String, Channel> channelMap = getChannels();
			List<String> statisticsParam = getKeys();
			List<String> showParam = judgeChannel(statisticsParam, channelMap);

			Paginator paginator = (pageList).getPaginator();

			dataProcess(tiList, channelMap, list, showParam);

			tiList.setPaginator(paginator);
			return setPageInfo(request, response, tiList);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[queryRealTimeData error]", e);
		}
		return resultMap;
	}

	// 公共列表
	private void formbuilder(String range, Statistics statistics, ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		addDateList(modelMap, 1);

		Map<String, Channel> channelMap = getChannels();

		List<String> keyConstant = getKeys();

		List<String> keys = new ArrayList<String>();

		keys = judgeChannel(keyConstant, channelMap);

		keys.add("uvCount");
		keys.add("downloadCount");
		keys.add("registerCount");
		keys.add("firstLoginCount");
		keys.add("applyCount");
		keys.add("applyUserCount");
		keys.add("applySuccessCount");

		// keys.add("unitPrice");
		// keys.add("calculateAmount");
		modelMap.put("fieldList", keys);
		Map<String, String> map = matchField();
		List<String> values = new ArrayList<String>();
		for (String param : keys) {
			values.add(map.get(param));
		}
		LoggerUtil.info("[channel keys] keys={}", JacksonUtil.obj2json(keys));
		modelMap.put("values", values);
		modelMap.put("channelList", getChannels().values());
	}

	private List<String> judgeChannel(List<String> keyConstant, Map<String, Channel> channelMap) {
		List<String> keys = new ArrayList<String>();
		keys.add("channel");
		keys.add("date");
		for (String str : keyConstant) {
			boolean isContains = true;
			for (Map.Entry<String, Channel> param : channelMap.entrySet()) {
				Channel channel = param.getValue();
				if (StringUtils.isBlank(channel.getShowFields()) || !channel.getShowFields().contains(str)) {
					isContains = false;
					break;
				}
			}
			if (channelMap.size() > 0 && isContains) {
				keys.add(str);
			}

		}
		return keys;
	}

	private List<String> getChannelKeys() {
		User user = getCurrentUser();
		String channels = user.getBindChannel();
		if (StringUtils.isNotBlank(channels)) {
			LoggerUtil.info("[getChannelKeys] username={}, channels={}", user.getUsername(), channels);
			List<String> channelList = new ArrayList<String>();
			String[] params = channels.split(",");
			for (String param : params) {
				channelList.add(param);
			}
			return channelList;
		}
		return null;
	}

	/**
	 * 获取相对应key的channel(渠道集合)
	 * 
	 * @return
	 */
	private Map<String, Channel> getChannels() {
		List<String> channelList = getChannelKeys();
		if (channelList == null) {
			channelList = new ArrayList<String>();
		}
		return channelService.selectChannelByKeys(channelList);
	}

	/**
	 * 统计表中的字段名称集合
	 * 
	 * @return
	 */
	private List<String> getKeys() {
		List<String> keyConstant = new ArrayList<String>();
		keyConstant.add("date");
		keyConstant.add("uvCount");
		keyConstant.add("downloadCount");
		keyConstant.add("registerCount");
		keyConstant.add("firstLoginCount");
		keyConstant.add("applyCount");
		keyConstant.add("applyUserCount");
		keyConstant.add("applySuccessCount");
		return keyConstant;
	}

	/**
	 * key -value 形式 存贮ftl页面对应的形式
	 * 
	 * @return
	 */
	private Map<String, String> matchField() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("channel", "渠道");
		map.put("date", "日期");
		map.put("pvCount", "pv数量");
		map.put("uvCount", "uv");
		map.put("registerCount", "注册数");
		map.put("applyCount", "申请数");
		map.put("applyUserCount", "申请人数");
		map.put("applySuccessCount", "申请成功数");
		map.put("firstLoginCount", "首次登陆数");
		map.put("downloadCount", "激活数（下载）");
		map.put("unitPrice", "单价");
		map.put("calculateAmount", "结算金额");
		return map;
	}

	@SuppressWarnings("rawtypes")
	private void dataProcess(PageList<Map> tiList, Map<String, Channel> channelMap, List<Statistics> list,
			List<String> showParam) {
		for (Statistics stat : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("channel", stat.getChannel());
			map.put("date", stat.getDate());
			// map.put("pvCount", stat.getPvCount());
			map.put("uvCount", stat.getUvCount());
			map.put("downloadCount", stat.getDownloadCount());
			map.put("registerCount", stat.getRegisterCount());
			map.put("firstLoginCount", stat.getFirstLoginCount());
			map.put("applyCount", stat.getApplyCount());
			map.put("applyUserCount", stat.getApplyUserCount());
			map.put("applySuccessCount", stat.getApplySuccessCount());
			Double unitPrice = channelMap.get(stat.getChannel()).getUnitPrice();
			// map.put("unitPrice", unitPrice);
			if (unitPrice == null) {
				unitPrice = 0.0;
			}
			// map.put("calculateAmount", (unitPrice *
			// stat.getApplySuccessCount())); // 结算金额
			tiList.add(map);
		}
	}

	@RequestMapping("/downloadWindow.do")
	public String download(HttpServletRequest request, String queryChannel, String statisticsDate,
			HttpServletResponse response, ModelMap modelMap) {

		Map<String, Channel> channelMap = getChannels();

		Statistics statistics = new Statistics();
		statistics.setType(StatisticsType.CHANNEL_WORKING_DATA);// 加工后数据
		statistics.setStatus(StatisticsStatus.ALREADY);

		List<String> channelKeys = getChannelKeys();
		if (channelKeys == null || channelKeys.size() == 0) {
			return PAGE_DOWNadvertisement_CHANNEL;
		}
		statistics.setChannelList(channelKeys);
		if (!StringUtils.isBlank(statisticsDate)) {
			statistics.setDate(statisticsDate);
		}
		if (!StringUtils.isBlank(queryChannel)) {
			statistics.setChannel(queryChannel);
		}

		List<Statistics> list = statisticsService.selectStatistics(statistics, new PageBounds());

		List<String> statisticsParam = getKeys();

		List<List<String>> toList = new ArrayList<List<String>>();
		setTitle(toList);
		List<String> showParam = judgeChannel(statisticsParam, channelMap);

		for (Statistics stat : list) {
			List<String> rowList = new ArrayList<String>();
			rowList.add(stat.getChannel());
			rowList.add(stat.getDate());

			if (showParam.size() > 0) {
				if (showParam.contains("uvCount")) {
					rowList.add(stat.getUvCount() + "");
				}
				if (showParam.contains("downloadCount")) {
					rowList.add(stat.getDownloadCount() + "");
				}
				if (showParam.contains("registerCount")) {
					rowList.add(stat.getRegisterCount() + "");
				}
				if (showParam.contains("firstLoginCount")) {
					rowList.add(stat.getFirstLoginCount() + "");
				}
				if (showParam.contains("applyCount")) {
					rowList.add(stat.getApplyCount() + "");
				}
				if (showParam.contains("applyUserCount")) {
					rowList.add(stat.getApplyUserCount() + "");
				}
				if (showParam.contains("applySuccessCount")) {
					rowList.add(stat.getApplySuccessCount() + "");
				}
			}

			Double unitPrice = channelMap.get(stat.getChannel()).getUnitPrice();
			if (unitPrice == null) {
				unitPrice = 0.0;
			}
			rowList.add(unitPrice + "");
			rowList.add((unitPrice * 0) + "");
			toList.add(rowList);
		}
		Map<String, List<List<String>>> sheetMap = new HashMap<String, List<List<String>>>();
		sheetMap.put("渠道数据", toList);
		String path = OSS_HOST_FILES + "/";
		try {
			path += ExcelUtil.createSuffixXlsExcelByMoreSheets(sheetMap, "渠道数据");
		} catch (Exception e) {
			LoggerUtil.error("[statistics down error]", e);
		}

		modelMap.put("path", path);
		return PAGE_DOWNadvertisement_CHANNEL;
	}

	private void setTitle(List<List<String>> toList) {
		Map<String, Channel> channelMap = getChannels();

		List<String> keyConstant = getKeys();

		List<String> keys = new ArrayList<String>();

		keys = judgeChannel(keyConstant, channelMap);

		keys.add("unitPrice");
		keys.add("calculateAmount");
		Map<String, String> map = matchField();
		List<String> values = new ArrayList<String>();
		for (String param : keys) {
			values.add(map.get(param));
		}
		toList.add(values);
	}

	@ResponseBody
	@RequestMapping("/delete.do")
	public Object delete(Long statisticsId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (statisticsId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			Statistics statistics = new Statistics();
			statistics.setStatus(CommonStatus.OFFLINE);
			statistics.setStatisticsId(statisticsId);
			statisticsService.updateStatistics(statistics);

			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}

	@RequestMapping("/view.do")
	public String view(Long statisticsId, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		Statistics statistics = statisticsService.selectStatisticsById(statisticsId);
		request.setAttribute("itemEdit", statistics);
		return PAGE_VIEW;
	}

	// 用户通过月份查看数据
	@RequestMapping("/monthStatistics.do")
	public String month(String range, Statistics statistics, ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		formbuilder(range, statistics, modelMap, request, response);
		addMonthList(modelMap);
		return PAGE_MONTH_LIST;
	}

	@SuppressWarnings("deprecation")
	private void addMonthList(ModelMap modelMap) {
		List<String> dateList = new ArrayList<String>();
		String month = "";
		for (int i = 0; i <= new Date().getMonth(); i++) {
			int temp = i + 1;
			if (temp < 10) {
				month = "0" + temp;
			} else {
				month = temp + "";
			}
			dateList.add("2017-" + month);
		}
		Collections.reverse(dateList);
		modelMap.put("dateList", dateList);
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/queryMonth.do")
	public Object monthQuery(String range, String statisticsDate, String queryChannel, Statistics statistics,
			HttpServletRequest request, HttpServletResponse response, String showAllParam) {
		List<Statistics> list = statisticsService.selectMonthStatistics(getChannelKeys(), statisticsDate);

		Paginator paginator = new Paginator();
		paginator.setLimit(1000);
		paginator.setPage(1);
		PageList<Map> tiList = new PageList<Map>();
		for (Statistics stat : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("channel", stat.getChannel());
			map.put("date", stat.getDate());
			map.put("pvCount", stat.getPvCount());
			map.put("uvCount", stat.getUvCount());

			tiList.add(map);
		}
		tiList.setPaginator(paginator);
		return setPageInfo(request, response, tiList);
	}

	@RequestMapping("/list.do")
	public String statisticList(String range, Statistics statistics, ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		return PAGE_ADVERTISEMENT_LIST;
	}

	@RequestMapping("/advertiserList.do")
	public String advertiserList(String range, Statistics statistics, ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		return PAGE_ADVERTISER_LIST;
	}

	@ResponseBody
	@RequestMapping("/queryStatisticToday.do")
	public Object queryStatisticToday(String queryChannel, AdvertisementStatistics statistics,
			HttpServletRequest request, HttpServletResponse response) {
		Probability probability = new Probability();
		Advertiser advertiser = getBindAdvertiserByUser();
		if (advertiser != null) {
			probability.setAdvertiserId(advertiser.getAdvertiserId());
		}
		Map<String, Object> result = new HashMap<String, Object>();
		List<AdvertisementStatistics> resultList = new ArrayList<AdvertisementStatistics>();
		String today = DateUtils.format(new Date(), "yyyy-MM-dd");
		List<Probability> list = probabilityService.selectProbabilitys(probability, new PageBounds());
		resultList = advertisementStatisticsService.calculateStatistics(getProjectId(request), list, today);
		result.put("data", resultList);
		result.put("draw", request.getParameter("draw"));
		result.put("recordsTotal", 1000);
		result.put("recordsFiltered", 1000);
		return result;
	}

	@ResponseBody
	@RequestMapping("/queryAdvertiserStatisticToday.do")
	public Object queryAdvertiserStatisticToday(String queryChannel, AdvertisementStatistics statistics,
			HttpServletRequest request, HttpServletResponse response) {
		Advertiser advertiser = getBindAdvertiserByUser();
		Probability probability = new Probability();
		if (advertiser != null) {
			probability.setAdvertiserId(advertiser.getAdvertiserId());
		}
		Map<String, Object> result = new HashMap<String, Object>();
		List<AdvertisementStatistics> resultList = new ArrayList<AdvertisementStatistics>();
		String today = DateUtils.format(new Date(), "yyyy-MM-dd");
		List<Probability> list = probabilityService.selectProbabilitys(probability, new PageBounds());
		resultList = advertisementStatisticsService.calculateStatistics(getProjectId(request), list, today);
		resultList = advertisementStatisticsService.combineDateAndPosition(resultList);
		result.put("data", resultList);
		result.put("draw", request.getParameter("draw"));
		result.put("recordsTotal", 1000);
		result.put("recordsFiltered", 1000);
		return result;
	}

	@ResponseBody
	@RequestMapping("/queryStatisticFromDB.do")
	public Object queryStatisticFromDB(String queryChannel, AdvertisementStatistics statistics, boolean isAdvertiser,
			boolean isPosition, boolean isDate, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		Advertiser advertiser = getBindAdvertiserByUser();
		if (advertiser != null) {
			statistics.setAdvertiserId(advertiser.getAdvertiserId());
		}
		List<AdvertisementStatistics> list = advertisementStatisticsService.selectAdvertisementStatistics(statistics,
				new PageBounds());
		List<AdvertisementStatistics> resultList = new ArrayList<AdvertisementStatistics>();
		if (isAdvertiser == false && isDate == false && isPosition == false) {
			resultList = list;
		} else {
			if (isAdvertiser && isPosition) {
				resultList = advertisementStatisticsService.combineAdvertiserAndPosition(list);
			} else if (isDate && isPosition) {
				resultList = advertisementStatisticsService.combineDateAndPosition(list);
			} else if (isAdvertiser && isDate) {
				resultList = advertisementStatisticsService.combineAdvertiserAndDate(list);
			}
		}
		result.put("data", resultList);
		result.put("draw", request.getParameter("draw"));
		result.put("recordsTotal", 1000);
		result.put("recordsFiltered", 1000);
		return result;
	}

	@ResponseBody
	@RequestMapping("/queryStatisticByAdvertiser.do")
	public Object queryStatisticByAdvertiser(String queryChannel, AdvertisementStatistics statistics,
			boolean isAdvertiser, boolean isPosition, boolean isDate, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		Advertiser advertiser = getBindAdvertiserByUser();
		if (advertiser != null) {
			statistics.setAdvertiserId(advertiser.getAdvertiserId());
		}
		List<AdvertisementStatistics> list = advertisementStatisticsService.selectAdvertisementStatistics(statistics,
				new PageBounds());
		List<AdvertisementStatistics> resultList = new ArrayList<AdvertisementStatistics>();

		resultList = advertisementStatisticsService.combineDateAndPosition(list);
		result.put("data", resultList);
		result.put("draw", request.getParameter("draw"));
		result.put("recordsTotal", 1000);
		result.put("recordsFiltered", 1000);
		return result;
	}

	@ResponseBody
	@RequestMapping("/taskSchedule.do")
	public Object taskSchedule(String queryChannel, AdvertisementStatistics statistics, HttpServletRequest request,
			HttpServletResponse response, Integer days) {
		Map<String, Object> resultMap = new HashMap<>();
		Probability probability = new Probability();
		if (days == null) {
			days = 1;
		}
		Date recordDate = DateUtils.addDays(new Date(), -days);
		String date = DateUtils.format(recordDate, "yyyy-MM-dd");

		List<Probability> proList = new ArrayList<Probability>();
		List<AdvertisementStatistics> result = new ArrayList<AdvertisementStatistics>();
		try {
			proList = probabilityService.selectProbabilitys(probability, new PageBounds());
			result = advertisementStatisticsService.calculateStatistics(getProjectId(request), proList, date);
			for (AdvertisementStatistics sta : result) {
				sta.setProjectId(getProjectId(request));
				AdvertisementStatistics param = new AdvertisementStatistics();
				param.setDate(date);
				param.setAdvertisementId(sta.getAdvertisementId());
				param.setPositionId(sta.getPositionId());
				List<AdvertisementStatistics> existList = advertisementStatisticsService.selectAdvertisementStatistics(
						param, new PageBounds());
				if (existList.size() > 0) {
					// 不作处理
				} else {
					advertisementStatisticsService.insertAdvertisementStatistics(sta);
				}
			}
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setSpecAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());

		} catch (Exception e) {
			LoggerUtil.error("[advertisement statistics]: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/download.do")
	public Object download(AdvertisementStatistics statistics, boolean isAdvertiser, boolean isDate,
			boolean isPosition, HttpServletRequest request, HttpServletResponse response) {
		String queryChannel = null;
		Map<String, Object> map = new HashMap<String, Object>();

		Map<String, Object> result = new HashMap<String, Object>();

		Object object = new HashMap<String, Object>();

		List<AdvertisementStatistics> list = new ArrayList<AdvertisementStatistics>();
		if (statistics.getQueryStartTime() == null && statistics.getQueryEndTime() == null && isAdvertiser == false
				&& isDate == false && isPosition == false) {
			object = queryStatisticToday(queryChannel, statistics, request, response);
			map = (Map<String, Object>) object;
		} else {
			object = queryStatisticFromDB(queryChannel, statistics, isAdvertiser, isPosition, isDate, request, response);
			map = (Map<String, Object>) object;
		}

		list = (List<AdvertisementStatistics>) map.get("data");

		String path = advertisementStatisticsService.downStatistics(list);
		result.put("path", path);
		return result;
	}
}
