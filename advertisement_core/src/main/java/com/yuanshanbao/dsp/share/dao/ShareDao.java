package com.yuanshanbao.dsp.share.dao;

import java.util.List;

import com.yuanshanbao.dsp.share.model.Share;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface ShareDao {
	public List<Share> selectShare(Share share, PageBounds pageBounds);

	public Share selectShare(Long shareId);

	public int insertShare(Share share);

	public int deleteShare(Long shareId);

	public int updateShare(Share share);
}
