package com.yuanshanbao.dsp.information.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.CommonUtil;
import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.thread.ThreadPool;
import com.yuanshanbao.dsp.channel.model.Channel;
import com.yuanshanbao.dsp.channel.model.ChannelProcedure;
import com.yuanshanbao.dsp.channel.service.ChannelProcedureService;
import com.yuanshanbao.dsp.channel.service.ChannelService;
import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.config.ConfigWrapper;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.IniBean;
import com.yuanshanbao.dsp.information.dao.InformationDao;
import com.yuanshanbao.dsp.information.model.Information;
import com.yuanshanbao.dsp.information.model.InformationStatus;
import com.yuanshanbao.dsp.notify.information.InformationDeliverNotify;
import com.yuanshanbao.dsp.notify.information.ManualInformationDeliverNotify;
import com.yuanshanbao.dsp.partner.agent.AbstractAgentNotifyHandler;
import com.yuanshanbao.dsp.product.model.Product;
import com.yuanshanbao.dsp.product.service.ProductService;
import com.yuanshanbao.dsp.project.service.ProjectService;
import com.yuanshanbao.dsp.share.model.Share;
import com.yuanshanbao.dsp.share.service.ShareService;
import com.yuanshanbao.dsp.sms.service.MessageSender;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class InformationServiceImpl implements InformationService {

	@Autowired
	private InformationDao informationDao;

	@Autowired
	private ChannelProcedureService informationProcedureService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ExtendInfoService extendInfoService;

	@Autowired
	private MessageSender messageSender;

	@Autowired
	private ShareService shareService;

	@Autowired
	private RedisService redisService;

	@Autowired
	private ChannelService channelService;

	@Override
	public List<Information> selectInformations(Information information, PageBounds pageBounds) {
		return informationDao.selectInformations(information, pageBounds);
	}

	private List<Information> setProperties(List<Information> informationList) {
		List<String> channelIdList = new ArrayList<String>();
		for (Information information : informationList) {
			channelIdList.add(information.getChannel());
		}
		Map<String, List<ChannelProcedure>> procedureMap = informationProcedureService
				.selectChannelProcedureByChannelIds(channelIdList);
		for (Information information : informationList) {
			information.setProcedureList(procedureMap.get(information.getChannel()));
		}
		return informationList;
	}

	@Override
	public Information selectInformation(Long informationId) {
		Information information = new Information();
		if (informationId == null) {
			return null;
		}
		information.setInformationId(informationId);
		List<Information> list = selectInformations(information, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertInformation(Information information) {
		int result = -1;

		result = informationDao.insertInformation(information);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteInformation(Long informationId) {
		int result = -1;

		result = informationDao.deleteInformation(informationId);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateInformation(Information information) {
		int result = -1;

		result = informationDao.updateInformation(information);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	/**
	 * @param information
	 * @return
	 * @throws IOException
	 */
	@Override
	public void insertOrUpdateInformation(Information information) {
		if (information.getInformationId() == null) {
			insertInformation(information);
		} else {
			updateInformation(information);
		}
	}

	@Override
	public Map<Long, Information> selectInformationByIds(List<Long> informationIdList) {
		Map<Long, Information> map = new HashMap<Long, Information>();
		if (informationIdList == null || informationIdList.size() == 0) {
			return map;
		}
		List<Information> list = informationDao.selectInformations(informationIdList);
		for (Information information : list) {
			map.put(information.getInformationId(), information);
		}
		return map;
	}

	@Override
	public Information selectInformationByUserId(Long userId) {
		Information information = new Information();
		information.setUserId(userId);
		List<Information> list = selectInformations(information, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void checkExist(Information information) {
		Information params = new Information();
		params.setType(information.getType());
		params.setMobile(information.getMobile());
		params.setMerchantId(information.getMerchantId());
		List<Information> informationList = selectInformations(params, new PageBounds());
		for (Information insu : informationList) {
			if (DateUtils.getDiffDays(insu.getCreateTime(), new Date()) < 180) {
				throw new BusinessException(ComRetCode.ORDER_MOBILE_HAS_EXIST_ERROR);
			}
		}
		if (StringUtils.isNotBlank(information.getIdentityCard())) {
			params = new Information();
			params.setType(information.getType());
			params.setIdentityCard(information.getIdentityCard());
			informationList = selectInformations(params, new PageBounds());
			for (Information insu : informationList) {
				if (DateUtils.getDiffDays(insu.getCreateTime(), new Date()) < 180) {
					throw new BusinessException(ComRetCode.ORDER_IDENTITY_CARD_HAS_EXIST_ERROR);
				}
			}
		}
	}

	@Override
	public void tryDeliver(Information information, boolean changeGoods, boolean forceDeliver) {
		notifyDeliver(information, forceDeliver);
	}

	private boolean notifyDeliver(Information information, boolean forceDeliver) {
		LoggerUtil.order("开始通知发货, informationId={}", information.getInformationId());
		boolean gorderResult = true;
		try {
			boolean result;
			Product product = productService.selectProduct(information.getProductId());
			String deliverOrderUrl = product.getDeliverOrderUrl();
			if (StringUtils.isNotBlank(deliverOrderUrl) && !isTestInformation(information.getName())) {
				InformationDeliverNotify notify = (InformationDeliverNotify) Class.forName(deliverOrderUrl)
						.newInstance();
				if (forceDeliver) {
					notify.setForceDeliver();
				}
				result = notify.deliver(information);
			} else {
				result = new ManualInformationDeliverNotify().deliver(information);
			}
			if (!result) {
				gorderResult = false;
			}
		} catch (BusinessException be) {
			throw be;
		} catch (Exception e) {
			LoggerUtil.error("notifyDeliver", e);
			throw new BusinessException(ComRetCode.ORDER_DELIVER_ERROR);
		}
		if (gorderResult) {
			information.setStatus(InformationStatus.SUBMIT_SUCCESS_STATE);
			updateInformation(information);
			sendSms(information);
			updateShareDetails(information);
			notifyAgent(information);
		}
		return gorderResult;
	}

	@Override
	public void notifyAgent(final Information information) {
		final Channel channel = ConfigWrapper.getChannel(information.getChannel());
		if (channel != null && StringUtils.isNotBlank(channel.getNotifyHandler())) {
			if (checkChannelBonus(information) == ComRetCode.SUCCESS) {
				try {
					ThreadPool.getInstance().exec(new Runnable() {
						@Override
						public void run() {
							try {
								Information currentInformation = information;
								if (information.getInformationId() != null) {
									currentInformation = selectInformation(information.getInformationId());
								}
								AbstractAgentNotifyHandler notifyHandler = (AbstractAgentNotifyHandler) Class.forName(
										channel.getNotifyHandler()).newInstance();
								// notifyHandler.setInsurantService(insurantService);
								notifyHandler.setRedisCacheService(redisService);
								// notifyHandler.setInsurantReturnLogService(insurantReturnLogService);
								notifyHandler.notifyAgent(currentInformation);
							} catch (Exception e) {
								LoggerUtil.error("[notifyAgent]", e);
							}
						}
					});
				} catch (Exception e) {
					LoggerUtil.error("[notifyAgent]", e);
				}
			}
		}
	}

	public int checkChannelBonus(Information information) {
		Channel channel = channelService.selectChannel(information.getChannel());
		if (channel == null) {
			return ComRetCode.SUCCESS;
		}
		Double bonus = channel.getBonus();
		if (bonus == null) {
			bonus = 1D;
		}
		int returnCode = ComRetCode.SUCCESS;
		return returnCode;
	}

	private boolean isTestInformation(String name) {
		String names = IniBean.getIniValue("test_insurant_names");
		if (StringUtils.isNotBlank(names) && names.contains(name)) {
			return true;
		}
		return false;
	}

	private void sendSms(Information information) {
		try {
			if (!ConfigWrapper.isSendSms(information.getActivityId(), information.getChannel(),
					information.getMerchantId(), null)
					|| "dev".equals(CommonUtil.getEnvironment())
					|| information.getStatus() != InformationStatus.SUBMIT_SUCCESS_STATE) {
				return;
			}

			if (!checkSendSms(information)) {
				return;
			}
			String merchantName = "VIPKid";
			String telephone = "";
			// if (information.getMerchant() != null) {
			// merchantName = insurance.getMerchant().getName();
			// telephone = insurance.getMerchant().getTelephone();
			// }
			String template = ConfigWrapper.getSmsTemplate(information);
			if (template.startsWith("SMS_")) {
				messageSender.sendSmsAli(template, information.getMobile(), information.getName(), merchantName, null,
						telephone);
			} else {
				messageSender.sendSmsChuangLan(information.getMobile(),
						messageSender.format(template, information.getName(), merchantName, null, telephone));
			}
		} catch (Exception e) {
			LoggerUtil.error("[sendSms]", e);
		}
	}

	private boolean checkSendSms(Information information) {
		return true;
	}

	private void updateShareDetails(Information information) {
		if (StringUtils.isBlank(information.getShareMobile())) {
			return;
		}
		Share share = new Share();
		share.setMobile(information.getMobile());
		share.setShareMobile(information.getShareMobile());
		share.setActivityId(Long.valueOf(information.getShareActivityId()));
		share.setStatus(CommonStatus.ONLINE);
		shareService.insertOrUpdateShare(share);
	}
}
