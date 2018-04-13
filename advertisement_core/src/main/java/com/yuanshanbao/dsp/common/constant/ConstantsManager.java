package com.yuanshanbao.dsp.common.constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.yuanshanbao.common.constant.SessionConstants;
import com.yuanshanbao.common.util.CookieUtils;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.dsp.activity.model.Activity;
import com.yuanshanbao.dsp.activity.service.ActivityService;
import com.yuanshanbao.dsp.advertisement.model.Advertisement;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementCategory;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementStrategy;
import com.yuanshanbao.dsp.advertisement.service.AdvertisementCategoryService;
import com.yuanshanbao.dsp.advertisement.service.AdvertisementService;
import com.yuanshanbao.dsp.advertisement.service.AdvertisementStrategyService;
import com.yuanshanbao.dsp.channel.model.Channel;
import com.yuanshanbao.dsp.channel.service.ChannelService;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.config.model.Config;
import com.yuanshanbao.dsp.config.model.Function;
import com.yuanshanbao.dsp.config.service.ConfigService;
import com.yuanshanbao.dsp.config.service.FunctionService;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.location.model.Location;
import com.yuanshanbao.dsp.location.model.MerchantLocation;
import com.yuanshanbao.dsp.location.service.LocationService;
import com.yuanshanbao.dsp.location.service.MerchantLocationService;
import com.yuanshanbao.dsp.merchant.model.Merchant;
import com.yuanshanbao.dsp.merchant.service.MerchantService;
import com.yuanshanbao.dsp.page.model.Page;
import com.yuanshanbao.dsp.page.service.PageService;
import com.yuanshanbao.dsp.project.model.Project;
import com.yuanshanbao.dsp.project.service.ProjectService;
import com.yuanshanbao.dsp.tags.model.Tags;
import com.yuanshanbao.dsp.tags.model.TagsType;
import com.yuanshanbao.dsp.tags.service.TagsService;
import com.yuanshanbao.dsp.tags.service.TagsTypeService;

public class ConstantsManager {

	public static final String TAGS_SPLIT = ",";

	public static final long CATEGORY = 20;
	public static final long ADVERTISEMENT = 21;
	public static final long FORMTAG = 32;

	public static final long GENDER = 0;

	public static final String[] locationRemove = { "省", "市", "盟", "州", "县", "地区", "区" };

	private static Map<Long, TagsType> tagsTypeMap = new HashMap<Long, TagsType>();
	private static Map<String, TagsType> tagsTypeNameMap = new HashMap<String, TagsType>();

	private static Map<Long, Tags> tagsMap = new HashMap<Long, Tags>();
	private static Map<Long, List<Tags>> typeMap = new HashMap<Long, List<Tags>>();
	private static Map<Long, List<Tags>> showTypeMap = new HashMap<Long, List<Tags>>();

	private static Map<Long, List<Tags>> constantsTypeMap = new HashMap<Long, List<Tags>>();

	private static Map<String, Location> locationMap = new HashMap<String, Location>();
	private static Map<String, Location> locationNameMap = new HashMap<String, Location>();

	private static Map<Long, Map<String, MerchantLocation>> merchantLocationMap = new HashMap<Long, Map<String, MerchantLocation>>();

	private static ConstantsManager instance = null;

	@Resource
	private TagsService tagsService;

	@Resource
	private TagsTypeService tagsTypeService;

	@Resource
	private LocationService locationService;

	@Resource
	private ActivityService activityService;

	@Resource
	private PageService pageService;

	@Resource
	private MerchantService merchantService;

	@Resource
	private ConfigService configService;

	@Resource
	private FunctionService functionService;

	@Resource
	private ChannelService channelService;

	@Resource
	private AdvertisementService advertisementService;

	@Resource
	private AdvertisementStrategyService advertisementStrategyService;

	@Resource
	private AdvertisementCategoryService advertisementCategoryService;

	@Resource
	private MerchantLocationService merchantLocationService;

