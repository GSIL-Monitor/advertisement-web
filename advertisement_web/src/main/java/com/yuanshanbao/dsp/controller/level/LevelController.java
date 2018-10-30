package com.yuanshanbao.dsp.controller.level;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.earnings.service.EarningsService;
import com.yuanshanbao.dsp.level.model.Level;
import com.yuanshanbao.dsp.level.service.LevelService;
import com.yuanshanbao.dsp.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/30.
 */
@Controller
@RequestMapping("/level")
public class LevelController extends BaseController{
    @Autowired
    private LevelService levelService;
    @Autowired
    private EarningsService earningsService;
    @RequestMapping("/list")
    @ResponseBody
    public Object getLevel(String token, Level level){
        Map<Object, Object> resultMap = new HashMap<>();
        try {
            //获取当前用户信息
          /*  User loginToken = tokenService.verifyLoginToken(token);
            if (loginToken == null) {
                throw new BusinessException();
            }
            User user = userService.selectUserById(loginToken.getUserId());*/
            User user = userService.selectUserById((long) 2);
            if (user == null || user.getUserId() == 0) {
                throw new BusinessException(ComRetCode.NOT_LOGIN);

            }

            if (user.getMobile() == null){
               level.setStatus(0);
               resultMap.put("levelStatus",level);
            }else {
                level.setStatus(1);
                resultMap.put("levelStatus",level);

            }
            int countProuctId = earningsService.selectCountProuctIds(user.getUserId());

            if (countProuctId > 0 && countProuctId <10){
                level.setStatus(1);
                level.setCountCard(10 - countProuctId);
                resultMap.put("levelStatus",level);
            }else if (countProuctId == 10){
                level.setStatus(2);
                level.setCountCard(50 - countProuctId);
                resultMap.put("levelStatus",level);
            }else if (countProuctId > 10 && countProuctId< 50){
                level.setStatus(2);

                resultMap.put("levelStatus",level);
            }else {
                level.setStatus(3);
                resultMap.put("levelStatus",level);
            }


        }catch (BusinessException e) {
            InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
        } catch (Exception e) {
            LoggerUtil.error("[productList error:]", e);
            InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
        }
        return resultMap;
    }
}
