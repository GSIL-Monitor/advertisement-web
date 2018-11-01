package com.yuanshanbao.dsp.user.dao;

import java.util.List;

import com.yuanshanbao.dsp.user.model.BaseInfo;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface UserBaseInfoDao {

	public int insertBaseInfo(BaseInfo baseInfo);

	public int updateBaseInfo(BaseInfo baseInfo);

	public int deleteBaseInfo(String userId);

	public List<BaseInfo> selectBaseInfo(BaseInfo baseInfo, PageBounds pageBounds);

	public List<BaseInfo> selectBaseInfoByIds(List<String> userIdList);

}