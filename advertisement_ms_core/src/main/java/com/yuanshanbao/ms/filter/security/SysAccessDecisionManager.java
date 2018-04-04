package com.yuanshanbao.ms.filter.security;

import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.FilterInvocation;

public class SysAccessDecisionManager implements AccessDecisionManager {

	private static final Logger logger = Logger.getLogger("access");

	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		long start = System.currentTimeMillis();
		if (configAttributes == null) {
			logger.info(object.toString() + " need role is null!");
			return;
		}

		Iterator<ConfigAttribute> ite = configAttributes.iterator();
		while (ite.hasNext()) {
			ConfigAttribute ca = ite.next();
			String needRole = ((SecurityConfig) ca).getAttribute();
			logger.info("Authentication list:" + authentication.getAuthorities().size());
			logger.info("Need Role: " + needRole);
			for (GrantedAuthority ga : authentication.getAuthorities()) {
				if (needRole.equals(ga.getAuthority())) { // ga is user's role.
					if (logger.isInfoEnabled()) {
						if (object instanceof FilterInvocation && authentication.getPrincipal() instanceof User) {
							User user = (User) authentication.getPrincipal();
							FilterInvocation fi = (FilterInvocation) object;
							logger.info("[user:" + user.getUsername() + ";url:" + fi.getFullRequestUrl() + ";"
									+ "result:success;]");
						} else {
							logger.info("[user:" + authentication.getPrincipal() + ";url:" + object.toString() + ";"
									+ "result:success;]");
						}
					}
					logger.info("login time:" + (System.currentTimeMillis() - start));
					return;
				}
			}
		}

		if (object instanceof FilterInvocation && authentication.getPrincipal() instanceof User) {
			User user = (User) authentication.getPrincipal();
			FilterInvocation fi = (FilterInvocation) object;
			logger.info("[user:" + user.getUsername() + ";url:" + fi.getFullRequestUrl() + ";"
					+ "result:fail;reson:no permission;]");
		} else {
			logger.info("[user:" + authentication.getPrincipal() + ";url:" + object.toString() + ";"
					+ "result:fail;reson:no permission;]");
		}
		// if (authentication.getPrincipal().equals("anonymousUser")) {
		throw new AccessDeniedException("没有权限");
		// }
	}

	@Override
	public boolean supports(ConfigAttribute arg0) {
		return true;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}
}