package com.yuanshanbao.ad.banner.dao;

import java.util.List;

import com.yuanshanbao.ad.banner.model.Banner;
import com.yuanshanbao.paginator.domain.PageBounds;

/**
 * Created by Ynght on 2016/10/24.
 */
public interface BannerDao {
	List<Banner> getAllUsableBanners();

	void insertBanner(Banner banner);

	int updateBanner(Banner banner);

	List<Banner> selectBanners(Banner banner, PageBounds pageBounds);
}
