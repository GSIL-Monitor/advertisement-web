package com.yuanshanbao.ms.controller.entrance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yuanshanbao.ad.common.model.Entrance;
import com.yuanshanbao.ad.common.service.EntranceService;
import com.yuanshanbao.ad.core.CommonStatus;
import com.yuanshanbao.ad.core.InterfaceRetCode;
import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.UploadUtils;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/entrance")
public class AdminEntranceController extends PaginationController {

	private static final String PAGE_LIST = "advertisement/entrance/listEntrance";

	private static final String PAGE_INSERT = "advertisement/entrance/insertEntrance";

	private static final String PAGE_UPDATE = "advertisement/entrance/updateEntrance";

	private static final String PAGE_VIEW = "advertisement/entrance/viewEntrance";

	@Autowired
	private EntranceService entranceService;

	@RequestMapping("/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, Entrance entrance, HttpServletRequest request, HttpServletResponse response) {
		Object object = entranceService.selectEntrance(entrance, getPageBounds(range, request));
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
	public Object insert(Entrance entrance, @RequestParam("file") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			validateParameters(entrance);
			if (!file.isEmpty()) {
				entrance.setImgUrl(UploadUtils.uploadFile(file, UploadUtils.FTP_COMMON_FILE));
			}
			entranceService.insertEntrance(entrance);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("insert entrance error: ", e2);
		}

		return result;
	}

	@ResponseBody
	@RequestMapping("/delete.do")
	public Object delete(Long entranceId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {

			if (entranceId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			Entrance entrance = new Entrance();
			entrance.setEntranceId(entranceId);
			entrance.setIsDel(1);
			entranceService.updateEntrance(entrance);

			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(HttpServletRequest request, HttpServletResponse response, Entrance entrance) {
		List<Entrance> list = entranceService.selectEntrance(entrance, new PageBounds());
		if (list != null && list.size() >= 0) {
			entrance = list.get(0);
		}

		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());
		request.setAttribute("itemEdit", entrance);
		return PAGE_UPDATE;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object update(Entrance entrance, @RequestParam("file") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			validateParameters(entrance);
			if (!file.isEmpty()) {
				entrance.setImgUrl(UploadUtils.uploadFile(file, UploadUtils.FTP_COMMON_FILE));
			}
			entranceService.updateEntrance(entrance);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("update entrance error: ", e2);
		}
		return result;
	}

	@RequestMapping("/view.do")
	public String view(Entrance entrance, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		List<Entrance> list = entranceService.selectEntrance(entrance, new PageBounds());
		if (list != null && list.size() >= 0) {
			entrance = list.get(0);
		}
		request.setAttribute("itemEdit", entrance);
		return PAGE_VIEW;
	}

}
