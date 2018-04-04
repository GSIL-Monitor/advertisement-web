package com.yuanshanbao.ms.model.admin;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import org.hibernate.validator.constraints.NotBlank;

public class Menu implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private String parent_id;

	@NotBlank(message = "菜单名不能为空")
	private String name;

	private String icon_name;

	@NotBlank(message = "请选择菜单所属栏目")
	private String category_id;

	private String category_name;

	private String right_id;

	private String right_url;

	private String right_name;

	private boolean selected;

	private Set<Menu> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Menu> getChildren() {
		return children;
	}

	public void setChildren(Set<Menu> children) {
		this.children = children;
	}

	public void addChild(Menu menu) {
		if (children == null) {
			children = new LinkedHashSet<Menu>();
		}

		children.add(menu);
	}

	public String getRight_id() {
		return right_id;
	}

	public void setRight_id(String right_id) {
		this.right_id = right_id;
	}

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getRight_name() {
		return right_name;
	}

	public void setRight_name(String right_name) {
		this.right_name = right_name;
	}

	public String getRight_url() {
		return right_url;
	}

	public void setRight_url(String right_url) {
		this.right_url = right_url;
	}

	public String getIcon_name() {
		return icon_name;
	}

	public void setIcon_name(String icon_name) {
		this.icon_name = icon_name;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
