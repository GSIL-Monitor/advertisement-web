package com.yuanshanbao.ad.user.model;

import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.validator.constraints.NotBlank;

public class CropImage {

	public static final String CORP_PARAM = "a";
	public static final String RESIZE_PARAM = "p";
	public static final String PARAM_SPLIT = "|";
	public static final String PARAM_START = "@";

	@NotBlank(messageCode = ComRetCode.UPLOAD_AVATAR_ERROR)
	private String path;
	@NotBlank(messageCode = ComRetCode.UPLOAD_AVATAR_ERROR)
	private Integer originalWidth;
	@NotBlank(messageCode = ComRetCode.UPLOAD_AVATAR_ERROR)
	private Integer originalHeight;
	@NotBlank(messageCode = ComRetCode.UPLOAD_AVATAR_ERROR)
	private Integer actualWidth;
	@NotBlank(messageCode = ComRetCode.UPLOAD_AVATAR_ERROR)
	private Integer actualHeight;
	@NotBlank(messageCode = ComRetCode.UPLOAD_AVATAR_ERROR)
	private Integer x;
	@NotBlank(messageCode = ComRetCode.UPLOAD_AVATAR_ERROR)
	private Integer y;
	@NotBlank(messageCode = ComRetCode.UPLOAD_AVATAR_ERROR)
	private Integer w;
	@NotBlank(messageCode = ComRetCode.UPLOAD_AVATAR_ERROR)
	private Integer h;

	private Double multi;
	private String link;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getOriginalWidth() {
		return originalWidth;
	}

	public void setOriginalWidth(Integer originalWidth) {
		this.originalWidth = originalWidth;
	}

	public Integer getOriginalHeight() {
		return originalHeight;
	}

	public void setOriginalHeight(Integer originalHeight) {
		this.originalHeight = originalHeight;
	}

	public Integer getActualWidth() {
		return actualWidth;
	}

	public void setActualWidth(Integer actualWidth) {
		this.actualWidth = actualWidth;
	}

	public Integer getActualHeight() {
		return actualHeight;
	}

	public void setActualHeight(Integer actualHeight) {
		this.actualHeight = actualHeight;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getW() {
		return w;
	}

	public void setW(Integer w) {
		this.w = w;
	}

	public Integer getH() {
		return h;
	}

	public void setH(Integer h) {
		this.h = h;
	}

	public double getMulti() {
		return multi;
	}

	public void setMulti(double multi) {
		this.multi = multi;
	}

	public String getLink() {
		return link;
	}

	private void calculateMulti(int width, int height) {
		if (x < 0) {
			x = 0;
		}
		if (y < 0) {
			y = 0;
		}

		multi =  ((double)width / w);
		setW((int) width);
		setH((int) height);
		setX((int) (multi * x));
		setY((int) (multi * y));

		multi = multi * actualWidth / originalWidth;
	}

	public String getMultiLink(String link, double multi) {
		if (link.contains(PARAM_START)) {
			link += PARAM_SPLIT;
		} else {
			link += PARAM_START;
		}
		link += (int) (multi * 100) + RESIZE_PARAM;
		return link;
	}

	public String generateLink(int width, int height) {
		if (multi == null) {
			calculateMulti(width, height);
		}
		link = path + PARAM_START;
		if (multi > 0) {
			int m = (int) (multi * 100);
			link += m + RESIZE_PARAM + PARAM_SPLIT;
		}
		if (w > 0 && h > 0) {
			link += x + "-" + y + "-" + w + "-" + h + CORP_PARAM + PARAM_SPLIT;
		}
		return link.substring(0, link.length() - 1);
	}
}
