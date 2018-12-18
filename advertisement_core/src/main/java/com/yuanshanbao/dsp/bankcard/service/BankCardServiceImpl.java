package com.yuanshanbao.dsp.bankcard.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.agency.model.Agency;
import com.yuanshanbao.dsp.agency.model.vo.AgencyStatus;
import com.yuanshanbao.dsp.agency.model.vo.AgencyType;
import com.yuanshanbao.dsp.agency.service.AgencyService;
import com.yuanshanbao.dsp.bankcard.dao.BankCardDao;
import com.yuanshanbao.dsp.bankcard.model.BankCard;
import com.yuanshanbao.dsp.bankcard.model.vo.BankCardStatus;
import com.yuanshanbao.dsp.payment.PaymentInterfaceService;
import com.yuanshanbao.dsp.product.model.Product;
import com.yuanshanbao.dsp.product.service.ProductService;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.dsp.user.model.UserLevel;
import com.yuanshanbao.dsp.user.service.UserService;
import com.yuanshanbao.paginator.domain.PageBounds;

/**
 * Created by Administrator on 2018/10/23.
 */
@Service
public class BankCardServiceImpl implements BankCardService {

	public final static BigDecimal MANAGER_PERCENTAGE = BigDecimal.valueOf(0.85);
	public final static BigDecimal DIRECTOR_PERCENTAGE = BigDecimal.valueOf(0.95);

	public final static BigDecimal MANAGER_INDIRET_PERCENTAGE = BigDecimal.valueOf(0.1);
	public final static BigDecimal DIRECTOR_INDIRET_PERCENTAGE = BigDecimal.valueOf(0.15);
	public final static BigDecimal CEO_INDIRET_PERCENTAGE = BigDecimal.valueOf(0.25);

	@Autowired
	private BankCardDao bankCardDao;

	@Autowired
	private UserService userService;

	@Autowired
	private AgencyService agencyService;

	@Autowired
	private ProductService productService;

	@Autowired
	private PaymentInterfaceService paymentInterfaceService;

	@Override
	public List<BankCard> selectBankCards(BankCard bankCard, PageBounds pageBounds) {
		return bankCardDao.selectBankCards(bankCard, pageBounds);
	}

	@Override
	public List<BankCard> selectUserApplys(BankCard bankCard, PageBounds pageBounds) {
		return null;
	}

