package com.yuanshanbao.dsp.advertisement.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.advertisement.dao.AdvertisementDao;
import com.yuanshanbao.dsp.advertisement.model.Advertisement;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementCategory;
import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.advertiser.service.AdvertiserService;
import com.yuanshanbao.dsp.common.constant.RedisConstant;
import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.position.model.Position;
import com.yuanshanbao.dsp.position.service.PositionService;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.dsp.probability.service.ProbabilityService;
import com.yuanshanbao.dsp.quota.model.Quota;
import com.yuanshanbao.dsp.quota.service.QuotaService;
import com.yuanshanbao.dsp.tags.model.Tags;
import com.yuanshanbao.dsp.tags.service.TagsService;
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

	@Autowired
	private PositionService positionService;

	@Autowired
	private ProbabilityService probabilityService;

	@Autowired
	private QuotaService quotaService;

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

	public List<Advertisement> getAdvertisement(Long projectId, Long positionId) {
		List<Advertisement> advertismentList = new ArrayList<Advertisement>();
		Position position = positionService.selectPosition(positionId);
		if (position == null) {
			return advertismentList;
		}
		Probability probabilityParam = new Probability();
		probabilityParam.setProjectId(projectId);
		probabilityParam.setPositionId(positionId);
		List<Probability> probabilityList = probabilityService.selectProbabilitys(probabilityParam, new PageBounds());
		List<Long> advertisementIdList = new ArrayList<Long>();
		for (Probability probability : probabilityList) {
			if (probability.getStartTime() != null) {
				if (probability.getStartTime().after(new Date())) {
					continue;
				}
			}
			if (probability.getEndTime() != null) {
				if (probability.getEndTime().before(new Date())) {
					continue;
				}
			}
			advertisementIdList.add(probability.getAdvertisementId());
		}
		if (advertisementIdList.size() == 0) {
			return advertismentList;
		}
		Quota quotaParam = new Quota();
		quotaParam.setPositionId(positionId);
		quotaParam.setAdvertisementIdList(advertisementIdList);
		List<Quota> quotaList = quotaService.selectQuota(quotaParam, new PageBounds());
		for (Quota quota : quotaList) {
		
		}
		return advertismentList;
	}

	@Override
	public Map<String, Object> countAdvertisementSize(
			Advertisement advertisement) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Advertisement> advertisements = this.selectAdvertisement(advertisement,new PageBounds());
		int count = 0;
		for(Advertisement adv : advertisements){
			if(adv.getStatusValue().equals("已失效")){
				count ++;
			}
		}
		result.put("total", advertisements.size());
		result.put("use", advertisements.size() - count);
		result.put("down", count);
		return null;
	}
}
