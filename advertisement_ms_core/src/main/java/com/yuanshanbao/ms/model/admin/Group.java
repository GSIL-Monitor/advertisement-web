package com.yuanshanbao.ms.model.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

public class Group implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	
	@NotBlank(message="分组名称不能为空")
	private String name;
	
	@NotBlank(message="分组描述不能为空")
	private String description;
	
	private String parent_id;
	
	private List<Group> children;
	
	private List<Right> rights;
	
	private List<Group> queryScope;

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

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public List<Right> getRights() {
		return rights;
	}

	public void setRights(List<Right> rights) {
		this.rights = rights;
	}

	public List<Group> getChildren() {
		return children;
	}

	public void setChildren(List<Group> children) {
		this.children = children;
	}
	
	public void addChild(Group group) {
		if (children == null) {
			children = new ArrayList<Group>();
		}
		
		children.add(group);
	}

	public List<Group> getQueryScope() {
		return queryScope;
	}

	public void setQueryScope(List<Group> queryScope) {
		this.queryScope = queryScope;
	}
}
