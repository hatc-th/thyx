package com.hatc.common.servicestub.util;

/**
 * 过滤条件类 
 */
public class FilterCmd {
    public String fieldName = "";  ///< 字段名
    public String upperLimit = ""; ///< 操作值上限
    public String lowerLimit = ""; ///< 操作值下限
    public String logic = "";      ///< 逻辑操作符 (与或非)
    public String fieldType = "";  ///< 字段类型 （I:数值型、C:字符型、D:日期型）
    public String operater = "";   ///< 关系操作符 (比较、包含)（“>”，“<”，“LIKE”，“=”，“>=”，“<=”，“NOT NULL”，“NULL”）
    public String getFieldName() {
        return fieldName;
    }
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    public String getFieldType() {
        return fieldType;
    }
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
    public String getLogic() {
        return logic;
    }
    public void setLogic(String logic) {
        this.logic = logic;
    }
    public String getLowerLimit() {
        return lowerLimit;
    }
    public void setLowerLimit(String lowerLimit) {
        this.lowerLimit = lowerLimit;
    }
    public String getOperater() {
        return operater;
    }
    public void setOperater(String operater) {
        this.operater = operater;
    }
    public String getUpperLimit() {
        return upperLimit;
    }
    public void setUpperLimit(String upperLimit) {
        this.upperLimit = upperLimit;
    }
};
