package com.yuanshanbao.common.qrcode;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class QRCodeUtil {

	private static final int MAX_SIZE = 720;
	private static final int DEFAULT_SIZE = 360;
	private static final int DEFAULT_BIT_MAP = BufferedImage.TYPE_INT_RGB;
	private static final String DEFAULT_FORMAT = "jpeg";
	private static final List<String> SUPPORT_FROMATS = Arrays.asList(ImageIO
			.getWriterFormatNames());

	public static void generateImage(HttpServletResponse response,
			String content, String picFormat, Integer size) throws Exception {
		if (StringUtils.isBlank(picFormat)) {
			picFormat = DEFAULT_FORMAT;
		}
		if (size == null) {
			size = DEFAULT_SIZE;
		}
		if (SUPPORT_FROMATS.contains(picFormat.trim().toLowerCase())) {
			picFormat = picFormat.trim().toLowerCase();
		}

		if (size > MAX_SIZE) {
			size = MAX_SIZE;
		} else if (size <= 0) {
			size = DEFAULT_SIZE;
		}
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "gbk");
		hints.put(EncodeHintType.MARGIN, 0);
		BitMatrix bitMatrix = multiFormatWriter.encode(content,
				BarcodeFormat.QR_CODE, size, size, hints);

		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "private,no-cache,no-store");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/" + picFormat + "; charset=gbk");
		// 目前只有天下3是32位深，其他游戏都是24位深，BufferedImage.TYPE_INT_RGB
		MatrixToImageWriter.writeToStream(bitMatrix, picFormat,
				response.getOutputStream(), DEFAULT_BIT_MAP);
	}

	public static void generateImage(HttpServletResponse response,
			String content) throws Exception {
		generateImage(response, content, null, null);
	}
}
