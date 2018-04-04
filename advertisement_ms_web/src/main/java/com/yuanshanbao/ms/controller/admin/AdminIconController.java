package com.yuanshanbao.ms.controller.admin;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.ms.model.admin.Icon;
import com.yuanshanbao.ms.service.admin.IconService;
import com.yuanshanbao.ms.service.cache.IconCacheService;
import com.yuanshanbao.paginator.domain.PageBounds;

@Controller
public class AdminIconController extends PaginationController {
	private static final String PAGE_ADMIN = "admin/system/icon/adminIcon";

	private static final String PAGE_INSERT = "admin/system/icon/insertIcon";

	@Autowired
	private IconService iconService;

	@Autowired
	private IconCacheService iconCacheService;

	@RequestMapping("/icon/icon.do")
	public void icon(@RequestParam("name") String name, HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		byte[] bytes = null;

		bytes = iconCacheService.query(name);

		if (bytes != null && bytes.length > 0) {
			response.getOutputStream().write(bytes);
			response.getOutputStream().flush();
		} else {
			Blob icon = iconService.queryIconByName(name);

			if (icon != null) {
				bytes = new byte[(int) icon.length()];
				System.out.println(icon.getBinaryStream().available());
				System.out.println(icon.getBytes(1, 100));

				icon.getBinaryStream().read(bytes);
				iconCacheService.insert(name, bytes);
				response.getOutputStream().write(bytes);
				response.getOutputStream().flush();
			}
		}
	}

	@RequestMapping("/admin/icon/adminIcons.do")
	public String adminIcons(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_ADMIN;
	}

	@ResponseBody
	@RequestMapping("/admin/icon/queryIcons.do")
	public Object queryIcons(@RequestParam(value = "name", required = false) String name, String range,
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "dir", required = false) String dir, HttpServletRequest request,
			HttpServletResponse response) {
		Range parsedRange = new Range(range.replaceAll(RANGE_PREFIX, ""));
		PageBounds pageBounds = new PageBounds(parsedRange.getPage(), parsedRange.getLimit());
		List<Icon> iconList = iconService.queryIcons(name, pageBounds);

		return iconList;
	}

	@ResponseBody
	@RequestMapping("/admin/icon/deleteIcon.do")
	public Object deleteIcon(String name, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> result = new HashMap<String, String>();

		if (iconService.deleteIcon(name)) {
			iconCacheService.delete(name);
			result.put(RET_CODE_PARAM, RET_SUCCESS);
			return result;
		} else {
			result.put(RET_CODE_PARAM, RET_FAILURE);
			return result;
		}
	}

	@RequestMapping("/admin/icon/insertIconWindow.do")
	public String insertIconWindow(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_INSERT;
	}

	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping("/admin/icon/insertIcon.do")
	public Object insertIcon(@RequestParam("file") MultipartFile file, String name, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, String> result = new HashMap<String, String>();

		if (file == null) {
			result.put(RET_CODE_PARAM, RET_INTERROR);
			result.put(RET_ERROR_MSG, "请选择上传的文件！");
			return "<html><body><textarea>" + JSONObject.fromObject(result).toString() + "</textarea></body></html>";
		}

		if (!isLegalIconName(file.getOriginalFilename())) {
			result.put(RET_CODE_PARAM, RET_INTERROR);
			result.put(RET_ERROR_MSG, "请上传png或者gif图片！");
			return "<html><body><textarea>" + JSONObject.fromObject(result).toString() + "</textarea></body></html>";
		}

		if (file != null) {
			String originalName = file.getOriginalFilename();
			// String name = originalName.substring(0,
			// originalName.lastIndexOf("."));
			byte[] image = file.getBytes();
			if (iconService.insertIcon(name, image)) {
				result.put(RET_CODE_PARAM, RET_SUCCESS);
				result.put(RET_HTML, getSuccessHtml("添加图标成功！"));
				return "<html><body><textarea>" + JSONObject.fromObject(result).toString()
						+ "</textarea></body></html>";
			} else {
				result.put(RET_CODE_PARAM, RET_INTERROR);
				result.put(RET_ERROR_MSG, "数据库异常，请稍后再试！");
				return "<html><body><textarea>" + JSONObject.fromObject(result).toString()
						+ "</textarea></body></html>";
			}
		}

		result.put(RET_CODE_PARAM, RET_INTERROR);
		result.put(RET_ERROR_MSG, "系统异常，请稍后再试！");
		return "<html><body><textarea>" + JSONObject.fromObject(result).toString() + "</textarea></body></html>";
	}

	private boolean isLegalIconName(String fileName) {
		return fileName.endsWith(".png") || fileName.endsWith(".gif") || fileName.endsWith(".jpg");
	}
}
