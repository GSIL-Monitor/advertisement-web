package com.yuanshanbao.dsp.channel.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.dsp.channel.model.ChannelProcedure;

public interface ChannelProcedureService {

	public List<ChannelProcedure> selectChannelProcedures(ChannelProcedure channelProcedure, PageBounds pageBounds);

	public ChannelProcedure selectChannelProcedure(Long procedureId);

	public void insertChannelProcedure(ChannelProcedure channelProcedure);

	public void deleteChannelProcedure(Long procedureId);

	public void updateChannelProcedure(ChannelProcedure channelProcedure);

	public void insertOrUpdateChannelProcedure(ChannelProcedure channelProcedure);

	public Map<Long, ChannelProcedure> selectChannelProcedureByIds(List<Long> procedureIdList);

	public Map<String, List<ChannelProcedure>> selectChannelProcedureByChannelIds(List<String> channelIdList);

}
