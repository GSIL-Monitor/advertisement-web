package com.yuanshanbao.ad.tags.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.ad.tags.model.Tags;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface TagsService {

	public List<Tags> selectTags(Tags tags, PageBounds pageBounds);

	public Tags selectTags(Long tagsId);

	public void insertTags(Tags tags);

	public void deleteTags(Long tagsId);

	public void deleteTags(Tags tags);

	public void updateTags(Tags tags);

	public void insertOrUpdateTags(Tags tags);

	public Map<Long, Tags> selectTagsByIds(List<Long> tagsIds);

}
