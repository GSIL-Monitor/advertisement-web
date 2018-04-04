package com.yuanshanbao.paginator.domain;

public class PageItem {

	public static final String PREVIOUS = "<";
	public static final String NEXT = ">";
	public static final String SPLIT = "...";

	private String name;
	private String params;
	private boolean selected;

	public PageItem(String name, String link, boolean selected) {
		super();
		this.name = name;
		if (!selected) {
			this.params = link;
		}
		this.selected = selected;
	}

	public static PageItem createPrevious(String link) {
		return new PageItem(PREVIOUS, link, false);
	}

	public static PageItem createNext(String link) {
		return new PageItem(NEXT, link, false);
	}

	public static PageItem createSPLIT() {
		return new PageItem(SPLIT, null, false);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
