package com.yuanshanbao.dsp.information.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.dsp.information.model.ExtendInfo;
import com.yuanshanbao.dsp.information.model.Information;
import com.yuanshanbao.dsp.page.model.Page;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface ExtendInfoService {

	public void insertExtendInfo(ExtendInfo extendInfo);

	public void updateExtendInfo(ExtendInfo extendInfo);

	public void deleteExtendInfo(ExtendInfo extendInfo);

	public List<ExtendInfo> selectExtendInfo(ExtendInfo extendInfo, PageBounds pageBounds);

	public Map<String, ExtendInfo> selectExtendInfo(Map<String, Object> params);

	public void insertOrUpdateExtendInfo(ExtendInfo extendInfo);

	public void saveExtendInfo(Map<String, String> parameterMap, Information information, Page page);

	public Map<String, String> getExtendInfoMap(Long userId, Long informationId);

	public Map<Long, List<ExtendInfo>> selectExtendInfoByInformationIds(List<Long> informationIds);

}
