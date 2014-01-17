package com.hatc.hibernate.pojo;

import java.util.Date;

/**
 * TFplActivity entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TFplActivity implements java.io.Serializable {

	// Fields

	private Long faId;
	private TFpl TFpl;
	private String faPilot;
	private Long acid;
	private Date atd;
	private Date ata;
	private String eet;
	private Long faIaltn;
	private String faActad;
	private String faReason;
	private Long faRsId;
	private String faRemark;
	private String faAdjust;
	private String faDesc;
	
	private String faPilotName;
	private String faActadName;

	// Constructors

	/** default constructor */
	public TFplActivity() {
	}

	/** minimal constructor */
	public TFplActivity(Long faId, TFpl TFpl) {
		this.faId = faId;
		this.TFpl = TFpl;
	}

	/** full constructor */
	public TFplActivity(Long faId, TFpl TFpl, String faPilot, Long acid,
			Date atd, Date ata, String eet, Long faIaltn, String faActad,
			String faReason, Long faRsId, String faRemark, String faAdjust,
			String faDesc) {
		this.faId = faId;
		this.TFpl = TFpl;
		this.faPilot = faPilot;
		this.acid = acid;
		this.atd = atd;
		this.ata = ata;
		this.eet = eet;
		this.faIaltn = faIaltn;
		this.faActad = faActad;
		this.faReason = faReason;
		this.faRsId = faRsId;
		this.faRemark = faRemark;
		this.faAdjust = faAdjust;
		this.faDesc = faDesc;
	}

	// Property accessors

	public Long getFaId() {
		return this.faId;
	}

	public void setFaId(Long faId) {
		this.faId = faId;
	}

	public TFpl getTFpl() {
		return this.TFpl;
	}

	public void setTFpl(TFpl TFpl) {
		this.TFpl = TFpl;
	}

	public String getFaPilot() {
		return this.faPilot;
	}

	public void setFaPilot(String faPilot) {
		this.faPilot = faPilot;
	}

	public Long getAcid() {
		return this.acid;
	}

	public void setAcid(Long acid) {
		this.acid = acid;
	}

	public Date getAtd() {
		return this.atd;
	}

	public void setAtd(Date atd) {
		this.atd = atd;
	}

	public Date getAta() {
		return this.ata;
	}

	public void setAta(Date ata) {
		this.ata = ata;
	}

	public String getEet() {
		return this.eet;
	}

	public void setEet(String eet) {
		this.eet = eet;
	}

	public Long getFaIaltn() {
		return this.faIaltn;
	}

	public void setFaIaltn(Long faIaltn) {
		this.faIaltn = faIaltn;
	}

	public String getFaActad() {
		return this.faActad;
	}

	public void setFaActad(String faActad) {
		this.faActad = faActad;
	}

	public String getFaReason() {
		return this.faReason;
	}

	public void setFaReason(String faReason) {
		this.faReason = faReason;
	}

	public Long getFaRsId() {
		return this.faRsId;
	}

	public void setFaRsId(Long faRsId) {
		this.faRsId = faRsId;
	}

	public String getFaRemark() {
		return this.faRemark;
	}

	public void setFaRemark(String faRemark) {
		this.faRemark = faRemark;
	}

	public String getFaAdjust() {
		return this.faAdjust;
	}

	public void setFaAdjust(String faAdjust) {
		this.faAdjust = faAdjust;
	}

	public String getFaDesc() {
		return this.faDesc;
	}

	public void setFaDesc(String faDesc) {
		this.faDesc = faDesc;
	}

	public String getFaPilotName() {
		return faPilotName;
	}

	public void setFaPilotName(String faPilotName) {
		this.faPilotName = faPilotName;
	}

	public String getFaActadName() {
		return faActadName;
	}

	public void setFaActadName(String faActadName) {
		this.faActadName = faActadName;
	}

}