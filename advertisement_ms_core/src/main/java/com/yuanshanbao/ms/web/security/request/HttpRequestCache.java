package com.yuanshanbao.ms.web.security.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

public class HttpRequestCache extends HttpSessionRequestCache {
	/**
     * Stores the current request, provided the configuration properties allow it.
     */
	@Override
    public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
        // TODO
    }

	@Override
    public SavedRequest getRequest(HttpServletRequest currentRequest, HttpServletResponse response) {
        // TODO
        return null;
    }
}
