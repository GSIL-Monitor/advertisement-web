package com.yuanshanbao.ms.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.yuanshanbao.ms.http.wrapper.HttpServletRequestWrapper;

/**
 * <p>Store session to other place if necessary.</p>
 * 
 * <p>When system is large enough, we will consider store our session to 
 * 
 * disstributed storage.</p>
 * 
 * @author rnjin
 *
 */
@SuppressWarnings("serial")
public class SysSessionFilter extends HttpServlet implements Filter {
	private static final Logger logger = Logger.getLogger("syssessionfilter");
	
	private String sessionId = "MS_SID";

	private String cookieDomain = "ms.yuanshanbao.com";

	private String cookiePath = "/";

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String sid = null;
		
		Cookie cookies[] = request.getCookies();
		Cookie sCookie = null;
		
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				sCookie = cookies[i];
				if (sCookie.getName().equals(sessionId)) {
					sid = sCookie.getValue();
					break;
				}
			}
		}
		
		if (sid == null || sid.length() == 0) {
			sid = java.util.UUID.randomUUID().toString();
			Cookie sessionCookie = new Cookie(sessionId, sid);
			sessionCookie.setMaxAge(-1);
			if (this.cookieDomain != null && this.cookieDomain.length() > 0) {
				String[] segs = this.cookieDomain.split(",");
				for (String seg : segs) {
					sessionCookie.setDomain(seg);
				}
			}
			sessionCookie.setPath(this.cookiePath);
			response.addCookie(sessionCookie);
		}
		filterChain.doFilter(new HttpServletRequestWrapper(sid, request), servletResponse);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.sessionId = filterConfig.getInitParameter("sessionId");
		if(sessionId == null || sessionId.length() == 0){
			sessionId = "SID";
		}
		
		this.cookieDomain = filterConfig.getInitParameter("cookieDomain");
		logger.info("SysSessionFilter at domain: "+this.cookieDomain);
		if (this.cookieDomain == null) {
			this.cookieDomain = "data.urs.netease.com";
		}
		
		this.cookiePath = filterConfig.getInitParameter("cookiePath");
		if (this.cookiePath == null || this.cookiePath.length() == 0) {
			this.cookiePath = "/";
		}
	}
}
