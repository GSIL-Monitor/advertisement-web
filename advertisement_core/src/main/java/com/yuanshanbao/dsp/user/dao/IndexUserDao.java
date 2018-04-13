package com.yuanshanbao.dsp.user.dao;

import java.util.List;

import com.yuanshanbao.dsp.base.shard.TableShard;
import com.yuanshanbao.dsp.common.constant.CommonConstant;
import com.yuanshanbao.dsp.user.model.IndexUser;
import com.yuanshanbao.paginator.domain.PageBounds;

@TableShard(tableName = CommonConstant.INDEX_USER_TABLE_NAME, shardType = CommonConstant.INDEX_USER_SHARD_TYPE, shardBy = CommonConstant.INDEX_USER_SHARD_BY)
public interface IndexUserDao {

	public List<IndexUser> selectIndexUsers(IndexUser indexUser, PageBounds pageBounds);

	public int insertIndexUser(IndexUser indexUser);

}
