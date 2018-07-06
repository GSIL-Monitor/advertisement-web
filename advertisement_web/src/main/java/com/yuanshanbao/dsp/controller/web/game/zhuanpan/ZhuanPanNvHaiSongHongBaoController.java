package com.yuanshanbao.dsp.controller.web.game.zhuanpan;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.core.InterfaceRetCode;

@Controller
@RequestMapping({ "/activity/zhuanpan", "/m/activity/zhuanpan", "/game/zp", "/m/game/zp" })
public class ZhuanPanNvHaiSongHongBaoController extends BaseZhuanPanController {

	private static final String ZHUANPAN_NVHAISONGHONGBAO = "zhuanpan_nvhaisonghongbao";
	private static final Integer POSITION = 4;

	private double[] probabilityRandom = { 0.5, 0.3, 0.2 };
	private String[] prizeName = { "100万出行保障", "神秘礼包", "滴滴红包" };

	@RequestMapping("/nhshb")
	public String zhuanpan(HttpServletRequest request, ModelMap modelMap, String channel) {
		String redirectUrl = forceRedirectUrl(request, channel, ZHUANPAN_NVHAISONGHONGBAO);
		if (StringUtils.isNotBlank(redirectUrl)) {
			return redirect(redirectUrl);
		}
		// setIndex(request, modelMap, ZHUANPAN_NVHAISONGHONGBAO, prizeName,
		// probabilityRandom);
		return getFtlPath(request, "/game/zhuanpan/nhshb/index");
	}

	@ResponseBody
	@RequestMapping("/nhshb/luck")
	public Object luck(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			pickPrizeAndSetResult(request, response, resultMap, ZHUANPAN_NVHAISONGHONGBAO);
			resultMap.put("position", POSITION);
			resultMap.put("angle", positionAngles[POSITION - 1]);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			LoggerUtil.error("[nhshbLuck: error]", e);
		}
		return resultMap;
	}

	@ResponseBody
	@RequestMapping("/nhshb/myPrize")
	public Object myPrize(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap.put("myPrizeList", getMyPrizeList(request, ZHUANPAN_NVHAISONGHONGBAO));
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			LoggerUtil.error("[nhshbMyPrize: error]", e);
		}
		return resultMap;
	}
}