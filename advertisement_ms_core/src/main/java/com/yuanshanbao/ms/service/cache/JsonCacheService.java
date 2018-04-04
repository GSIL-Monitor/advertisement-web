package com.yuanshanbao.ms.service.cache;

import java.io.Serializable;

public interface JsonCacheService<T extends Serializable> {
	public abstract T query(String sourceKey);
	
	public abstract void insert(String sourceKey, T t);
	
	public abstract void update(String sourceKey, T t);
	
	public abstract void delete(String sourceKey);
}
