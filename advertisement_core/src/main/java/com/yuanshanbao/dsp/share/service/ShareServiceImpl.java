package com.yuanshanbao.dsp.share.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.share.dao.ShareDao;
import com.yuanshanbao.dsp.share.model.Share;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class ShareServiceImpl implements ShareService {
	@Autowired
	private ShareDao shareDao;

	@Override
	public List<Share> selectShare(Share share, PageBounds pageBounds) {
		return shareDao.selectShare(share, pageBounds);
	}

	@Override
	public Share selectShare(Long shareId) {
		Share share = new Share();
		if (shareId == null) {
			return null;
		}
		share.setShareId(shareId);
		List<Share> list = shareDao.selectShare(share, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertShare(Share share) {
		int result = -1;

		result = shareDao.insertShare(share);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public void insertOrUpdateShare(Share share) {
		int result = -1;
		List<Share> list = selectShare(share, new PageBounds());
		Share existShare = new Share();
		if (list.size() > 0) {
			existShare = list.get(0);
			share.setShareId(existShare.getShareId());
			result = shareDao.updateShare(share);
		} else {
			result = shareDao.insertShare(share);
		}
		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteShare(Long shareId) {
		int result = -1;

		result = shareDao.deleteShare(shareId);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateInformation(Share share) {
		int result = -1;

		result = shareDao.updateShare(share);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}
}
