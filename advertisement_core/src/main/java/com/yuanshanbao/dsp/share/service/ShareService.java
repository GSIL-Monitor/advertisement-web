package com.yuanshanbao.dsp.share.service;

import java.util.List;

import com.yuanshanbao.dsp.share.model.Share;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface ShareService {
	public List<Share> selectShare(Share share, PageBounds pageBounds);

	public Share selectShare(Long shareId);

	public void insertShare(Share share);

	public void deleteShare(Long shareId);

	public void updateInformation(Share share);

	public void insertOrUpdateShare(Share share);
}
