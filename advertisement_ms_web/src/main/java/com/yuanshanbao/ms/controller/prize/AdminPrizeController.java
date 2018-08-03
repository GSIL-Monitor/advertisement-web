package com.yuanshanbao.ms.controller.prize;

import java.io.IOException;
import java.util.HashMap;
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

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.UploadUtils;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.prize.model.Prize;
import com.yuanshanbao.dsp.prize.service.PrizeService;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/prize")
public class AdminPrizeController extends PaginationController {
	private static final String PAGE_LIST = "advertisement/prize/listPrize";

	private static final String PAGE_INSERT = "insurance/prize/insertPrize";

	private static final String PAGE_UPDATE = "insurance/prize/updatePrize";

	private static final String PAGE_DETAIL = "insurance/prize/detailPrize";

	@Autowired
	private PrizeService prizeService;

	@RequestMapping("/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, Prize prize, HttpServletRequest request, HttpServletResponse response) {
		Object object = prizeService.selectInsurancePrizes(prize, getPageBounds(range, request));
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
	public Object insert(Prize prize, @RequestParam("smallImage") MultipartFile smallImage,
			@RequestParam("image") MultipartFile image, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			if (!image.isEmpty()) {
				prize.setImageUrl(UploadUtils.uploadFile(image, "file/game"));
			}
			if (!smallImage.isEmpty()) {
				prize.setSmallImageUrl(UploadUtils.uploadFile(smallImage, "file/game"));
			}
			validateParameters(prize);
			prizeService.insertPrize(prize);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (IOException e2) {
			LoggerUtil.error("insert prize error: ", e2);
		}

		return result;
	}

	@ResponseBody
	@RequestMapping("/delete.do")
	public Object delete(Long prizeId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {

			if (prizeId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			Prize prize = new Prize();
			prize.setStatus(CommonStatus.OFFLINE);
			prize.setPrizeId(prizeId);
			prizeService.updatePrize(prize);

			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}
}