	public static boolean validateConstants(long[] types, Long id) {
		for (long type : types) {
			if (validateConstants(type, id)) {
				return true;
			}
		}
		return false;
	}

	public static boolean validateConstants(Long type, Long id) {
		if (type == null || id == null) {
			return false;
		}
		List<Tags> tagsList = getTagsList(type);
		for (Tags tags : tagsList) {
			if (tags.getTagsId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	public static String getTagsValue(Long id) {
		Tags tags = tagsMap.get(id);
		if (tags != null) {
			return tags.getName();
		}
		return null;
	}

	public static Tags getTags(Long id) {
		Tags tags = tagsMap.get(id);
		if (tags != null) {
			return tags;
		}
		return null;
	}

	public static Tags getTags(Long typeId, Long tagsId) {
		Tags tags = tagsMap.get(tagsId);
		if (typeId != null && tags != null && tags.getType() == typeId) {
			return tags;
		}
		return null;
	}

	public static List<Tags> getConstantsTagsListByType(Long type) {
		return constantsTypeMap.get(type);
	}

	public static Tags getConstantsTags(Long type, Long id) {
		List<Tags> list = getConstantsTagsListByType(type);
		if (list == null || id == null) {
			return null;
		}
		for (Tags tags : list) {
			if (tags.getTagsId() == id) {
				return tags;
			}
		}
		return null;
	}

	public static List<Tags> getTagsList(Long type) {
		List<Tags> list = typeMap.get(type);
		if (list != null) {
			return list;
		} else {
			list = constantsTypeMap.get(type);
			if (list != null) {
				return list;
			}
		}
		return new ArrayList<Tags>();
	}

	public static List<Tags> getTagsList(String key) {
		TagsType tagsType = tagsTypeNameMap.get(key);
		if (tagsType != null) {
			return getTagsList(tagsType.getTypeId());
		}
		return new ArrayList<Tags>();
	}

	public static List<Tags> getTagsListByShowType(Long showType) {
		List<Tags> list = showTypeMap.get(showType);
		if (list != null) {
			return list;
		} else {
			list = constantsTypeMap.get(showType);
			if (list != null) {
				return list;
			}
		}
		return new ArrayList<Tags>();
	}

	public static List<Tags> getTagsListByTypeName(String name) {
		Long type = null;
		if (StringUtils.isBlank(name)) {
			return null;
		}
		TagsType existType = tagsTypeNameMap.get(name);
		if (existType == null) {
			return null;
		}
		type = existType.getTypeId();
		List<Tags> list = typeMap.get(type);
		if (list != null) {
			return list;
		} else {
			list = constantsTypeMap.get(type);
			if (list != null) {
				return list;
			}
		}
		return new ArrayList<Tags>();
	}

	public static void refresh() {
		if (instance != null) {
			instance.refreshConstants();
		}
	}

	public void init() {
		refreshConstants();
		instance = this;
	}

	private void refreshConstants() {
		LoggerUtil.info("init InitBean");
		refreshTagsType();
		refreshTags();
		refreshConstantsTags();
		refreshLocationMap();
		refreshConfigs();
	}

	private void refreshTagsType() {
		TagsType tagsType = new TagsType();
		tagsType.setStatus(CommonStatus.ONLINE);
		List<TagsType> tagsTypeList = tagsTypeService.selectTagsType(tagsType, new PageBounds());
		Map<Long, TagsType> tempTypeMap = new LinkedHashMap<Long, TagsType>();
		Map<String, TagsType> tempTypeNameMap = new LinkedHashMap<String, TagsType>();
		for (TagsType type : tagsTypeList) {
			tempTypeMap.put(type.getTypeId(), type);
			if (StringUtils.isNotBlank(type.getTypeName())) {
				tempTypeNameMap.put(type.getTypeName(), type);
			}
		}
		tagsTypeMap = tempTypeMap;
		tagsTypeNameMap = tempTypeNameMap;
	}

	private void refreshConfigs() {
		Channel channelParam = new Channel();
		channelParam.setStatus(CommonStatus.ONLINE);
		Activity activityParam = new Activity();
		activityParam.setStatus(CommonStatus.ONLINE);
		Page pageParam = new Page();
		pageParam.setStatus(CommonStatus.ONLINE);
		Merchant merchantParam = new Merchant();
		merchantParam.setStatus(CommonStatus.ONLINE);
		Config configParam = new Config();
		configParam.setStatus(CommonStatus.ONLINE);
		Function functionParam = new Function();
		functionParam.setStatus(CommonStatus.ONLINE);
		Advertisement advertisementParam = new Advertisement();
		advertisementParam.setStatus(CommonStatus.ONLINE);
		AdvertisementStrategy strategyParam = new AdvertisementStrategy();
		strategyParam.setStatus(CommonStatus.ONLINE);
		AdvertisementCategory categoryParam = new AdvertisementCategory();
		categoryParam.setStatus(CommonStatus.ONLINE);

		PageBounds pageBounds = new PageBounds();
		ConfigManager.refreshConfig(channelService.selectChannels(channelParam, pageBounds),
				activityService.selectActivitys(activityParam, pageBounds),
				merchantService.selectMerchants(merchantParam, pageBounds),
				pageService.selectPages(pageParam, pageBounds),
				functionService.selectFunctions(functionParam, pageBounds),
				configService.selectConfig(configParam, pageBounds),
				advertisementService.selectAdvertisement(advertisementParam, pageBounds),
				advertisementStrategyService.selectAdvertisementStrategy(strategyParam, pageBounds),
				advertisementCategoryService.selectCategory(categoryParam, pageBounds));
	}

	private void refreshLocationMap() {
		Map<String, Location> map = new HashMap<String, Location>();
		Map<String, Location> nameMap = new HashMap<String, Location>();
		List<Location> locationList = locationService.selectLocations(new Location(), new PageBounds());
		Map<Long, Map<String, MerchantLocation>> tempMerchantLocationMap = merchantLocationService
				.selectMerchantLocationMap();
		for (Location location : locationList) {
			map.put(location.getCode(), location);
			nameMap.put(location.getName(), location);
		}
		for (Location location : locationList) {
			Location parent = map.get(location.getParentCode());
			if (parent != null) {
				location.setParent(parent);
				parent.getChildren().add(location);
			}
		}
		locationMap = map;
		locationNameMap = nameMap;
		merchantLocationMap = tempMerchantLocationMap;
	}

	/**
	 * 刷新
	 */
	public synchronized void refreshTags() {
		Map<Long, Tags> tempTagsMap = new HashMap<Long, Tags>();
		Map<Long, List<Tags>> tempTypeMap = new HashMap<Long, List<Tags>>();
		Map<Long, List<Tags>> tempShowTypeMap = new HashMap<Long, List<Tags>>();
		Tags tags = new Tags();
		tags.setStatus(CommonStatus.ONLINE);
		List<Tags> tagsList = tagsService.selectTags(tags, new PageBounds());

		refreshTagsMap(tempTagsMap, tempTypeMap, tempShowTypeMap, tagsList);
		tagsMap = tempTagsMap;
		typeMap = tempTypeMap;
		showTypeMap = tempShowTypeMap;
	}

	public synchronized void refreshConstantsTags() {
		constantsTypeMap = typeMap;
	}

	public void refreshTagsMap(Map<Long, Tags> tempTagsMap, Map<Long, List<Tags>> tempTypeMap,
			Map<Long, List<Tags>> tempShowTypeMap, List<Tags> tagsList) {
		for (Tags t : tagsList) {
			tempTagsMap.put(t.getTagsId(), t);
			if (tagsTypeMap.get(t.getType()) == null) {
				continue;
			}
			List<Tags> list = tempTypeMap.get(t.getType());
			List<Tags> list1 = tempShowTypeMap.get(t.getType());
			if (list == null) {
				list = new ArrayList<Tags>();
				tempTypeMap.put(t.getType(), list);
			}
			if (list1 == null) {
				list1 = new ArrayList<Tags>();
				if (t.getSearchType() != null) {
					tempShowTypeMap.put(t.getSearchType(), list1);
				}
			}
			list.add(t);
			list1.add(t);
		}
		LoggerUtil.info("refresh " + (tagsList == null ? 0 : tagsList.size()) + " inis");
	}

	public static Map<Long, TagsType> getTagsTypeMap() {
		return tagsTypeMap;
	}

	public static TagsType getTagsType(Long key) {
		return tagsTypeMap.get(key);
	}

	public static TagsType getTagsType(String key) {
		return tagsTypeNameMap.get(key);
	}

	public static String getTypeDescription(Long key) {
		TagsType tagsType = tagsTypeMap.get(key);
		if (tagsType == null) {
			return null;
		}
		return tagsType.getTypeDescription();
	}

	public static String getTypeName(Long key) {
		return tagsTypeMap.get(key).getTypeName();
	}

	public static Map<Long, List<Tags>> getTypeMap() {
		return typeMap;
	}

	public static Map<Long, Tags> getTagsMap() {
		return tagsMap;
	}

	public static Map<Long, List<Tags>> getShowTypeMap() {
		return showTypeMap;
	}

	public static Map<String, Object> getConstantsMap() {
		Map<String, Object> constantsMap = new HashMap<String, Object>();
		for (Entry<Long, List<Tags>> entry : getTypeMap().entrySet()) {
			constantsMap.put(ConstantsManager.getTypeName(entry.getKey()) + "List", entry.getValue());
		}
		constantsMap.put("commonStatusList", CommonStatus.getCodeDescriptionMap().entrySet());
		return constantsMap;
	}

	public static Map<String, Location> getLocationMap() {
		return locationMap;
	}

	public static void setLocationMap(Map<String, Location> locationMap) {
		ConstantsManager.locationMap = locationMap;
	}

	public static Map<String, Location> getLocationNameMap() {
		return locationNameMap;
	}

	public static void setLocationNameMap(Map<String, Location> locationNameMap) {
		ConstantsManager.locationNameMap = locationNameMap;
	}

	public static Location getLocationByCode(String code) {
		return locationMap.get(code);
	}

	public static Location getLocationByName(String name) {
		return locationNameMap.get(name);
	}

	public static String getLocationStrByCode(String code) {
		Location location = getLocationByCode(code);
		if (location != null) {
			String locationStr = location.getShortName();
			int type = location.getType();
			for (int i = 1; i < type; i++) {
				location = getLocationByCode(location.getParentCode());
				locationStr = location.getShortName() + locationStr;
			}
			return locationStr;
		}
		return null;
	}

	public static Map<Long, Map<String, MerchantLocation>> getMerchantLocationMap() {
		return merchantLocationMap;
	}

	public static Map<String, MerchantLocation> getMerchantLocationById(Long merchantId) {
		return merchantLocationMap.get(merchantId);
	}

	public static List<Tags> getTagsListSortByScore(Long type) {
		List<Tags> list = getTagsList(type);
		Collections.sort(list, new Comparator<Tags>() {

			@Override
			public int compare(Tags o1, Tags o2) {
				if (o1.getScore() != null && o2.getScore() != null) {
					return o2.getScore() - o1.getScore();
				}
				return 0;
			}
		});
		return list;
	}

	public static Long getProjectId(ProjectService projectService, HttpServletRequest request) {
		Project project = getProject(projectService, request);
		if (project == null) {
			// throw new BusinessException(ComRetCode.NOT_LOGIN);
			return 1L;
		}
		return project.getProjectId();
	}

	public static Project getProject(ProjectService projectService, HttpServletRequest request) {
		Long projectId = (Long) request.getSession().getAttribute(SessionConstants.SESSION_PROJECT_ID);
		if (projectId == null) {
			String projectKey = CookieUtils.getCookieValue(request, "project_key");
			return projectService.selectProject(projectKey);
		}
		return null;
	}
}
