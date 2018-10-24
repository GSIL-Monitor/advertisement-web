package com.yuanshanbao.dsp.controller.index;

import java.util.ArrayList;
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
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.activity.model.Activity;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementPosition;
import com.yuanshanbao.dsp.config.ConfigConstants;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.product.model.Product;
import com.yuanshanbao.dsp.product.model.ProductCategory;
import com.yuanshanbao.dsp.product.model.ProductStatus;
import com.yuanshanbao.dsp.product.model.vo.ProductVo;
import com.yuanshanbao.dsp.product.service.ProductService;
import com.yuanshanbao.paginator.domain.Order;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@RequestMapping({ "/index", "/i/index" })
@Controller
public class IndexController extends BaseController {

	private static final String EDUCATION_APP = "education_app";

	@Autowired
	private ProductService productService;

	// 客户端首页
	@RequestMapping("/home")
	@ResponseBody
	public Object index(HttpServletRequest request, HttpServletResponse response, String activityKey, Product product,
			PageBounds pageBounds, String token, Integer client) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			Activity activity = ConfigManager.getActivityByKey(EDUCATION_APP);
			if (activity == null) {
				throw new BusinessException();
			}
			// 产品列表
			product.setActivityId(activity.getActivityId());
			product.setStatus(ProductStatus.ONLINE);
			PageList<Product> productList = (PageList<Product>) productService.selectProducts(product,
					formatPageBounds(pageBounds));

			resultMap.put("productList", productList);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setSpecAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
		} catch (Exception e) {
			LoggerUtil.error("[home index]: ", e);
			InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
		}
		return resultMap;
	}

}
