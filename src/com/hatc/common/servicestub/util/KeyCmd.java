package com.hatc.common.servicestub.util;

public class KeyCmd {
	public String keyName = "";  	    ///< 字段名（数据库表对应字段名）
	public String  limit = ""; 		    ///< 操作值
	public String  fieldType = "";  	///< 字段类型   （I:数值型、C:字符型、D:日期型）
	public String  operater = "";   	///< 关系操作符 （“>”，“<”，“LIKE”，“=”，“>=”，“<=”，“NOT NULL”，“NULL”）
    public String getFieldType() {
        return fieldType;
    }
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
    public String getKeyName() {
        return keyName;
    }
    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }
    public String getLimit() {
        return limit;
    }
    public void setLimit(String limit) {
        this.limit = limit;
    }
    public String getOperater() {
        return operater;
    }
    public void setOperater(String operater) {
        this.operater = operater;
    }
}
