package com.yuanshanbao.dsp.controller.agency;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.agency.model.Agency;
import com.yuanshanbao.dsp.agency.service.AgencyService;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.user.service.UserService;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.dsp.user.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/22.
 */
@Controller
@RequestMapping("/agency")
public class AgencyController extends BaseController {

    @Autowired
    private AgencyService agencyService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;


    @RequestMapping("/list")
    @ResponseBody
    public Object getAgencyList(HttpServletRequest request, Agency agency, PageBounds pageBounds, String token) {

        Map<Object, Object> resultMap = new HashMap<>();

        List<Agency> twoAgencyList = null;
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
            agency.setInviteUserId(user.getUserId());

            List<Agency> oneAgencyList = agencyService.selectAgencys(agency, pageBounds);
            if (oneAgencyList.size() == 0)
                throw new BusinessException(ComRetCode.NO_AGENCY);
            for (Agency oneAgency : oneAgencyList) {
                BigDecimal oneBrikerage = BigDecimal.valueOf(0);
                BigDecimal twoBrikerage = BigDecimal.valueOf(0);
                BigDecimal sumOneBrikerage = BigDecimal.valueOf(0);
                BigDecimal sumTwoBrikerage = BigDecimal.valueOf(0);
                if (oneAgency != null) {
                    oneBrikerage = agencyService.getAgencyBrokerage(oneAgency.getUserId());
                    if (oneBrikerage == null)
                        oneBrikerage = BigDecimal.valueOf(0);
                    sumOneBrikerage = sumOneBrikerage.add(oneBrikerage);
                    agency.setInviteUserId(oneAgency.getUserId());
                    twoAgencyList = agencyService.selectAgencys(agency, pageBounds);
                    if (twoAgencyList.size() == 0)
                        continue;
                }
                for (Agency twoAgency : twoAgencyList) {
                    twoBrikerage = agencyService.getAgencyBrokerage(twoAgency.getUserId());
                    if (twoBrikerage == null) {
                        twoBrikerage = BigDecimal.valueOf(0);
                    }
                    sumTwoBrikerage = sumTwoBrikerage.add(twoBrikerage);
                }
                BigDecimal sum = sumOneBrikerage.add(sumTwoBrikerage);
                resultMap.put(oneAgency.getUserId(), sum);

            }

        } catch (BusinessException e) {
            InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
        } catch (Exception e) {
            LoggerUtil.error("[productList error:]", e);
            InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
        }
        return resultMap;
    }

    @RequestMapping("/award")
    @ResponseBody
    public Object getAwardList(HttpServletRequest request, Agency agency, PageBounds pageBounds) {
        Map<String, Object> resultMap = new HashMap<>();
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
            agency.setInviteUserId(user.getUserId());
            List<Agency> oneAgencyList = agencyService.selectAgencys(agency, pageBounds);
            if (oneAgencyList.size() == 0) {
                throw new BusinessException(ComRetCode.NO_AGENCY);
            }
            resultMap.put("oneAgencyList", oneAgencyList);
            //一级代理列表
            for (Agency oneAgency : oneAgencyList) {
                BigDecimal sumTwoAgencyBrokerage = BigDecimal.valueOf(0);
                BigDecimal sumOneAgencyBrokerage = BigDecimal.valueOf(0);
                BigDecimal oneAgencyBrokerage = agencyService.getAgencyBrokerage(oneAgency.getInviteUserId());
                sumOneAgencyBrokerage = sumOneAgencyBrokerage.add(oneAgencyBrokerage);
                agency.setInviteUserId(oneAgency.getUserId());
                List<Agency> twoAgencyList = agencyService.selectAgencys(agency, pageBounds);
                if (twoAgencyList.size() == 0) {
                    break;
                }
                for (Agency twoAgency : twoAgencyList) {
                    BigDecimal twoAgencyBrokerage = agencyService.getAgencyBrokerage(twoAgency.getInviteUserId());
                    sumTwoAgencyBrokerage = sumTwoAgencyBrokerage.add(twoAgencyBrokerage);
                }
                resultMap.put("twoAgencyList", twoAgencyList);
                BigDecimal sumAgencyBrokerage = sumOneAgencyBrokerage.add(sumTwoAgencyBrokerage);
                resultMap.put("brokerage", sumAgencyBrokerage);
            }


        } catch (BusinessException e) {
            InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
        } catch (Exception e) {
            LoggerUtil.error("[productList error:]", e);
            InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
        }
        return resultMap;
    }

}
