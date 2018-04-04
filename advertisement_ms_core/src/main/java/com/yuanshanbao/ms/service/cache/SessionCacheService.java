package com.yuanshanbao.ms.service.cache;

import java.io.Serializable;

public interface SessionCacheService extends ObjectCacheService<Serializable> {
	public void expire(String sid, int seconds);
	
	public long ttl(String sid);
}
