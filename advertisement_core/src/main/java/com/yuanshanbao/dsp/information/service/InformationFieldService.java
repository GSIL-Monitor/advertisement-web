package com.yuanshanbao.dsp.information.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.dsp.information.model.InformationField;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface InformationFieldService {

	public List<InformationField> selectInformationFields(InformationField informationField, PageBounds pageBounds);

	public InformationField selectInformationField(Long fieldId);

	public void insertInformationField(InformationField informationField);

	public void deleteInformationField(Long fieldId);

	public void updateInformationField(InformationField informationField);

	public void insertOrUpdateInformationField(InformationField informationField);

	public Map<Long, InformationField> selectInformationFieldByIds(List<Long> fieldIdList);

}
