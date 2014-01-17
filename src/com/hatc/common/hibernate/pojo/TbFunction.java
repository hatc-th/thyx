package com.hatc.common.hibernate.pojo;

import java.util.Date;

/**
 * TbFunction entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TbFunction implements java.io.Serializable {
    
    // Fields
    private static final long serialVersionUID = 1931640610427180320L;
    
    private String functionId;
    
    private Date updateTime;
    
    private String validity;
    
    private String logSign;
    
    private String link;
    
    private String name;
    
    private String orderId;
    
    private String secretLevel;
    
    private String needAuth;
    
    // Constructors
    
    /** default constructor */
    public TbFunction() {
    }
    
    /** minimal constructor */
    public TbFunction(String functionId) {
        this.functionId = functionId;
    }
    
    /** full constructor */
    public TbFunction(String functionId, Date updateTime, String validity, String logSign, String link, String name,
            String orderId, String secretLevel, String needAuth) {
        this.functionId = functionId;
        this.updateTime = updateTime;
        this.validity = validity;
        this.logSign = logSign;
        this.link = link;
        this.name = name;
        this.orderId = orderId;
        this.secretLevel = secretLevel;
        this.needAuth = needAuth;
    }
    
    // Property accessors
    
    public String getFunctionId() {
        return this.functionId;
    }
    
    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }
    
    public Date getUpdateTime() {
        return this.updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    public String getValidity() {
        return this.validity;
    }
    
    public void setValidity(String validity) {
        this.validity = validity;
    }
    
    public String getLogSign() {
        return this.logSign;
    }
    
    public void setLogSign(String logSign) {
        this.logSign = logSign;
    }
    
    public String getLink() {
        return this.link;
    }
    
    public void setLink(String link) {
        this.link = link;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getOrderId() {
        return this.orderId;
    }
    
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    public String getSecretLevel() {
        return this.secretLevel;
    }
    
    public void setSecretLevel(String secretLevel) {
        this.secretLevel = secretLevel;
    }
    
    public String getNeedAuth() {
        return this.needAuth;
    }
    
    public void setNeedAuth(String needAuth) {
        this.needAuth = needAuth;
    }
    
}