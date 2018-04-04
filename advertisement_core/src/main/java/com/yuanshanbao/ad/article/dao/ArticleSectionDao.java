package com.yuanshanbao.ad.article.dao;

import java.util.List;

import com.yuanshanbao.ad.article.model.ArticleSection;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface ArticleSectionDao {

	public int insertArticleSection(ArticleSection articleSection);
	
	public int updateArticleSection(ArticleSection articleSection);
	
	public int deleteArticleSection(ArticleSection articleSection);
	
	public List<ArticleSection> selectArticleSections(ArticleSection articleSection, PageBounds pageBounds);
	
	public List<ArticleSection> selectArticleSectionByArticleIds(List<Long> articleIds);
	
}
