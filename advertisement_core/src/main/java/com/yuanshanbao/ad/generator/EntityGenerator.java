package com.yuanshanbao.ad.generator;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class EntityGenerator {

	private static final String MYSQL_PREFIX = "jdbc_mysql.";

	private String entityPath; // 实体类路径
	private String daoPath; // dao层路径
	private String servicePath; // service层路径
	private String mapperPath; // service层路径

	private String entityTemplatePath; // 实体类模板路径
	private String daoTemplatePath; // 生成dao 模版路径
	private String daoImplTemplatePath; // 生成dao.impl 模版路径
	private String serviceTemplatePath; // 生成service 模版路径
	private String serviceImplTemplatePath; // 生成serviceImple 模板路径
	private String mapperTemplatePath; // 生成serviceImple 模板路径
	
	private String primaryKey;
	private String primaryType;

	// 数据库常量
	private String driver;
	private String url;
	private String username;
	private String password;

	/**
	 * 初始化参数 从配置文件中得到
	 */
	public void init() {
		String propertyPath = "autoGenerator.properties"; // 使用默认路径
		init(propertyPath);
	}

	private void init(String propertyPath) {
		Map<String, String> properties = Property.getProperties(propertyPath);
		entityPath = properties.get("entityPath");
		daoPath = properties.get("daoPath");
		servicePath = properties.get("servicePath");
		mapperPath = properties.get("mapperPath");

		entityTemplatePath = properties.get("entityTemplatePath");
		daoTemplatePath = properties.get("daoTemplatePath");
		daoImplTemplatePath = properties.get("daoImplTemplatePath");
		serviceTemplatePath = properties.get("serviceTemplatePath");
		serviceImplTemplatePath = properties.get("serviceImplTemplatePath");
		mapperTemplatePath = properties.get("mapperTemplatePath");

		// 数据库连接
		driver = properties.get(MYSQL_PREFIX + "driver");
		url = properties.get(MYSQL_PREFIX + "url");
		username = properties.get(MYSQL_PREFIX + "username");
		password = properties.get(MYSQL_PREFIX + "password");
	}

	public void build(String pkgName, String tbName) {

		List<String> entities = getEntityName();
		if (entities.contains(tbName)) {
			DefaultTemplate defaultTemplate = new DefaultTemplate();
			Template template;
			try {

				// 添加实体类
				template = new Configuration().getTemplate(entityTemplatePath, "UTF-8");
				tbName = GeneratorCommonUtils.firstCharUpperCase(tbName.replace("tb_", ""));
				entityPath = GeneratorCommonUtils.getRealPath(entityPath, pkgName);
				daoPath = GeneratorCommonUtils.getRealPath(daoPath, pkgName);
				servicePath = GeneratorCommonUtils.getRealPath(servicePath, pkgName);
				mapperPath = GeneratorCommonUtils.getRealPath(mapperPath, pkgName);
				File file = new File(entityPath + "/" + tbName + ".java");
				List<Entity> entityList = getParameterName("tb_" + GeneratorCommonUtils.firstCharLowerCase(tbName));
				for (Entity entity : entityList) {
					if (entity.getColumnKey()) {
						primaryKey = entity.getColumnName();
						primaryType = entity.getDataType();
					}
				}
				tbName = GeneratorCommonUtils.underlineToCamel(tbName);
				if (file.exists()) {
					System.out.println("MODEL已存在不添加");
				} else {
					defaultTemplate.setEntities(entityList);
					defaultTemplate.setEntityName(tbName);
					defaultTemplate.setPackagePath(GeneratorCommonUtils.getImportPath(entityPath));
					GeneratorCommonUtils.createFile(defaultTemplate, entityPath + "/", tbName + ".java", template);
				}

				// 添加dao,service
				String daoImport = GeneratorCommonUtils.getImportPath(daoPath);
				String serviceImport = GeneratorCommonUtils.getImportPath(servicePath);

				String entityName = tbName;
				String entityViable = GeneratorCommonUtils.firstCharLowerCase(tbName);
				String daoName = entityName + "Dao";
				String daoViable = GeneratorCommonUtils.firstCharLowerCase(daoName);
				String daoImplName = entityName + "DaoImpl";
				String serviceName = entityName + "Service";
				String serviceImplName = entityName + "ServiceImpl";
				String modelPath = GeneratorCommonUtils.getImportPath(entityPath) + "." + entityName;
				String dao = daoImport + "." + daoName;
				String service = serviceImport + "." + serviceName;
				
				// ~ 生成dao
				// ======================================================================================
				file = new File(daoPath + "/" + entityName + "Dao.java");
				if (file.exists()) {
					System.out.println("DAO已存在，请删除后添加");
				} else {
					Template temp = new Configuration().getTemplate(daoTemplatePath, "UTF-8");
					defaultTemplate = new DefaultTemplate();
					defaultTemplate.setDaoName(daoName);
					defaultTemplate.setPackagePath(daoImport);
					defaultTemplate.setModelPath(modelPath);
					defaultTemplate.setEntityName(entityName);
					defaultTemplate.setEntityViable(entityViable);
					defaultTemplate.setPrimaryKey(GeneratorCommonUtils.firstCharUpperCase(primaryKey));
					defaultTemplate.setPrimaryKeyViable(primaryKey);
					defaultTemplate.setPrimaryType(primaryType);
					GeneratorCommonUtils.createFile(defaultTemplate, daoPath, entityName + "Dao.java", temp);
				}
				// ~ 生成daoImpl
				// ======================================================================================
				file = new File(daoPath + "/" + entityName + "DaoImpl.java");
				if (file.exists()) {
					System.out.println("DAOIMPL已存在，请删除后添加");
				} else {
					Template temp = new Configuration().getTemplate(daoImplTemplatePath, "UTF-8");
					defaultTemplate = new DefaultTemplate();
					defaultTemplate.setDaoPath(dao);
					defaultTemplate.setModelPath(modelPath);
					defaultTemplate.setPackagePath(daoImport);
					defaultTemplate.setDaoImplName(daoImplName);
					defaultTemplate.setEntityName(entityName);
					defaultTemplate.setEntityViable(entityViable);
					defaultTemplate.setDaoName(daoName);
					defaultTemplate.setPrimaryKey(GeneratorCommonUtils.firstCharUpperCase(primaryKey));
					defaultTemplate.setPrimaryKeyViable(primaryKey);
					defaultTemplate.setPrimaryType(primaryType);
					GeneratorCommonUtils.createFile(defaultTemplate, daoPath, entityName + "DaoImpl.java", temp);
				}
				// ~ 生成service
				// ======================================================================================
				file = new File(servicePath + "/" + entityName + "Service.java");
				if (file.exists()) {
					System.out.println("SERVICE已存在，请删除后添加");
				} else {
					Template temp = new Configuration().getTemplate(serviceTemplatePath, "UTF-8");
					defaultTemplate = new DefaultTemplate();
					defaultTemplate.setPackagePath(serviceImport);
					defaultTemplate.setDaoPath(dao);
					defaultTemplate.setModelPath(modelPath);
					defaultTemplate.setServiceName(serviceName);
					defaultTemplate.setDaoName(daoName);
					defaultTemplate.setDaoEntity(daoViable);
					defaultTemplate.setEntityName(entityName);
					defaultTemplate.setEntityViable(entityViable);
					defaultTemplate.setPrimaryKey(GeneratorCommonUtils.firstCharUpperCase(primaryKey));
					defaultTemplate.setPrimaryKeyViable(primaryKey);
					defaultTemplate.setPrimaryType(primaryType);
					GeneratorCommonUtils.createFile(defaultTemplate, servicePath, entityName + "Service.java", temp);
				}
				// ~ 生成serviceImpl
				// ======================================================================================
				file = new File(servicePath + "/" + entityName + "ServiceImpl.java");
				if (file.exists()) {
					System.out.println("SERVICEIMPL已存在，请删除后添加");
				} else {
					Template temp = new Configuration().getTemplate(serviceImplTemplatePath, "UTF-8");
					defaultTemplate = new DefaultTemplate();
					defaultTemplate.setDaoPath(dao);
					defaultTemplate.setPackagePath(serviceImport);
					defaultTemplate.setServicePath(service);
					defaultTemplate.setModelPath(modelPath);
					defaultTemplate.setServiceImplName(serviceImplName);
					defaultTemplate.setDaoName(daoName);
					defaultTemplate.setServiceName(serviceName);
					defaultTemplate.setDaoEntity(daoViable);
					defaultTemplate.setEntityName(entityName);
					defaultTemplate.setEntityViable(entityViable);
					defaultTemplate.setPrimaryKey(GeneratorCommonUtils.firstCharUpperCase(primaryKey));
					defaultTemplate.setPrimaryKeyViable(primaryKey);
					defaultTemplate.setPrimaryType(primaryType);
					GeneratorCommonUtils
							.createFile(defaultTemplate, servicePath, entityName + "ServiceImpl.java", temp);
				}
				
				// ~ 生成mybatis配置文件
				// ======================================================================================
				file = new File(mapperPath + "/" + entityName + "DaoImpl.xml");
				if (file.exists()) {
					System.out.println("mybatis配置文件已存在，请删除后添加");
				} else {
					Template temp = new Configuration().getTemplate(mapperTemplatePath, "UTF-8");
					defaultTemplate = new DefaultTemplate();
					defaultTemplate.setEntities(entityList);
					defaultTemplate.setDaoPath(dao);
					defaultTemplate.setPackagePath(serviceImport);
					defaultTemplate.setServicePath(service);
					defaultTemplate.setModelPath(modelPath);
					defaultTemplate.setServiceImplName(serviceImplName);
					defaultTemplate.setDaoName(daoName);
					defaultTemplate.setServiceName(serviceName);
					defaultTemplate.setDaoEntity(daoViable);
					defaultTemplate.setTbName(GeneratorCommonUtils.camelToUnderline(entityName));
					defaultTemplate.setEntityName(entityName);
					defaultTemplate.setEntityViable(entityViable);
					defaultTemplate.setPrimaryKey(GeneratorCommonUtils.firstCharUpperCase(primaryKey));
					defaultTemplate.setPrimaryKeyViable(primaryKey);
					defaultTemplate.setPrimaryType(primaryType);
					defaultTemplate.setPrimaryKeyUD(GeneratorCommonUtils.camelToUnderline(primaryKey));
					GeneratorCommonUtils
							.createFile(defaultTemplate, mapperPath, entityName + "DaoImpl.xml", temp);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	private List<String> getEntityName() {
		Connection conn = GeneratorCommonUtils.getConnection(driver, url, username, password);
		String sql = "select table_name from information_schema.tables where table_schema='advertisement'and table_type='base table'";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Set<String> set = new HashSet<String>();
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String tableName = rs.getString("table_name");
				if (!tableName.contains("admin") && !tableName.contains("sequence")) {
					for (int i = 0; i < 100; i++) {
						if (tableName.contains("_" + i)) {
							tableName = tableName.replace("_" + i, "");
						}
					}

					set.add(tableName);
				}
			}
			rs.close(); // 关闭ResultSet
			pstmt.close(); // 关闭Statement
			conn.close(); // 关闭Connection
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<String>(set);
	}

	private List<Entity> getParameterName(String tableName) {
		Connection conn = GeneratorCommonUtils.getConnection(driver, url, username, password);
		String sql = "SELECT COLUMN_NAME,DATA_TYPE,COLUMN_KEY FROM INFORMATION_SCHEMA.columns WHERE TABLE_NAME='" + tableName
				+ "' ";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Entity> result = new ArrayList<Entity>();
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String columnName = rs.getString("COLUMN_NAME");
				String dataType = rs.getString("DATA_TYPE");
				String columnKey = rs.getString("COLUMN_KEY");
				Entity entity = new Entity();
				entity.setColumnName(columnName);
				entity.setDataType(dataType);
				entity.setColumnKey(columnKey);
				result.add(entity);
			}
			rs.close(); // 关闭ResultSet
			pstmt.close(); // 关闭Statement
			conn.close(); // 关闭Connection
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		EntityGenerator generator = new EntityGenerator();
		generator.init();
		generator.build("distribution", "tb_order_distribution_bind");
	}
}
