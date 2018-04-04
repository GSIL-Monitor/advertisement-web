package com.yuanshanbao.ad.article.service;

import java.util.List;

import com.yuanshanbao.ad.article.model.Article;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface ArticleService {

	public void insertArticle(Article article);
	
	public void updateArticle(Article article);
	
	public void deleteArticle(Article article);
	
	public List<Article> selectArticles(Article article, PageBounds pageBounds);
	
	public Article selectArticleById(Long articleId);
}
