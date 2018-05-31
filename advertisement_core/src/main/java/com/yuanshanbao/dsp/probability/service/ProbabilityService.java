package com.yuanshanbao.dsp.probability.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yuanshanbao.dsp.probability.model.Probability;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface ProbabilityService {

	public List<Probability> selectProbabilitys(Probability probability, PageBounds pageBounds);

	public List<Probability> selectGeneralProbabilitys(Probability probability, PageBounds pageBounds);

	public Probability selectProbability(Long probabilityId);

	public void insertProbability(Probability probability);

	public void deleteProbability(Long probabilityId);

	public void updateProbability(Probability probability);

	public void insertOrUpdateProbability(Probability probability);

	public Map<Long, Probability> selectProbabilitys(List<Long> probabilityIdList);

	public List<Probability> selectProbabilitys(HttpServletRequest request, String activityKey, String channel);

	public Probability pickPrize(HttpServletRequest request, String activityKey, String channel,
			List<Long> pickedPrizeIdList);

	public List<Probability> selectProbabilityFromCache(Long projectId, Long positionId, List<Long> advertisementIdList);

	public List<Probability> selectProbabilityByKeyFromCache(Long projectId, String activityKey, String channelKey,
			List<Long> advertisementIdList);
}
