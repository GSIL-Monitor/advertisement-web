package com.yuanshanbao.common.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

public class AliyunOSSUtil {

	private OSSClient client;
	private String bucketName;

	public AliyunOSSUtil(String endpoint, String accessKeyId, String accessKeySecret, String bucketName) {
		client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		this.bucketName = bucketName;
	}

	public void putObject(String bucketName, String key, String filePath) throws Exception {

		// 初始化OSSClient

		createFolderIfNotExist(client, bucketName, key);
		// 获取指定文件的输入流
		File file = new File(filePath);
		InputStream content = new FileInputStream(file);

		// 创建上传Object的Metadata
		ObjectMetadata meta = new ObjectMetadata();

		// 必须设置ContentLength
		meta.setContentLength(file.length());

		// 上传Object.
		PutObjectResult result = client.putObject(bucketName, key, content, meta);

		// 打印ETag
		System.out.println(result.getETag());
	}

	public void createFolderIfNotExist(OSSClient client, String bucketName, String path) throws IOException {
		String[] segs = path.split("/");
		for (int i = 0; i < segs.length - 1; i++) {
			boolean object = client.doesObjectExist(bucketName, segs[i]);
			if (!object) {
				createFile(client, bucketName, path);
			}
		}
	}

	private void createFile(OSSClient client, String bucketName, String objectName) throws IOException {
		ObjectMetadata objectMeta = new ObjectMetadata();
		/*
		 * 这里的size为0,注意OSS本身没有文件夹的概念,这里创建的文件夹本质上是一个size为0的Object,dataStream仍然可以有数据
		 */
		byte[] buffer = new byte[0];
		ByteArrayInputStream in = new ByteArrayInputStream(buffer);
		objectMeta.setContentLength(0);
		try {
			client.putObject(bucketName, objectName, in, objectMeta);
		} finally {
			in.close();
		}
	}

	public void putObject(InputStream content, long length, String path) throws IOException {
		createFolderIfNotExist(client, bucketName, path);
		// 创建上传Object的Metadata
		ObjectMetadata meta = new ObjectMetadata();

		// 必须设置ContentLength
		meta.setContentLength(length);

		// 上传Object.
		PutObjectResult result = client.putObject(bucketName, path, content, meta);
	}

	public void deleteFile(String path) {
		client.deleteObject(bucketName, path);
	}

	public OSSClient getClient() {
		return client;
	}

	public static void main(String[] args) throws Exception {
		String endpoint = "http://cdn.ilanchou.com";
		String accessKeyId = "Vy4g76o3f3N1sZ6Q";
		String accessKeySecret = "1Kg0wL93O6XuUIkeU6hpCylmrrr4ZS";
		String bucketName = "ilanchou";
		AliyunOSSUtil oss = new AliyunOSSUtil(endpoint, accessKeyId, accessKeySecret, bucketName);
		File file = new File("E:/other/file/test.jpg");
		oss.putObject(new FileInputStream(file), file.length(), "test2/test2.jpg");
		String url = "http://ilanchou.oss-cn-beijing.aliyuncs.com/test_company/logo/1452520899583_1783.png";
		url = url.replaceAll("//", "");
		String path = url.substring(url.indexOf("/") + 1, url.length());
		oss.deleteFile(path);
	}
}
