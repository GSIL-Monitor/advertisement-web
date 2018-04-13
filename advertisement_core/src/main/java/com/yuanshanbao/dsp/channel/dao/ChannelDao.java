package com.yuanshanbao.dsp.channel.dao;

import java.util.List;

import com.yuanshanbao.dsp.channel.model.Channel;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface ChannelDao {

	public List<Channel> selectChannels(Channel channel, PageBounds pageBounds);

	public int insertChannel(Channel channel);

	public int deleteChannel(Long channelId);

	public int updateChannel(Channel channel);

	public List<Channel> selectChannels(List<Long> channelIdList);

	public List<Channel> selectChannelByKeys(List<String> keys);
}
