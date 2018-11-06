package com.yuanshanbao.dsp.controller.bill;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.bill.service.BillService;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.plan.model.Plan;
import com.yuanshanbao.dsp.plan.model.PlanStatus;
import com.yuanshanbao.dsp.plan.service.PlanService;
import com.yuanshanbao.paginator.domain.PageBounds;

@Controller
@RequestMapping("/bill")
public class BillController {
	@Autowired
	private BillService billService;

	@Autowired
	private PlanService planService;

	@ResponseBody
	@RequestMapping("/creatPlanBill")
	public Object creatPlanBill() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Plan param = new Plan();
		param.setStatus(PlanStatus.ONLINE);
		List<Plan> list = planService.selectPlan(param, new PageBounds());
		try {
			for (Plan plan : list) {
				planService.paymentForPlan(plan);
			}
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (Exception e2) {
			LoggerUtil.error("creatPlanBill  function -  error", e2);
		}
		return resultMap;
	}

	@ResponseBody
	@RequestMapping("/calculatePlanCount")
	public Object calculatePlanCount(String calculatedate) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String date = null;
		try {
			if (calculatedate != null) {
				if (ValidateUtil.isBirthday(calculatedate, DateUtils.DEFAULT_DATE_FORMAT)) {
					date = calculatedate;
				}
			} else {
				date = DateUtils.format(new Date());
			}
			billService.calculateByPlan(date);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (Exception e2) {
			LoggerUtil.error("calculateByPlan  function -  error", e2);
		}
		return resultMap;
	}
}
