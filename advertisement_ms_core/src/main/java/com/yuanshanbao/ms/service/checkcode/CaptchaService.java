package com.yuanshanbao.ms.service.checkcode;

import java.io.OutputStream;

/**
 * Generate checkcode for system.
 * 
 * @author rnjin
 *
 */
public interface CaptchaService {
	/**
	 * Generate a gif Captcha for a output stream.
	 * 
	 * @param os
	 * @return
	 */
	public String generateGif(OutputStream os);
	
	/**
	 * Generate a jpeg captcha for a output stream.
	 * 
	 * @param os
	 * @return
	 */
	public String generateJpeg(OutputStream os);
}
