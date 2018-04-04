package com.yuanshanbao.ad.tags.dao;

import java.util.List;

import com.yuanshanbao.ad.tags.model.Tags;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface TagsDao {

	public List<Tags> selectTags(Tags tags, PageBounds pageBounds);

	public int insertTags(Tags tags);

	public int deleteTags(Tags tags);

	public int updateTags(Tags tags);
	
	public List<Tags> selectTags(List<Long> tagsId);
}
