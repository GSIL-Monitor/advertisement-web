package com.yuanshanbao.ad.user.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.ad.base.dao.BaseDaoImpl;
import com.yuanshanbao.ad.user.model.BaseInfo;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class UserBaseInfoDaoImpl extends BaseDaoImpl implements UserBaseInfoDao {

	@Override
	public int insertBaseInfo(BaseInfo baseInfo) {
		return getSqlSession().insert("User.insertBaseInfo", baseInfo);
	}

	@Override
	public int updateBaseInfo(BaseInfo baseInfo) {
		return getSqlSession().update("User.updateBaseInfo", baseInfo);
	}

	@Override
	public int deleteBaseInfo(String userId) {
		return getSqlSession().delete("User.deleteBaseInfo", userId);
	}

	@Override
	public List<BaseInfo> selectBaseInfo(BaseInfo baseInfo, PageBounds pageBounds) {
		return getSqlSession().selectList("User.selectBaseInfo", baseInfo, pageBounds);
	}

	@Override
	public List<BaseInfo> selectBaseInfoByIds(List<String> userIdList) {
		return getSqlSession().selectList("User.selectBaseInfoByIds", userIdList);
	}

}
