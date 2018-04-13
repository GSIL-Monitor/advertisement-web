package com.yuanshanbao.dsp.controller.article;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.sd4324530.fastweixin.api.response.GetSignatureResponse;
import com.yuanshanbao.dsp.article.model.Article;
import com.yuanshanbao.dsp.article.model.ArticleStatus;
import com.yuanshanbao.dsp.article.service.ArticleService;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.weixin.service.WeixinService;
import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.RequestUtil;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping({ "/article", "/m/article", "/i/article" })
public class ArticleController extends BaseController {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private WeixinService weixinService;

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest request, Integer category, PageBounds pageBounds,
			HttpServletResponse response, ModelMap modelMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Article article = new Article();
			article.setCategory(category);
			if (category == null) {
				//article.setCategory(ArticleStatus.LOTTERY_NEWS);
			}
			PageList<Article> articleList = (PageList<Article>) articleService.selectArticles(article,
					formatPageBounds(pageBounds));
			resultMap.put("title", ArticleStatus.getCategoryDescription(category));
			resultMap.put("articleList", articleList);
			resultMap.put(ComRetCode.PAGINTOR, articleList.getPaginator());
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[list error:]", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	@ResponseBody
	@RequestMapping("/detail")
	public Object poster(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Long articleId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (articleId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			Article article = articleService.selectArticleById(articleId);
			if (article == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}

			GetSignatureResponse jsSignature = weixinService.getJSSignature(RequestUtil.getCompleteRequestUrl(request));
			if (jsSignature != null) {
				resultMap.put("appId", weixinService.getAppId(WeixinService.CONFIG_SERVICE));
				resultMap.put("jsSignature", jsSignature);
			}

			resultMap.put("isIos", isIos(request, article));
			resultMap.put("article", article);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[detail error: " + articleId + "]", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	public String isIos(HttpServletRequest request, Article article) {
		String isIos = "0";
		String agent = request.getHeader("User-Agent").toLowerCase();
		if (agent.indexOf("yuanshan_ios") >= 0) {
			if (StringUtils.isNotBlank(article.getButtonLink())) {
				if (article.getButtonLink().toLowerCase().indexOf("yuanshan://") >= 0) {
					isIos = "1";
				}
			}
		}
		return isIos;
	}

}
