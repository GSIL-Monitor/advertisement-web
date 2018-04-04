package com.yuanshanbao.common.ret.generator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.yuanshanbao.common.ret.ComRetCode;

public class ComRetCodeGenerator {
	public static void main(String[] args) throws Exception {
		Map<String, Integer> fieldMap = new HashMap<String, Integer>();
		Field[] fields = ComRetCode.class.getFields();
		for (Field field : fields) {
			Object value = field.get(null);
			if (value instanceof Integer) {
				fieldMap.put(field.getName(), (Integer) value);
			}
		}

		String descriptionEnd = "_DESC";
		String template = getContent("template.txt");
		List<String> messageList = readLine("error_message.txt");
		int index = 0;
		LinkedList<CodeType> list = new LinkedList<CodeType>();
		for (String line : messageList) {
			if (line.startsWith("#")) {
				String[] segs = line.replaceAll("#", "").split(" ");
				index = Integer.parseInt(segs[0] + "1");
				CodeType currentType = new CodeType();
				currentType.setStartIndex(index);
				currentType.setName(line);
				currentType.setFieldMap(fieldMap);
				list.addLast(currentType);

			} else if (line.trim().length() > 0) {
				String[] segs = line.split("=");
				String desc = segs[0].trim();
				String content = segs[1].trim();
				String key = desc.substring(0, desc.length() - descriptionEnd.length());
				CodeItem item = new CodeItem(key, desc, index, content);
				list.peekLast().addItem(item);
				index++;
			}
		}
		StringBuffer buffer = new StringBuffer();
		for (CodeType codeType : list) {
			buffer.append("\r\n").append("	/** " + codeType.getName() + " **/").append("\r\n\r\n");
			for (CodeItem item : codeType.getCodeList()) {
				buffer.append("	/* " + item.getIndex() + item.getContent() + " */").append("\r\n");
				buffer.append("	public static final int " + item.getKey() + " = " + item.getIndex() + ";").append("\r\n");
				buffer.append("	public static final String " + item.getDescription() + " = \"" + item.getContent() + "\";").append("\r\n\r\n");
			}
		}
		System.out.print(template.replace("${errorMessage}", buffer.toString()));
	}

	private static String getContent(String fileName) throws IOException {
		List<String> list = readLine(fileName);
		StringBuffer content = new StringBuffer();
		for (String line : list) {
			content.append(line).append("\r\n");
		}
		return content.toString();
	}

	public static List<String> readLine(String fileName) throws IOException {
		List<String> result = new ArrayList<String>();
		InputStream resource = ComRetCodeGenerator.class.getResourceAsStream(fileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(resource));
		String line = null;
		while ((line = reader.readLine()) != null) {
			result.add(line);
		}
		return result;
	}
}
