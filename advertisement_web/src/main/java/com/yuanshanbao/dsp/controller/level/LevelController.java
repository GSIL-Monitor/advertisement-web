package com.yuanshanbao.dsp.controller.level;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.agency.model.Agency;
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
            User user = getLoginUser(token);
            Agency agency = levelService.selectLevel(user.getUserId());
            if (user.getMobile() == null){

               resultMap.put("levelStatus",agency.getStatusValue());
            }else {
                resultMap.put("levelStatus",agency.getStatusValue());
            }
            int countProuctId = earningsService.selectCountProuctIds(user.getUserId());
            if (countProuctId > 0 && countProuctId <10){
                level.setCountCard(10 - countProuctId);
                resultMap.put("levelStatus",agency.getStatusValue());
            }else if (countProuctId == 10){
                level.setCountCard(50 - countProuctId);
                resultMap.put("levelStatus",agency.getStatusValue());
            }else if (countProuctId > 10 && countProuctId< 50){
                resultMap.put("levelStatus",agency.getStatusValue());
            }else {
                resultMap.put("levelStatus",agency.getStatusValue());
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
