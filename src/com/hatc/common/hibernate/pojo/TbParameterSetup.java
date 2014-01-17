package com.hatc.common.hibernate.pojo;

/**
 * TbParameterSetup ϵͳ����
 */

public class TbParameterSetup implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -3731656609114786064L;

	private String paraName; // ��������

	private String upper; // ��������

	private String sort; // �������

	private String type; // ��������

	private String lower; // ��������

	private String property; // ��������

	private String measure; // ������λ

	private String description; // ��������

	private Long executeFlag; // δ֪��ʶ

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