	@Transactional
	@Override
	public int insertBankCard(BankCard bankCard) {
		int result = -1;
		result = bankCardDao.insertBankCard(bankCard);
		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		} else {
			LoggerUtil.info("insertBankCard : number:" + result);
			return result;
		}

	}

	@Override
	public void getApplyBankCardInfo(User user, Long productId, String userName, String mobile) {
		Product product = productService.selectProduct(productId);
		Agency param = new Agency();
		param.setProductId(productId);
		param.setUserId(user.getUserId());
		List<Agency> agencyList = agencyService.selectAgencys(param, new PageBounds());
		User inviteUser = userService.selectUserById(user.getInviteUserId());
		if (agencyList == null || agencyList.size() == 0) {
			// 添加用户信息
			Agency agency = new Agency();
			agency.setUserId(user.getUserId());
			agency.setInviteUserId(user.getInviteUserId());
			if (inviteUser != null) {
				agency.setAgencyName(inviteUser.getDisplayName());
			}
			agency.setMobile(mobile);
			agency.setStatus(AgencyStatus.ONCHECK);
			agency.setProductName(product.getName());
			agency.setProductId(productId);
			agency.setName(userName);
			if (inviteUser != null) {
				if (inviteUser.getLevel() == null) {
					inviteUser.setLevel(UserLevel.MANAGER);
				}
				agency.setBrokerage(product.getBrokerage().multiply(DIRECTOR_PERCENTAGE));
				/*
				 * if (inviteUser != null && inviteUser.getLevel() ==
				 * UserLevel.MANAGER) {
				 * agency.setBrokerage(product.getBrokerage(
				 * ).multiply(MANAGER_PERCENTAGE)); } else if (inviteUser !=
				 * null && inviteUser.getLevel() == UserLevel.MAJORDOMO) {
				 * agency
				 * .setBrokerage(product.getBrokerage().multiply(DIRECTOR_PERCENTAGE
				 * )); } else if (inviteUser != null && inviteUser.getLevel() ==
				 * UserLevel.BAILLIFF) {
				 * agency.setBrokerage(product.getBrokerage()); } else {
				 * agency.setBrokerage
				 * (product.getBrokerage().multiply(MANAGER_PERCENTAGE)); }
				 */
			}
			agencyService.insertAgency(agency);
		} else {
			Agency updateAgency = new Agency();
			updateAgency.setId(agencyList.get(0).getId());
			updateAgency.setUserId(agencyList.get(0).getUserId());
			updateAgency.setProductId(agencyList.get(0).getProductId());
			agencyService.updateAgency(updateAgency);
		}

	}

	@Override
	public void addVIPAgentOrBankCardInfo(Long inviteUserId, Long productId, String userName, String mobile) {
		// TODO Auto-generated method stub
		Product product = productService.selectProduct(productId);
		Agency param = new Agency();
		param.setProductId(productId);
		param.setInviteUserId(inviteUserId);
		param.setMobile(mobile);
		param.setName(userName);
		List<Agency> agencyList = agencyService.selectAgencys(param, new PageBounds());
		if (agencyList.size() == 0) {
			Agency agency = new Agency();
			agency.setInviteUserId(inviteUserId);
			agency.setMobile(mobile);
			agency.setName(userName);
			agency.setType(AgencyType.AGENT_CREDIT_CARD);
			agency.setStatus(AgencyStatus.ONCHECK);
			if (product != null) {
				agency.setProductId(product.getProductId());
				agency.setProductName(product.getName());
				agency.setBrokerage(product.getBrokerage());
			}
			agencyService.insertAgency(agency);
		} else {
			Agency updateAgency = new Agency();
			updateAgency.setId(agencyList.get(0).getId());
			updateAgency.setInviteUserId(agencyList.get(0).getInviteUserId());
			updateAgency.setProductId(agencyList.get(0).getProductId());
			agencyService.updateAgency(updateAgency);
		}
	}

	@Transactional
	@Override
	public void transferUserAccount(List<BankCard> bankCardList, String productId) {
		// TODO Auto-generated method stub
		try {
			List<Agency> list = new ArrayList<Agency>();
			Product product = productService.selectProduct(Long.valueOf(productId));
			for (BankCard bank : bankCardList) {
				// 筛选批核人数
				if (bank.getStatus() == BankCardStatus.APPROVED) {
					Agency param = new Agency();
					param.setMobile(bank.getMobile().substring(7, bank.getMobile().length()));
					param.setName(bank.getName().substring(0, 1));
					param.setProductId(product.getProductId());
					param.setProductName(product.getName());
					list.addAll(agencyService.selectAgencys(param, new PageBounds()));
				}
			}
			updateAgencyStatusAndTransfer(list);
			for (BankCard bankCard : bankCardList) {
				bankCard.setBankName(product.getName());
				// bank.setProductId(Long.valueOf(productId));
				insertBankCard(bankCard);
			}

		} catch (Exception e2) {
			LoggerUtil.error("transferUserAccount transfer function - error", e2);
		}

	}

	private void updateAgencyStatusAndTransfer(List<Agency> list) {
		// 更新批卡成功状态
		// 收益金额转到账户余额
		BigDecimal indiretBrokerage = BigDecimal.valueOf(0);
		if (list.size() != 0) {
			for (Agency agen : list) {
				agen.setStatus(AgencyStatus.OFFCHECK);
				agencyService.updateAgency(agen);
				// 给办卡人上级佣金
				if (agen.getInviteUserId() != null) {
					Map<String, Object> checkResultMap = paymentInterfaceService.distribute(
							String.valueOf(agen.getInviteUserId()),
							System.nanoTime() + String.format("%06d", agen.getId()), String.valueOf(agen.getUserId()),
							agen.getBrokerage());
					Integer retCode = Integer.valueOf(checkResultMap.get("retCode").toString());
					if (retCode != null && retCode.equals(ComRetCode.SUCCESS)) {
						User user = userService.selectUserById(agen.getInviteUserId());
						if (user != null) {
							if (user.getLevel() == null) {
								user.setLevel(UserLevel.MANAGER);
							}
							if (user.getLevel() == UserLevel.MANAGER) {
								indiretBrokerage = agen.getBrokerage().multiply(MANAGER_INDIRET_PERCENTAGE);
							} else if (user.getLevel() == UserLevel.MAJORDOMO) {
								indiretBrokerage = agen.getBrokerage().multiply(DIRECTOR_INDIRET_PERCENTAGE);
							} else if (user.getLevel() == UserLevel.BAILLIFF) {
								indiretBrokerage = agen.getBrokerage().multiply(CEO_INDIRET_PERCENTAGE);
							}
							if (user.getInviteUserId() != null) {
								/**
								 * userId 邀请人id handleId agency办卡记录id orderId
								 * 当前用户id
								 */
								paymentInterfaceService.distribute(String.valueOf(user.getInviteUserId()),
										System.nanoTime() + String.format("%06d", agen.getId()),
										String.valueOf(user.getUserId()),
										indiretBrokerage.setScale(2, RoundingMode.HALF_UP));
							}

						}
					}
				}
			}
		}
	}
}
