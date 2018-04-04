package com.yuanshanbao.ad.article.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.ad.article.model.ArticleSection;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface ArticleSectionService {

	public void insertArticleSection(ArticleSection articleSection);
	
	public void updateArticleSection(ArticleSection articleSection);
	
	public void deleteArticleSection(ArticleSection articleSection);
	
	public List<ArticleSection> selectArticleSection(ArticleSection articleSection, PageBounds pageBounds);
	
	public ArticleSection selectArticleSectionById(Long sectionId);
	
	public Map<Long, List<ArticleSection>> selectArticleSectionByArticleIds(List<Long> articleIds);
	
}
