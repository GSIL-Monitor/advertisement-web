package com.yuanshanbao.ms.controller.activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Add {
	public static void main(String[] args) {
		try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw
			List<String> content = new ArrayList<String>();
			/* 读入TXT文件 */
			String pathname = "C:\\Users\\Administrator\\Desktop\\log\\log.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
			File filename = new File(pathname); // 要读取以上路径的input。txt文件
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象
			String line = "";
			line = br.readLine();
			while (line != null) {
				line = br.readLine(); // 一次读入一行数据
				content.add(line);
			}
			Map<String, String> mflMap = new HashMap<String, String>(2000);
			for (String s : content) {
				if (s != null && s.contains("/zh/result/commit")) {
					if (s.contains("resultKey=vipkid")) {
						String sid = s.substring(s.indexOf("sid=") + 4, s.indexOf(", pid"));
						if (sid != null) {
							String name = s.substring(s.indexOf("name=") + 5, s.indexOf("&ques"));
							String age = s.substring(s.indexOf("answer1=") + 8, s.indexOf("&resultKey"));
							mflMap.put(sid, name + "," + age);
						}
						// System.err.println(s.substring(s.indexOf("sid=") + 4,
						// s.indexOf(", pid")) + "  "
						// + s.substring(s.indexOf("name=") + 5,
						// s.indexOf("&ques")));
					}
				}
			}
			System.err.println("--------------------------------------");
			for (String s : content) {
				String sid = "";
				if (s != null) {
					sid = s.substring(s.indexOf("sid=") + 4, s.indexOf(", pid"));
				}
				if (mflMap.containsKey(sid)) {
					String mobile = s.substring(s.indexOf("mobile=") + 7, s.indexOf("mobile=") + 7 + 11);
					mflMap.put(sid, mflMap.get(sid) + "," + mobile);
				}
			}
			for (String value : mflMap.values()) {
				String[] ss = value.split(",");
				// if (!ss[1].contains(",") && !ss[1].contains(".")) {
				// String sql =
				// "INSERT INTO `tb_information` ( `merchant_id`, `goods_id`, `activity_id`, `mobile`, `name`, `identity_card`, `gender`, `birthday`, `email`, `province`, `city`, `start_time`, `end_time`, `policy_number`, `channel`, `ip`, `type`, `score`, `status`, `create_time`, `update_time`) VALUES ('52', '75', '201',"
				// + "'"
				// + ss[1]
				// + "'"
				// + ","
				// + "'"
				// + ss[0]
				// + "'"
				// + ","
				// +
				// " NULL, NULL, NULL, NULL, '110000', NULL, NULL, NULL, NULL, NULL, '111.193.179.125', '1', NULL, '2', '2018-07-09 17:17:05', '2018-07-09 17:17:05')";
				// System.err.println(sql);
				// }
				if (!ss[2].contains(",") && !ss[2].contains(".")) {
					System.err.println(value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
