package com.yuanshanbao.dsp.creative.service;

import java.util.List;

import com.yuanshanbao.dsp.creative.model.Creative;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface CreativeService {

	public void insertCreative(Creative creative);

	public void updateCreative(Creative creative);

	public void deleteCreative(Creative creative);

	public List<Creative> selectCreative(Creative creative, PageBounds pageBounds);

	public Creative selectCreative(Long creativeId);

	public List<Long> selectCreativeIds(Creative creative);

}
