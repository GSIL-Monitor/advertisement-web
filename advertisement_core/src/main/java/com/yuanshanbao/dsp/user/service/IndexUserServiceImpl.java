package com.yuanshanbao.dsp.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.user.dao.IndexUserDao;
import com.yuanshanbao.dsp.user.model.IndexUser;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class IndexUserServiceImpl implements IndexUserService {

	@Autowired
	private IndexUserDao indexUserDao;

	@Override
	public List<IndexUser> selectIndexUsers(IndexUser indexUser, PageBounds pageBounds) {
		return indexUserDao.selectIndexUsers(indexUser, pageBounds);
	}

	@Override
	public IndexUser selectIndexUser(String openId) {
		IndexUser indexUser = new IndexUser();
		if (openId == null) {
			return null;
		}
		indexUser.setOpenId(openId);
		List<IndexUser> list = selectIndexUsers(indexUser, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertIndexUser(IndexUser indexUser) {
		int result = -1;

		result = indexUserDao.insertIndexUser(indexUser);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

}
