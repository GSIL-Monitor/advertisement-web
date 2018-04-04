package com.yuanshanbao.ms.filter.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class SysSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private static final Logger logger = Logger.getLogger(SysSecurityMetadataSource.class);

	private PathMatcher pathMatcher = new AntPathMatcher();
	
	@Autowired
	private SysRightsInfo sysRightsInfo;
	
	public void loadRightsMap() {
		sysRightsInfo.reloadRightsMap();
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	// 返回所请求资源所需要的权限
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		if (logger.isDebugEnabled()) {
			logger.debug("getAttributes(Object) - start");
		}
		// guess object is a URL.
		String url = ((FilterInvocation) object).getRequestUrl();
		
		if (url != null && url.indexOf("?") > 0) {
			url = url.substring(0, url.indexOf("?"));
		}
		
		Map<String, ArrayList<ConfigAttribute>> sysRightsMap = sysRightsInfo.getSysRightsMap();
		
		Iterator<String> ite = sysRightsMap.keySet().iterator();
		while (ite.hasNext()) {
			String resURL = ite.next();
			if (pathMatcher.matchStart(resURL, url)) {
				Collection<ConfigAttribute> returnCollection = sysRightsMap.get(resURL);
				if (logger.isDebugEnabled()) {
					logger.debug("URL need right"); 
				}
				return returnCollection;
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getAttributes(Object) - end"); //$NON-NLS-1$  
		}
		
		return new ArrayList<ConfigAttribute>();
	}

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return true;
	}
}