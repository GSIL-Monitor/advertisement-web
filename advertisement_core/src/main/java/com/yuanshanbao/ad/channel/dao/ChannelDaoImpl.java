package com.yuanshanbao.ad.channel.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.ad.base.dao.BaseDaoImpl;
import com.yuanshanbao.ad.channel.model.Channel;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class ChannelDaoImpl extends BaseDaoImpl implements ChannelDao {
	@Override
	public List<Channel> selectChannels(Channel channel, PageBounds pageBounds) {
		return getSqlSession().selectList("channel.selectChannel", channel, pageBounds);
	}

	@Override
	public int insertChannel(Channel channel) {
		return getSqlSession().insert("channel.insertChannel", channel);
	}

	@Override
	public int deleteChannel(Long channelId) {
		return getSqlSession().delete("channel.deleteChannel", channelId);
	}

	@Override
	public int updateChannel(Channel channel) {
		return getSqlSession().update("channel.updateChannel", channel);
	}

	@Override
	public List<Channel> selectChannels(List<Long> channelIdList) {
		if (channelIdList == null || channelIdList.size() == 0) {
			return new ArrayList<Channel>();
		}
		return getSqlSession().selectList("channel.selectChannelByIds", channelIdList);
	}

	@Override
	public List<Channel> selectChannelByKeys(List<String> keys) {
		if (keys == null || keys.size() == 0) {
			return new ArrayList<Channel>();
		}
		return getSqlSession().selectList("channel.selectChannelByKeys", keys);
	}

}
