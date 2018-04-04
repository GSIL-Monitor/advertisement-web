package com.yuanshanbao.ad.article.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.ad.article.dao.ArticleDao;
import com.yuanshanbao.ad.article.model.Article;
import com.yuanshanbao.ad.article.model.ArticleSection;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleDao articleDao;

	@Autowired
	private ArticleSectionService sectionService;

	@Override
	public void insertArticle(Article article) {
		int result = -1;

		result = articleDao.insertArticle(article);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);

		}
	}

	@Override
	public void updateArticle(Article article) {
		int result = -1;

		result = articleDao.updateArticle(article);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);

		}
	}

	@Override
	public void deleteArticle(Article article) {
		int result = -1;

		result = articleDao.deleteArticle(article);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);

		}
	}

	@Override
	public List<Article> selectArticles(Article article, PageBounds pageBounds) {
		return setProperty(articleDao.selectArticles(article, pageBounds));
	}
	
	private List<Article> setProperty(List<Article> articleList) {
		List<Long> articleIds = new ArrayList<Long>();
		for (Article po : articleList) {
			articleIds.add(po.getArticleId());
		}
		Map<Long, List<ArticleSection>> map = sectionService.selectArticleSectionByArticleIds(articleIds);
		for (Article po : articleList) {
			po.setSectionList(map.get(po.getArticleId()));
		}
		return articleList;
	}

	@Override
	public Article selectArticleById(Long articleId) {
		if (articleId == null) {
			return null;
		}
		Article article = new Article();
		article.setArticleId(articleId);
		List<Article> list = selectArticles(article, new PageBounds());

		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
