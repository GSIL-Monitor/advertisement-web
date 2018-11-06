package com.yuanshanbao.dsp.advertisement.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.dsp.advertisement.model.Advertisement;
import com.yuanshanbao.dsp.advertisement.model.MediaInformation;
import com.yuanshanbao.dsp.advertisement.model.vo.AdvertisementVo;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface AdvertisementService {

	public void insertAdvertisement(Advertisement advertisement);

	public void updateAdvertisement(Advertisement advertisement);

	public void deleteAdvertisement(Advertisement advertisement);

	public List<Advertisement> selectAdvertisement(Advertisement advertisement, PageBounds pageBounds);

	public Advertisement selectAdvertisement(Long advertisementId);

	public Advertisement selectAdvertisement(Advertisement advertisement);

	public Map<Long, Advertisement> selectAdvertisementByIds(List<Long> advertsementIds);

	public List<Advertisement> selectAdvertisementByAdvertiserIds(Long advertserIds);

	public Long getAdvertisementCount(Long advertisementId);

	public List<Advertisement> getAdvertisement(Long projectId, Long positionId, MediaInformation instance);

	public void increaseAdvertisementCount(Long projectId, Long advertisementId, Long positionId);

	public void increaseAdvertisementShowCount(Long projectId, Long advertisementId, Long positionId);

	public Map<String, Object> countAdvertisementSize(Advertisement advertisement);

	public List<AdvertisementVo> selectGift(String activityId, String channel);

	public List<Advertisement> getGift(Long projectId, String activityKey, String channelKey, MediaInformation instance);
}
