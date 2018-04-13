package com.yuanshanbao.dsp.channel.dao;

import java.util.List;

import com.yuanshanbao.dsp.channel.model.ChannelPromotionInfo;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface ChannelPromotionInfoDao {

	public List<ChannelPromotionInfo> selectChannelPromotionInfos(ChannelPromotionInfo channelPromotionInfo, PageBounds pageBounds);

	public int insertChannelPromotionInfo(ChannelPromotionInfo channelPromotionInfo);

	public int deleteChannelPromotionInfo(Long promotionInfoId);

	public int updateChannelPromotionInfo(ChannelPromotionInfo channelPromotionInfo);

	public List<ChannelPromotionInfo> selectChannelPromotionInfos(List<Long> channelPromotionInfoIdList);
}
