package com.yuanshanbao.ms.controller.banner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.UploadUtils;
import com.yuanshanbao.dsp.banner.model.Banner;
import com.yuanshanbao.dsp.banner.service.BannerService;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/banner")
public class AdminBannerController extends PaginationController {

	private static final String PAGE_LIST = "advertisement/banner/listBanner";

	private static final String PAGE_INSERT = "advertisement/banner/insertBanner";

	private static final String PAGE_UPDATE = "advertisement/banner/updateBanner";

	private static final String PAGE_VIEW = "advertisement/banner/viewBanner";

	@Autowired
	private BannerService bannerService;

	@RequestMapping("/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, Banner banner, HttpServletRequest request, HttpServletResponse response) {
		Object object = bannerService.selectBanners(banner, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/insertWindow.do")
	public String insertWindow(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());
		return PAGE_INSERT;
	}

	@ResponseBody
	@RequestMapping("/insert.do")
	public Object insert(Banner banner, String bannerStartTime, String bannerEndTime,
			@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			if (!file.isEmpty()) {
				banner.setImgUrl(UploadUtils.uploadFile(file, UploadUtils.FTP_COMMON_FILE));
			}
			if (StringUtils.isNotBlank(bannerStartTime)) {
				banner.setStartTime(DateUtils.formatToTimestamp(bannerStartTime, "yyyy-MM-dd HH:mm:ss"));
			}
			if (StringUtils.isNotBlank(bannerEndTime)) {
				banner.setEndTime(DateUtils.formatToTimestamp(bannerEndTime, "yyyy-MM-dd HH:mm:ss"));
			}
			validateParameters(banner);
			bannerService.insertBanner(banner);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("insert banner error: ", e2);
		}

		return result;
	}

	@ResponseBody
	@RequestMapping("/delete.do")
	public Object delete(Integer bannerId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {

			if (bannerId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			Banner banner = new Banner();
			banner.setStatus(CommonStatus.OFFLINE);
			banner.setBannerId(bannerId);
			bannerService.updateBanner(banner);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(HttpServletRequest request, HttpServletResponse response, Banner banner) {
		List<Banner> list = bannerService.selectBanners(banner, new PageBounds());
		if (list != null && list.size() >= 0) {
			banner = list.get(0);
		}

		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());
		request.setAttribute("itemEdit", banner);
		return PAGE_UPDATE;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object update(Banner banner, String bannerStartTime, String bannerEndTime,
			@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {

			if (!file.isEmpty()) {
				banner.setImgUrl(UploadUtils.uploadFile(file, UploadUtils.FTP_COMMON_FILE));
			}
			if (StringUtils.isNotBlank(bannerStartTime)) {
				banner.setStartTime(DateUtils.formatToTimestamp(bannerStartTime, "yyyy-MM-dd HH:mm:ss"));
			}
			if (StringUtils.isNotBlank(bannerEndTime)) {
				banner.setEndTime(DateUtils.formatToTimestamp(bannerEndTime, "yyyy-MM-dd HH:mm:ss"));
			}
			validateParameters(banner);
			bannerService.updateBanner(banner);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("update banner error: ", e2);
		}
		return result;
	}

	@RequestMapping("/view.do")
	public String view(Banner banner, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		List<Banner> list = bannerService.selectBanners(banner, new PageBounds());
		if (list != null && list.size() >= 0) {
			banner = list.get(0);
		}
		request.setAttribute("itemEdit", banner);
		return PAGE_VIEW;
	}

}
