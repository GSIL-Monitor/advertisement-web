package com.yuanshanbao.common.util.html;

import org.htmlparser.Tag;
import org.htmlparser.Text;
import org.htmlparser.visitors.NodeVisitor;

public class CustomNodeVisitor extends NodeVisitor {

	private StringBuffer result = new StringBuffer();
	private int index = 1;

	@Override
	public void visitTag(Tag tag) {
		super.visitTag(tag);
		if (tag.getTagName().equalsIgnoreCase("li")) {
			result.append(index++ + ". ");
		}
		if (tag.getTagName().equalsIgnoreCase("br")) {
			result.append("\n");
		}
	}

	@Override
	public void visitEndTag(Tag tag) {
		super.visitEndTag(tag);
		if (tag.getTagName().equalsIgnoreCase("p")) {
			result.append("\n");
		}
		if (tag.getTagName().equalsIgnoreCase("ol")) {
			index = 1;
		}
	}

	@Override
	public void visitStringNode(Text string) {
		super.visitStringNode(string);
		String text = string.getText();
		result.append(text.replaceAll("&nbsp;", " "));
	}

	public StringBuffer getResult() {
		return result;
	}

	public void setResult(StringBuffer result) {
		this.result = result;
	}

}
