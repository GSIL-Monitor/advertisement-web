package com.yuanshanbao.dsp.controller.agency;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.agency.model.Agency;
import com.yuanshanbao.dsp.agency.model.vo.AgencyStatus;
import com.yuanshanbao.dsp.agency.service.AgencyService;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.dsp.user.service.TokenService;
import com.yuanshanbao.dsp.user.service.UserService;
import com.yuanshanbao.paginator.domain.PageBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Created by Administrator on 2018/10/22.
 */
@Controller
@RequestMapping("/i/agency")
public class AgencyController extends BaseController {

	@Autowired
	private AgencyService agencyService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private UserService userService;

	@RequestMapping("/list")
	@ResponseBody
	public Object getAgencyList(HttpServletRequest request, Agency agency, PageBounds pageBounds, String token) {

		Map<Object, Object> resultMap = new HashMap<>();

		try {
			// 获取当前用户信息
			User user = getLoginUser(token);
			agency.setInviteUserId(user.getUserId());
			List<Agency> agencyList = agencyService.selectAgencys(agency, pageBounds);

			Map<Long,Agency> agencyMap = new LinkedHashMap<Long,Agency>();
			for (Agency agen: agencyList){
				if (agen.getStatus()!=null && agen.getStatus() != AgencyStatus.OFFCHECK){
					agen.setBrokerage(BigDecimal.valueOf(0));
				}
				agencyMap.put(agen.getUserId(),agen);

			}
			resultMap.put("agencyList", agencyMap.values());
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
			for (Iterator iterator = oneAgencyList.iterator();iterator.hasNext();){
				Agency agen = (Agency) iterator.next();
				if (agen.getBrokerage() == null) {
					iterator.remove();
				}
			}
			// 总佣金
			BigDecimal brokerages = agencyService.getBrokerages(agency, pageBounds);
			for (Agency agen : oneAgencyList) {
				agency.setInviteUserId(agen.getUserId());
				twoAgencyList = agencyService.selectAgencys(agency, new PageBounds());
			}
			resultMap.put("oneAgencyList", oneAgencyList);
			resultMap.put("twoAgencyList", twoAgencyList);
			resultMap.put("brokerage",brokerages.setScale(2, RoundingMode.HALF_UP));
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[productList error:]", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

}
