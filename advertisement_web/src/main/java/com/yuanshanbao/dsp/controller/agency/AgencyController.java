package com.yuanshanbao.dsp.controller.agency;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.agency.model.Agency;
import com.yuanshanbao.dsp.agency.model.vo.AgencyVo;
import com.yuanshanbao.dsp.agency.service.AgencyService;
import com.yuanshanbao.dsp.controller.base.BaseController;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.dsp.user.service.TokenService;
import com.yuanshanbao.dsp.user.service.UserService;
import com.yuanshanbao.paginator.domain.PageBounds;
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
            List<Agency> agencyList = agencyService.selectAgencys(agency, pageBounds);
            List<AgencyVo> agencyVoList = new ArrayList<>();
            for (Agency agen : agencyList) {
                AgencyVo agencyVo = new AgencyVo();
                agency.setInviteUserId(agen.getUserId());
                BigDecimal brokerages = agencyService.getBrokerages(agency, pageBounds);
                agencyVo.setInviteUserId(agen.getUserId());
                agencyVo.setUserName(agen.getUserName());
                agencyVo.setProductName(agen.getProductName());
                agencyVo.setInviteTime(agen.getInviteTime());
                agencyVo.setStatus(agen.getStatus());
                agencyVo.setBrokerage(brokerages);
                agencyVoList.add(agencyVo);
            }
            resultMap.put("agencyLlist", agencyVoList);

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
            //总佣金
            BigDecimal brokerages = agencyService.getBrokerages(agency, pageBounds);
            List<Agency> oneAgencyList = agencyService.selectAgencys(agency, pageBounds);
            List<Agency> twoAgencyList = new ArrayList<>();
            for (Agency agen : oneAgencyList) {
                agency.setInviteUserId(agen.getUserId());
                twoAgencyList = agencyService.selectAgencys(agency, new PageBounds());
            }
            resultMap.put("oneAgencyList", oneAgencyList);
            resultMap.put("twoAgencyList", twoAgencyList);
            resultMap.put("brokerage", brokerages);
        } catch (BusinessException e) {
            InterfaceRetCode.setAppCodeDesc(resultMap, e.getReturnCode(), e.getMessage());
        } catch (Exception e) {
            LoggerUtil.error("[productList error:]", e);
            InterfaceRetCode.setAppCodeDesc(resultMap, ComRetCode.FAIL);
        }
        return resultMap;
    }


}
