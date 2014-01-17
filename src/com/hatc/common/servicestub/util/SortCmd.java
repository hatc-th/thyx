package com.hatc.common.servicestub.util;

/**
 *	排序条件类
 */
public class SortCmd {
    public String fieldName = "";  ///< 字段名
    public String order = "";      ///< 升降序 (ASC, DESC)
    public String getFieldName() {
        return fieldName;
    }
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    public String getOrder() {
        return order;
    }
    public void setOrder(String order) {
        this.order = order;
    }
};
