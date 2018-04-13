package com.yuanshanbao.dsp.user.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.user.model.IndexUser;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class IndexUserDaoImpl extends BaseDaoImpl implements IndexUserDao {
	
	@Override
	public List<IndexUser> selectIndexUsers(IndexUser indexUser, PageBounds pageBounds) {
		return getSqlSession().selectList("IndexUser.selectIndexUser", indexUser, pageBounds);
	}

	@Override
	public int insertIndexUser(IndexUser indexUser) {
		return getSqlSession().insert("IndexUser.insertIndexUser", indexUser);
	}

}
