package com.yuanshanbao.dsp.channel.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.activity.service.ActivityService;
import com.yuanshanbao.dsp.channel.dao.ChannelDao;
import com.yuanshanbao.dsp.channel.model.Channel;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.dsp.quota.model.Quota;
import com.yuanshanbao.dsp.quota.service.QuotaService;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class ChannelServiceImpl implements ChannelService {

	@Autowired
	private ChannelDao channelDao;

	@Autowired
	private ActivityService activityServcie;

	@Autowired
	private QuotaService quotaServcie;

	@Override
	public List<Channel> selectChannels(Channel channel, PageBounds pageBounds) {
		return setProperty(channelDao.selectChannels(channel, pageBounds));
	}

	private List<Channel> setProperty(List<Channel> list) {
		List<Long> idsList = new ArrayList<Long>();

		// for (Channel channel : list) {
		// idsList.add(channel.getAppId());
		// }
		//
		// for (Channel channel : list) {
		// channel.setAppKey(AppType.getkey(channel.getAppId()));
		// channel.setAppName(AppType.getDescription(channel.getAppKey()));
		// }
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

	@Override
	public Channel selectChannelByKey(String key) {
		Channel channel = new Channel();
		if (StringUtils.isBlank(key)) {
			return null;
		}
		List<String> keys = new ArrayList<String>();
		keys.add(key);
		List<Channel> channelList = channelDao.selectChannelByKeys(keys);
		if (channelList.size() > 0) {
			channel = channelList.get(0);
			return channel;
		} else {
			return null;
		}
	}

	// 该渠道下计划进行竞价
	public Probability channelBidding(List<Probability> list, String channelKey) {
		Channel channel = ConfigManager.getChannel(channelKey);
		// 获得channel成本价
		BigDecimal cost = channel.getUnitPrice();
		if (list == null || list.size() == 0) {
			return null;
		}
		List<Long> ids = new ArrayList<Long>();
		Map<Long, Probability> proMap = new HashMap<Long, Probability>();
		for (Probability probability : list) {
			ids.add(probability.getProbabilityId());
			proMap.put(probability.getProbabilityId(), probability);
		}
		Map<Long, Quota> quotaMap = quotaServcie.selectQuotaByProbabilityId(ids);
		List<Quota> quotaList = (List<Quota>) quotaMap.values();
		Collections.sort(quotaList, new Comparator<Quota>() {
			@Override
			public int compare(Quota o1, Quota o2) {
				return o2.getBestBid().compareTo(o1.getBestBid());
			}
		});
		Quota quota = quotaList.get(0);
		if (cost != null && (cost.compareTo(quota.getBestBid())) < 0) {
			return null;
		}
		return proMap.get(quota.getProbabilityId());
	}

	public static void main(String[] args) {
		List<Quota> list = new ArrayList<Quota>();
		for (int i = 0; i < 10; i++) {
			Quota quota = new Quota();
			quota.setBestBid(new BigDecimal(Math.random()));
			list.add(quota);
		}

		Collections.sort(list, new Comparator<Quota>() {
			@Override
			public int compare(Quota o1, Quota o2) {
				return o2.getBestBid().compareTo(o1.getBestBid());
			}
		});
		for (Quota q : list) {
			System.err.println(q.getBestBid());
		}
	}
}
