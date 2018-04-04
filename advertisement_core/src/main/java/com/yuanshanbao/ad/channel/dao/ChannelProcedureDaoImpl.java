package com.yuanshanbao.ad.channel.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.ad.base.dao.BaseDaoImpl;
import com.yuanshanbao.ad.channel.model.ChannelProcedure;

@Repository
public class ChannelProcedureDaoImpl extends BaseDaoImpl implements ChannelProcedureDao {
	@Override
	public List<ChannelProcedure> selectChannelProcedures(ChannelProcedure channelProcedure, PageBounds pageBounds) {
		return getSqlSession().selectList("channelProcedure.selectChannelProcedure", channelProcedure, pageBounds);
	}

	@Override
	public int insertChannelProcedure(ChannelProcedure channelProcedure) {
		return getSqlSession().insert("channelProcedure.insertChannelProcedure", channelProcedure);
	}

	@Override
	public int deleteChannelProcedure(Long channelProcedureId) {
		return getSqlSession().delete("channelProcedure.deleteChannelProcedure", channelProcedureId);
	}

	@Override
	public int updateChannelProcedure(ChannelProcedure channelProcedure) {
		return getSqlSession().update("channelProcedure.updateChannelProcedure", channelProcedure);
	}

	@Override
	public List<ChannelProcedure> selectChannelProcedures(List<Long> channelProcedureIdList) {
		if (channelProcedureIdList == null || channelProcedureIdList.size() == 0) {
			return new ArrayList<ChannelProcedure>();
		}
		return getSqlSession().selectList("channelProcedure.selectChannelProcedureByIds", channelProcedureIdList);
	}

	@Override
	public List<ChannelProcedure> selectChannelProceduresByChannelIds(List<String> channelList) {
		if (channelList == null || channelList.size() == 0) {
			return new ArrayList<ChannelProcedure>();
		}
		return getSqlSession().selectList("channelProcedure.selectChannelProcedureByChannels", channelList);
	}

}
