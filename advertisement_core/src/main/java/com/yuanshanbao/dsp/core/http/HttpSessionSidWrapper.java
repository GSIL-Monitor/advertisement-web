package com.yuanshanbao.dsp.core.http;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.yuanshanbao.dsp.common.redis.base.RedisService;

public class HttpSessionSidWrapper extends HttpSessionWrapper {
	
	private static final int SESSION_EXPIRE_TIME = 4 * 60 * 60;
	
	private String sid = "";

	private RedisService sessionCacheService;

	public HttpSessionSidWrapper(String sid, RedisService sessionCacheService, HttpSession session) {
		super(session);
		this.sid = sid;
		this.sessionCacheService = sessionCacheService;
	}

	@SuppressWarnings("unchecked")
	public Object getAttribute(String arg0) {
		Map<String, Object> attributesMap = new HashMap<String, Object>();
		if (sessionCacheService.ttl(sid) > 0) {
			Object obj = sessionCacheService.hessian2Get(sid);
			if (obj == null) {
				return null;
			} else {
				attributesMap = (HashMap<String, Object>) obj;
			}

			return attributesMap.get(arg0);
		} else {
			sessionCacheService.del(sid);
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Enumeration getAttributeNames() {
		Map<String, Object> attributesMap = new HashMap<String, Object>();

		Object obj = sessionCacheService.hessian2Get(sid);
		if (obj == null) {
			return new Enumerator(new HashSet<Object>(), true);
		} else {
			attributesMap = (HashMap<String, Object>) obj;
		}

		if (attributesMap.size() == 0) {
			return new Enumerator(new HashSet<Object>(), true);
		}
		return (new Enumerator(attributesMap.keySet(), true));
	}

	public void invalidate() {
		Object obj = sessionCacheService.hessian2Get(sid);
		if (obj == null) {
			return;
		} else {
			sessionCacheService.del(this.sid);
		}
	}

	@SuppressWarnings("unchecked")
	public void removeAttribute(String arg0) {
		HashMap<String, Object> attributesMap = new HashMap<String, Object>();

		Object obj = sessionCacheService.hessian2Get(sid);
		if (obj == null) {
			return;
		} else {
			attributesMap = (HashMap<String, Object>) obj;
		}

		if (attributesMap.size() != 0) {
			attributesMap.remove(arg0);
			sessionCacheService.hessian2Set(sid, attributesMap);
		}
	}

	@SuppressWarnings("unchecked")
	public void setAttribute(String arg0, Object arg1) {
		HashMap<String, Object> attributesMap = new HashMap<String, Object>();

		Object obj = null;

		if (sessionCacheService.ttl(sid) > 0) {
			obj = sessionCacheService.hessian2Get(sid);
			attributesMap = (HashMap<String, Object>) obj;
			if (attributesMap == null) {
				attributesMap = new HashMap<String, Object>();
			}
			attributesMap.put(arg0, arg1);
			sessionCacheService.hessian2Set(sid, attributesMap);
			sessionCacheService.expire(sid, SESSION_EXPIRE_TIME);
		} else {
			sessionCacheService.del(sid);
			attributesMap.put(arg0, arg1);
			sessionCacheService.hessian2Set(sid, attributesMap);
			sessionCacheService.expire(sid, SESSION_EXPIRE_TIME);
		}
	}

	@Override
	public String getId() {
		return sid;
	}
}