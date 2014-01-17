package com.hatc.hibernate.pojo;

import java.util.Date;

/**
 * TFsInterpret entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TFsInterpret implements java.io.Serializable {

	// Fields

	private Long fiId;
	private Long planid;
	private String fiInterpreter;
	private Date fiTime;
	private String fiMetar;
	private String fiSigmet;
	private String fiNotam;
	private String fiConflict;
	private String remark;

	// Constructors

	/** default constructor */
	public TFsInterpret() {
	}

	/** full constructor */
	public TFsInterpret(Long planid, String fiInterpreter, Date fiTime,
			String fiMetar, String fiSigmet, String fiNotam, String fiConflict,
			String remark) {
		this.planid = planid;
		this.fiInterpreter = fiInterpreter;
		this.fiTime = fiTime;
		this.fiMetar = fiMetar;
		this.fiSigmet = fiSigmet;
		this.fiNotam = fiNotam;
		this.fiConflict = fiConflict;
		this.remark = remark;
	}

	// Property accessors

	public Long getFiId() {
		return this.fiId;
	}

	public void setFiId(Long fiId) {
		this.fiId = fiId;
	}

	public Long getPlanid() {
		return this.planid;
	}

	public void setPlanid(Long planid) {
		this.planid = planid;
	}

	public String getFiInterpreter() {
		return this.fiInterpreter;
	}

	public void setFiInterpreter(String fiInterpreter) {
		this.fiInterpreter = fiInterpreter;
	}

	public Date getFiTime() {
		return this.fiTime;
	}

	public void setFiTime(Date fiTime) {
		this.fiTime = fiTime;
	}

	public String getFiMetar() {
		return this.fiMetar;
	}

	public void setFiMetar(String fiMetar) {
		this.fiMetar = fiMetar;
	}

	public String getFiSigmet() {
		return this.fiSigmet;
	}

	public void setFiSigmet(String fiSigmet) {
		this.fiSigmet = fiSigmet;
	}

	public String getFiNotam() {
		return this.fiNotam;
	}

	public void setFiNotam(String fiNotam) {
		this.fiNotam = fiNotam;
	}

	public String getFiConflict() {
		return this.fiConflict;
	}

	public void setFiConflict(String fiConflict) {
		this.fiConflict = fiConflict;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}