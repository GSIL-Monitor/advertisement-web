package com.yuanshanbao.ad.cache;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.ad.banner.dao.BannerDao;
import com.yuanshanbao.ad.banner.model.Banner;

/**
 * Created by Ynght on 2016/10/24.
 */
public class BannerCache {

	private static List<Banner> bannerList = new ArrayList<>();

	@Autowired
	private BannerDao bannerDao;

	private BannerCache() {
	}

	public void init() {
		LoggerUtil.info("init BannerCache");
		refresh();
	}

	public void refresh() {
		bannerList = bannerDao.getAllUsableBanners();
		LoggerUtil.info("refresh " + (bannerList == null ? 0 : bannerList.size())
				+ " banner");
	}

	public static List<Banner> getAllUsableBannerList() {
		return bannerList;
	}

	public static List<Banner> getBannerList() {
		return bannerList;
	}

	public static void setBannerList(List<Banner> bannerList) {
		BannerCache.bannerList = bannerList;
	}

}
