package com.yuanshanbao.ms.http.wrapper;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yuanshanbao.common.util.CommonUtil;
import com.yuanshanbao.ms.service.cache.SessionCacheService;

public class HttpSessionSidWrapper extends HttpSessionWrapper {
	private String sid = "";

	private SessionCacheService sessionCacheService;

	private Map<String, Object> map = null;

	public HttpSessionSidWrapper(String sid, SessionCacheService sessionCacheService, HttpSession session) {
		super(session);
		this.sid = sid;
		this.sessionCacheService = sessionCacheService;
		this.map = new HashMap<String, Object>();
	}

	@SuppressWarnings("unchecked")
	public Object getAttribute(String arg0) {
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		if (sessionCacheService.ttl(sid) > 0) {
			Object obj = sessionCacheService.query(sid);
			if (obj == null) {
				return null;
			} else {
				attributesMap = (HashMap<String, Object>) obj;
			}

			return attributesMap.get(arg0);
		} else {
			sessionCacheService.delete(sid);
			if ("dev".equals(CommonUtil.getEnvironment())) {
				if ("SPRING_SECURITY_CONTEXT".equals(arg0)) {
					SecurityContext context = SecurityContextHolder.getContext();
					// context.setAuthentication(new
					// UsernamePasswordAuthenticationToken("admin",
					// "21232f297a57a5a743894a0e4a801fc3"));
					return context;
				}
			}
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Enumeration getAttributeNames() {
		Map<String, Object> attributesMap = new HashMap<String, Object>();

		Object obj = sessionCacheService.query(sid);
		if (obj == null) {
			return new Enumerator(new HashSet<Object>(), true);
		} else {
			attributesMap = (HashMap<String, Object>) obj;
		}

		if (attributesMap.size() == 0) {
			return new Enumerator(new HashSet<Object>(), true);
		}
		return (new Enumerator(this.map.keySet(), true));
	}

	public void invalidate() {
		Object obj = sessionCacheService.query(sid);
		if (obj == null) {
			return;
		} else {
			sessionCacheService.delete(this.sid);
		}
	}

	@SuppressWarnings("unchecked")
	public void removeAttribute(String arg0) {
		HashMap<String, Object> attributesMap = new HashMap<String, Object>();

		Object obj = sessionCacheService.query(sid);
		if (obj == null) {
			return;
		} else {
			attributesMap = (HashMap<String, Object>) obj;
		}

		if (attributesMap.size() != 0) {
			attributesMap.remove(arg0);
			sessionCacheService.update(sid, attributesMap);
		}
	}

	@SuppressWarnings("unchecked")
	public void setAttribute(String arg0, Object arg1) {
		HashMap<String, Object> attributesMap = new HashMap<String, Object>();

		Object obj = null;

		if (sessionCacheService.ttl(sid) > 0) {
			obj = sessionCacheService.query(sid);
			attributesMap = (HashMap<String, Object>) obj;
			if (attributesMap == null) {
				attributesMap = new HashMap<String, Object>();
			}
			attributesMap.put(arg0, arg1);
			sessionCacheService.update(sid, attributesMap);
		} else {
			sessionCacheService.delete(sid);
			attributesMap.put(arg0, arg1);
			sessionCacheService.update(sid, attributesMap);
		}
	}

	@Override
	public String getId() {
		return sid;
	}
}