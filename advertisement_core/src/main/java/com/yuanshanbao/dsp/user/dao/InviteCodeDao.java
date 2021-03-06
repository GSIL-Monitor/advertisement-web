package com.yuanshanbao.dsp.user.dao;

import java.util.List;

import com.yuanshanbao.dsp.user.model.InviteCode;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface InviteCodeDao {

	public List<InviteCode> selectInviteCode(InviteCode inviteCode, PageBounds pageBounds);

	public int insertInviteCode(InviteCode inviteCode);

	public int deleteInviteCode(InviteCode inviteCode);

	public int updateInviteCode(InviteCode inviteCode);

}
