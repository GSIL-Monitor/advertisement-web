package com.yuanshanbao.dsp.controller.agency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.agency.model.Agency;
import com.yuanshanbao.dsp.agency.model.vo.AgencyVo;
import com.yuanshanbao.dsp.agency.service.AgencyService;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.paginator.domain.PageBounds;

/**
 * Created by Administrator on 2018/10/22.
 */
@Controller
@RequestMapping("/i/agency")
public class AgencyController extends BaseController {

	@Autowired
	private AgencyService agencyService;

	@RequestMapping("/list")
	@ResponseBody
	public Object getAgencyList(HttpServletRequest request, Agency agency, PageBounds pageBounds, String token) {

		Map<Object, Object> resultMap = new HashMap<>();
		try {
			// 获取当前用户信息
			User user = getLoginUser(token);
			List<AgencyVo> agencyInfos = agencyService.getAgencyInfos(user, agency, pageBounds);
			resultMap.put("agencyList", agencyInfos);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[productList error:]", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	@RequestMapping("/award")
	@ResponseBody
	public Object getAwardList(HttpServletRequest request, Agency agency, PageBounds pageBounds, String token) {
		Map<String, Object> resultMap = new HashMap<>();
		List<Agency> twoAgencyList = new ArrayList<>();
		try {
			User user = getLoginUser(token);
			agency.setInviteUserId(user.getUserId());
			List<Agency> oneAgencyList = agencyService.selectAgencys(agency, pageBounds);
			for (Iterator<Agency> iterator = oneAgencyList.iterator(); iterator.hasNext();) {
				Agency agen = (Agency) iterator.next();
				if (agen.getBrokerage() == null) {
					iterator.remove();
				}
			}
			// 总佣金
			BigDecimal brokerages = agencyService.getBrokerages(agency, user, pageBounds);
			for (Agency agen : oneAgencyList) {
				agency.setInviteUserId(agen.getUserId());
				twoAgencyList.addAll(agencyService.selectAgencys(agency, new PageBounds()));
			}
			for (Iterator<Agency> iterator = twoAgencyList.iterator(); iterator.hasNext();) {
				Agency twoAgen = (Agency) iterator.next();
				if (twoAgen.getBrokerage() == null) {
					iterator.remove();
				}
			}
			List<AgencyVo> agencyListVo = agencyService.getAgencyListVo(twoAgencyList, user);
			resultMap.put("oneAgencyList", oneAgencyList);
			resultMap.put("twoAgencyList", agencyListVo);
			resultMap.put("brokerage", brokerages.setScale(2, RoundingMode.HALF_UP));
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[productList error:]", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

}
