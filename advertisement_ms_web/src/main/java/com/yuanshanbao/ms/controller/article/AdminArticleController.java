package com.yuanshanbao.ms.controller.article;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yuanshanbao.dsp.article.model.Article;
import com.yuanshanbao.dsp.article.model.ArticleStatus;
import com.yuanshanbao.dsp.article.service.ArticleService;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.UploadUtils;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/article")
public class AdminArticleController extends PaginationController {

	private static final String PAGE_LIST = "advertisement/article/listArticle";

	private static final String PAGE_INSERT = "advertisement/article/insertArticle";

	private static final String PAGE_UPDATE = "advertisement/article/updateArticle";

	private static final String PAGE_DETAIL = "advertisement/article/detailArticle";

	private static final String PAGE_EDITOR = "advertisement/article/editorArticle";

	private static final String PAGE_SIMPLE = "advertisement/article/simpleArticle";

	@Autowired
	private ArticleService articleService;

	@RequestMapping("/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, Article article, HttpServletRequest request, HttpServletResponse response) {
		Object object = articleService.selectArticles(article, getPageBounds(range, request));
		return setPageInfo(request, response, (PageList) object);
	}

	@RequestMapping("/insertWindow.do")
	public String insertWindow(HttpServletRequest request, HttpServletResponse response) {
		setProperty(request);
		return PAGE_INSERT;
	}

	@RequestMapping("/simpleArticle.do")
	public String simple(HttpServletRequest request, HttpServletResponse response) {
		setProperty(request);
		return PAGE_SIMPLE;
	}

	private void setProperty(HttpServletRequest request) {
		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());
		request.setAttribute("typeList", ArticleStatus.getButtonDescriptionMap().entrySet());
		request.setAttribute("descList", ArticleStatus.getContentTypeDescription().entrySet());
		request.setAttribute("categorysList", ArticleStatus.getCategoryDescription().entrySet());
	}

	@ResponseBody
	@RequestMapping("/insert.do")
	public Object insert(Article article, @RequestParam("image") MultipartFile image,
			@RequestParam("weixin") MultipartFile weixin, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (!image.isEmpty()) {
				// throw new
				// BusinessException(ComRetCode.ACTIVITY_IMAGEURL_FORMAT_ERROR);
				article.setImageUrl(UploadUtils.uploadFile(image, UploadUtils.FTP_COMMON_FILE));
			}
			if (!weixin.isEmpty()) {
				article.setWeixinUrl(UploadUtils.uploadFile(weixin, UploadUtils.FTP_COMMON_FILE));
			}
			validateParameters(article);// 微信分享图，分享语，分享描述必须有

			articleService.insertArticle(article);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (IOException e2) {
			LoggerUtil.error("insert article error: ", e2);
		}

		return result;
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(Long articleId, HttpServletRequest request, HttpServletResponse response) {
		Article article = articleService.selectArticleById(articleId);
		setProperty(request);
		request.setAttribute("article", article);
		return PAGE_UPDATE;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object update(Article article, @RequestParam("image") MultipartFile image, String desc,
			@RequestParam("weixin") MultipartFile weixin, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (!image.isEmpty()) {
				article.setImageUrl(UploadUtils.uploadFile(image, UploadUtils.FTP_COMMON_FILE));
			}
			if (!weixin.isEmpty()) {
				article.setWeixinUrl(UploadUtils.uploadFile(weixin, UploadUtils.FTP_COMMON_FILE));
			}
			if (StringUtils.isNotBlank(desc)) {
				article.setContent(desc);
			}
			validateParameters(article);// 微信分享图，分享语，分享描述必须有

			articleService.updateArticle(article);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (IOException e2) {
			LoggerUtil.error("insert article error: ", e2);
		}

		return result;
	}

	@ResponseBody
	@RequestMapping("delete.do")
	public Object update(Long articleId, int status, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			if (articleId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}

			Article article = new Article();
			article.setArticleId(articleId);
			article.setStatus(status);
			articleService.updateArticle(article);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}

	@RequestMapping("/detail.do")
	public String see(Long articleId, HttpServletRequest request, HttpServletResponse response) {
		Article article = articleService.selectArticleById(articleId);
		request.setAttribute("article", article);
		return PAGE_DETAIL;
	}

	@RequestMapping("/editor.do")
	public String editor(Long articleId, HttpServletRequest request, HttpServletResponse response) {
		if (articleId != null) {
			Article article = articleService.selectArticleById(articleId);
			request.setAttribute("article", article);
		}
		return PAGE_EDITOR;
	}

}
