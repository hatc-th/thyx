package com.hatc.hibernate.pojo;

import java.util.Date;

/**
 * TFplRs entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TFplRs implements java.io.Serializable {

	// Fields

	private Long rsId;
	private Long planid;
	private String rsCode;
	private String rsName;
	private String rsType;
	private String rsDep;
	private String rsDes;
	private Date createDate;
	private Date updateDate;
	private Date effectDate;
	private String memo;

	// Constructors

	/** default constructor */
	public TFplRs() {
	}

	/** minimal constructor */
	public TFplRs(Long planid) {
		this.planid = planid;
	}

	/** full constructor */
	public TFplRs(Long planid, String rsCode, String rsName, String rsType,
			String rsDep, String rsDes, Date createDate, Date updateDate,
			Date effectDate, String memo) {
		this.planid = planid;
		this.rsCode = rsCode;
		this.rsName = rsName;
		this.rsType = rsType;
		this.rsDep = rsDep;
		this.rsDes = rsDes;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.effectDate = effectDate;
		this.memo = memo;
	}

	// Property accessors

	public Long getRsId() {
		return this.rsId;
	}

	public void setRsId(Long rsId) {
		this.rsId = rsId;
	}

	public Long getPlanid() {
		return this.planid;
	}

	public void setPlanid(Long planid) {
		this.planid = planid;
	}

	public String getRsCode() {
		return this.rsCode;
	}

	public void setRsCode(String rsCode) {
		this.rsCode = rsCode;
	}

	public String getRsName() {
		return this.rsName;
	}

	public void setRsName(String rsName) {
		this.rsName = rsName;
	}

	public String getRsType() {
		return this.rsType;
	}

	public void setRsType(String rsType) {
		this.rsType = rsType;
	}

	public String getRsDep() {
		return this.rsDep;
	}

	public void setRsDep(String rsDep) {
		this.rsDep = rsDep;
	}

	public String getRsDes() {
		return this.rsDes;
	}

	public void setRsDes(String rsDes) {
		this.rsDes = rsDes;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getEffectDate() {
		return this.effectDate;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}