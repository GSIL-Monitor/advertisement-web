package com.yuanshanbao.common.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPCommand;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * FTP工具类
 * 
 */
public class FTPUtil {

	private static final Log logger = LogFactory.getLog(FTPUtil.class);

	/** 上传状态-创建目录失败 */
	public static final int UPLOADSTATUS_CREATE_DIRECTORY__FAIL = 1;

	/** 上传状态-创建目录成功 */
	public static final int UPLOADSTATUS_CREATE_DIRECTORY_SUCESS = 11;

	/** 上传状态-创建文件失败 */

	public static final int UPLOADSTATUS_UPLOAD_FILE__FAIL = 2;
	/** 上传状态-创建文件成功 */
	public static final int UPLOADSTATUS_UPLOAD_FILE_SUCESS = 10;

	/** 已有重名文件时，将原文件覆盖 */
	public static final int UPLOAD_TYPE_OVER = 1;

	/** 已有重名文件时，将上传内容续加到原文件后面 */
	public static final int UPLOAD_TYPE_APPEND = 2;

	/** 没有重名文件 */
	public static final String FILE_NO_EXISTS = "101";

	/** 有重名文件 */
	public static final String FILE_EXISTS = "100";

	/** 判断文件是否存在失败 */
	public static final String FILE_EXISTS_FAIL = "102";

	/** ftp服务器地址 */
	private String ftpServerName;

	/** ftp服务器端口 */
	private int ftpPort;

	/** ftp用户名 */
	private String ftpUsername;

	/** ftp密码 */
	private String ftpPassword;

	/** ftp服务器的http地址 */
	private String ftpHttpUrl;

	public FTPClient ftpClient = new FTPClient();

	public FTPUtil() {
	};

	public FTPUtil(String configName) {
		if (StringUtils.isBlank(getFtpServerName())) {
			// setFtpServerName(PropertyUtils.getProperty("ftpServerName"));
			// setFtpPort(Integer.parseInt(PropertyUtils.getProperty("ftpPort")));
			// setFtpUsername(PropertyUtils.getProperty("ftpUsername"));
			// setFtpPassword(PropertyUtils.getProperty("ftpPassword"));
			// setFtpHttpUrl(PropertyUtils.getProperty("ftpHttpUrl"));

		}
	}

	/**
	 * 连接到FTP服务器
	 * 
	 * @param hostname
	 * @param port
	 * @param username
	 * @param password
	 * @return
	 * @throws IOException
	 */
	public boolean connect() throws IOException {
		if (StringUtils.isBlank(getFtpServerName())) {
			setFtpHttpUrl(PropertyUtil.getProperty("ftp.httpUrl"));
			setFtpUsername(PropertyUtil.getProperty("ftp.username"));
			setFtpPassword(PropertyUtil.getProperty("ftp.password"));
			setFtpServerName(PropertyUtil.getProperty("ftp.server"));
			setFtpPort(PropertyUtil.getProperty("ftp.port"));
		}

		LoggerUtil.debug("连接FTP服务器 server[" + ftpServerName + "],port[" + ftpPort + "]");
		ftpClient.connect(ftpServerName, ftpPort);
		ftpClient.setControlEncoding("UTF-8");
		LoggerUtil.info("连接ftp服务器之后的返回码:" + ftpClient.getReplyCode());

		if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
			// if (ftpClient.login(ftpUsername, ftpPassword)) {
			// log.error("登录ftp服务器成功,当前的返回码:" + ftpClient.getReplyCode());
			// return true;
			// }else{
			// //登录ftp服务器失败
			// log.error("登录ftp服务器失败,登录名:" + ftpUsername
			// + ",ftpPassword:" + ftpPassword
			// + ",当前的返回码:" + ftpClient.getReplyCode());
			// }
			return this.login(ftpUsername, ftpPassword);
		} else {// 校验连接ftp服务器返回码失败
			LoggerUtil.info("校验ftp服务器返回码失败，返回码:" + ftpClient.getReplyCode());
		}

