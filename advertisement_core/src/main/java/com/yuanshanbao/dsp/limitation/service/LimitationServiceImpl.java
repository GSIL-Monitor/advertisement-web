package com.yuanshanbao.dsp.limitation.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.limitation.dao.LimitationDao;
import com.yuanshanbao.dsp.limitation.model.Limitation;
import com.yuanshanbao.dsp.limitation.model.LimitationType;
import com.yuanshanbao.dsp.merchant.model.Merchant;
import com.yuanshanbao.dsp.merchant.service.MerchantService;
import com.yuanshanbao.dsp.quota.model.Quota;
import com.yuanshanbao.dsp.statistics.dao.StatisticsDao;
import com.yuanshanbao.dsp.statistics.model.Statistics;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class LimitationServiceImpl implements LimitationService {

	@Autowired
	private LimitationDao limitationDao;

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private StatisticsDao statisticsDao;

	@Override
	public List<Limitation> selectLimitations(Limitation limitation, PageBounds pageBounds) {
		return setProperty(limitationDao.selectLimitations(limitation, pageBounds));
	}

	private List<Limitation> setProperty(List<Limitation> list) {
		List<Long> idList = new ArrayList<Long>();
		for (Limitation limitation : list) {
			idList.add(limitation.getMerchantId());
		}
		Map<Long, Merchant> merchantMap = merchantService.selectMerchantByIds(idList);
		for (Limitation limitation : list) {
			limitation.setMerchant(merchantMap.get(limitation.getMerchantId()));
		}
		return list;
	}

	@Override
	public List<Limitation> selectLimitationsWithStock(Limitation limitation, PageBounds pageBounds) {
		return limitationDao.selectLimitationsWithStock(limitation, pageBounds);
	}

	@Override
	public Limitation selectLimitation(Long limitationId) {
		Limitation limitation = new Limitation();
		if (limitationId == null) {
			return null;
		}
		limitation.setLimitationId(limitationId);
		List<Limitation> list = selectLimitations(limitation, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertLimitation(Limitation limitation) {
		int result = -1;

		result = limitationDao.insertLimitation(limitation);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteLimitation(Long limitationId) {
		int result = -1;

		result = limitationDao.deleteLimitation(limitationId);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateLimitation(Limitation limitation) {
		int result = -1;

		result = limitationDao.updateLimitation(limitation);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	/**
	 * @param limitation
	 * @return
	 * @throws IOException
	 */
	@Override
	public void insertOrUpdateLimitation(Limitation limitation) {
		if (limitation.getLimitationId() == null) {
			insertLimitation(limitation);
		} else {
			updateLimitation(limitation);
		}
	}

	@Override
	public Map<Long, Limitation> selectLimitations(List<Long> limitationIdList) {
		Map<Long, Limitation> map = new HashMap<Long, Limitation>();
		if (limitationIdList == null || limitationIdList.size() == 0) {
			return map;
		}
		List<Limitation> list = limitationDao.selectLimitations(limitationIdList);
		for (Limitation limitation : list) {
			map.put(limitation.getLimitationId(), limitation);
		}
		return map;
	}

	@Transactional
	@Override
	public boolean lockStock(Quota quota) {
		Limitation limitation = new Limitation();
		limitation.setMerchantId(quota.getMerchantId());
		List<Limitation> limitationList = selectLimitations(limitation, new PageBounds());
		if (limitationList.size() == 0) {
			return true;
		}
		Map<String, String> map = new HashMap<String, String>();
		for (Limitation limit : limitationList) {
			if (limit.equalsQuotaCondition(quota) && limit.getStatus().equals(LimitationType.REJECT)) {
				map.put(limit.getProductId() + limit.getLocationCode(), "limit");
			}
		}

		for (Limitation limit : limitationList) {
			if (limit.equalsQuotaCondition(quota)) {
				if (StringUtils.isNotBlank(map.get(limit.getInsuranceId() + limit.getLocationCode()))) {
					continue;
				}
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("limitationId", limit.getLimitationId());
				parameters.put("count", 1);
				int result = limitationDao.lockStock(parameters);
				if (result <= 0) {
					throw new BusinessException(ComRetCode.ORDER_LOCK_STOCK_FAIL_ERROR);
				}
				return true;
			}
		}
		return false;
	}

	@Transactional
	@Override
	public boolean restoreStock(Quota quota) {
		Limitation limitation = new Limitation();
		limitation.setMerchantId(quota.getMerchantId());
		List<Limitation> limitationList = selectLimitations(limitation, new PageBounds());
		if (limitationList.size() == 0) {
			return true;
		}
		for (Limitation limit : limitationList) {
			if (limit.equalsQuotaCondition(quota)) {
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("limitationId", limit.getLimitationId());
				parameters.put("count", 1);
				limitationDao.restoreStock(parameters);
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Limitation> selectAllLimitations(Limitation limitation, Statistics statistics, PageBounds pageBounds) {
		// List<Limitation> limitationList =
		// limitationDao.selectAllLimitations(limitation, pageBounds);
		// if (statistics != null && statistics.getQueryStartTime() != null) {
		// List<Statistics> statisticsList =
		// statisticsDao.selectEffectCountByProvince(statistics, pageBounds);
		// for (Limitation limit : limitationList) {
		// for (Statistics state : statisticsList) {
		// if (limit.getLocationCode().equals(state.getProvince())) {
		// limit.setCompletedCount(state.getEffectCount() +
		// limit.getCompletedCount());
		// }
		// if (limit.getLocationCode().equals(state.getCity())) {
		// limit.setCompletedCount(state.getEffectCount());
		// break;
		// }
		// }
		// }
		// }
		// return setAllProperty(limitationList);
		return null;
	}

	private List<Limitation> setAllProperty(List<Limitation> list) {
		// List<Long> idList = new ArrayList<Long>();
		// List<Long> insuranceIds = new ArrayList<Long>();
		// for (Limitation limitation : list) {
		// idList.add(limitation.getMerchantId());
		// insuranceIds.add(limitation.getInsuranceId());
		// }
		// Map<Long, Merchant> merchantMap =
		// merchantService.selectMerchantByIds(idList);
		// Map<Long, Insurance> insuranceMap =
		// insuranceService.selectInsurances(insuranceIds);
		// for (Limitation limitation : list) {
		// limitation.setMerchant(merchantMap.get(limitation.getMerchantId()));
		// limitation.setInsurance(insuranceMap.get(limitation.getInsuranceId()));
		// }
		// return list;
		return null;
	}

	@Override
	public List<Long> selectInsuranceIdsByLimitation(Limitation limitation) {
		return limitationDao.selectInsuranceIdsByLimitation(limitation);
	}

	@Override
	public Limitation selectLimitationById(Long limitationId) {
		Limitation limitation = new Limitation();
		limitation.setLimitationId(limitationId);
		List<Limitation> limitations = selectAllLimitations(limitation, null, new PageBounds());
		if (limitations != null && limitations.size() > 0) {
			return limitations.get(0);
		}
		return null;
	}
}
