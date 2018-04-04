package com.yuanshanbao.ad.tags.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.ad.base.dao.BaseDaoImpl;
import com.yuanshanbao.ad.tags.model.Tags;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class TagsDaoImpl extends BaseDaoImpl implements TagsDao {
	@Override
	public List<Tags> selectTags(Tags tags, PageBounds pageBounds) {
		return getSqlSession().selectList("tags.selectTags", tags, pageBounds);
	}

	@Override
	public int insertTags(Tags tags) {
		return getSqlSession().insert("tags.insertTags", tags);
	}

	@Override
	public int deleteTags(Tags tags) {
		return getSqlSession().delete("tags.deleteTags", tags);
	}

	@Override
	public int updateTags(Tags tags) {
		return getSqlSession().update("tags.updateTags", tags);
	}

	@Override
	public List<Tags> selectTags(List<Long> tagsId) {
		if (tagsId == null || tagsId.size() == 0) {
			return new ArrayList<Tags>();
		}
		return getSqlSession().selectList("tags.selectTagsByIds", tagsId);
	}

}
