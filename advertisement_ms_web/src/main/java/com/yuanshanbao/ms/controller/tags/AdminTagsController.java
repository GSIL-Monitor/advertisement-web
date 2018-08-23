package com.yuanshanbao.ms.controller.tags;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.UploadUtils;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.tags.model.Tags;
import com.yuanshanbao.dsp.tags.model.TagsType;
import com.yuanshanbao.dsp.tags.service.TagsService;
import com.yuanshanbao.dsp.tags.service.TagsTypeService;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.ms.controller.common.AdminServerController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/tags")
public class AdminTagsController extends PaginationController {
	private static final String PAGE_LIST = "advertisement/tags/listTags";

	private static final String PAGE_INSERT = "advertisement/tags/insertTags";

	private static final String PAGE_EDIT = "advertisement/tags/updateTags";

	private static final String PAGE_DETAIL = "advertisement/tags/viewTags";

	@Autowired
	private TagsService tagsService;

	@Autowired
	private TagsTypeService tagsTypeService;

	@RequestMapping("/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("typeList", ConstantsManager.getTagsTypeMap().values());
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/query.do")
	public Object query(String range, Tags tags, HttpServletRequest request, HttpServletResponse response) {
		Object result = tagsService.selectTags(tags, getPageBounds(range, request));
		PageList pageList = (PageList) result;
		return setPageInfo(request, response, pageList);
	}

	@ResponseBody
	@RequestMapping("/delete.do")
	public Object delete(HttpServletRequest request, HttpServletResponse response, Long tagsId) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			Tags tags = new Tags();
			tags.setStatus(CommonStatus.OFFLINE);
			tags.setTagsId(tagsId);
			tagsService.updateTags(tags);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}

	@RequestMapping("/insertWindow.do")
	public String insertWindow(HttpServletRequest request, HttpServletResponse response) {
		setProperty(request);
		return PAGE_INSERT;
	}

	private void setProperty(HttpServletRequest request) {
		TagsType tagsType = new TagsType();
		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());
		request.setAttribute("typeList", tagsTypeService.selectTagsType(tagsType, new PageBounds()));
	}

	@ResponseBody
	@RequestMapping("/insert.do")
	public Object insert(Tags tags, @RequestParam(value = "defaultImage", required = false) MultipartFile defaultImage,
			@RequestParam(value = "bigImageFile", required = false) MultipartFile bigImage, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			validateParameters(tags);
			if (!defaultImage.isEmpty()) {
				tags.setImage(UploadUtils.uploadFile(defaultImage, UploadUtils.FTP_COMMON_FILE));
			}
			if (!bigImage.isEmpty()) {
				tags.setBigImage(UploadUtils.uploadFile(bigImage, UploadUtils.FTP_COMMON_FILE));
			}
			tagsService.insertTags(tags);
			AdminServerController.refreshConfirm();
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("[tags update error]", e2);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.FAIL);
		}
		return result;
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(HttpServletRequest request, HttpServletResponse response, Tags tags) {
		setProperty(request);
		List<Tags> tagsList = tagsService.selectTags(tags, new PageBounds());

		if (tagsList != null && tagsList.size() > 0) {
			tags = tagsList.get(0);
		}

		request.setAttribute("itemEdit", tags);
		return PAGE_EDIT;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object updateUser(Tags tags,
			@RequestParam(value = "defaultImage", required = false) MultipartFile defaultImage,
			@RequestParam(value = "bigImage", required = false) MultipartFile bigImage) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			validateParameters(tags);
			if (!defaultImage.isEmpty()) {
				tags.setImage(UploadUtils.uploadFile(defaultImage, UploadUtils.FTP_COMMON_FILE));
			}
			if (!bigImage.isEmpty()) {
				tags.setBigImage(UploadUtils.uploadFile(bigImage, UploadUtils.FTP_COMMON_FILE));
			}
			tagsService.updateTags(tags);
			ConstantsManager.refresh();
			AdminServerController.refreshConfirm();
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("[tags update error]", e2);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.FAIL);
		}
		return result;
	}

	@RequestMapping("/detail.do")
	public String detail(HttpServletRequest request, HttpServletResponse response, Tags tags) {
		List<Tags> tagsList = tagsService.selectTags(tags, new PageBounds());
		if (tagsList != null && tagsList.size() > 0) {
			tags = tagsList.get(0);
		}

		request.setAttribute("itemEdit", tags);
		return PAGE_DETAIL;
	}

	public static void main(String[] args) {
		Object a = null;
		System.err.println(a.toString());
	}
}
