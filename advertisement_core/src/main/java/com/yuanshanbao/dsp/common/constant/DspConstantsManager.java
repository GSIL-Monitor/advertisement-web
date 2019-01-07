package com.yuanshanbao.dsp.common.constant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.SortUtil;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.channel.model.Channel;
import com.yuanshanbao.dsp.channel.service.ChannelService;
import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.plan.model.Plan;
import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.dsp.probability.model.ProbabilityStatus;
import com.yuanshanbao.dsp.probability.service.ProbabilityService;
import com.yuanshanbao.dsp.quota.model.Quota;
import com.yuanshanbao.dsp.quota.model.QuotaType;
import com.yuanshanbao.dsp.quota.service.QuotaService;
import com.yuanshanbao.paginator.domain.PageBounds;

public class DspConstantsManager {

	private static Map<String, Map<Long, BigDecimal>> channelBidMap = new HashMap<String, Map<Long, BigDecimal>>();
	private static Map<String, Map<Long, BigDecimal>> hdChannelBidMap = new HashMap<String, Map<Long, BigDecimal>>();
	private static DspConstantsManager instance = null;

	@Resource
	private RedisService redisService;
	@Resource
	private ChannelService channelService;
	@Resource
	private ProbabilityService probabilityService;
	@Resource
	private QuotaService quotaService;

	public static void refresh() {
		if (instance != null) {
			instance.refreshConstants();
		}
	}

	public void init() {
		refreshConstants();
		instance = this;
	}

	private void refreshConstants() {
		LoggerUtil.info("init DspConstants");
		refreshChannelBidMap();
		refreshAdvertisementChannelMap();
	}

	private void refreshChannelBidMap() {
		Channel channelParams = new Channel();
		channelParams.setStatus(CommonStatus.ONLINE);
		List<Channel> channelList = channelService.selectChannels(channelParams, new PageBounds());
		Map<String, Map<Long, BigDecimal>> tempChannelBidMap = new HashMap<String, Map<Long, BigDecimal>>();
		for (Channel channel : channelList) {
			Map<Long, BigDecimal> probabilityBidMap = new HashMap<Long, BigDecimal>();
			Probability proParams = new Probability();
			proParams.setChannel(channel.getKey());
			proParams.setStatus(ProbabilityStatus.ONLINE);
			List<Probability> proList = probabilityService.selectProbabilitys(proParams, new PageBounds());
			if (proList == null || proList.size() == 0) {
				continue;
			}
			for (Probability probability : proList) {
				Plan plan = ConfigManager.getPlanById(probability.getPlanId());
				if (plan != null && plan.getBestBid() != null) {
					BigDecimal bestBid = plan.getBestBid();
					if (QuotaType.CPM.equals(plan.getChargeType())) {
						bestBid = bestBid.divide(new BigDecimal(1000));
					}
					probabilityBidMap.put(probability.getProbabilityId(), bestBid);
				}
			}
			if (probabilityBidMap.size() == 0) {
				continue;
			}
			Map<Long, BigDecimal> sortMap = SortUtil.sortByValueAscending(probabilityBidMap);
			// 为每一条计划设置价格
			Map<Long, BigDecimal> bidMap = setPlanBid(channel, sortMap);
			tempChannelBidMap.put(channel.getKey(), bidMap);
		}
		channelBidMap = tempChannelBidMap;
	}

	private void refreshAdvertisementChannelMap() {
		Channel channelParams = new Channel();
		channelParams.setStatus(CommonStatus.ONLINE);
		List<Channel> channelList = channelService.selectChannels(channelParams, new PageBounds());
		Map<String, Map<Long, BigDecimal>> tempChannelBidMap = new HashMap<String, Map<Long, BigDecimal>>();
		for (Channel channel : channelList) {
			Map<Long, BigDecimal> probabilityBidMap = new HashMap<Long, BigDecimal>();
			Probability proParams = new Probability();
			proParams.setChannel(channel.getKey());
			proParams.setStatus(ProbabilityStatus.ONLINE);
			List<Probability> proList = probabilityService.selectProbabilitys(proParams, new PageBounds());
			if (proList == null || proList.size() == 0) {
				continue;
			}
			List<Long> proIds = new ArrayList<Long>();
			for (Probability probability : proList) {
				proIds.add(probability.getProbabilityId());
			}
			Map<Long, Quota> quotaMap = quotaService.selectQuotaByProbabilityId(proIds);
			for (Probability probability : proList) {
				Quota quota = quotaMap.get(probability.getProbabilityId());
				if (quota == null) {
					continue;
				}
				if (quota.getUnitPrice() == null) {
					continue;
				}
				BigDecimal unitPrice = quota.getUnitPrice();
				probabilityBidMap.put(probability.getProbabilityId(), unitPrice);
			}
			if (probabilityBidMap.size() == 0) {
				continue;
			}
			Map<Long, BigDecimal> sortMap = SortUtil.sortByValueAscending(probabilityBidMap);
			tempChannelBidMap.put(channel.getKey(), sortMap);
		}
		hdChannelBidMap = tempChannelBidMap;
	}

	public static Map<Long, BigDecimal> getBidByChannel(String channel) {
		Map<Long, BigDecimal> map = channelBidMap.get(channel);
		return map;
	}

	public static Map<Long, BigDecimal> getHdBidChannelMap(String channel) {
		Map<Long, BigDecimal> map = hdChannelBidMap.get(channel);
		return map;
	}

	private Map<Long, BigDecimal> setPlanBid(Channel channel, Map<Long, BigDecimal> probabilityBidMap) {
		Map<Long, BigDecimal> bidMap = new HashMap<Long, BigDecimal>();
		// 获得每一个probability的Ctr
		Map<Long, Double> probabilityExpectMap = new HashMap<Long, Double>();
		Map<Long, Double> probabilityCtrMap = new HashMap<Long, Double>();
		Double expect = new Double(0);
		for (Long probabilityId : probabilityBidMap.keySet()) {
			String ctrValue = redisService.get(RedisConstant.getProbabilityChannelCTRKey(probabilityId));
			if (ctrValue == null || !ValidateUtil.isDouble(ctrValue)) {
				ctrValue = "1";
			}
			expect = Double.valueOf(ctrValue) * probabilityBidMap.get(probabilityId).doubleValue();
			probabilityExpectMap.put(probabilityId, expect);
			probabilityCtrMap.put(probabilityId, Double.valueOf(ctrValue));
		}
		Map<Long, Double> sortExpectMap = SortUtil.sortByValueDescending(probabilityExpectMap);
		List<Long> probabilityIdList = new ArrayList<Long>();
		for (Map.Entry<Long, Double> entry : sortExpectMap.entrySet()) {
			probabilityIdList.add(entry.getKey());
		}
		int size = probabilityIdList.size();
		for (int i = 0; i < size; i++) {
			int j = i + 1;
			if (j > size - 1) {
				bidMap.put(probabilityIdList.get(i), probabilityBidMap.get(probabilityIdList.get(i)));
				break;
			}
			bidMap.put(
					probabilityIdList.get(i),
					BigDecimal.valueOf(sortExpectMap.get(probabilityIdList.get(j))
							/ probabilityCtrMap.get(probabilityIdList.get(i))));
		}
		return bidMap;
	}
}
