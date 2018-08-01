package com.yuanshanbao.dsp.information.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.dsp.information.model.Information;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface InformationService {

	public List<Information> selectInformations(Information information, PageBounds pageBounds);

	public Information selectInformation(Long informationId);

	public void insertInformation(Information information);

	public void deleteInformation(Long informationId);

	public void updateInformation(Information information);

	public void insertOrUpdateInformation(Information information);

	public Map<Long, Information> selectInformationByIds(List<Long> informationIdList);

	public Information selectInformationByUserId(Long userId);

	public void checkExist(Information information);

	public void tryDeliver(Information information, boolean b, boolean c);

}
