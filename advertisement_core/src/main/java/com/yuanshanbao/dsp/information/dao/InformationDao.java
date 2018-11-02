package com.yuanshanbao.dsp.information.dao;

import java.util.List;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.dsp.information.model.Information;

public interface InformationDao {

	public List<Information> selectInformations(Information information, PageBounds pageBounds);

	public int insertInformation(Information information);

	public int deleteInformation(Long informationId);

	public int updateInformation(Information information);

	public List<Information> selectInformations(List<Long> informationIdList);



	Information selectinformationByMobile(String mobile);
}
