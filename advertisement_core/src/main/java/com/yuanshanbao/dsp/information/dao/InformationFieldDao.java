package com.yuanshanbao.dsp.information.dao;

import java.util.List;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.dsp.information.model.InformationField;

public interface InformationFieldDao {

	public List<InformationField> selectInformationFields(
			InformationField informationField, PageBounds pageBounds);

	public int insertInformationField(InformationField informationField);

	public int deleteInformationField(Long fieldId);

	public int updateInformationField(InformationField informationField);

	public List<InformationField> selectInformationFields(List<Long> fieldIdList);
}
