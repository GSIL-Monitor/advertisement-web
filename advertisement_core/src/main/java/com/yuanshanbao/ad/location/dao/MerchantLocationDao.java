package com.yuanshanbao.ad.location.dao;

import java.util.List;

import com.yuanshanbao.ad.location.model.MerchantLocation;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface MerchantLocationDao {

	public List<MerchantLocation> selectMerchantLocations(MerchantLocation merchantLocation, PageBounds pageBounds);

	public int insertMerchantLocation(MerchantLocation merchantLocation);

	public int deleteMerchantLocation(Long merchantLocationId);

	public int updateMerchantLocation(MerchantLocation merchantLocation);

	public List<MerchantLocation> selectMerchantLocations(List<Long> merchantLocationIdList);

}
