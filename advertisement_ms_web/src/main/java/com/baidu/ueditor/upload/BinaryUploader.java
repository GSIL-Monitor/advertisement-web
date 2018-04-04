package com.baidu.ueditor.upload;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import com.yuanshanbao.common.util.UploadUtils;

public class BinaryUploader {

	public static final State save(HttpServletRequest request, Map<String, Object> conf) {
		boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;

		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}

		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());

		if (isAjaxUpload) {
			upload.setHeaderEncoding("UTF-8");
		}

		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> imgFileMap = multipartRequest.getFileMap();
			Iterator<MultipartFile> iterator = imgFileMap.values().iterator();
			MultipartFile file = null;
			while (iterator.hasNext()) {
				file = iterator.next();
			}

			if (file == null) {
				return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
			}

			String originFileName = file.getOriginalFilename();
			String suffix = FileType.getSuffixByFilename(originFileName);
			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}

			String imageUrl = UploadUtils.uploadFile(file, "article");
			if (!StringUtils.isBlank(imageUrl)) {
				BaseState state = new BaseState(true);
				state.putInfo("size", file.getSize());
				state.putInfo("title", file.getName());
				state.putInfo("url", PathFormat.format(imageUrl));
				state.putInfo("type", suffix);
				state.putInfo("original", originFileName + suffix);
				return state;
			}

		} catch (IOException e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}
}
