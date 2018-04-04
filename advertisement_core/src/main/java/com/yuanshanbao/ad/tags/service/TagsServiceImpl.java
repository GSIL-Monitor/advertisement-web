package com.yuanshanbao.ad.tags.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.ad.tags.dao.TagsDao;
import com.yuanshanbao.ad.tags.model.Tags;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class TagsServiceImpl implements TagsService {

	@Autowired
	private TagsDao tagsDao;

	@Override
	public List<Tags> selectTags(Tags tags, PageBounds pageBounds) {
		return tagsDao.selectTags(tags, pageBounds);
	}

	@Override
	public Tags selectTags(Long tagsId) {
		Tags tags = new Tags();
		tags.setTagsId(tagsId);
		List<Tags> list = selectTags(tags, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertTags(Tags tags) {
		int result = -1;

		result = tagsDao.insertTags(tags);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteTags(Long tagsId) {
		Tags tags = new Tags();
		tags.setTagsId(tagsId);
		deleteTags(tags);
	}

	@Override
	public void deleteTags(Tags tags) {
		int result = -1;

		result = tagsDao.deleteTags(tags);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateTags(Tags tags) {
		int result = -1;

		result = tagsDao.updateTags(tags);

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
	public void insertOrUpdateTags(Tags tags) {
		if (tags.getTagsId() == null) {
			insertTags(tags);
		} else {
			updateTags(tags);
		}
	}

	@Override
	public Map<Long, Tags> selectTagsByIds(List<Long> tagsIds) {
		Map<Long, Tags> map = new HashMap<Long, Tags>();
		if (tagsIds == null || tagsIds.size() == 0) {
			return map;
		}
		List<Tags> list = tagsDao.selectTags(tagsIds);
		for (Tags tags : list) {
			map.put(tags.getTagsId(), tags);
		}
		return map;
	}

}
