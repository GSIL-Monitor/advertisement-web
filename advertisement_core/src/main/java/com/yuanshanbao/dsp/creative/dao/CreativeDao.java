package com.yuanshanbao.dsp.creative.dao;

import java.util.List;

import com.yuanshanbao.dsp.creative.model.Creative;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface CreativeDao {

	public List<Creative> selectCreatives(Creative creative, PageBounds pageBounds);

	public int insertCreative(Creative creative);

	public int deleteCreative(Creative creative);

	public int updateCreative(Creative creative);

}
