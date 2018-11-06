package com.yuanshanbao.dsp.controller.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yuanshanbao.dsp.app.service.AppService;
import com.yuanshanbao.dsp.message.model.Message;
import com.yuanshanbao.dsp.message.service.MessageService;
import com.yuanshanbao.dsp.tags.model.Tags;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.activity.model.Activity;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementPosition;
import com.yuanshanbao.dsp.app.service.AppService;
import com.yuanshanbao.dsp.config.ConfigConstants;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.product.model.Product;
import com.yuanshanbao.dsp.product.model.ProductCategory;
import com.yuanshanbao.dsp.product.model.ProductStatus;
import com.yuanshanbao.dsp.product.service.ProductService;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@RequestMapping({"/index", "/i/index"})
@Controller
public class IndexController extends BaseController {

    private static final String WANGZHUAN = "wangzhuan";

    @Autowired
    private ProductService productService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private AppService appService;

    // 客户端首页
    @RequestMapping("/home")
    @ResponseBody
    public Object index(HttpServletRequest request, HttpServletResponse response, String activityKey, Product product,
                        PageBounds pageBounds, String token, Integer client) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Activity activity = ConfigManager.getActivityByKey(WANGZHUAN);
            if (activity == null) {
                throw new BusinessException();
            }
            // 产品列表
            product.setActivityId(activity.getActivityId());
            product.setStatus(ProductStatus.ONLINE);

            PageList<Product> productList = (PageList<Product>) productService.selectProducts(product, formatPageBounds(pageBounds));
            List<Tags> tagsList = new ArrayList<>();
            for (Product prod : productList) {
                Tags tags = new Tags();
                if (prod.getAdvantage() != null) {
                    tags.setImage(prod.getAdvantage());
                    tagsList.add(tags);
                }
            }
            //滚动消息列表
            Message message = new Message();
            message.setProductId(activity.getActivityId());
            List<Message> messageList = messageService.selectMessages(message, new PageBounds());
            String channel = appService.getAppChannel(request.getParameter("appId"));
            String appKey = getAppKey(request);
            Long activityId = null;
            if (activity != null) {
                activityId = activity.getActivityId();
            }
            List<ProductCategory> productCategorys = new ArrayList<ProductCategory>();
            String ids = ConfigManager.getConfigValue(activityId, channel, appKey,
                    ConfigConstants.PRODUCT_CATEGORY_INDEX_CONFIG);
            productCategorys = ConfigManager.getProductCategoryList(ids);

            resultMap.put("productCategorys", productCategorys);
            resultMap.put(ComRetCode.PAGINTOR, productList.getPaginator());
            setAdvertisement(client, resultMap, channel, appKey, activityId, AdvertisementPosition.ADVERTISEMENT_INDEX);

            resultMap.put("messageList", messageList);
            resultMap.put("productList", productList);
            resultMap.put("tagsList", tagsList);
            resultMap.put("messageList", messageList);
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

    @RequestMapping("/index")
    public String indexWeb(HttpServletRequest request, ModelMap resultMap) {
        try {
            String host = request.getHeader("Host");
            if (StringUtils.isNotBlank(host) && host.contains("d.xindk.com")) {
                return getFtlPath(request, "/site/myzt");
            }
            return redirect("/404.html");
        } catch (BusinessException e) {
            InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
        } catch (Exception e) {
            InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
            LoggerUtil.error("[Result: error]", e);
        }
        return getFtlPath(request, "/common/error");
    }

}
