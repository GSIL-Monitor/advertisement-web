package com.yuanshanbao.ms.controller.article;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yuanshanbao.ad.article.model.ArticleSection;
import com.yuanshanbao.ad.article.model.ArticleSectionStatus;
import com.yuanshanbao.ad.article.model.ArticleStatus;
import com.yuanshanbao.ad.article.service.ArticleSectionService;
import com.yuanshanbao.ad.core.CommonStatus;
import com.yuanshanbao.ad.core.InterfaceRetCode;
import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.UploadUtils;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/article/section")
public class AdminArticleSectionController extends PaginationController {

	private static final String PAGE_LIST = "advertisement/article/section/listSection";

	private static final String PAGE_INSERT = "advertisement/article/section/insertSection";

	private static final String PAGE_UPDATE = "advertisement/article/section/updateSection";

	private static final String PAGE_DETAIL = "advertisement/article/section/detailSection";

	private static final String PAGE_COMPLEX = "advertisement/article/section/complexSection";

	private static final String PAGE_EDITOR = "advertisement/article/section/editorSection";

	@Autowired
	private ArticleSectionService sectionService;

	@RequestMapping("/list.do")
	public String list(String articleId, HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("articleId", articleId);
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, ArticleSection articleSection, HttpServletRequest request,
			HttpServletResponse response) {
		Object object = sectionService.selectArticleSection(articleSection, getPageBounds(range, request));
		return setPageInfo(request, response, (PageList) object);
	}

	@RequestMapping("/insertWindow.do")
	public String insertWindow(String articleId, HttpServletRequest request, HttpServletResponse response) {
		setProperty(request);
		request.setAttribute("articleId", articleId);
		return PAGE_INSERT;
	}

	private void setProperty(HttpServletRequest request) {
		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());
		request.setAttribute("typeList", ArticleSectionStatus.getButtonDescriptionMap().entrySet());
		request.setAttribute("descList", ArticleStatus.getContentTypeDescription().entrySet());
		// request.setAttribute("categoryList",
		// ArticleStatus.getCategoryDescription().entrySet());
	}

	@RequestMapping("/complexWindow.do")
	public String complexWindow(String articleId, HttpServletRequest request, HttpServletResponse response) {
		setProperty(request);
		request.setAttribute("articleId", articleId);
		return PAGE_COMPLEX;
	}

	@ResponseBody
	@RequestMapping("/insert.do")
	public Object insert(ArticleSection articleSection, @RequestParam("image") MultipartFile image,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (!image.isEmpty()) {
				articleSection.setImageUrl(UploadUtils.uploadFile(image, UploadUtils.FTP_COMMON_FILE));
			}
			sectionService.insertArticleSection(articleSection);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (IOException e2) {
			LoggerUtil.error("insert article section error: ", e2);
		}

		return result;
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(Long sectionId, HttpServletRequest request, HttpServletResponse response) {
		setProperty(request);
		ArticleSection section = sectionService.selectArticleSectionById(sectionId);
		request.setAttribute("section", section);
		return PAGE_UPDATE;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object update(ArticleSection articleSection, @RequestParam("image") MultipartFile image,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (!image.isEmpty()) {
				articleSection.setImageUrl(UploadUtils.uploadFile(image, UploadUtils.FTP_COMMON_FILE));
			}
			sectionService.updateArticleSection(articleSection);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (IOException e2) {
			LoggerUtil.error("update article section error: ", e2);
		}

		return result;
	}

	@ResponseBody
	@RequestMapping("delete.do")
	public Object update(Long sectionId, int status, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			if (sectionId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}

			ArticleSection articleSection = new ArticleSection();
			articleSection.setSectionId(sectionId);
			articleSection.setStatus(status);
			sectionService.updateArticleSection(articleSection);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}

	@RequestMapping("/detail.do")
	public String see(Long sectionId, HttpServletRequest request, HttpServletResponse response) {
		ArticleSection section = sectionService.selectArticleSectionById(sectionId);
		request.setAttribute("section", section);
		return PAGE_DETAIL;
	}

	@RequestMapping("/editor.do")
	public String editor(Long sectionId, HttpServletRequest request, HttpServletResponse response) {
		if (sectionId != null) {
			ArticleSection section = sectionService.selectArticleSectionById(sectionId);
			request.setAttribute("section", section);
		}
		return PAGE_EDITOR;
	}

}
