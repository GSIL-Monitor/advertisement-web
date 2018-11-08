package com.yuanshanbao.dsp.controller.invite;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.qrcode.ZXingCode;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.UploadUtils;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.product.model.Product;
import com.yuanshanbao.dsp.product.service.ProductService;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.dsp.user.service.TokenService;
import com.yuanshanbao.dsp.user.service.UserService;
import com.yuanshanbao.dsp.weixin.service.WeixinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/31.
 */
@Controller
@RequestMapping("/i/invite")
public class InviteController extends BaseController {

    private static final String URL = "pages/invitecard/invitecard";
    private static final String H5URL = "https://wz.huhad.com/w/applicants.html";
    private static final String IMAGE_URL = "https://ktadtech.oss-cn-beijing.aliyuncs.com/test/image/avatar132529743323965055.png";
    private static String CODE = "";
    private static String XCXCODE = "";
    private static final String DETAILURL = "pages/index/detail/detail";
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private WeixinService weixinService;
    @Autowired
    private ProductService productService;


    @ResponseBody
    @RequestMapping("/inviteQRcode")
    public Object inviteFriend(String token) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            User user = tokenService.verifyLoginToken(token);
            if (user == null) {
                throw new BusinessException(ComRetCode.NOT_LOGIN);
            }

            /*String code = redisCacheService.get("code");*/
            if ("".equals(CODE)) {
                byte[] bytes = weixinService.dealQRCode(weixinService.CONFIG_WZXCX, String.valueOf(user.getUserId()), URL);
                if (bytes != null) {
                    InputStream input = new ByteArrayInputStream(bytes);
                    String qrCode = UploadUtils.uploadBytes(input, input.available(),
                            "test/image/avatar" + System.nanoTime() + (int) (Math.random() * 10000) + ".png");
//                    redisCacheService.set("code",qrCode);
                    CODE = qrCode;
                    resultMap.put("QRcode", qrCode);
                }
            }
            resultMap.put("QRcode", CODE);
            String url = URL + "?userId=" + user.getUserId();

            resultMap.put("user", user);
            resultMap.put("url", url);
            InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
        } catch (BusinessException e) {
            InterfaceRetCode.setSpecAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
        } catch (Exception e) {
            LoggerUtil.error("[home index]: ", e);
            InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
        }
        return resultMap;
    }

    @ResponseBody
    @RequestMapping("/getLogoQRcode")
    public Object getLogoQRcode(String token, @RequestParam(value = "productId", required = false) String  productId, @RequestParam("avatarUrl") String avatarUrl) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            User user = tokenService.verifyLoginToken(token);
            if (user == null) {
                throw new BusinessException(ComRetCode.NOT_LOGIN);

            }
            //H5二维码
            String H5Url = H5URL + "?userId=" + user.getUserId() + "&productId=" + productId;
            //插入logo
            if ("".equals(avatarUrl) && avatarUrl == null) {
                String applayCardCode = ZXingCode.getLogoQRCode(H5Url, "https://ktadtech.oss-cn-beijing.aliyuncs.com/test/img/1541566698341_1135.jpg");
                resultMap.put("CardCode", applayCardCode);
            }
            String applayCardCode = ZXingCode.getLogoQRCode(H5Url, avatarUrl);
            resultMap.put("applayCardCode", applayCardCode);
            resultMap.put("H5Url", H5Url);
            InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
        } catch (BusinessException e) {
            InterfaceRetCode.setSpecAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
        } catch (Exception e) {
            LoggerUtil.error("[home index]: ", e);
            InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
        }
        return resultMap;
    }

    @ResponseBody
    @RequestMapping("/xcxGetDetailCode")
    public Object xcxGetDetailCode(String token, @RequestParam(value = "productId", required = false) String  productId) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            User user = tokenService.verifyLoginToken(token);
            if (user == null) {
                throw new BusinessException(ComRetCode.NOT_LOGIN);
            }
            if ("".equals(XCXCODE)) {
                byte[] bytes = weixinService.dealQRCode(weixinService.CONFIG_WZXCX, productId+user.getUserId(), DETAILURL);
                if (bytes != null) {
                    InputStream input = new ByteArrayInputStream(bytes);
                    String qrCode = UploadUtils.uploadBytes(input, input.available(),
                            "test/image/avatar" + System.nanoTime() + (int) (Math.random() * 10000) + ".png");
                    XCXCODE = qrCode;
                    resultMap.put("QRcode", qrCode);
                }
            }
            resultMap.put("QRcode", XCXCODE);
            InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.SUCCESS);
        } catch (BusinessException e) {
            InterfaceRetCode.setSpecAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
        } catch (Exception e) {
            LoggerUtil.error("[home index]: ", e);
            InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
        }
        return resultMap;
    }

    @ResponseBody
    @RequestMapping("/getUrl")
    public Object getUrl(String token, @RequestParam(value = "productId", required = false) String productId) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            User user = tokenService.verifyLoginToken(token);
            if (user == null) {
                throw new BusinessException(ComRetCode.NOT_LOGIN);
            }
            Product product = productService.selectProduct(Long.valueOf(productId));
            resultMap.put("url", product.getDetailImageUrl());
        } catch (BusinessException e) {
            InterfaceRetCode.setSpecAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
        } catch (Exception e) {
            LoggerUtil.error("[home index]: ", e);
            InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
        }
        return resultMap;

    }
}
