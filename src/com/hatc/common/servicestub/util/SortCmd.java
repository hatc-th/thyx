package com.hatc.common.servicestub.util;

/**
 *	����������
 */
public class SortCmd {
    public String fieldName = "";  ///< �ֶ���
    public String order = "";      ///< ������ (ASC, DESC)
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
