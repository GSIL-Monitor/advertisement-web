package com.yuanshanbao.ms.model.admin;

import java.io.Serializable;

public class MonitorInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 可使用内存. */
	private long totalMemory;
	/** 剩余内存. */
	private long freeMemory;
	/** 最大可使用内存. */
	private long maxMemory;
	/** 操作系统. */
	private String osName;
	/** 总的物理内存. */
	private long totalPhysicalMemorySize;
	/** 剩余的物理内存. */
	private long freePhysicalMemorySize;
	/** 已使用的物理内存. */
	private long usedMemory;
	private long usedPhysicalMemorySize;
	/** 线程总数. */
	private int totalThread;

	private String cpu;

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getJdk() {
		return jdk;
	}

	public void setJdk(String jdk) {
		this.jdk = jdk;
	}

	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}

	private String jdk;

	private String connection;

	public long getTotalMemory() {
		return totalMemory;
	}

	public void setTotalMemory(long totalMemory) {
		this.totalMemory = totalMemory;
	}

	public long getFreeMemory() {
		return freeMemory;
	}

	public void setFreeMemory(long freeMemory) {
		this.freeMemory = freeMemory;
	}

	public long getMaxMemory() {
		return maxMemory;
	}

	public void setMaxMemory(long maxMemory) {
		this.maxMemory = maxMemory;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public long getTotalPhysicalMemorySize() {
		return totalPhysicalMemorySize;
	}

	public void setTotalPhysicalMemorySize(long totalPhysicalMemorySize) {
		this.totalPhysicalMemorySize = totalPhysicalMemorySize;
	}

	public long getUsedPhysicalMemorySize() {
		return usedPhysicalMemorySize;
	}

	public void setUsedPhysicalMemorySize(long usedPhysicalMemorySize) {
		this.usedPhysicalMemorySize = usedPhysicalMemorySize;
	}

	public long getFreePhysicalMemorySize() {
		return freePhysicalMemorySize;
	}

	public void setFreePhysicalMemorySize(long freePhysicalMemorySize) {
		this.freePhysicalMemorySize = freePhysicalMemorySize;
	}

	public long getUsedMemory() {
		return usedMemory;
	}

	public void setUsedMemory(long usedMemory) {
		this.usedMemory = usedMemory;
	}

	public int getTotalThread() {
		return totalThread;
	}

	public void setTotalThread(int totalThread) {
		this.totalThread = totalThread;
	}
}
