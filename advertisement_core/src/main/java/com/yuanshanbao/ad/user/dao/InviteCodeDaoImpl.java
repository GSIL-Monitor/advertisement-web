package com.yuanshanbao.ad.user.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.ad.base.dao.BaseDaoImpl;
import com.yuanshanbao.ad.user.model.InviteCode;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class InviteCodeDaoImpl extends BaseDaoImpl implements InviteCodeDao {
	@Override
	public List<InviteCode> selectInviteCode(InviteCode inviteCode, PageBounds pageBounds) {
		return getSqlSession().selectList("inviteCode.selectInviteCode", inviteCode, pageBounds);
	}

	@Override
	public int insertInviteCode(InviteCode inviteCode) {
		return getSqlSession().insert("inviteCode.insertInviteCode", inviteCode);
	}

	@Override
	public int deleteInviteCode(InviteCode inviteCode) {
		return getSqlSession().delete("inviteCode.deleteInviteCode", inviteCode);
	}

	@Override
	public int updateInviteCode(InviteCode inviteCode) {
		return getSqlSession().update("inviteCode.updateInviteCode", inviteCode);
	}

}
