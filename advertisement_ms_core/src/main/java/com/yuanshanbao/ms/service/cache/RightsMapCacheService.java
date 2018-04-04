package com.yuanshanbao.ms.service.cache;

import java.io.Serializable;

public interface RightsMapCacheService {
	public Object query();
	
	public void insert(Serializable t);
	
	public void update(Serializable t);
	
	public void delete();
}
