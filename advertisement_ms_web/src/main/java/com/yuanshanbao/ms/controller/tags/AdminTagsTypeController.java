package com.yuanshanbao.ms.controller.tags;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.tags.model.TagsType;
import com.yuanshanbao.dsp.tags.service.TagsService;
import com.yuanshanbao.dsp.tags.service.TagsTypeService;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/tags/type")
public class AdminTagsTypeController extends PaginationController {
	private static final String PAGE_LIST = "advertisement/tags-type/listTagsType";

	private static final String PAGE_INSERT = "advertisement/tags-type/insertTagsType";

	private static final String PAGE_EDIT = "advertisement/tags-type/updateTagsType";

	private static final String PAGE_DETAIL = "advertisement/tags-type/viewTagsType";

	@Autowired
	private TagsService tagsService;

	@Autowired
	private TagsTypeService tagsTypeService;

	@RequestMapping("/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/query.do")
	public Object query(String range, TagsType tagsType, HttpServletRequest request, HttpServletResponse response) {
		Object result = tagsTypeService.selectTagsType(tagsType, getPageBounds(range, request));
		PageList pageList = (PageList) result;
		return setPageInfo(request, response, pageList);
	}

	@ResponseBody
	@RequestMapping("/delete.do")
	public Object delete(HttpServletRequest request, HttpServletResponse response, Long tagsTypeId) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			TagsType tagsType = new TagsType();
			tagsType.setStatus(CommonStatus.OFFLINE);
			tagsType.setTypeId(tagsTypeId);
			tagsTypeService.updateTagsType(tagsType);
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
	public Object insert(TagsType tagsType, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			validateParameters(tagsType);
			tagsTypeService.insertTagsType(tagsType);
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
	public String updateWindow(HttpServletRequest request, HttpServletResponse response, TagsType tagsType) {
		setProperty(request);
		List<TagsType> tagsList = tagsTypeService.selectTagsType(tagsType, new PageBounds());

		if (tagsList != null && tagsList.size() > 0) {
			tagsType = tagsList.get(0);
		}

		request.setAttribute("itemEdit", tagsType);
		return PAGE_EDIT;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object updateUser(TagsType tagsType, MultipartFile file) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			validateParameters(tagsType);
			tagsTypeService.updateTagsType(tagsType);
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
	public String detail(HttpServletRequest request, HttpServletResponse response, TagsType tagsType) {
		List<TagsType> tagsTypeList = tagsTypeService.selectTagsType(tagsType, new PageBounds());
		if (tagsTypeList != null && tagsTypeList.size() > 0) {
			tagsType = tagsTypeList.get(0);
		}

		request.setAttribute("itemEdit", tagsType);
		return PAGE_DETAIL;
	}
}
