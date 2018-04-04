package com.yuanshanbao.ad.tags.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.ad.base.dao.BaseDaoImpl;
import com.yuanshanbao.ad.tags.model.TagsType;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class TagsTypeDaoImpl extends BaseDaoImpl implements TagsTypeDao {
	@Override
	public List<TagsType> selectTagsType(TagsType tagsType, PageBounds pageBounds) {
		return getSqlSession().selectList("tagsType.selectTagsType", tagsType, pageBounds);
	}

	@Override
	public int insertTagsType(TagsType TagsType) {
		return getSqlSession().insert("tagsType.insertTagsType", TagsType);
	}

	@Override
	public int deleteTagsType(TagsType TagsType) {
		return getSqlSession().delete("tagsType.deleteTagsType", TagsType);
	}

	@Override
	public int updateTagsType(TagsType TagsType) {
		return getSqlSession().update("tagsType.updateTagsType", TagsType);
	}

	@Override
	public List<TagsType> selectTagsType(List<Long> tagsTypeId) {
		if (tagsTypeId == null || tagsTypeId.size() == 0) {
			return new ArrayList<TagsType>();
		}
		return getSqlSession().selectList("tagsType.selectTagsTypeByIds", tagsTypeId);
	}

}
