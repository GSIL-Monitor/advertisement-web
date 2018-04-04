package com.yuanshanbao.ms.service.cache;

public interface BytesCacheService {
	public abstract byte[] query(String sourceKey);
	
	public abstract void insert(String sourceKey, byte[] bytes);
	
	public abstract void update(String sourceKey, byte[] bytes);
	
	public abstract void delete(String sourceKey);
}
