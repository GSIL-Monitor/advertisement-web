package com.yuanshanbao.dsp.location.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.dsp.location.model.MerchantLocation;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface MerchantLocationService {

	public List<MerchantLocation> selectMerchantLocations(MerchantLocation merchantLocation, PageBounds pageBounds);

	public MerchantLocation selectMerchantLocation(Long merchantLocationId);

	public MerchantLocation selectMerchantLocation(String locationCode, Long merchantId);

	public void insertMerchantLocation(MerchantLocation merchantLocation);

	public void deleteMerchantLocation(Long merchantLocationId);

	public void updateMerchantLocation(MerchantLocation merchantLocation);

	public void insertOrUpdateMerchantLocation(MerchantLocation merchantLocation);

	public Map<Long, MerchantLocation> selectMerchantLocations(List<Long> merchantLocationIdList);

	public Map<String, MerchantLocation> selectMerchantLocationsByMerchantId(Long merchantId);

	Map<Long, Map<String, MerchantLocation>> selectMerchantLocationMap();

}
