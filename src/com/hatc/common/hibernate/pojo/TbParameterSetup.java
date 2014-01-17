package com.hatc.common.hibernate.pojo;

/**
 * TbParameterSetup 系统参数
 */

public class TbParameterSetup implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -3731656609114786064L;

	private String paraName; // 参数名称

	private String upper; // 参数上限

	private String sort; // 参数类别

	private String type; // 参数类型

	private String lower; // 参数下限

	private String property; // 参数性质

	private String measure; // 参数单位

	private String description; // 参数描述

	private Long executeFlag; // 未知标识

	// Constructors
	/** default constructor */
	public TbParameterSetup() {
	}

	/** minimal constructor */
	public TbParameterSetup(String paraName) {
		this.paraName = paraName;
	}

	/** full constructor */
	public TbParameterSetup(String paraName, String upper, String sort, String type, String lower, String property,
			String measure, String description, Long executeFlag) {
		this.paraName = paraName;
		this.upper = upper;
		this.sort = sort;
		this.type = type;
		this.lower = lower;
		this.property = property;
		this.measure = measure;
		this.description = description;
		this.executeFlag = executeFlag;
	}

	// Property accessors
	public String getParaName() {
		return this.paraName;
	}

	public void setParaName(String paraName) {
		this.paraName = paraName;
	}

	public String getUpper() {
		return this.upper;
	}

	public void setUpper(String upper) {
		this.upper = upper;
	}

	public String getSort() {
		return this.sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLower() {
		return this.lower;
	}

	public void setLower(String lower) {
		this.lower = lower;
	}

	public String getProperty() {
		return this.property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getMeasure() {
		return this.measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getExecuteFlag() {
		return this.executeFlag;
	}

	public void setExecuteFlag(Long executeFlag) {
		this.executeFlag = executeFlag;
	}
}