package com.yuanshanbao.dsp.user.dao;

import java.util.List;

import com.yuanshanbao.dsp.base.shard.TableShard;
import com.yuanshanbao.dsp.common.constant.CommonConstant;
import com.yuanshanbao.dsp.user.model.BaseInfo;
import com.yuanshanbao.paginator.domain.PageBounds;

@TableShard(tableName = CommonConstant.USER_BASE_INFO_TABLE_NAME, shardType = CommonConstant.USER_SHARD_TYPE, shardBy = {
		CommonConstant.USER_SHARD_BY_ID, CommonConstant.USER_SHARD_BY_MOBILE })
public interface UserBaseInfoDao {

	public int insertBaseInfo(BaseInfo baseInfo);

	public int updateBaseInfo(BaseInfo baseInfo);

	public int deleteBaseInfo(String userId);

	public List<BaseInfo> selectBaseInfo(BaseInfo baseInfo, PageBounds pageBounds);

	public List<BaseInfo> selectBaseInfoByIds(List<String> userIdList);

}