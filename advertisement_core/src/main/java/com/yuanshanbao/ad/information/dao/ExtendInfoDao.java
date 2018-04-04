package com.yuanshanbao.ad.information.dao;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.ad.information.model.ExtendInfo;

public interface ExtendInfoDao {

	public int insertExtendInfo(ExtendInfo extendInfo);

	public int updateExtendInfo(ExtendInfo extendInfo);

	public int deleteExtendInfo(ExtendInfo extendInfo);

	public List<ExtendInfo> selectExtendInfo(ExtendInfo extendInfo, PageBounds pageBounds);

	public List<ExtendInfo> selectExtendInfoById(Map<String, Object> params);

	public List<ExtendInfo> selectExtendInfoByInformationIds(List<Long> insurantIds);

}
