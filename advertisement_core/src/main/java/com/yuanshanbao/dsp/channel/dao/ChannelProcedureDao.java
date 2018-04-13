package com.yuanshanbao.dsp.channel.dao;

import java.util.List;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.dsp.channel.model.ChannelProcedure;

public interface ChannelProcedureDao {

	public List<ChannelProcedure> selectChannelProcedures(
			ChannelProcedure channelProcedure, PageBounds pageBounds);

	public int insertChannelProcedure(ChannelProcedure channelProcedure);

	public int deleteChannelProcedure(Long procedureId);

	public int updateChannelProcedure(ChannelProcedure channelProcedure);

	public List<ChannelProcedure> selectChannelProcedures(List<Long> procedureIdList);

	public List<ChannelProcedure> selectChannelProceduresByChannelIds(List<String> channelList);
}
