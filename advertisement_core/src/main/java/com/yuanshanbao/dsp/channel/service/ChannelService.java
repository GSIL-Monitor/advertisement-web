package com.yuanshanbao.dsp.channel.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.dsp.channel.model.Channel;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface ChannelService {

	public List<Channel> selectChannels(Channel channel, PageBounds pageBounds);

	public Channel selectChannel(Long channelId);

	public void insertChannel(Channel channel);

	public void deleteChannel(Long channelId);

	public void updateChannel(Channel channel);

	public void insertOrUpdateChannel(Channel channel);

	public Map<Long, Channel> selectChannels(List<Long> channelIdList);

	public Channel selectChannel(String key);

	public Map<String, Channel> selectChannelByKeys(List<String> keys);

	public Channel selectChannelByKey(String key);
}
