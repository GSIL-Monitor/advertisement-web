package com.yuanshanbao.ms.controller.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;
import com.yuanshanbao.ad.core.CommonStatus;
import com.yuanshanbao.ad.core.InterfaceRetCode;
import com.yuanshanbao.ad.merchant.service.MerchantService;
import com.yuanshanbao.ad.product.model.Product;
import com.yuanshanbao.ad.product.service.ProductService;

@Controller
@RequestMapping("/admin/product")
public class AdminProductController extends PaginationController {

	private static final String PAGE_LIST = "advertisement/product/listProduct";

	private static final String PAGE_INSERT = "advertisement/product/insertProduct";

	private static final String PAGE_UPDATE = "advertisement/product/updateProduct";

	private static final String PAGE_VIEW = "advertisement/product/viewProduct";

	@Autowired
	private ProductService productService;

	@Autowired
	private MerchantService merchantService;

	@RequestMapping("/list.do")
	public String list(Long merchantId, HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("merchantId", merchantId);
		request.setAttribute("merchant", merchantService.selectMerchant(merchantId));
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, Product product, HttpServletRequest request, HttpServletResponse response) {
		Object object = productService.selectProducts(product, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/insertWindow.do")
	public String insertWindow(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());
		return PAGE_INSERT;
	}

	@ResponseBody
	@RequestMapping("/insert.do")
	public Object insert(Product product, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			validateParameters(product);
			productService.insertProduct(product);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}

	@ResponseBody
	@RequestMapping("/delete.do")
	public Object delete(Long productId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (productId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			Product product = new Product();
			product.setProductId(productId);
			product.setStatus(CommonStatus.OFFLINE);
			productService.updateProduct(product);

			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(HttpServletRequest request, HttpServletResponse response, Product product) {
		List<Product> list = productService.selectProducts(product, new PageBounds());
		if (list != null && list.size() > 0) {
			product = list.get(0);
		}

		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());
		request.setAttribute("itemEdit", product);
		return PAGE_UPDATE;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object update(Product product, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			validateParameters(product);
			productService.updateProduct(product);
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}
		return result;
	}

	@RequestMapping("/view.do")
	public String view(Product product, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		List<Product> list = productService.selectProducts(product, new PageBounds());
		if (list != null && list.size() > 0) {
			product = list.get(0);
		}
		request.setAttribute("itemEdit", product);
		return PAGE_VIEW;
	}

}
