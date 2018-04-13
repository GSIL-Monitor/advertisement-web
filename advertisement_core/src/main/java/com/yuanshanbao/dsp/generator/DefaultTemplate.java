package com.yuanshanbao.dsp.generator;

import java.util.List;

/**
 * @path com.yancheng.myframe.createTemplate DeaultTemplate.java
 * @author yanc
 * @createTime Jan 4, 2011 5:26:55 PM
 * @description
 */
public class DefaultTemplate {
	// ~ Field
	// =============================================================================================
	private String packagePath;
	private String baseDaoPath;
	private String baseDaoImplPath;
	private String modelPath;
	private String daoPath;
	private String servicePath;

	private String entityName;
	private String daoName;
	private String daoImplName;

	private String serviceName;
	private String serviceImplName;
	private String daoEntity;
	private String entityViable;
	private String serviceViable;
	private String daoViable;
	
	private String primaryKey;
	private String primaryKeyViable;
	private String primaryType;
	private String primaryKeyUD;
	
	private String ftlPath;
	
	private String tbName;
	
	private List<Entity> entities;

	// ~ Get and Set Methods
	// =================================================================================
	public String getPackagePath() {
		return packagePath;
	}

	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}

	public String getBaseDaoPath() {
		return baseDaoPath;
	}

	public void setBaseDaoPath(String baseDaoPath) {
		this.baseDaoPath = baseDaoPath;
	}

	public String getModelPath() {
		return modelPath;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

	public String getDaoName() {
		return daoName;
	}

	public void setDaoName(String daoName) {
		this.daoName = daoName;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getBaseDaoImplPath() {
		return baseDaoImplPath;
	}

	public void setBaseDaoImplPath(String baseDaoImplPath) {
		this.baseDaoImplPath = baseDaoImplPath;
	}

	public String getDaoPath() {
		return daoPath;
	}

	public void setDaoPath(String daoPath) {
		this.daoPath = daoPath;
	}

	public String getDaoImplName() {
		return daoImplName;
	}

	public void setDaoImplName(String daoImplName) {
		this.daoImplName = daoImplName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getDaoEntity() {
		return daoEntity;
	}

	public void setDaoEntity(String daoEntity) {
		this.daoEntity = daoEntity;
	}

	public String getEntityViable() {
		return entityViable;
	}

	public void setEntityViable(String entityViable) {
		this.entityViable = entityViable;
	}

	public String getServiceImplName() {
		return serviceImplName;
	}

	public void setServiceImplName(String serviceImplName) {
		this.serviceImplName = serviceImplName;
	}

	public String getServicePath() {
		return servicePath;
	}

	public void setServicePath(String servicePath) {
		this.servicePath = servicePath;
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getPrimaryKeyViable() {
		return primaryKeyViable;
	}

	public void setPrimaryKeyViable(String primaryKeyViable) {
		this.primaryKeyViable = primaryKeyViable;
	}

	public String getPrimaryType() {
		return primaryType;
	}

	public void setPrimaryType(String primaryType) {
		this.primaryType = primaryType;
	}

	public String getPrimaryKeyUD() {
		return primaryKeyUD;
	}

	public void setPrimaryKeyUD(String primaryKeyUD) {
		this.primaryKeyUD = primaryKeyUD;
	}

	public String getTbName() {
		return tbName;
	}

	public void setTbName(String tbName) {
		this.tbName = tbName;
	}

	public String getFtlPath() {
		return ftlPath;
	}

	public void setFtlPath(String ftlPath) {
		this.ftlPath = ftlPath;
	}

	public String getServiceViable() {
		return serviceViable;
	}

	public void setServiceViable(String serviceViable) {
		this.serviceViable = serviceViable;
	}

	public String getDaoViable() {
		return daoViable;
	}

	public void setDaoViable(String daoViable) {
		this.daoViable = daoViable;
	}
	
}
