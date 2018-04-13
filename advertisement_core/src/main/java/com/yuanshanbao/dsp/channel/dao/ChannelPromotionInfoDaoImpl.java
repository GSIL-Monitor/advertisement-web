package com.yuanshanbao.dsp.channel.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.channel.model.ChannelPromotionInfo;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class ChannelPromotionInfoDaoImpl extends BaseDaoImpl implements ChannelPromotionInfoDao {

	@Override
	public List<ChannelPromotionInfo> selectChannelPromotionInfos(ChannelPromotionInfo promotionInfo,
			PageBounds pageBounds) {
		return getSqlSession().selectList("promotionInfo.selectPromotionInfo", promotionInfo,
				pageBounds);
	}

	@Override
	public int insertChannelPromotionInfo(ChannelPromotionInfo promotionInfo) {
		return getSqlSession().insert("promotionInfo.insertPromotionInfo", promotionInfo);
	}

	@Override
	public int deleteChannelPromotionInfo(Long promotionInfoId) {
		return getSqlSession().delete("promotionInfo.deletePromotionInfo", promotionInfoId);
	}

	@Override
	public int updateChannelPromotionInfo(ChannelPromotionInfo promotionInfo) {
		return getSqlSession().update("promotionInfo.updatePromotionInfo", promotionInfo);
	}

	@Override
	public List<ChannelPromotionInfo> selectChannelPromotionInfos(List<Long> promotionInfoIdList) {
		if (promotionInfoIdList == null || promotionInfoIdList.size() == 0) {
			return new ArrayList<ChannelPromotionInfo>();
		}
		return getSqlSession().selectList("promotionInfo.selectPromotionInfoByIds", promotionInfoIdList);
	}

}
