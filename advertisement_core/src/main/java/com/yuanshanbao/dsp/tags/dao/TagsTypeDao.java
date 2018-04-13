package com.yuanshanbao.dsp.tags.dao;

import java.util.List;

import com.yuanshanbao.dsp.tags.model.TagsType;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface TagsTypeDao {

	public List<TagsType> selectTagsType(TagsType tagsType, PageBounds pageBounds);

	public int insertTagsType(TagsType tagsType);

	public int deleteTagsType(TagsType tagsType);

	public int updateTagsType(TagsType tagsType);
	
	public List<TagsType> selectTagsType(List<Long> tagsTypeId);
}
