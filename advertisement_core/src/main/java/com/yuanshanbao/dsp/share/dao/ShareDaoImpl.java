package com.yuanshanbao.dsp.share.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.share.model.Share;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class ShareDaoImpl extends BaseDaoImpl implements ShareDao {
	@Override
	public List<Share> selectShare(Share share, PageBounds pageBounds) {
		return getSqlSession().selectList("share.selectShare", share, pageBounds);
	}

	@Override
	public Share selectShare(Long shareId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertShare(Share share) {
		return getSqlSession().insert("share.insertShare", share);
	}

	@Override
	public int deleteShare(Long shareId) {
		return getSqlSession().delete("share.deleteShare", shareId);
	}

	@Override
	public int updateShare(Share share) {
		return getSqlSession().update("share.updateShare", share);

	}
}
