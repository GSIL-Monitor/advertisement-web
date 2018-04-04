package com.yuanshanbao.ms.filter.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;

import com.yuanshanbao.ms.model.admin.Right;
import com.yuanshanbao.ms.service.admin.RightService;
import com.yuanshanbao.ms.service.cache.RightsMapCacheService;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class SysRightsInfo {
	private static final Logger logger = Logger.getLogger(SysSecurityMetadataSource.class);

	@Autowired
	private RightService rightService;
	
	@Autowired
	private RightsMapCacheService rightsMapCacheService;

	@SuppressWarnings("unchecked")
	public Map<String, ArrayList<ConfigAttribute>> getSysRightsMap() {
		Object obj = rightsMapCacheService.query();
		
		if (obj != null) {
			return (Map<String, ArrayList<ConfigAttribute>>) obj;
		} else {
			return reloadRightsMap();
		}
	}
	
	public Map<String, ArrayList<ConfigAttribute>> reloadRightsMap() {
		HashMap<String, ArrayList<ConfigAttribute>> result = new HashMap<String, ArrayList<ConfigAttribute>>();
		
		// 通过数据库中的信息设置，resouce和role
		logger.info("begin reload system rights map.");
		for (Right right : rightService.queryRights(new Right(), new PageBounds())) {
			ArrayList<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
			ConfigAttribute ca = new SecurityConfig(right.getId());
			atts.add(ca);
			result.put(right.getUrl(), atts);
			logger.debug("reloading system rights map ->[" + right.getId() + ":" + right.getUrl() + "]");
		}
		
		logger.info("complete reload system rights map.");
		
		rightsMapCacheService.insert(result);
		
		return result;
	}
}
