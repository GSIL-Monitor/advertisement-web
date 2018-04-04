package com.yuanshanbao.ad.common.redis.base;

import java.math.BigDecimal;
import java.util.Map;

public interface RedisUtilService {
	void addWinningDetail2Redis(Map<String, BigDecimal> resultWinningDetail, Long gameId, String periodId);

	Map<String, Object> getReportAndHotGames(int count);
}
