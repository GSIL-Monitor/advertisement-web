package com.yuanshanbao.ad.article.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.ad.article.dao.ArticleSectionDao;
import com.yuanshanbao.ad.article.model.ArticleSection;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class ArticleSectionServiceImpl implements ArticleSectionService {

	@Autowired
	private ArticleSectionDao sectionDao;

	@Override
	public void insertArticleSection(ArticleSection articleSection) {
		int result = -1;

		result = sectionDao.insertArticleSection(articleSection);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);

		}
	}

	@Override
	public void updateArticleSection(ArticleSection articleSection) {
		int result = -1;

		result = sectionDao.updateArticleSection(articleSection);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);

		}
	}

	@Override
	public void deleteArticleSection(ArticleSection articleSection) {
		int result = -1;

		result = sectionDao.deleteArticleSection(articleSection);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);

		}
	}

	@Override
	public List<ArticleSection> selectArticleSection(ArticleSection articleSection, PageBounds pageBounds) {
		return sectionDao.selectArticleSections(articleSection, pageBounds);
	}

	@Override
	public ArticleSection selectArticleSectionById(Long sectionId) {
		if (sectionId == null) {
			return null;
		}
		ArticleSection srticleSection = new ArticleSection();
		srticleSection.setSectionId(sectionId);
		List<ArticleSection> list = selectArticleSection(srticleSection, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<Long, List<ArticleSection>> selectArticleSectionByArticleIds(List<Long> articleIds) {
		Map<Long, List<ArticleSection>> map = new HashMap<Long, List<ArticleSection>>();
		if (articleIds == null || articleIds.size() == 0) {
			return map;
		}
		List<ArticleSection> sections = sectionDao.selectArticleSectionByArticleIds(articleIds);
		for (ArticleSection section : sections) {
			List<ArticleSection> existSections = map.get(section.getArticleId());
			if (existSections == null) {
				existSections = new ArrayList<ArticleSection>();
			}
			existSections.add(section);
		}
		return map;
	}

}
