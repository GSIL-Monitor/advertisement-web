package com.yuanshanbao.common.exception;


/**
 * 业务处理类异常
 * @author
 *
 */
@SuppressWarnings("serial")
public class BusinessException extends RuntimeException {

	public BusinessException()
	{
		super();
	}

	public BusinessException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public BusinessException(String message)
	{
		super(message);
	}

	public BusinessException(Throwable cause)
	{
		super(cause);
	}
	
	private int returnCode;

	public BusinessException(int returnCode) {
		super();
		this.returnCode = returnCode;
	}

	public BusinessException(Exception e, int returnCode) {
		super(e);
		this.returnCode = returnCode;
	}
	
	public BusinessException(String message, int returnCode) {
		super(message);
		this.returnCode = returnCode;
	}
	
	public BusinessException(String message, Exception e, int returnCode) {
		super(message, e);
		this.returnCode = returnCode;
	}

	public int getReturnCode() {
		return returnCode;
	}

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }
}
