package com.yuanshanbao.common.util.html;

import org.htmlparser.Parser;

public class HtmlUtils {

	public static String translateToPlainText(String html) {
		try {
			Parser parser = Parser.createParser(html, "utf-8");
			CustomNodeVisitor visitor = new CustomNodeVisitor();
			parser.visitAllNodesWith(visitor);
			return visitor.getResult().toString();
		} catch (Exception ex) {
			return html;
		}
	}
}
