package com.yuanshanbao.common.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.StandardStream;

/**
 * 图片缩略图工具类
 * 
 * @author ywzhang
 *
 */
public class ImageUtil {

	public static String mg_path = PropertyUtil.getProperty("mg_path");

	// 定义一个数组，用于保存可上传的文件类型
	public static String[] fileTypes = { "jpg", "jpeg", "png" };

	/**
	 * 按比例缩放图片
	 * 
	 * @param imgName
	 *            图片文件名称，不包括路径
	 * @param multiple
	 *            缩放比例（倍数0-1），multiple==0时不缩放
	 */
	public static File drawImgResize(String srcPicDir, String desPicDir, String imgName, String thumbImgName, double multiple) {
		if (!checkImgDir(srcPicDir, desPicDir, imgName)) {
			LoggerUtil.info("存储图片的目录或原始文件不存在！");
			return null;
		}
		String srcImagePath = srcPicDir + "/" + imgName;
		String desImagePath = desPicDir + "/" + thumbImgName;
		try {
			return resizeImage(srcImagePath, desImagePath, multiple);
		} catch (Exception e) {
			LoggerUtil.error("Exception in drawImgResize. ", e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 按宽高缩放图片
	 * 
	 * @param imgName
	 *            图片文件名称，不包括路径
	 * @param
	 * @throws Exception
	 */
	public static boolean drawImgResize(String srcPicDir, String desPicDir, String imgName, String thumbImgName,
			int width, int height) throws Exception {
		if (!checkImgDir(srcPicDir, desPicDir, imgName)) {
			LoggerUtil.info("存储图片的目录或原始文件不存在！");
			return false;
		}
		String srcImagePath = srcPicDir + "/" + imgName;
		String desImagePath = desPicDir + "/" + thumbImgName;
		Process process = null;
		try {
			// process = Runtime.getRuntime().exec(
			// "gm convert -resize " + width + "x" + height + " " + srcImagePath
			// + " " + desImagePath);
			// process.waitFor();
			resizeImage(srcImagePath, desImagePath, width, height);
			return true;
		} catch (Exception e) {
			LoggerUtil.error("Exception in drawImgResize. ", e);
			throw e;
		} finally {
			if (process != null) {
				process.destroy();
			}
		}
	}

	/**
	 * 获取图片文件的宽和高,返回的数组第一个元素是宽,第二个是高
	 * 
	 * @param imageFileName
	 *            文件的全路径名
	 * @return
	 */
	public static int[] getWeightAndHeight(String imageFileName) {
		try {
			int[] result = new int[2];
			return result;
		} catch (Exception e) {
			LoggerUtil.error("Exception in getWeightAndHeight. " + e.getMessage(), e);
			return new int[2];
		}
	}

	/**
	 * 检查存放图片的目录和原始文件是否存在
	 * 
	 * @param imgName
	 * @return
	 */
	private static boolean checkImgDir(String srcPicDir, String desPicDir, String imgName) {
		try {
			FileUtil.getDir(srcPicDir, true);
			FileUtil.getDir(desPicDir, true);
		} catch (Exception e) {
			LoggerUtil.error("图片目录创建失败！" + srcPicDir, e);
			return false;
		}
		String srcPicPath = srcPicDir + "/" + imgName;
		File srcPic = new File(srcPicPath);
		if (!srcPic.exists()) {
			LoggerUtil.info("待转换的文件不存在！Image not exist. " + srcPic);
			return false;
		}
		return true;
	}

	/**
	 * 按比例缩放图片
	 * 
	 * @param multiple
	 *            缩放比例（倍数），multiple==0时不缩放
	 * @param srcImagePath
	 * @param desImagePath
	 * @throws Exception
	 */
	private static File resizeImage(String srcImagePath, String desImagePath, double multiple) throws Exception {
		BufferedImage image = ImageIO.read(new File(srcImagePath));
		if (image != null) {
			int width = image.getWidth();
			int height = image.getHeight();

			int newWidth = (int) (width * multiple);
			int newHeight = (int) (height * multiple);

			resizeImage(srcImagePath, desImagePath, newWidth, newHeight);
			return new File(desImagePath);
		}
		return null;
	}

	/**
	 * 
	 * @param srcPicPath
	 *            图片路径
	 * @param desPicPath
	 *            draw后的路径
	 * @param width
	 *            draw后的宽度
	 * @param height
	 *            draw后的高度
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws IM4JavaException
	 */
	private static void resizeImage(String srcPicPath, String desPicPath, int width, int height) throws IOException,
			InterruptedException, IM4JavaException {
		IMOperation op = new IMOperation();
		op.addImage();
		op.resize(width, height);
		// op.font("Arial").fill("red").draw("text 100,100 www.taobao.com");//在图片上加文字
		op.quality(85d);
		op.addImage();
		// IM4JAVA是同时支持ImageMagick和GraphicsMagick的，如果为true则使用GM，如果为false支持IM。
		ConvertCmd cmd = new ConvertCmd(true);
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.indexOf("win") >= 0) { // linux下不要设置此值，不然会报错
			cmd.setSearchPath(mg_path);
		}
		cmd.setErrorConsumer(StandardStream.STDERR);
		cmd.run(op, srcPicPath, desPicPath);
	}

	/**
	 * 
	 * @param srcPicPath
	 *            图片路径
	 * @param desPicPath
	 *            draw后的路径
	 * @param width
	 *            draw后的宽度
	 * @param height
	 *            draw后的高度
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws IM4JavaException
	 */
	public static void cutImage(String srcPicPath, String desPicPath, int width, int height, int x, int y)
			throws IOException, InterruptedException, IM4JavaException {
		IMOperation op = new IMOperation();
		op.addImage();
		op.crop(width, height, x, y);
		// op.font("Arial").fill("red").draw("text 100,100 www.taobao.com");//在图片上加文字
		op.quality(85d);
		op.addImage();
		// IM4JAVA是同时支持ImageMagick和GraphicsMagick的，如果为true则使用GM，如果为false支持IM。
		ConvertCmd cmd = new ConvertCmd(true);
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.indexOf("win") >= 0) { // linux下不要设置此值，不然会报错
			cmd.setSearchPath(mg_path);
		}
		cmd.setErrorConsumer(StandardStream.STDERR);
		cmd.run(op, srcPicPath, desPicPath);
	}
}