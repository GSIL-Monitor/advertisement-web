package com.yuanshanbao.dsp.quota.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.CommonUtil;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.quota.dao.QuotaDao;
import com.yuanshanbao.dsp.quota.model.Quota;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class QuotaServiceImpl implements QuotaService {

	@Autowired
	private QuotaDao quotaDao;

	@Override
	public void insertQuota(Quota quota) {
		int result = -1;

		result = quotaDao.insertQuota(quota);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateQuota(Quota quota) {
		int result = -1;

		result = quotaDao.updateQuota(quota);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteQuota(Quota quota) {
		int result = -1;

		result = quotaDao.deleteQuota(quota);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public List<Quota> selectQuota(Quota quota, PageBounds pageBounds) {
		return quotaDao.selectQuota(quota, pageBounds);
	}

	@Override
	public Quota selectQuota(Long quotaId) {
		if (quotaId == null) {
			return null;
		}
		Quota quota = new Quota();
		quota.setQuotaId(quotaId);
		List<Quota> list = selectQuota(quota, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<Long, Quota> selectQuotaByIds(List<Long> quotaIds) {
		Map<Long, Quota> map = new HashMap<Long, Quota>();
		if (quotaIds == null || quotaIds.size() == 0) {
			return map;
		}
		List<Quota> list = quotaDao.selectQuotaByIds(quotaIds);
		for (Quota quota : list) {
			map.put(quota.getQuotaId(), quota);
		}
		return map;
	}

	@Override
	public List<Quota> selectQuotaFromCache(Long projectId, Long positionId, List<Long> advertisementIdList) {
		List<Quota> resultList = new ArrayList<Quota>();
		if (projectId == null) {
			return resultList;
		}
		List<Quota> quotaList = ConstantsManager.getQuotaList(projectId);
		if (quotaList == null) {
			return resultList;
		}
		for (Quota quota : quotaList) {
			if (!CommonUtil.isNullOrEquals(quota.getPositionId(), positionId)) {
				continue;
			}
			if (advertisementIdList == null || advertisementIdList.contains(quota.getAdvertisementId())) {
				resultList.add(quota);
			}
		}
		return resultList;
	}

}
