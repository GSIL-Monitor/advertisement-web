package com.yuanshanbao.ad.user.service;

import java.util.List;

import com.yuanshanbao.ad.user.model.InviteCode;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface InviteCodeService {

	public List<InviteCode> selectInviteCode(InviteCode inviteCode, PageBounds pageBounds);

	public void insertInviteCode(InviteCode inviteCode);

	public void deleteInviteCode(InviteCode inviteCode);

	public void updateInviteCode(InviteCode inviteCode);

	public InviteCode selectInviteCode(String inviteCode);

	public InviteCode validateInviteCode(String inviteCode);

}
