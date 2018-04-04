package com.yuanshanbao.ad.information.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.ad.information.model.FieldConstrains;
import com.yuanshanbao.ad.information.model.InformationField;

public interface FieldConstrainsService {

	public List<FieldConstrains> selectFieldConstrains(FieldConstrains fieldConstrains, PageBounds pageBounds);

	public FieldConstrains selectFieldConstrains(Long constrainsId);

	public void insertFieldConstrains(FieldConstrains fieldConstrains);

	public void deleteFieldConstrains(Long fieldId);

	public void updateFieldConstrains(FieldConstrains fieldConstrains);

	public Map<Long, FieldConstrains> selectFieldConstrainsByIds(List<Long> constrainsIdList);

	public void checkField(List<FieldConstrains> fieldConstrains, InformationField field, Map<String, String> parameterMap);

}
