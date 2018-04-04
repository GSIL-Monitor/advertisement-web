package com.yuanshanbao.ad.channel.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.ad.channel.model.ChannelPromotionInfo;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface ChannelPromotionInfoService {

	public List<ChannelPromotionInfo> selectChannelPromotionInfos(ChannelPromotionInfo channelPromotionInfo, PageBounds pageBounds);

	public ChannelPromotionInfo selectChannelPromotionInfo(Long promotionInfoId);

	public void insertChannelPromotionInfo(ChannelPromotionInfo channelPromotionInfo);

	public void deleteChannelPromotionInfo(Long promotionInfoId);

	public void updateChannelPromotionInfo(ChannelPromotionInfo channelPromotionInfo);

	public void insertOrUpdateChannelPromotionInfo(ChannelPromotionInfo channelPromotionInfo);

	public Map<Long, ChannelPromotionInfo> selectChannelPromotionInfoByIds(List<Long> promotionInfoIdList);

	public void modifyChannelPromotionInfo(ChannelPromotionInfo channelPromotionInfo);
}
