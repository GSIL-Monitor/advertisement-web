package com.yuanshanbao.ms.controller.bill;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.bill.service.BillService;

@Controller
@RequestMapping("/admin/bill")
public class AdminBillController {
	@Autowired
	private BillService billService;

	@ResponseBody
	@RequestMapping("/admin/bill")
	public Object countBalance() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		billService.payment(new Advertiser());
		return resultMap;
	}
}
