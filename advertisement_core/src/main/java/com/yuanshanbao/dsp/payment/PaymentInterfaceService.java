package com.yuanshanbao.dsp.payment;

import com.yuanshanbao.dsp.order.model.Order;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface PaymentInterfaceService {

    // 预支付Token
    Map<String, Object> getPrePayToken(Order order, String paymethod);

    Map<String, Object> queryBalance(String userId);

    Map<String, Object> queryBillList(String userId, int page);

    Map<String, Object> verifyIdentity(String userId, String mobile, String name, String identityCard, String userIp);

    Map<String, Object> queryIdentity(String userId);

    Map<String, Object> queryBankCard(String userId);

    Map<String, Object> bindBankCard(String userId, String bankCardNumber, String userIp);

    Map<String, Object> withdraw(String userId, String withdrawAmount, String userIp);

    Map<String, Object> refund(String userId, String orderId, String refundId, BigDecimal refundAmount,
                               String refundReason, int refundType);

    Map<String, Object> charge(String userId, String chargeAmount, String paymethod, String userIp);

    Map<String, Object> queryOrderPay(String userId, String orderId);

    Map<String, Object> queryChargePay(String userId, String chargeId);

    Map<String, Object> distribute(String userId, String handleId, String orderId, BigDecimal trueAmount);

    Map<String, Object> queryPaymethod(String userId);

    Map<String, Object> reconciliationAmount(Timestamp startTime, Timestamp endTime);

    Map<String, Object> reconciliationOrderDetail(List<Order> orderList);

}
