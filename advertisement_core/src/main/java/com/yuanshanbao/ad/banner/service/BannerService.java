package com.yuanshanbao.ad.banner.service;

import java.util.List;

import com.yuanshanbao.ad.banner.model.Banner;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface BannerService {
	
	public List<Banner> selectBanners(Banner banner, PageBounds pageBounds);

	public Banner selectBanner(Long bannerId);

	public void insertBanner(Banner banner);

	public void deleteBanner(Banner banner);

	public void updateBanner(Banner banner);


}
