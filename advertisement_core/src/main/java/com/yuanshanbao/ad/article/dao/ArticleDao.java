package com.yuanshanbao.ad.article.dao;

import java.util.List;

import com.yuanshanbao.ad.article.model.Article;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface ArticleDao {

	public int insertArticle(Article article);
	
	public int updateArticle(Article article);
	
	public int deleteArticle(Article article);
	
	public List<Article> selectArticles(Article article, PageBounds pageBounds);
	
}
