package com.yuanshanbao.ms.model.admin;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

public class Right implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	
	@NotBlank(message="权限名称不能为空")
	private String name;

	@NotBlank(message="描述不能为空")
	private String description;

	@NotBlank(message="权限对应URL不能为空")
	private String url;
	
	private String module_id;
	
	private String module_name;

	private List<Group> groups;

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getModule_id() {
		return module_id;
	}

	public void setModule_id(String module_id) {
		this.module_id = module_id;
	}

	public String getModule_name() {
		return module_name;
	}

	public void setModule_name(String module_name) {
		this.module_name = module_name;
	}
}