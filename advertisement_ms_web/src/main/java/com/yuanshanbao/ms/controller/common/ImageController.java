package com.yuanshanbao.ms.controller.common;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yuanshanbao.common.util.UploadUtils;
import com.yuanshanbao.ms.controller.base.PaginationController;

@Controller
@RequestMapping("/admin/common")
public class ImageController extends PaginationController {

	@ResponseBody
	@RequestMapping("/image.do")
	public Object insert(@RequestParam("file") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, String> result = new HashMap<String, String>();
		if (file == null) {
			result.put(RET_CODE_PARAM, RET_INTERROR);
			result.put(RET_ERROR_MSG, "请选择上传的文件！");
			return "<html><body><textarea>"
					+ JSONObject.fromObject(result).toString()
					+ "</textarea></body></html>";
		}
		
		String fileName = file.getOriginalFilename();
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1,
				fileName.length());
		ext = ext.toLowerCase();

		InputStream in = file.getInputStream();
		fileName = "activity" + "_" + System.currentTimeMillis() + "_"
				+ new Random().nextInt(10000) + "." + ext;
//		int rt = UploadUtils.uploadFiles(in, fileName);
//		if (rt != FTPUtil.UPLOADSTATUS_UPLOAD_FILE_SUCESS) {
//			return false;
//		}
		String imageUrl = UploadUtils.getHttpUrl() + "/" + fileName;
		return imageUrl;
	}
}
