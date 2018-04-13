package com.yuanshanbao.dsp.channel.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.dsp.channel.dao.ChannelProcedureDao;
import com.yuanshanbao.dsp.channel.model.ChannelProcedure;
import com.yuanshanbao.dsp.core.CommonStatus;

@Service
public class ChannelProcedureServiceImpl implements ChannelProcedureService {

	@Autowired
	private ChannelProcedureDao channelProcedureDao;

	@Override
	public List<ChannelProcedure> selectChannelProcedures(ChannelProcedure channelProcedure, PageBounds pageBounds) {
		return channelProcedureDao.selectChannelProcedures(channelProcedure, pageBounds);
	}

	@Override
	public void insertChannelProcedure(ChannelProcedure channelProcedure) {
		int result = -1;

		result = channelProcedureDao.insertChannelProcedure(channelProcedure);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteChannelProcedure(Long procedureId) {
		int result = -1;

		result = channelProcedureDao.deleteChannelProcedure(procedureId);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateChannelProcedure(ChannelProcedure channel) {
		int result = -1;

		result = channelProcedureDao.updateChannelProcedure(channel);

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
	public void insertOrUpdateChannelProcedure(ChannelProcedure channelProcedure) {
		if (channelProcedure.getProcedureId() != null) {
			return;
		}
		ChannelProcedure exist = selectChannelProcedure(channelProcedure.getProcedureId());
		if (exist == null) {
			channelProcedure.setStatus(CommonStatus.ONLINE);
			insertChannelProcedure(channelProcedure);
		} else {
			updateChannelProcedure(channelProcedure);
		}
	}

	@Override
	public Map<Long, ChannelProcedure> selectChannelProcedureByIds(List<Long> procedureIdList) {
		Map<Long, ChannelProcedure> map = new HashMap<Long, ChannelProcedure>();
		if (procedureIdList == null || procedureIdList.size() == 0) {
			return map;
		}
		List<ChannelProcedure> list = channelProcedureDao.selectChannelProcedures(procedureIdList);
		for (ChannelProcedure channelProcedure : list) {
			map.put(channelProcedure.getProcedureId(), channelProcedure);
		}
		return map;
	}

	@Override
	public ChannelProcedure selectChannelProcedure(Long procedureId) {
		ChannelProcedure channelProcedure = new ChannelProcedure();
		channelProcedure.setProcedureId(procedureId);
		List<ChannelProcedure> list = selectChannelProcedures(channelProcedure, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<String, List<ChannelProcedure>> selectChannelProcedureByChannelIds(List<String> channelList) {
		Map<String, List<ChannelProcedure>> map = new HashMap<String, List<ChannelProcedure>>();
		if (channelList == null || channelList.size() == 0) {
			return map;
		}
		List<ChannelProcedure> resultList = channelProcedureDao.selectChannelProceduresByChannelIds(channelList);
		for (ChannelProcedure channelProcedure : resultList) {
			List<ChannelProcedure> list = map.get(channelProcedure.getChannel());
			if (list == null) {
				list = new ArrayList<ChannelProcedure>();
				map.put(channelProcedure.getChannel(), list);
			}
			list.add(channelProcedure);
		}
		return map;
	}

}
