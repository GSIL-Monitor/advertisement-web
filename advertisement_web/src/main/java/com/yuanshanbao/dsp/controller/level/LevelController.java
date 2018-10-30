package com.yuanshanbao.dsp.controller.level;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.earnings.service.EarningsService;
import com.yuanshanbao.dsp.level.model.Level;
import com.yuanshanbao.dsp.level.model.vo.LevelStatus;
import com.yuanshanbao.dsp.level.model.vo.LevelVo;
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
            LevelVo levelVo = new LevelVo();
            if (user.getMobile() == null){
                levelVo.setStatus(LevelStatus.NULL_DESCRIPTION);
               resultMap.put("levelStatus",levelVo);
            }else {
                levelVo.setStatus(LevelStatus.ONLEVEL_DESCRIPTION);
                resultMap.put("levelStatus",levelVo);
            }
            int countProuctId = earningsService.selectCountProuctIds(user.getUserId());

            if (countProuctId > 0 && countProuctId <10){
                levelVo.setStatus(LevelStatus.ONLEVEL_DESCRIPTION);
                level.setCountCard(10 - countProuctId);
                resultMap.put("levelStatus",levelVo);
            }else if (countProuctId == 10){
                levelVo.setStatus(LevelStatus.OFFLEVEL_DESCRIPTION);
                level.setCountCard(50 - countProuctId);
                resultMap.put("levelStatus",levelVo);
            }else if (countProuctId > 10 && countProuctId< 50){
                levelVo.setStatus(LevelStatus.OFFLEVEL_DESCRIPTION);
                resultMap.put("levelStatus",levelVo);
            }else {
                levelVo.setStatus(LevelStatus.LEVEL_DESCRIPTION);
                resultMap.put("levelStatus",levelVo);
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
