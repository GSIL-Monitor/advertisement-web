package com.yuanshanbao.ad.advertisement.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.ad.advertisement.dao.AdvertisementDao;
import com.yuanshanbao.ad.advertisement.model.Advertisement;
import com.yuanshanbao.ad.advertisement.model.AdvertisementCategory;
import com.yuanshanbao.ad.advertisement.model.Advertiser;
import com.yuanshanbao.ad.common.constant.RedisConstant;
import com.yuanshanbao.ad.common.redis.base.RedisService;
import com.yuanshanbao.ad.tags.model.Tags;
import com.yuanshanbao.ad.tags.service.TagsService;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

	private static final String ADVERTIEMENT_INCREASE_RANGE = "3,7";

	private static final String ADVERTIEMENT_INITIAL_RANGE = "3000,5000";

	@Autowired
	private AdvertisementDao advertisementDao;

	@Autowired
	private AdvertiserService advertiserService;

	@Autowired
	private RedisService redisCacheService;

	@Autowired
	private TagsService tagsService;
	
	@Autowired
	private AdvertisementCategoryService categoryService;

	@Override
	public void insertAdvertisement(Advertisement advertisement) {

		int result = -1;

		result = advertisementDao.insertAdvertisement(advertisement);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public void updateAdvertisement(Advertisement advertisement) {

		int result = -1;

		result = advertisementDao.updateAdvertisement(advertisement);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public void deleteAdvertisement(Advertisement advertisement) {

		int result = -1;

		result = advertisementDao.deleteAdvertisement(advertisement);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public List<Advertisement> selectAdvertisement(Advertisement advertisement, PageBounds pageBounds) {
		return setProperty(advertisementDao.selectAdvertisement(advertisement, pageBounds));
	}

	private List<Advertisement> setProperty(List<Advertisement> list) {
		List<Long> advertiserIds = new ArrayList<Long>();
		List<Long> tagsIds = new ArrayList<Long>();
		List<Long> categoryIds = new ArrayList<Long>();
		for (Advertisement advertisement : list) {
			advertiserIds.add(advertisement.getAdvertiserId());
			tagsIds.add(advertisement.getTagsId());
			categoryIds.add(advertisement.getCategory());
		}

		Map<Long, Advertiser> map = advertiserService.selectAdvertiserByIds(advertiserIds);
		Map<Long, Tags> tagsMap = tagsService.selectTagsByIds(tagsIds);
		Map<Long, AdvertisementCategory> categoryMap = categoryService.selectCategoryByIds(categoryIds);
		for (Advertisement advertisement : list) {
			advertisement.setAdvertiser(map.get(advertisement.getAdvertiserId()));
			advertisement.setTags(tagsMap.get(advertisement.getTagsId()));
			advertisement.setAdvertisementCategory(categoryMap.get(advertisement.getCategory()));
		}
		return list;
	}

	@Override
	public Map<Long, Advertisement> selectAdvertisementByIds(List<Long> advertisementIds) {
		Map<Long, Advertisement> map = new HashMap<Long, Advertisement>();
		if (advertisementIds == null || advertisementIds.size() == 0) {
			return map;
		}
		List<Advertisement> list = advertisementDao.selectAdvertisementByIds(advertisementIds);
		for (Advertisement ad : list) {
			map.put(ad.getAdvertisementId(), ad);
		}
		return map;
	}

	@Override
	public Advertisement selectAdvertisement(Long advertisementId) {
		if (advertisementId == null) {
			return null;
		}
		Advertisement advertisement = new Advertisement();
		advertisement.setAdvertisementId(advertisementId);
		List<Advertisement> list = selectAdvertisement(advertisement, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}

		return null;
	}

	@Override
	public Advertisement selectAdvertisement(Advertisement advertisement) {
		if (advertisement == null) {
			return null;
		}
		List<Advertisement> list = selectAdvertisement(advertisement, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Advertisement> selectAdvertisementByAdvertiserIds(Long advertserIds) {
		if (advertserIds == null) {
			return null;
		}
		Advertisement advertisement = new Advertisement();
		advertisement.setAdvertiserId(advertserIds);
		List<Advertisement> list = selectAdvertisement(advertisement, new PageBounds());
		if (list.size() > 0) {
			return list;
		}
		return null;
	}

	@Override
	public Long getAdvertisementCount(Long advertisementId) {
		String key = RedisConstant.getAdvertisementShowCountKey(advertisementId + "");
		String str = (String) redisCacheService.get(key);
		long count = 0;
		if (ValidateUtil.isNumber(str)) {
			count = Long.parseLong(str);
		}
		if (count == 0) {
			count = (long) getRandomFromRange(ADVERTIEMENT_INITIAL_RANGE);
			redisCacheService.set(key, count + "");
		}
		return count;
	}

	@Override
	public void increaseAdvertisementCount(Long advertisementId) {
		int increaseCount = 1;
		increaseCount = getRandomFromRange(ADVERTIEMENT_INCREASE_RANGE);
		redisCacheService.increBy(RedisConstant.getAdvertisementShowCountKey(advertisementId + ""), increaseCount);
	}

	private Integer getRandomFromRange(String numRange) {
		int random = 0;
		String[] s = numRange.split(",");
		if (s.length == 2) {
			double min = Double.parseDouble(s[0]);
			double max = Double.parseDouble(s[1]);
			random = (int) (Math.random() * (max - min + 1) + min);
		}
		return random;
	}
}
