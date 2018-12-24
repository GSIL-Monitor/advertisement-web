package com.yuanshanbao.dsp.controller.invite;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.qrcode.ZXingCode;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.UploadUtils;
import com.yuanshanbao.dsp.common.constant.RedisConstant;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.product.model.Product;
import com.yuanshanbao.dsp.product.service.ProductService;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.dsp.user.model.UserLevel;
import com.yuanshanbao.dsp.user.service.TokenService;
import com.yuanshanbao.dsp.user.service.UserService;
import com.yuanshanbao.dsp.weixin.service.WeixinService;

/**
 * Created by Administrator on 2018/10/31.
 */
@Controller
@RequestMapping("/i/invite")
public class InviteController extends BaseController {

	private static final String URL = "pages/index/index";
	private static final String H5_AGENT_URL = "https://wz.huhad.com/w/products";
	private static final String H5_HOME_URL = "https://wz.huhad.com/w/home";
	private static final String H5_DETAIL_URL = "https://wz.huhad.com/w/detail";
	private static final String H5_BANK_INFO = "https://wz.huhad.com/w/applicants.html";
	private static final String DETAILURL = "pages/index/detail/detail";

	private static final String xcxInviteImageUrl = "https://ktadtech.oss-cn-beijing.aliyuncs.com/test/img/1541159952231_7337.png";
	private static final String h5InviteImageUrl = "https://ktadtech.oss-cn-beijing.aliyuncs.com/test/img/h5inviteimage20181130.png";
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
	public Object inviteFriend(HttpServletRequest request, String token) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			User user = differentiateTokenUser(request, token);
			if (user == null) {
				throw new BusinessException(ComRetCode.NOT_LOGIN);
			}
			if (user.getLevel() == null) {
				user.setLevel(UserLevel.MANAGER);
				userService.updateUser(user);
			}
			if (user.getLevel() == UserLevel.VIP_AGENT) {
				String H5Url = H5_AGENT_URL + "?userId=" + user.getUserId();
				userService.createQRCodeURL(user, H5Url, resultMap);
				resultMap.put("inviteImageUrl", h5InviteImageUrl);

			} else {
				if (StringUtils.isBlank(token)) {
					String H5Url = H5_HOME_URL + "?userId=" + user.getUserId();
					userService.createQRCodeURL(user, H5Url, resultMap);
					resultMap.put("inviteImageUrl", xcxInviteImageUrl);
				} else {
					String code = redisCacheService.get(RedisConstant.WX_XCX_CODE + user.getUserId());
					if (StringUtils.isBlank(code)) {
						byte[] bytes = weixinService.dealQRCode(weixinService.CONFIG_WZXCX,
								String.valueOf(user.getUserId()), URL);
						if (bytes != null) {
							InputStream input = new ByteArrayInputStream(bytes);
							String qrCode = UploadUtils.uploadBytes(input, input.available(), "test/image/avatar"
									+ System.nanoTime() + (int) (Math.random() * 10000) + ".png");
							redisCacheService.set(RedisConstant.WX_XCX_CODE + user.getUserId(), qrCode);
							resultMap.put("QRcode", qrCode);
						}
					} else {
						resultMap.put("QRcode", code);
					}
					String url = URL + "?userId=" + user.getUserId();
					resultMap.put("inviteImageUrl", xcxInviteImageUrl);
					resultMap.put("user", user);
					resultMap.put("url", url);
				}
			}
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
	public Object getLogoQRcode(String token, @RequestParam(value = "productId", required = false) String productId,
			@RequestParam("avatarUrl") String avatarUrl) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			User user = tokenService.verifyLoginToken(token);
			if (user == null) {
				throw new BusinessException(ComRetCode.NOT_LOGIN);
			}
			String applayCardCode = null;
			// H5代理版二维码
			String H5Url = H5_AGENT_URL + "?userId=" + user.getUserId() + "&productId=" + productId;
			// 插入logo
			if (avatarUrl == null) {
				applayCardCode = ZXingCode.getLogoQRCode(H5Url,
						"https://ktadtech.oss-cn-beijing.aliyuncs.com/test/img/1541566698341_1135.jpg");
				resultMap.put("CardCode", applayCardCode);
			} else {
				applayCardCode = ZXingCode.getLogoQRCode(H5Url, avatarUrl);
				resultMap.put("applayCardCode", applayCardCode);
			}
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
	public Object xcxGetDetailCode(HttpServletRequest request, String token, String productId) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			User user = differentiateTokenUser(request, token);
			if (user == null) {
				throw new BusinessException(ComRetCode.NOT_LOGIN);
			}

			if (user.getLevel() == null) {
				user.setLevel(UserLevel.MANAGER);
				userService.updateUser(user);
			}
			if (user.getLevel() == UserLevel.VIP_AGENT) {
				String H5Url = H5_DETAIL_URL + "?userId=" + user.getUserId() + "&productId=" + productId
						+ "&fromPage=products";
				String applayCardCode = ZXingCode.getLogoQRCode(H5Url, null);
				resultMap.put("QRcode", applayCardCode);
				resultMap.put("user", user);
				resultMap.put("url", H5Url);
			} else {

				if (StringUtils.isBlank(token)) {
					String H5Url = H5_DETAIL_URL + "?userId=" + user.getUserId() + "&productId=" + productId;
					userService.createQRCodeURL(user, H5Url, resultMap);
				} else {
					// String code = redisCacheService
					// .get(RedisConstant.WX_XCX_DETAIL_CODE + user.getUserId()
					// + productId);
					// if (StringUtils.isBlank(code)) {
					// byte[] bytes =
					// weixinService.dealQRCode(weixinService.CONFIG_WZXCX,
					// productId + "," + user.getUserId(), DETAILURL);
					// if (bytes != null) {
					// InputStream input = new ByteArrayInputStream(bytes);
					// String qrCode = UploadUtils.uploadBytes(input,
					// input.available(), "test/image/avatar"
					// + System.nanoTime() + (int) (Math.random() * 10000) +
					// ".png");
					// redisCacheService.set(RedisConstant.WX_XCX_DETAIL_CODE +
					// user.getUserId() + productId,
					// qrCode);
					// resultMap.put("QRcode", qrCode);
					// }
					// } else {
					// resultMap.put("QRcode", code);
					// }
					String H5Url = H5_BANK_INFO + "?userId=" + user.getUserId() + "&productId=" + productId;
					userService.createQRCodeURL(user, H5Url, resultMap);
					resultMap.put("user", user);
				}

			}

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
	public Object getUrl(HttpServletRequest request, String token,
			@RequestParam(value = "productId", required = false) String productId) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			User user = differentiateTokenUser(request, token);
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
