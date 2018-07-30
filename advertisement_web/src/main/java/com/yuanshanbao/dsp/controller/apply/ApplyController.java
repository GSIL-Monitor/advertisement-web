package com.yuanshanbao.dsp.controller.apply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.constant.SessionConstants;
import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.NumberUtil;
import com.yuanshanbao.dsp.apply.model.Apply;
import com.yuanshanbao.dsp.apply.model.ApplyVo;
import com.yuanshanbao.dsp.apply.service.ApplyService;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.information.model.Information;
import com.yuanshanbao.dsp.product.model.Product;
import com.yuanshanbao.dsp.product.service.ProductService;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.dsp.user.service.TokenService;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping({ "/apply", "/i/apply" })
public class ApplyController extends BaseController {

	@Autowired
	private ApplyService applyService;

	@Autowired
	private ProductService productService;

	@Autowired
	private TokenService tokenService;

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest request, Apply apply, String token, PageBounds pageBounds,
			HttpServletResponse response, ModelMap modelMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			User user = userService.selectUserByToken(token);
			if (user == null) {
				throw new BusinessException(ComRetCode.NOT_LOGIN);
			}
			apply.setUserId(user.getUserId());
			PageList<Apply> applys = (PageList<Apply>) applyService.selectApplys(apply, formatPageBounds(pageBounds));
			PageList<ApplyVo> applyList = new PageList<ApplyVo>();
			if (applys != null) {
				for (Apply app : applys) {
					applyList.add(new ApplyVo(app));
				}
				applyList.setPaginator(applys.getPaginator());
			}
			resultMap.put("applyList", applyList);
			resultMap.put(ComRetCode.PAGINTOR, applyList.getPaginator());
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[applyList error:]", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	@ResponseBody
	@RequestMapping("/view")
	public Object view(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Long productId,
			String token, Information information) {
		return commit(request, response, modelMap, token, information, productId);
	}

	@ResponseBody
	@RequestMapping("/commit")
	public Object commit(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, String token,
			Information information, Long productId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			User user = userService.selectUserByToken(token);
			if (user == null) {
				throw new BusinessException(ComRetCode.NOT_LOGIN);
			}
			// setShowCount(request, productId);
			applyService.applyProduct(user, productId, information);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[commitApply error] productId=" + productId + "]", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	private void setShowCount(HttpServletRequest request, Long productId) {
		String sessionKey = SessionConstants.SESSION_PRODUCT_APPLY_CLICK + "_" + productId;
		String clickValue = (String) request.getSession().getAttribute(sessionKey);
		if (StringUtils.isBlank(clickValue)) {
			request.getSession().setAttribute(sessionKey, "true");
			if (productId != null) {
				productService.increaseProductCount(productId);
				applyService.increaseApplyCount();
			}
		}
	}

	@ResponseBody
	@RequestMapping("/getLuckList")
	public Object getLuckList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			Long productId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<String> luckList = getApplyUserList(resultMap, productId);
			resultMap.put("luckList", luckList);
			if (productId == 43L) {
				resultMap.put("luckList", "");
			}
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[getLuckList error]", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	protected List<String> getApplyUserList(Map<String, Object> resultMap, Long productId) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 4; i++) {
			// String apply = format("${name}刚刚拿到了<loanAmount>",
			// CommonUtil.randomName(), randomLoanAmount(productId));
			String apply = "";
			list.add(apply);
		}
		return list;
	}

	@ResponseBody
	@RequestMapping("/applyCount")
	public Object applyCount(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap.put("applyCount", NumberUtil.toList(applyService.getApplyCount(), 5));
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[applyCount error:]", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	@ResponseBody
	@RequestMapping("/prehandle")
	public Object prehandle(String tempToken, Long productId, HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (productId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			Product product = productService.selectProduct(productId);
			if (product == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			User user = tokenService.verifyLoginToken(tempToken);
			if (user == null) {
				resultMap.put("url", product.getApplyInterface());
				InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
				return resultMap;
			}
			resultMap.put("url", productService.getApplyInterface(product, user, request));
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[applyInterface error:]", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

	@RequestMapping("/prehandler")
	public String prehandler(String token, Long productId, HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String url = "";
		try {
			if (productId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			Product product = productService.selectProduct(productId);
			if (product == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			User user = tokenService.verifyLoginToken(token);
			if (user == null) {
				url = product.getApplyInterface();
			} else {
				url = productService.getApplyInterface(product, user, request);
			}
			LoggerUtil.info("redirect url:{}", url);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[prehandler error:]", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return "redirect:" + url;
	}
}
