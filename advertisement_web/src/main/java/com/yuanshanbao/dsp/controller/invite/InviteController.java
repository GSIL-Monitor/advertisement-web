package com.yuanshanbao.dsp.controller.invite;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.UploadUtils;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.dsp.user.service.TokenService;
import com.yuanshanbao.dsp.user.service.UserService;
import com.yuanshanbao.dsp.weixin.service.WeixinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

//import com.yuanshanbao.common.qrcode.ZXingCode;



/**
 * Created by Administrator on 2018/10/31.
 */
@Controller
@RequestMapping("/invite")
public class InviteController extends BaseController {

	private static final String WANGZHUAN_KEY = "wzxcx";
	private static final String URL = "pages/invitecard/invitecard";
	@Autowired
	private UserService userService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private WeixinService weixinService;

	@ResponseBody
	@RequestMapping("/inviteQRcode")
	public Object inviteFriend(String token) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			User user = userService.selectUserById(2l);
			byte[] bytes = weixinService.dealQRCode(weixinService.CONFIG_WZXCX, "", URL);
			InputStream input = new ByteArrayInputStream(bytes);
			String qrCode = UploadUtils.uploadBytes(input, input.available(), "test/image/avatar" + System.nanoTime()
					+ (int) (Math.random() * 10000) + ".png");
			/* String path = UploadUtils.uploadFile(file, "test/img"); */
			resultMap.put("user", user);
			resultMap.put("QRcode", qrCode);
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
