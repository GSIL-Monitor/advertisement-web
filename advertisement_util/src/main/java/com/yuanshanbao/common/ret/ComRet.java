package com.yuanshanbao.common.ret;

/**
 * 返回结果
 *
 */
public class ComRet {

	protected int retCode;
	protected String retDesc;
	protected String signature;

	public int getRetCode() {
		return retCode;
	}

	public void setRetCode(int retCode) {
		this.retCode = retCode;
	}

	public String getRetDesc() {
		return retDesc;
	}

	public void setRetDesc(String retDesc) {
		this.retDesc = retDesc;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	/**
	 * 只读常量，不能修改属性
	 */
	public static final ComRet SYSTEM_ERR_RET = new ComRet();
	static {
		SYSTEM_ERR_RET.retCode = ComRetCode.FAIL;
		SYSTEM_ERR_RET.retDesc = ComRetCode.message(SYSTEM_ERR_RET.retCode);
	}
}
