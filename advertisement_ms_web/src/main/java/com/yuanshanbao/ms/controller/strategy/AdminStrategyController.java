package com.yuanshanbao.ms.controller.strategy;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.strategy.model.Strategy;
import com.yuanshanbao.dsp.strategy.service.StrategyService;
import com.yuanshanbao.ms.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/15.
 */
@Controller
@RequestMapping("/admin/strategy")
public class AdminStrategyController  extends BaseController{

    private static final String PAGE_INSERT = "admin/order/insertStrategy";
    @Autowired
    private StrategyService strategyService;


    @RequestMapping("/strategyWindow.do")
    public String insertStrategyWindow(@RequestParam("strategyId") String strategyId, HttpServletRequest request, HttpServletResponse response){
        request.setAttribute("strategyId",strategyId);
        return PAGE_INSERT;
    }

    @RequestMapping("/insertStrategy.do")
    public Object insertStrategy( Strategy strategy){
        Map<String ,Object> result = new HashMap<>();
        try {
            validateParameters(strategy);
            strategyService.insertStrategy(strategy);
        } catch (BusinessException e) {
            InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
        } catch (Exception e2) {
            LoggerUtil.error("order insert function - upload image error", e2);
        }
        return  result;
    }
}
