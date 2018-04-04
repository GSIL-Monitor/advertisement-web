package com.yuanshanbao.ad.banner.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.ad.banner.dao.BannerDao;
import com.yuanshanbao.ad.banner.model.Banner;
import com.yuanshanbao.ad.core.CommonStatus;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class BannerServiceImpl implements BannerService {

	@Autowired
	private BannerDao bannerDao;

	@Override
	public List<Banner> selectBanners(Banner banner, PageBounds pageBounds) {
		return bannerDao.selectBanners(banner, pageBounds);
	}

	@Override
	public Banner selectBanner(Long bannerId) {
		return null;
	}

	@Override
	public void insertBanner(Banner banner) {
		bannerDao.insertBanner(banner);
	}

	@Override
	public void deleteBanner(Banner banner) {

		if (banner == null || banner.getBannerId() == null) {
			throw new BusinessException(ComRetCode.WRONG_PARAMETER);
		}

		banner.setStatus(CommonStatus.OFFLINE);
		bannerDao.updateBanner(banner);
	}

	@Override
	public void updateBanner(Banner banner) {
		bannerDao.updateBanner(banner);

	}

}
