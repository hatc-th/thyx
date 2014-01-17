package com.hatc.hibernate.pojo;

import java.util.Date;

/**
 * TRegularRs entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TRegularRs implements java.io.Serializable {

	// Fields

	private Long rrId;
	private String rrUser;
	private String rrCode;
	private String rrName;
	private String rrDep;
	private String rrDes;
	private Date createDate;
	private Date updateDate;
	private Date effectDate;
	private String memeo;

	// Constructors

	/** default constructor */
	public TRegularRs() {
	}

	/** full constructor */
	public TRegularRs(String rrUser, String rrCode, String rrName,
			String rrDep, String rrDes, Date createDate, Date updateDate,
			Date effectDate, String memeo) {
		this.rrUser = rrUser;
		this.rrCode = rrCode;
		this.rrName = rrName;
		this.rrDep = rrDep;
		this.rrDes = rrDes;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.effectDate = effectDate;
		this.memeo = memeo;
	}

	// Property accessors

	public Long getRrId() {
		return this.rrId;
	}

	public void setRrId(Long rrId) {
		this.rrId = rrId;
	}

	public String getRrUser() {
		return this.rrUser;
	}

	public void setRrUser(String rrUser) {
		this.rrUser = rrUser;
	}

	public String getRrCode() {
		return this.rrCode;
	}

	public void setRrCode(String rrCode) {
		this.rrCode = rrCode;
	}

	public String getRrName() {
		return this.rrName;
	}

	public void setRrName(String rrName) {
		this.rrName = rrName;
	}

	public String getRrDep() {
		return this.rrDep;
	}

	public void setRrDep(String rrDep) {
		this.rrDep = rrDep;
	}

	public String getRrDes() {
		return this.rrDes;
	}

	public void setRrDes(String rrDes) {
		this.rrDes = rrDes;
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

	public String getMemeo() {
		return this.memeo;
	}

	public void setMemeo(String memeo) {
		this.memeo = memeo;
	}

}