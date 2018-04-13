package com.yuanshanbao.dsp.channel.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.activity.service.ActivityService;
import com.yuanshanbao.dsp.app.model.AppType;
import com.yuanshanbao.dsp.channel.dao.ChannelDao;
import com.yuanshanbao.dsp.channel.model.Channel;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class ChannelServiceImpl implements ChannelService {

	@Autowired
	private ChannelDao channelDao;

	@Autowired
	private ActivityService activityServcie;

	@Override
	public List<Channel> selectChannels(Channel channel, PageBounds pageBounds) {
		return setProperty(channelDao.selectChannels(channel, pageBounds));
	}

	private List<Channel> setProperty(List<Channel> list) {
		List<Long> idsList = new ArrayList<Long>();

		for (Channel channel : list) {
			idsList.add(channel.getAppId());
		}

		for (Channel channel : list) {
			channel.setAppKey(AppType.getkey(channel.getAppId()));
			channel.setAppName(AppType.getDescription(channel.getAppKey()));
		}
		return list;

	}

	@Override
	public Channel selectChannel(Long channelId) {
		Channel channel = new Channel();
		if (channelId == null) {
			return null;
		}
		channel.setChannelId(channelId);
		List<Channel> list = selectChannels(channel, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Channel selectChannel(String key) {
		Channel channel = new Channel();
		if (StringUtils.isBlank(key)) {
			return null;
		}
		channel.setKey(key);
		List<Channel> list = selectChannels(channel, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertChannel(Channel channel) {
		int result = -1;

		result = channelDao.insertChannel(channel);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteChannel(Long channelId) {
		int result = -1;

		result = channelDao.deleteChannel(channelId);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateChannel(Channel channel) {
		int result = -1;

		result = channelDao.updateChannel(channel);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	/**
	 * @param channel
	 * @return
	 * @throws IOException
	 */
	@Override
	public void insertOrUpdateChannel(Channel channel) {
		if (channel.getChannelId() == null) {
			insertChannel(channel);
		} else {
			updateChannel(channel);
		}
	}

	@Override
	public Map<Long, Channel> selectChannels(List<Long> channelIdList) {
		Map<Long, Channel> map = new HashMap<Long, Channel>();
		if (channelIdList == null || channelIdList.size() == 0) {
			return map;
		}
		List<Channel> list = channelDao.selectChannels(channelIdList);
		for (Channel channel : list) {
			map.put(channel.getChannelId(), channel);
		}
		return map;
	}

	@Override
	public Map<String, Channel> selectChannelByKeys(List<String> keys) {
		Map<String, Channel> map = new HashMap<String, Channel>();
		if (keys == null || keys.size() == 0) {
			return map;
		}

		List<Channel> channelList = channelDao.selectChannelByKeys(keys);
		for (Channel param : channelList) {
			map.put(param.getKey(), param);
		}
		return map;
	}

}
