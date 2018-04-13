package com.yuanshanbao.dsp.tags.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.tags.dao.TagsTypeDao;
import com.yuanshanbao.dsp.tags.model.TagsType;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class TagsTypeServiceImpl implements TagsTypeService {

	@Autowired
	private TagsTypeDao tagsTypeDao;

	@Override
	public List<TagsType> selectTagsType(TagsType tagsType, PageBounds pageBounds) {
		return tagsTypeDao.selectTagsType(tagsType, pageBounds);
	}

	@Override
	public TagsType selectTagsType(Long tagsTypeId) {
		TagsType tagsType = new TagsType();
		tagsType.setTypeId(tagsTypeId);
		List<TagsType> list = selectTagsType(tagsType, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertTagsType(TagsType tagsType) {
		int result = -1;

		result = tagsTypeDao.insertTagsType(tagsType);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteTagsType(Long tagsTypeId) {
		TagsType tagsType = new TagsType();
		tagsType.setTypeId(tagsTypeId);
		deleteTagsType(tagsType);
	}

	@Override
	public void deleteTagsType(TagsType tagsType) {
		int result = -1;

		result = tagsTypeDao.deleteTagsType(tagsType);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateTagsType(TagsType tagsType) {
		int result = -1;

		result = tagsTypeDao.updateTagsType(tagsType);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	/**
	 * @param tags
	 * @return
	 * @throws IOException
	 */
	@Override
	public void insertOrUpdateTagsType(TagsType tagsType) {
		if (tagsType.getTypeId() == null) {
			insertTagsType(tagsType);
		} else {
			updateTagsType(tagsType);
		}
	}

	@Override
	public Map<Long, TagsType> selectTagsTypeByIds(List<Long> tagsTypeIds) {
		Map<Long, TagsType> map = new HashMap<Long, TagsType>();
		if (tagsTypeIds == null || tagsTypeIds.size() == 0) {
			return map;
		}
		List<TagsType> list = tagsTypeDao.selectTagsType(tagsTypeIds);
		for (TagsType tags : list) {
			map.put(tags.getTypeId(), tags);
		}
		return map;
	}

}
