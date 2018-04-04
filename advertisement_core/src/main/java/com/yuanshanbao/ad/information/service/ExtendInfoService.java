package com.yuanshanbao.ad.information.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.ad.information.model.ExtendInfo;
import com.yuanshanbao.ad.information.model.Information;
import com.yuanshanbao.ad.page.model.Page;

public interface ExtendInfoService {

	public void insertExtendInfo(ExtendInfo extendInfo);

	public void updateExtendInfo(ExtendInfo extendInfo);

	public void deleteExtendInfo(ExtendInfo extendInfo);

	public List<ExtendInfo> selectExtendInfo(ExtendInfo extendInfo, PageBounds pageBounds);

	public Map<String, ExtendInfo> selectExtendInfo(Map<String, Object> params);

	public void insertOrUpdateExtendInfo(ExtendInfo extendInfo);

	public void saveExtendInfo(Map<String, String> parameterMap, Information information, Page page);

	public Map<String, String> getExtendInfoMap(String userId, Long informationId);

	public Map<Long, List<ExtendInfo>> selectExtendInfoByInformationIds(List<Long> informationIds);

}
