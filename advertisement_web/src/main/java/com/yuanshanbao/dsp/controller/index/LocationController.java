package com.yuanshanbao.dsp.controller.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.FileUtil;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.sms.model.MarketingSms;
import com.yuanshanbao.dsp.sms.service.MarketingSmsService;

@Controller
public class LocationController {

	@Autowired
	private MarketingSmsService smsService;
	
	

	@ResponseBody
	@RequestMapping("/readLoc")
	public Object commit(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {

			List<String> mobileList = new ArrayList<String>();
			mobileList.add("18077585868");
			for (String mobile : mobileList) {
				try {
					MarketingSms marketingSms = new MarketingSms();
					marketingSms.setMobile(mobile);
					marketingSms.setMarketingTaskId(31L);
					marketingSms.setStatus(CommonStatus.ONLINE);
					smsService.insertMarketingSms(marketingSms);
				} catch (Exception e2) {
					LoggerUtil.error("[marketing duplicate] mobile=" + mobile + ", marketingTaskId=1",
							e2);
				}
			}
			List<String> repeat = FileUtil.readFile("C://Users/Administrator/Desktop/贷款重复.txt");
			// Map<String, MobileLocation> map =
			// mobileService.selectAllMobileLocation();
			String result = "";
			for (String mobile : repeat) {
				// MarktingSms sms = new MarktingSms();
				// sms.setMobile(mobile);
				// sms.setStatus(1);
				// smsService.insertMarktingSms(sms);
				String insert = "insert into tb_markting_sms set status=1, mobile=" + mobile
						+ ", create_time=sysdate(), update_time=sysdate(); \r\n";
				System.out.println(insert);
				result += insert;
			}
			FileUtil.write("C://Users/Administrator/Desktop/mobile.sql", result.getBytes(), false);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			LoggerUtil.error("[ddhb: error]", e);
		}
		return resultMap;
	}

}
