package com.yuanshanbao.dsp.advertisement.dao;

import java.util.List;

import com.yuanshanbao.dsp.advertisement.model.Advertisement;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface AdvertisementDao {

	public int insertAdvertisement(Advertisement advertisement);
	
	public int updateAdvertisement(Advertisement advertisement);
	
	public int deleteAdvertisement(Advertisement advertisement);
	
	public List<Advertisement> selectAdvertisement(Advertisement advertisement, PageBounds pageBounds);
	
	public List<Advertisement> selectAdvertisementByIds(List<Long> advertisementIds);
	
}
