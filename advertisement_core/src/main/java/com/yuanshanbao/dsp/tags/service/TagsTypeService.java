package com.yuanshanbao.dsp.tags.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.dsp.tags.model.TagsType;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface TagsTypeService {

	public List<TagsType> selectTagsType(TagsType tagsType, PageBounds pageBounds);

	public TagsType selectTagsType(Long tagsTypeId);

	public void insertTagsType(TagsType tagsType);

	public void deleteTagsType(Long tagsTypeId);

	public void deleteTagsType(TagsType tagsType);

	public void updateTagsType(TagsType tagsType);

	public void insertOrUpdateTagsType(TagsType TagsTtagsTypeype);

	public Map<Long, TagsType> selectTagsTypeByIds(List<Long> tagsTypeIds);

}
