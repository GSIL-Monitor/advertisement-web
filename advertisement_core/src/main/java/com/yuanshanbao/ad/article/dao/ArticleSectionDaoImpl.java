package com.yuanshanbao.ad.article.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.ad.article.model.ArticleSection;
import com.yuanshanbao.ad.base.dao.BaseDaoImpl;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class ArticleSectionDaoImpl extends BaseDaoImpl implements ArticleSectionDao {

	@Override
	public int insertArticleSection(ArticleSection articleSection) {
		return getSqlSession().insert("ArticleSection.insertArticleSection", articleSection);
	}

	@Override
	public int updateArticleSection(ArticleSection articleSection) {
		return getSqlSession().update("ArticleSection.updateArticleSection", articleSection);
	}

	@Override
	public int deleteArticleSection(ArticleSection articleSection) {
		return getSqlSession().delete("ArticleSection.deleteArticleSection", articleSection);
	}

	@Override
	public List<ArticleSection> selectArticleSections(ArticleSection articleSection, PageBounds pageBounds) {
		return getSqlSession().selectList("ArticleSection.selectArticleSections", articleSection, pageBounds);
	}

	@Override
	public List<ArticleSection> selectArticleSectionByArticleIds(List<Long> articleIds) {
		if (articleIds == null || articleIds.size() == 0) {
			return new ArrayList<ArticleSection>();
		}
		return getSqlSession().selectList("ArticleSection.selectArticleSectionByArticleIds", articleIds);
	}
	
}
