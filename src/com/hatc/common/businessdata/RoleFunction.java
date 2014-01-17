/**
 * @file
 * @brief 角色功能数据对象类
 * @author 郑庆为
 * @version 1.0, 2007-9-19
 * @version 1.0, 2007-9-19, 郑庆为
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
/** 类名称: RoleFunction
 * 处理内容: 权限功能定义
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

    /** 功能ID 代码（编码规则见数据字典）*/
    private String functionId = new String();
    /**
     * 顺序ID
     */
    private String orderId = new String();
    /**
     * 功能名称（菜单名等）
     */
    private String functionName = new String();
    /**
     * 功能描述、网页链接
     */
    private String functionDescription = new String();
    /**
     * 是否记录日志
     */
    private boolean needLog;
    /**
     * 是否有效
     */
    private boolean isValid;
    /**
     * 更新时间
     */
    private String updateTime = new String();
    /**
     * 常用功能（0：不是常用功能；2：是常用功能
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
