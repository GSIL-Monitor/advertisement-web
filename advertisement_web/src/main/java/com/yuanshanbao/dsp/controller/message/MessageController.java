package com.yuanshanbao.dsp.controller.message;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.message.model.Message;
import com.yuanshanbao.dsp.message.service.MessageService;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.paginator.domain.PageBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/29.
 */
@Controller
@RequestMapping("/i/message")
public class MessageController extends BaseController{
    @Autowired
    private MessageService messageService;
    @ResponseBody
    @RequestMapping("/list")
    public  Object getMessage(HttpServletRequest request, String  token , Message message){
        Map<String, Object> resultMap = new HashMap<>();
        try {
//            User loginUser = getLoginUser(token);
            User user = userService.selectUserById((long) 2);
            if (user == null || user.getUserId() == 0) {
                throw new BusinessException(ComRetCode.NOT_LOGIN);
            }
            message.setUserId(user.getUserId());
            List<Message> messageList = messageService.selectMessages(message, new PageBounds());
            resultMap.put("messageList",messageList);
        }catch (BusinessException e) {
            InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
        } catch (Exception e) {
            LoggerUtil.error("[productList error:]", e);
            InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
        }
        return resultMap;

    }
}
