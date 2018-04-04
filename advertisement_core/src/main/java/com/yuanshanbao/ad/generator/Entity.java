package com.yuanshanbao.ad.generator;

public class Entity {

	private String columnName;
	private String dataType;
	private String columnKey;

	public String getColumnName() {
		columnName = GeneratorCommonUtils.underlineToCamel(columnName);
		return columnName;
	}

	public String getUDName() {
		return GeneratorCommonUtils.camelToUnderline(getColumnName());
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Boolean getColumnKey() {
		if (columnKey.equals("PRI")) {
			return true;
		}
		return false;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	public String getMapperDataType() {
		if ("varchar,text,String".contains(dataType)) {
			return "VARCHAR";
		}
		if ("bigint,int,double,float,Long,Integer,Double,Float,decimal".contains(dataType)) {
			return "NUMERIC";
		}
		if ("timestamp,datetime,Timestamp".contains(dataType)) {
			return "TIMESTAMP";
		}
		return null;
	}

	public String getDataType() {
		if ("varchar,text".contains(dataType)) {
			dataType = "String";
		}
		if ("int".contains(dataType)) {
			dataType = "Integer";
		}
		if ("bigint".contains(dataType)) {
			dataType = "Long";
		}
		if ("double,float,decimal".contains(dataType)) {
			dataType = "Double";
		}
		if ("timestamp,datetime".contains(dataType)) {
			dataType = "Timestamp";
		}
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getField() {
		return "private " + getDataType() + " " + GeneratorCommonUtils.underlineToCamel(columnName) + ";\n";
	}

	public String getGetMethods() {
		return "public " + getDataType() + " get" + GeneratorCommonUtils.firstCharUpperCase(getColumnName()) + "() {"
				+ "\n" + "\t\treturn " + getColumnName() + ";\n\t}\n";
	}

	public String getSetMethods() {
		return "public void set" + GeneratorCommonUtils.firstCharUpperCase(getColumnName()) + "(" + getDataType() + " "
				+ getColumnName() + ") {" + "\n" + "\t\tthis." + getColumnName() + " = " + getColumnName() + ";\n\t}\n";
	}

}
