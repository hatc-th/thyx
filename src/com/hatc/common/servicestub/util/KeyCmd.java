package com.hatc.common.servicestub.util;

public class KeyCmd {
	public String keyName = "";  	    ///< �ֶ��������ݿ���Ӧ�ֶ�����
	public String  limit = ""; 		    ///< ����ֵ
	public String  fieldType = "";  	///< �ֶ�����   ��I:��ֵ�͡�C:�ַ��͡�D:�����ͣ�
	public String  operater = "";   	///< ��ϵ������ ����>������<������LIKE������=������>=������<=������NOT NULL������NULL����
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
