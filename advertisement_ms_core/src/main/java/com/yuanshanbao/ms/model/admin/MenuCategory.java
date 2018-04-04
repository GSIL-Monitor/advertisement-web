package com.yuanshanbao.ms.model.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

public class MenuCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	@NotBlank(message = "栏目名称不能为空")
	private String name;

	@NotBlank(message = "栏目描述不能为空")
	private String description;

	private boolean selected;

	private List<Menu> menuList = new ArrayList<Menu>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void addMenu(Menu menu) {
		menuList.add(menu);
	}

	public List<Menu> getMenuList() {
		return menuList;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
