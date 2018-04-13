package com.yuanshanbao.dsp.channel.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.channel.dao.ChannelPromotionInfoDao;
import com.yuanshanbao.dsp.channel.model.Channel;
import com.yuanshanbao.dsp.channel.model.ChannelPromotionInfo;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class ChannelPromotionInfoServiceImpl implements ChannelPromotionInfoService {

	@Autowired
	ChannelPromotionInfoDao channelPromotionInfoDao;

	@Autowired
	ChannelService channelService;

	@Override
	public List<ChannelPromotionInfo> selectChannelPromotionInfos(ChannelPromotionInfo channelPromotionInfo,
			PageBounds pageBounds) {
		return setProperty(channelPromotionInfoDao.selectChannelPromotionInfos(channelPromotionInfo, pageBounds));
	}

	private List<ChannelPromotionInfo> setProperty(List<ChannelPromotionInfo> list) {
		List<String> keys = new ArrayList<String>();

		for (ChannelPromotionInfo channelPromotionInfo : list) {
			keys.add(channelPromotionInfo.getChannel());
		}

		Map<String, Channel> map = channelService.selectChannelByKeys(keys);

		for (ChannelPromotionInfo channelPromotionInfo : list) {
			channelPromotionInfo.setChannelObject(map.get(channelPromotionInfo.getChannel()));
		}
		return list;
	}

	@Override
	public ChannelPromotionInfo selectChannelPromotionInfo(Long promotionInfoId) {
		ChannelPromotionInfo channelPromotionInfo = new ChannelPromotionInfo();
		if (promotionInfoId == null) {
			return null;
		}
		channelPromotionInfo.setPromotionInfoId(promotionInfoId);
		List<ChannelPromotionInfo> list = selectChannelPromotionInfos(channelPromotionInfo, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertChannelPromotionInfo(ChannelPromotionInfo channelPromotionInfo) {
		int result = -1;

		result = channelPromotionInfoDao.insertChannelPromotionInfo(channelPromotionInfo);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteChannelPromotionInfo(Long promotionInfoId) {
		int result = -1;

		result = channelPromotionInfoDao.deleteChannelPromotionInfo(promotionInfoId);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateChannelPromotionInfo(ChannelPromotionInfo channelPromotionInfo) {
		int result = -1;

		result = channelPromotionInfoDao.updateChannelPromotionInfo(channelPromotionInfo);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void insertOrUpdateChannelPromotionInfo(ChannelPromotionInfo channelPromotionInfo) {
		if (channelPromotionInfo.getPromotionInfoId() == null) {
			insertChannelPromotionInfo(channelPromotionInfo);
		} else {
			updateChannelPromotionInfo(channelPromotionInfo);
		}
	}

	@Override
	public void modifyChannelPromotionInfo(ChannelPromotionInfo channelPromotionInfo) {
		ChannelPromotionInfo promotionInfo = new ChannelPromotionInfo();
		promotionInfo.setIdfa(channelPromotionInfo.getIdfa());
		List<ChannelPromotionInfo> list = selectChannelPromotionInfos(promotionInfo, new PageBounds());
		if (list.size() < 0) {
			if (channelPromotionInfo.getPromotionInfoId() == null) {
				insertChannelPromotionInfo(channelPromotionInfo);
			} else {
				updateChannelPromotionInfo(channelPromotionInfo);
			}
		}
	}

	@Override
	public Map<Long, ChannelPromotionInfo> selectChannelPromotionInfoByIds(List<Long> promotionInfoIdList) {
		Map<Long, ChannelPromotionInfo> map = new HashMap<Long, ChannelPromotionInfo>();
		if (promotionInfoIdList == null || promotionInfoIdList.size() == 0) {
			return map;
		}
		List<ChannelPromotionInfo> list = channelPromotionInfoDao.selectChannelPromotionInfos(promotionInfoIdList);
		for (ChannelPromotionInfo channelPromotionInfo : list) {
			map.put(channelPromotionInfo.getPromotionInfoId(), channelPromotionInfo);
		}
		return map;
	}

}
