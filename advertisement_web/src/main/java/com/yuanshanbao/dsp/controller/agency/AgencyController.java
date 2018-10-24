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

        Map<String, Object> resultMap = new HashMap<>();
        Map<Agency, BigDecimal> AgenyMap = new HashMap<>();
        try {
            //获取当前用户信息
          /*  User loginToken = tokenService.verifyLoginToken(token);
            if (loginToken == null) {
                throw new BusinessException();
            }
            User user = userService.selectUserById(loginToken.getUserId());*/
            User user = userService.selectUserById((long) 2);
            if (user == null || user.getUserId() == 0) {
                throw new BusinessException();
            } else {
                agency.setInviteUserId(user.getUserId());
                //获得用户的代理人列表
                List<Agency> oneAgencyList = agencyService.selectAgencys(agency, pageBounds);
                if (oneAgencyList.size() == 0)
                    throw new BusinessException(ComRetCode.NO_AGENCY);
                BigDecimal oneCommission = BigDecimal.valueOf(0);
                BigDecimal twoCommission = BigDecimal.valueOf(0);

                for (int i = 0; i < oneAgencyList.size(); i++) {
                    if (oneAgencyList.get(i).getUserId() != null) {
                        oneCommission = agencyService.getAgencyCommission(oneAgencyList.get(i).getUserId());
                        if (oneCommission == null)
                            oneCommission = BigDecimal.valueOf(0);
                        agency.setInviteUserId(oneAgencyList.get(i).getUserId());
                        List<Agency> twoAgencyList = agencyService.selectAgencys(agency, pageBounds);
                        if (twoAgencyList.size() == 0)
                            continue;
                        for (int j = 0; j <=twoAgencyList.size(); j++) {
                            twoCommission = agencyService.getAgencyCommission(twoAgencyList.get(i).getUserId());
                            if (twoCommission == null) {
                                twoCommission = BigDecimal.valueOf(0);
                            }
                        }
                    }
                    BigDecimal addCommission = oneCommission.add(twoCommission);
                    AgenyMap.put(oneAgencyList.get(i), addCommission);
                }

                return AgenyMap;
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
                throw new BusinessException();
            }
            agency.setInviteUserId(user.getUserId());
            List<Agency> oneAgencyList = agencyService.selectAgencys(agency, pageBounds);
            if (oneAgencyList.size() == 0 ){
                throw new BusinessException(ComRetCode.NO_AGENCY);
            }
            resultMap.put("oneAgencyList", oneAgencyList);
            //一级代理列表
            for (Agency oneAgency : oneAgencyList) {
                agency.setInviteUserId(oneAgency.getUserId());
                List<Agency> twoAgencyList = agencyService.selectAgencys(agency, pageBounds);
                if (twoAgencyList.size() == 0)
                    throw new BusinessException(ComRetCode.NO_AGENCY);
                resultMap.put("twoAgencyList", twoAgencyList);
                for (Agency twoAgency : twoAgencyList) {
                    agency.setInviteUserId(twoAgency.getUserId());
                    List<Agency> threeAgencyList = agencyService.selectAgencys(agency, pageBounds);
                    resultMap.put("threeAgencyList", threeAgencyList);
                }
            }
        } catch (BusinessException e) {
            InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
        } catch (Exception e) {
            LoggerUtil.error("[productList error:]", e);
            InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
        }
        return resultMap;
    }

    public static void main(String[] args) {

        List<BigDecimal> list = new ArrayList<>();
        List<BigDecimal> bigDecimals = new ArrayList<>();
        list.add(BigDecimal.valueOf(12));
        list.add(BigDecimal.valueOf(12));
        list.add(BigDecimal.valueOf(10));
        list.add(BigDecimal.valueOf(20));
        bigDecimals.add(BigDecimal.valueOf(12));
        bigDecimals.add(BigDecimal.valueOf(12));


    }

}
