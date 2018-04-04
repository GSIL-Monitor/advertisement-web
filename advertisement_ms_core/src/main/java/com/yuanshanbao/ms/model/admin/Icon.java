package com.yuanshanbao.ms.model.admin;

import java.io.Serializable;
import java.sql.Blob;

public class Icon implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String name;
	
	private Blob image;

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

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}
}
