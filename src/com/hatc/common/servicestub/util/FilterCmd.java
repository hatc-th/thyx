package com.hatc.common.servicestub.util;

/**
 * ���������� 
 */
public class FilterCmd {
    public String fieldName = "";  ///< �ֶ���
    public String upperLimit = ""; ///< ����ֵ����
    public String lowerLimit = ""; ///< ����ֵ����
    public String logic = "";      ///< �߼������� (����)
    public String fieldType = "";  ///< �ֶ����� ��I:��ֵ�͡�C:�ַ��͡�D:�����ͣ�
    public String operater = "";   ///< ��ϵ������ (�Ƚϡ�����)����>������<������LIKE������=������>=������<=������NOT NULL������NULL����
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
