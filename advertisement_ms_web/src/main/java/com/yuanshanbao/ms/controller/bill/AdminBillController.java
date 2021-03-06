package com.yuanshanbao.ms.controller.bill;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.advertiser.service.AdvertiserService;
import com.yuanshanbao.dsp.bill.model.Bill;
import com.yuanshanbao.dsp.bill.model.BillStatus;
import com.yuanshanbao.dsp.bill.service.BillService;
import com.yuanshanbao.dsp.common.constant.RedisConstant;
import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.order.model.Order;
import com.yuanshanbao.dsp.order.service.OrderService;
import com.yuanshanbao.dsp.plan.model.Plan;
import com.yuanshanbao.dsp.plan.model.PlanStatus;
import com.yuanshanbao.dsp.plan.service.PlanService;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.dsp.probability.service.ProbabilityService;
import com.yuanshanbao.dsp.quota.model.QuotaType;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/bill")
public class AdminBillController extends PaginationController {

	private static final String VIRTUAL_CHANNEL = "yuanshan_channel";

	private static final String PAGE_LIST = "advertisement/bill/listBill";

	private static final String PAGE_BALANCE_LIST = "advertisement/bill/listBalance";

	private static final String PAGE_INSERT_RECHARGE = "advertisement/bill/insertRecharge";

	private static final String PAGE_ADMIN_DEDUCTION = "advertisement/bill/adminDeduction";

	@Autowired
	private BillService billService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProbabilityService probabilityService;

	@Autowired
	private AdvertiserService advertiserService;

	@Autowired
	private PlanService planService;

	@Autowired
	private RedisService redisService;

	@RequestMapping("/banlanceList.do")
	public String banlanceList() {
		return PAGE_BALANCE_LIST;
	}

	@ResponseBody
	@RequestMapping("/queryBanlance.do")
	public Object queryBanlance(String range, Bill bill, HttpServletRequest request, HttpServletResponse response) {
		Advertiser advertiser = getBindAdvertiserByUser();
		if (advertiser == null) {
			advertiser = new Advertiser();
		}
		Object object = advertiserService.selectAdvertiser(advertiser, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/list.do")
	public String list(HttpServletRequest request) {
		Advertiser advertiser = getBindAdvertiserByUser();
		if (advertiser != null) {
			request.setAttribute("advertiserId", advertiser.getAdvertiserId());
			request.setAttribute("balance", advertiser.getBalance());
		}
		return PAGE_LIST;
	}

	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, Bill bill, HttpServletRequest request, HttpServletResponse response) {
		bill.setStatus(BillStatus.MERGE_BILL);
		Object object = billService.selectBill(bill, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/rechargeWindow")
	public String rechargeWindow(Long advertiserId, HttpServletRequest request, HttpServletResponse response) {
		Advertiser advertiser = advertiserService.selectAdvertiser(advertiserId);
		request.setAttribute("advertiserId", advertiserId);
		request.setAttribute("advertiser", advertiser);
		return PAGE_INSERT_RECHARGE;
	}

	@ResponseBody
	@RequestMapping("/recharge.do")
	public Object recharge(HttpServletRequest request, HttpServletResponse response, Bill bill) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			validateParameters(bill);
			if (bill.getAdvertiserId() == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			billService.recharge(bill);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("recharge  function -  error", e2);
		}

		return resultMap;
	}

	@ResponseBody
	@RequestMapping("/creatBill")
	public Object creatBill() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Advertiser advertiser = new Advertiser();
		advertiser.setStatus(CommonStatus.ONLINE);
		List<Advertiser> list = advertiserService.selectAdvertiser(advertiser, new PageBounds());
		try {
			for (Advertiser adv : list) {
				billService.payment(adv);
			}
		} catch (Exception e2) {
			LoggerUtil.error("calculateCount  function -  error", e2);
		}
		return resultMap;
	}

	@ResponseBody
	@RequestMapping("/creatPlanBill")
	public Object creatPlanBill() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Plan param = new Plan();
		param.setStatus(PlanStatus.ONLINE);
		List<Plan> list = planService.selectPlan(param, new PageBounds());
		try {
			for (Plan plan : list) {
				billService.paymentForPlan(plan);
			}
		} catch (Exception e2) {
			LoggerUtil.error("creatPlanBill  function -  error", e2);
		}
		return resultMap;
	}

	@ResponseBody
	@RequestMapping("/calculateCount")
	public Object calculateCount(String calculatedate) {
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
			billService.calculate(date);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (Exception e2) {
			LoggerUtil.error("calculateCount  function -  error", e2);
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

	@ResponseBody
	@RequestMapping("/createOrderBill")
	public Object createOrderBill() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Order order = new Order();
		order.setStatus(CommonStatus.ONLINE);
		List<Order> list = orderService.selectOrder(order, new PageBounds());
		try {
			for (Order param : list) {
				billService.payment(param);
			}
		} catch (Exception e2) {
			LoggerUtil.error("calculateCount  function -  error", e2);
		}
		return resultMap;
	}

	@RequestMapping("/deductionWindow.do")
	public String deductionWindow(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_ADMIN_DEDUCTION;
	}

	@RequestMapping("/deductionByAdmin.do")
	@ResponseBody
	public Object deductionByAdmin(HttpServletRequest request, HttpServletResponse response, Long planId,
			Integer clickCount, Integer showCount) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Plan plan = planService.selectPlan(planId);
			if (plan == null) {
				return resultMap;
			}
			Probability params = new Probability();
			params.setPlanId(planId);
			params.setChannel(VIRTUAL_CHANNEL);
			List<Probability> list = probabilityService.selectProbabilitys(params, new PageBounds());
			if (list != null && list.size() > 0) {
				Probability probability = list.get(0);
				redisService.increBy(RedisConstant.getPlanClickCountPVKey(null, planId + "", VIRTUAL_CHANNEL),
						clickCount);
				redisService
						.increBy(RedisConstant.getPlanShowCountPVKey(null, planId + "", VIRTUAL_CHANNEL), showCount);
				BigDecimal unitPrice = new BigDecimal(0);
				double increAmount = 0;
				if (QuotaType.CPM.equals(plan.getChargeType())) {
					unitPrice = plan.getBestBid().divide(new BigDecimal(1000));
					increAmount = (new BigDecimal(showCount).multiply(unitPrice)).doubleValue();
				} else if (QuotaType.CPC.equals(plan.getChargeType())) {
					unitPrice = plan.getBestBid();
					increAmount = (new BigDecimal(clickCount).multiply(unitPrice)).doubleValue();
				}
				redisService.increByDouble(
						RedisConstant.getProbabilityBalanceCountKey(null, probability.getProbabilityId()), increAmount);
				LoggerUtil.info("deductionByAdmin  planId=" + planId + " amount=" + increAmount + " operator="
						+ getCurrentUser().getUsername());
			}
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
			LoggerUtil.error("deductionByAdmin error", e2);
		}
		return resultMap;
	}
}
