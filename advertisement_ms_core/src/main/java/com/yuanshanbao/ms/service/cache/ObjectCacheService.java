package com.yuanshanbao.ms.service.cache;

import java.io.Serializable;

public interface ObjectCacheService<T extends Serializable> {
	public abstract Object query(String sourceKey);
	
	public abstract void insert(String sourceKey, T t);
	
	public abstract void update(String sourceKey, T t);
	
	public abstract void delete(String sourceKey);
}
