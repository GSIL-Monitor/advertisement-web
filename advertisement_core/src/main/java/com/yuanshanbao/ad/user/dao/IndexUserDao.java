package com.yuanshanbao.ad.user.dao;

import java.util.List;

import com.yuanshanbao.ad.base.shard.TableShard;
import com.yuanshanbao.ad.common.constant.CommonConstant;
import com.yuanshanbao.ad.user.model.IndexUser;
import com.yuanshanbao.paginator.domain.PageBounds;

@TableShard(tableName = CommonConstant.INDEX_USER_TABLE_NAME, shardType = CommonConstant.INDEX_USER_SHARD_TYPE, shardBy = CommonConstant.INDEX_USER_SHARD_BY)
public interface IndexUserDao {

	public List<IndexUser> selectIndexUsers(IndexUser indexUser, PageBounds pageBounds);

	public int insertIndexUser(IndexUser indexUser);

}
