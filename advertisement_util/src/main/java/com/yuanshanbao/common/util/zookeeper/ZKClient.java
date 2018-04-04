package com.yuanshanbao.common.util.zookeeper;

public interface ZKClient {
	
	void doSth();
	
	boolean isMaster();
	
	boolean isAlive();
}
