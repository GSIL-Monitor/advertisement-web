package com.yuanshanbao.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class UploadUtils {

	public static String LOCAL_AVATAR = PropertyUtil.getProperty("image.local.avatar");
	public static String LOCAL_COMPANY_LOGO = PropertyUtil.getProperty("image.local.company.logo");
	public static String LOCAL_ONLINE_RESUME = PropertyUtil.getProperty("file.local.resume.online");

	public static String FTP_RESUME = PropertyUtil.getProperty("file.ftp.resume");
	public static String FTP_APPLY = PropertyUtil.getProperty("file.ftp.apply");
	public static String ONLINE_FTP_RESUME = PropertyUtil.getProperty("file.ftp.resume.online");
	public static String FTP_AVATAR = PropertyUtil.getProperty("image.ftp.avatar");
	public static String FTP_IDENTITY = PropertyUtil.getProperty("image.ftp.identity");
	public static String FTP_CARD = PropertyUtil.getProperty("image.ftp.card");
	public static String FTP_IMAGE_ORIGINAL = PropertyUtil.getProperty("image.ftp.original");
	public static String FTP_COMPANY_LOGO = PropertyUtil.getProperty("image.ftp.company.logo");
	public static String FTP_COMPANY_IMAGE = PropertyUtil.getProperty("image.ftp.company.image");
	public static String FTP_COMPANY_LICENCE = PropertyUtil.getProperty("image.ftp.company.licence");
	public static String FTP_CHANNEL_IMAGE = PropertyUtil.getProperty("image.ftp.channel.logo");

	public static String FTP_AUTHENTICATION_IMAGE = PropertyUtil.getProperty("image.ftp.authentication.image");

//	public static String FTP_ACTIVITY_IMAGE = PropertyUtil.getProperty("image.ftp.activity.image");

	public static String FTP_COMMUNITY_IMAGE = PropertyUtil.getProperty("image.ftp.community.image");

	public static String FTP_LIVE_IMAGE = PropertyUtil.getProperty("image.ftp.live.image");
	
	public static String FTP_COMMON_FILE = PropertyUtil.getProperty("ms.ftp.common.file");

	public static String OSS_ACCESS_KEY = PropertyUtil.getProperty("aliyun.accessKeyId");
	public static String OSS_ACCESS_SECRET = PropertyUtil.getProperty("aliyun.accessKeySecret");
	public static String OSS_HOST_ENDPOINT = PropertyUtil.getProperty("oss.host.endpoint");
	public static String OSS_HOST_FILES = PropertyUtil.getProperty("oss.host.files");
	public static String OSS_HOST_IMAGES = PropertyUtil.getProperty("oss.host.images");
	public static String OSS_BUCKET_NAME = PropertyUtil.getProperty("oss.bucket");

	private static FTPUtil ftpUtil;
	private static String httpUrl;

	private static AliyunOSSUtil ossUtils;

	static {
		try {
			init();
		} catch (IOException e) {
			LoggerUtil.error("UploadUtils-init-error", e);
		}
	}

	public static void init() throws IOException {
		ossUtils = new AliyunOSSUtil(OSS_HOST_ENDPOINT, OSS_ACCESS_KEY, OSS_ACCESS_SECRET, OSS_BUCKET_NAME);

		ftpUtil = new FTPUtil();
		httpUrl = PropertyUtil.getProperty("ftp.httpUrl");
		ftpUtil.setFtpHttpUrl(httpUrl);
		ftpUtil.setFtpUsername(PropertyUtil.getProperty("ftp.username"));
		ftpUtil.setFtpPassword(PropertyUtil.getProperty("ftp.password"));
		ftpUtil.setFtpServerName(PropertyUtil.getProperty("ftp.server"));
		ftpUtil.setFtpPort(PropertyUtil.getProperty("ftp.port"));
		// ftpUtil.setFtpPasv(PropertyUtil.getProperty("ftp.pasv"));
		// ftpUtil.connect();

	}

	public static FTPUtil getFtpUtil() throws IOException {
		if (ftpUtil == null) {
			init();
		}
		return ftpUtil;
	}

	//
	// public static String uploadCropImage(String prefix, String path, double
	// multi, int width, int height, int x, int y) throws Exception {
	// String fileName = path.substring(path.lastIndexOf("/") + 1);
	// byte[] content =
	// HttpUtil.sendGetRequestAndGetBytes(PropertyUtil.getProperty("host.web") +
	// path);
	// File originalFile = FileUtil.createFile(FTP_IMAGE_ORIGINAL, fileName,
	// content);
	// String parent = originalFile.getParent();
	// String resizeFileName = getImageSizeFileName(originalFile.getName(), 0,
	// 0);
	// String regularFileName = getImageSizeFileName(originalFile.getName(),
	// width, height);
	// String smallFileName = getSmallAvatarFileName(regularFileName, width,
	// height);
	// ImageUtil.drawImgResize(parent, parent, originalFile.getName(),
	// resizeFileName, multi);
	// ImageUtil.cutImage(parent + "/" + resizeFileName, parent + "/" +
	// regularFileName, width, height, x, y);
	// ImageUtil.drawImgResize(parent, parent, regularFileName, smallFileName,
	// 0.5);
	//
	// String regularPath = getHttpUrl() + prefix + "/" + regularFileName;
	// String smallPath = getHttpUrl() + prefix + "/" + smallFileName;
	// int rt = uploadFiles(new FileInputStream(parent + "/" + regularFileName),
	// regularPath);
	// if (rt != FTPUtil.UPLOADSTATUS_UPLOAD_FILE_SUCESS) {
	// return null;
	// }
	// rt = uploadFiles(new FileInputStream(parent + "/" + smallFileName),
	// smallPath);
	// return regularPath;
	// }

	public static String getSmallAvatarFileName(String fileName, int width, int height) {
		return getImageSizeFileName(fileName, (int) (width * 0.5), (int) (height * 0.5));
	}

	public static String getImageSizeFileName(String name, int width, int height) {
		String singleName = name.substring(0, name.lastIndexOf("."));
		String extension = name.substring(name.lastIndexOf("."));
		return singleName + "_" + width + "_" + height + extension;
	}

	public static String uploadFile(MultipartFile file, String suffixPath) throws IOException {
		if (file == null || file.getSize() <= 0) {
			return null;
		}
		InputStream in = file.getInputStream();
		String fileName = file.getOriginalFilename();
		fileName = getFileName(fileName);

		String baseUrl = OSS_HOST_FILES;
		if (FTP_AVATAR.equals(suffixPath) || FTP_COMPANY_LOGO.equals(suffixPath)
				|| FTP_COMPANY_IMAGE.equals(suffixPath)
				) {
			baseUrl = OSS_HOST_IMAGES;
		}
		String path = suffixPath + "/" + fileName;
		uploadFiles(in, file.getSize(), path);
		return baseUrl + "/" + path;
	}

	public static String getFileName(String fileName) {
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		ext = ext.toLowerCase();
		return System.currentTimeMillis() + "_" + new Random().nextInt(10000) + "." + ext;
	}

	public static void uploadFiles(InputStream content, long length, String path) throws IOException {
		// return getFtpUtil().upload(file, path, 1);
		ossUtils.putObject(content, length, path);
	}
	public static String uploadBytes(InputStream content, long length, String suffixPath) throws IOException {
		// return getFtpUtil().upload(file, path, 1);

		String baseUrl = OSS_HOST_FILES;
		if (FTP_AVATAR.equals(suffixPath) || FTP_COMPANY_LOGO.equals(suffixPath)
				|| FTP_COMPANY_IMAGE.equals(suffixPath)
				) {
			baseUrl = OSS_HOST_IMAGES;
		}
		String path = suffixPath;
		ossUtils.putObject(content, length, path);
		return baseUrl + "/" + path;
	}

	public static String getHttpUrl() {
		return httpUrl;
	}

	public static AliyunOSSUtil getOssUtils() {
		return ossUtils;
	}

	public static void deleteFile(String path) {
		if (StringUtils.isBlank(path)) {
			return;
		}
		try {
			path = path.replaceAll("//", "");
			path = path.substring(path.indexOf("/") + 1, path.length());
			ossUtils.deleteFile(path);
		} catch (Exception e) {
			LoggerUtil.error("", e);
		}
	}

}
