package com.yuanshanbao.ms.controller.sms;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.ad.core.InterfaceRetCode;
import com.yuanshanbao.ad.sms.model.SmsVerifyCode;
import com.yuanshanbao.ad.sms.service.VerifyCodeService;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/mobileVerify")
public class AdminSmsController extends PaginationController {

	public static final String PAGE_LIST = "advertisement/sms/listMobileVerify";

	@Autowired
	private VerifyCodeService codeService;
	
	@RequestMapping("/list.do")
	public String list(HttpServletRequest request,
			HttpServletResponse response) {
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, SmsVerifyCode smsVerifyCode,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(smsVerifyCode.getMobile())) {
			if (smsVerifyCode.getMobile().length() < 5) {
				map.put("mobile", "00" + smsVerifyCode.getMobile()+"%");
			} else {
				map.put("mobile", smsVerifyCode.getMobile().trim());
			}
		}
		if (smsVerifyCode.getSmsReceiveTime() > 0) {
			map.put("smsReceiveTime", smsVerifyCode.getSmsReceiveTime());
		}
		
		Object object = codeService.selectMobileVerify(map, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}
	
	
	@ResponseBody
	@RequestMapping("/send.do")
	public Object delete(String mobile, String userIp, HttpServletRequest request, 
			HttpServletResponse response, String userId) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			result.put("randomCode", null);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}
		return result;
	}
	

}