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
@RequestMapping("/i/invite")
public class InviteController extends BaseController {


	private static final String URL = "pages/invitecard/invitecard";
	private static final String IMAGE_URL = "https://ktadtech.oss-cn-beijing.aliyuncs.com/test/img/1541144361248_1832.png";

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
			String url = IMAGE_URL+"?userId="+user.getUserId()+"&userName="+user.getUserName()+"&avatar="+user.getAvatar();
			resultMap.put("user", user);
			resultMap.put("QRcode", qrCode);
			resultMap.put("url",url);
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
