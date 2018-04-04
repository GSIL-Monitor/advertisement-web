package com.yuanshanbao.ad.article.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.ad.article.model.Article;
import com.yuanshanbao.ad.base.dao.BaseDaoImpl;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class ArticleDaoImpl extends BaseDaoImpl implements ArticleDao {

	@Override
	public int insertArticle(Article article) {
		return getSqlSession().insert("Article.insertArticle", article);
	}

	@Override
	public int updateArticle(Article article) {
		return getSqlSession().update("Article.updateArticle", article);
	}

	@Override
	public int deleteArticle(Article article) {
		return getSqlSession().delete("Article.deleteArticle", article);
	}

	@Override
	public List<Article> selectArticles(Article article, PageBounds pageBounds) {
		return getSqlSession().selectList("Article.selectArticles", article, pageBounds);
	}

}
