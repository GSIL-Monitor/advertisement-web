package com.yuanshanbao.ms.utils;

public interface UrlMatcher {
	
	public Object compile(String paramString);

	public boolean pathMatchesUrl(Object paramObject, String paramString);

	public String getUniversalMatchPattern();

	public boolean requiresLowerCaseUrl();
}
