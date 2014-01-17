package com.hatc.common.hibernate.pojo;

import java.math.BigDecimal;

/**
 * TbCode entity. @author MyEclipse Persistence Tools
 */

public class TbCode implements java.io.Serializable {

	// Fields

	private TbCodeId id;
	private String codeDetail;
	private String name;
	private String superiorId;
	private BigDecimal exportFlag;
	private BigDecimal sysFlag;
	private String orderId;
	private String nameenc;

	// Constructors

	/** default constructor */
	public TbCode() {
	}

	/** minimal constructor */
	public TbCode(TbCodeId id) {
		this.id = id;
	}

	/** full constructor */
	public TbCode(TbCodeId id, String codeDetail, String name,
			String superiorId, BigDecimal exportFlag, BigDecimal sysFlag,
			String orderId, String nameenc) {
		this.id = id;
		this.codeDetail = codeDetail;
		this.name = name;
		this.superiorId = superiorId;
		this.exportFlag = exportFlag;
		this.sysFlag = sysFlag;
		this.orderId = orderId;
		this.nameenc = nameenc;
	}

	// Property accessors

	public TbCodeId getId() {
		return this.id;
	}

	public void setId(TbCodeId id) {
		this.id = id;
	}

	public String getCodeDetail() {
		return this.codeDetail;
	}

	public void setCodeDetail(String codeDetail) {
		this.codeDetail = codeDetail;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSuperiorId() {
		return this.superiorId;
	}

	public void setSuperiorId(String superiorId) {
		this.superiorId = superiorId;
	}

	public BigDecimal getExportFlag() {
		return this.exportFlag;
	}

	public void setExportFlag(BigDecimal exportFlag) {
		this.exportFlag = exportFlag;
	}

	public BigDecimal getSysFlag() {
		return this.sysFlag;
	}

	public void setSysFlag(BigDecimal sysFlag) {
		this.sysFlag = sysFlag;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getNameenc() {
		return this.nameenc;
	}

	public void setNameenc(String nameenc) {
		this.nameenc = nameenc;
	}

}