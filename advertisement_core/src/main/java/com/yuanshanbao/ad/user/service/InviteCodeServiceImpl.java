package com.yuanshanbao.ad.user.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.ad.core.CommonStatus;
import com.yuanshanbao.ad.user.dao.InviteCodeDao;
import com.yuanshanbao.ad.user.model.InviteCode;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class InviteCodeServiceImpl implements InviteCodeService {

	@Autowired
	private InviteCodeDao inviteCodeDao;

	@Override
	public List<InviteCode> selectInviteCode(InviteCode inviteCode, PageBounds pageBounds) {
		return inviteCodeDao.selectInviteCode(inviteCode, pageBounds);
	}

	@Override
	public void insertInviteCode(InviteCode inviteCode) {
		int result = -1;
		result = inviteCodeDao.insertInviteCode(inviteCode);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteInviteCode(InviteCode inviteCode) {
		int result = -1;

		result = inviteCodeDao.deleteInviteCode(inviteCode);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateInviteCode(InviteCode inviteCode) {
		int result = -1;
		result = inviteCodeDao.updateInviteCode(inviteCode);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public InviteCode selectInviteCode(String code) {
		InviteCode inviteCode = new InviteCode();
		inviteCode.setInviteCode(code);
		List<InviteCode> list = selectInviteCode(inviteCode, new PageBounds());
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public InviteCode validateInviteCode(String inviteCode) {
		InviteCode invite = null;
		if (StringUtils.isNotBlank(inviteCode)) {
			invite = selectInviteCode(inviteCode);
			if (invite == null || invite.getStatus() == null) {
				throw new BusinessException("inviteCode", ComRetCode.REGISTER_INVITE_CODE_ERROR);
			}
			if (invite.getStatus() == CommonStatus.OFFLINE) {
				throw new BusinessException("inviteCode", ComRetCode.REGISTER_INVITE_CODE_USED_ERROR);
			}
		}
		return invite;
	}

}
