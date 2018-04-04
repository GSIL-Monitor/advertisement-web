package com.yuanshanbao.ad.banner.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.ad.banner.model.Banner;
import com.yuanshanbao.ad.base.dao.BaseDaoImpl;
import com.yuanshanbao.paginator.domain.PageBounds;

/**
 * Created by Ynght on 2016/10/24.
 */
@Repository
public class BannerDaoImpl extends BaseDaoImpl implements BannerDao {
    @Override
    public List<Banner> getAllUsableBanners() {
        return getSqlSession().selectList("Banner.getAllUsableBanners");
    }

    @Override
    public void insertBanner(Banner banner) {
        getSqlSession().insert("Banner.insert", banner);
    }

    @Override
    public int updateBanner(Banner banner) {
        return getSqlSession().update("Banner.update", banner);
    }

	@Override
	public List<Banner> selectBanners(Banner banner, PageBounds pageBounds) {
		return getSqlSession().selectList("Banner.selectBanners", banner, pageBounds);
	}
}