		disconnect();
		LoggerUtil.info("连接ftp服务器失败，自动调用disconnect()方法关闭链接.");
		return false;
	}

	private boolean login(String userName, String password) {
		LoggerUtil.debug("current user 当前登录用户:" + userName);
		try {
			this.ftpClient.sendCommand(FTPCommand.USER, userName);
			LoggerUtil.debug("发送完用户名之后的返回码:" + ftpClient.getReplyCode());
			if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				return true;
			}

			if (!FTPReply.isPositiveIntermediate(ftpClient.getReplyCode())) {
				return false;
			}

			this.ftpClient.sendCommand(FTPCommand.PASS, password);
			LoggerUtil.debug("发送完密码之后的返回码:" + ftpClient.getReplyCode());

			if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				return true;
			}
		} catch (IOException e) {
			LoggerUtil.error("登录过程中出错," + e.getMessage(), e);
			return false;
		}
		return true;
	}

	/**
	 * 上传文件前，做预设置
	 * 
	 * @param remote
	 * @throws IOException
	 */
	private int preUpload(String remote) throws IOException {

		// 设置PassiveMode传输
		ftpClient.enterLocalPassiveMode();

		// 设置以二进制流的方式传输
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		ftpClient.setControlEncoding("UTF-8");

		// 对远程目录的处理
		if (remote.contains("/")) {
			// 创建服务器远程目录结构，创建失败直接返回
			return createDirecroty(remote, ftpClient);

		} else
			return UPLOADSTATUS_CREATE_DIRECTORY_SUCESS;
	}

	/**
	 * 上传文件到FTP服务器。支持多级目录嵌套，支持递归创建不存在的目录结构。
	 * 
	 * @param localFile
	 *            本地文件
	 * @param remote
	 *            要上传到FTP服务器上的文件的路径
	 * @return
	 * @throws IOException
	 */
	public int upload(InputStream localFile, String remote, int type) throws IOException {
		int preUpload = tryToPreUpload(remote);
		if (preUpload == UPLOADSTATUS_CREATE_DIRECTORY__FAIL)
			return UPLOADSTATUS_CREATE_DIRECTORY__FAIL;
		else
			return uploadFile(remote, localFile, ftpClient, type);

	}

	private int tryToPreUpload(String remote) throws IOException {
		try {
			return preUpload(remote);
		} catch (FTPConnectionClosedException e) {
		} catch (SocketException e1) {
		}
		LoggerUtil.info("reconnect ftp");
		try {
			disconnect();
		} catch (Exception e) {
			LoggerUtil.error("discontect ftp error", e);
		}
		connect();
		return tryToPreUpload(remote);
	}

	/**
	 * 断开FTP服务器的链接
	 * 
	 * @throws IOException
	 */
	public void disconnect() throws IOException {
		if (ftpClient.isConnected()) {
			ftpClient.disconnect();
		}
	}

	/**
	 * 递归创建目录
	 * 
	 * @param remote
	 * @param ftpClient
	 * @return
	 * @throws IOException
	 */
	private int createDirecroty(String remote, FTPClient ftpClient) throws IOException {

		int status = UPLOADSTATUS_CREATE_DIRECTORY_SUCESS;

		String directory = remote.substring(0, remote.lastIndexOf("/") + 1);

		if (!directory.equalsIgnoreCase("/")
				&& !ftpClient.changeWorkingDirectory(new String(directory.getBytes("GBK"), "iso-8859-1"))) {

			// 如果远程目录不存在，则递归创建远程服务器目录
			int start = 0;
			int end = 0;
			if (directory.startsWith("/")) {
				start = 1;
			} else {
				start = 0;
			}
			end = directory.indexOf("/", start);
			while (true) {
				String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), "iso-8859-1");
				if (!ftpClient.changeWorkingDirectory(subDirectory)) {
					if (ftpClient.makeDirectory(subDirectory)) {
						ftpClient.changeWorkingDirectory(subDirectory);
					} else {
						return UPLOADSTATUS_CREATE_DIRECTORY__FAIL;
					}
				}

				start = end + 1;
				end = directory.indexOf("/", start);

				// 检查所有目录是否创建完毕
				if (end <= start) {
					break;
				}
			}
		}
		return status;
	}

	private int uploadFile(String remoteFile, InputStream localFile, FTPClient ftpClient, int type) throws IOException {
		int status;

		OutputStream out = null;
		if (type == UPLOAD_TYPE_OVER)
			out = ftpClient.storeFileStream(new String(remoteFile.getBytes("UTF-8"), "iso-8859-1"));
		else if (type == UPLOAD_TYPE_APPEND) {
			out = ftpClient.appendFileStream(new String(remoteFile.getBytes("UTF-8"), "iso-8859-1"));
		}

		byte[] bytes = new byte[1024];
		int c;
		while ((c = localFile.read(bytes)) != -1) {
			out.write(bytes, 0, c);
		}
		out.flush();
		localFile.close();
		out.close();
		boolean result = ftpClient.completePendingCommand();

		status = result ? UPLOADSTATUS_UPLOAD_FILE_SUCESS : UPLOADSTATUS_UPLOAD_FILE__FAIL;

		return status;
	}

	/**
	 * Description: 从FTP服务器下载文件
	 * 
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @param fileName
	 *            要下载的文件名
	 * @param localPath
	 *            下载后保存到本地的路径
	 * @return
	 */
	public boolean downFile(String remotePath, String fileName, String localPath) {
		boolean success = false;
		try {
			ftpClient.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			ftpClient.enterLocalPassiveMode();
			FTPFile[] fs = ftpClient.listFiles();
			for (FTPFile ff : fs) {
				if (ff.getName().equals(fileName)) {
					File localFile = new File(localPath + File.separator + ff.getName());

					OutputStream is = new FileOutputStream(localFile);
					ftpClient.retrieveFile(ff.getName(), is);
					is.close();
				}
			}
			success = true;
		} catch (IOException e) {
			logger.error("从" + remotePath + "下载文件出错");
		}
		return success;
	}

	public int[] getWeightAndHeight(String imageFileName) {
		int[] result = new int[2];
		try {
			ftpClient.enterLocalPassiveMode();
			InputStream is = ftpClient.retrieveFileStream(imageFileName);
			BufferedImage buff = ImageIO.read(is);
			// 读取原图长宽设置
			result[0] = buff.getWidth();
			result[1] = buff.getHeight();
			is.close();
			return result;
		} catch (IOException e) {
			LoggerUtil.error("Exception in getWeightAndHeight. " + e.getMessage(), e);
			return result;
		}
	}

	/**
	 * 重命名
	 * 
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @param newName
	 * @param oldName
	 * @return
	 */
	public boolean rename(String remotePath, String oldName, String newName) {
		boolean success = false;
		try {
			ftpClient.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			this.ftpClient.rename(oldName, newName);
			success = true;
		} catch (IOException e) {
			logger.error("在目录" + remotePath + "下重命名" + oldName + "出错");
		}
		return success;
	}

	private boolean getRightName(String listName, String goalName) {
		if (listName.endsWith(goalName + ".txt") || listName.endsWith(goalName + ".TXT")) {
			return true;
		}
		return false;
	}

	// 判断文件是否已存在
	public String isFileExists(String remote) {
		try {
			ftpClient.enterLocalPassiveMode();
			FTPFile[] files = ftpClient.listFiles(remote);
			if (files.length == 1) {
				return FILE_EXISTS;
			} else if (files.length == 0) {
				return FILE_NO_EXISTS;
			} else {
				return FILE_EXISTS_FAIL;
			}
		} catch (IOException e) {
			logger.info("isFileExists error", e);
			return FILE_EXISTS_FAIL;
		}
	}

	public void deleteFile(String remote) throws IOException {
		this.ftpClient.deleteFile(remote);
	}

	public FTPClient getFtpClient() {
		return ftpClient;
	}

	public void setFtpClient(FTPClient ftpClient) {
		this.ftpClient = ftpClient;
	}

	public String getFtpHttpUrl() {
		return ftpHttpUrl;
	}

	public void setFtpHttpUrl(String ftpHttpUrl) {
		this.ftpHttpUrl = ftpHttpUrl;
	}

	public String getFtpServerName() {
		return ftpServerName;
	}

	public void setFtpServerName(String ftpServerName) {
		this.ftpServerName = ftpServerName;
	}

	public int getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(String port) {
		try {
			this.ftpPort = Integer.parseInt(port);
		} catch (NumberFormatException e) {
		}
	}

	public String getFtpUsername() {
		return ftpUsername;
	}

	public void setFtpUsername(String ftpUsername) {
		this.ftpUsername = ftpUsername;
	}

	public String getFtpPassword() {
		return ftpPassword;
	}

	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}

}
