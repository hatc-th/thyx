/**
 * @file
 * @brief ��ɫ�������ݶ�����
 * @author ֣��Ϊ
 * @version 1.0, 2007-9-19
 * @version 1.0, 2007-9-19, ֣��Ϊ
 */
package com.hatc.common.businessdata;

import java.io.Serializable;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author zhengqw
 * @version 1.0
 */

//***************************************************************************
/** ������: RoleFunction
 * ��������: Ȩ�޹��ܶ���
 */
//***************************************************************************
public class RoleFunction implements Serializable {
   /**
     * 
     */
    private static final long serialVersionUID = 2673778215146898161L;
public RoleFunction() {
   }

    public String getFunctionDescription() {
        return functionDescription;
    }

    public String getFunctionName() {
        return functionName;
    }

    public boolean isIsValid() {
        return isValid;
    }

    public boolean isNeedLog() {
        return needLog;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setNeedLog(boolean needLog) {
        this.needLog = needLog;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public void setFunctionDescription(String functionDescription) {
        this.functionDescription = functionDescription;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    /** ����ID ���루�������������ֵ䣩*/
    private String functionId = new String();
    /**
     * ˳��ID
     */
    private String orderId = new String();
    /**
     * �������ƣ��˵����ȣ�
     */
    private String functionName = new String();
    /**
     * ������������ҳ����
     */
    private String functionDescription = new String();
    /**
     * �Ƿ��¼��־
     */
    private boolean needLog;
    /**
     * �Ƿ���Ч
     */
    private boolean isValid;
    /**
     * ����ʱ��
     */
    private String updateTime = new String();
    /**
     * ���ù��ܣ�0�����ǳ��ù��ܣ�2���ǳ��ù���
     */
    private String commonValue = new String();
    
	public String getCommonValue() {
		return commonValue;
	}

	public void setCommonValue(String commonValue) {
		this.commonValue = commonValue;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
};
