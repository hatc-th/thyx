package com.hatc.hibernate.pojo;

/**
 * TFplRsP entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TFplRsP implements java.io.Serializable {

	// Fields

	private Long rsPId;
	private Long rsId;
	private Long rrId;
	private Long rsPNum;
	private String rsPPos;
	private String rsPName;
	private Long rsPType;
	private String rsPCode;

	// Constructors

	/** default constructor */
	public TFplRsP() {
	}

	/** full constructor */
	public TFplRsP(Long rsId, Long rrId, Long rsPNum, String rsPPos,
			String rsPName, Long rsPType, String rsPCode) {
		this.rsId = rsId;
		this.rrId = rrId;
		this.rsPNum = rsPNum;
		this.rsPPos = rsPPos;
		this.rsPName = rsPName;
		this.rsPType = rsPType;
		this.rsPCode = rsPCode;
	}

	// Property accessors

	public Long getRsPId() {
		return this.rsPId;
	}

	public void setRsPId(Long rsPId) {
		this.rsPId = rsPId;
	}

	public Long getRsId() {
		return this.rsId;
	}

	public void setRsId(Long rsId) {
		this.rsId = rsId;
	}

	public Long getRrId() {
		return this.rrId;
	}

	public void setRrId(Long rrId) {
		this.rrId = rrId;
	}

	public Long getRsPNum() {
		return this.rsPNum;
	}

	public void setRsPNum(Long rsPNum) {
		this.rsPNum = rsPNum;
	}

	public String getRsPPos() {
		return this.rsPPos;
	}

	public void setRsPPos(String rsPPos) {
		this.rsPPos = rsPPos;
	}

	public String getRsPName() {
		return this.rsPName;
	}

	public void setRsPName(String rsPName) {
		this.rsPName = rsPName;
	}

	public Long getRsPType() {
		return this.rsPType;
	}

	public void setRsPType(Long rsPType) {
		this.rsPType = rsPType;
	}

	public String getRsPCode() {
		return this.rsPCode;
	}

	public void setRsPCode(String rsPCode) {
		this.rsPCode = rsPCode;
	}

}