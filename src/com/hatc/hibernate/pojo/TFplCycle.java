package com.hatc.hibernate.pojo;

import java.util.Date;

/**
 * TFplCycle entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TFplCycle implements java.io.Serializable {

	// Fields

	private Long cyId;
	private Long planid;
	private Date cyBtime;
	private Date cyEtime;
	private Long cyLengh;
	private String cyUnit;
	private String cyWeekday;
	private String remark;
	private Date fplCtime;

	// Constructors

	/** default constructor */
	public TFplCycle() {
	}

	/** minimal constructor */
	public TFplCycle(Date cyBtime, String cyUnit) {
		this.cyBtime = cyBtime;
		this.cyUnit = cyUnit;
	}

	/** full constructor */
	public TFplCycle(Long planid, Date cyBtime, Date cyEtime, Long cyLengh,
			String cyUnit, String cyWeekday, String remark, Date fplCtime) {
		this.planid = planid;
		this.cyBtime = cyBtime;
		this.cyEtime = cyEtime;
		this.cyLengh = cyLengh;
		this.cyUnit = cyUnit;
		this.cyWeekday = cyWeekday;
		this.remark = remark;
		this.fplCtime = fplCtime;
	}

	// Property accessors

	public Long getCyId() {
		return this.cyId;
	}

	public void setCyId(Long cyId) {
		this.cyId = cyId;
	}

	public Long getPlanid() {
		return this.planid;
	}

	public void setPlanid(Long planid) {
		this.planid = planid;
	}

	public Date getCyBtime() {
		return this.cyBtime;
	}

	public void setCyBtime(Date cyBtime) {
		this.cyBtime = cyBtime;
	}

	public Date getCyEtime() {
		return this.cyEtime;
	}

	public void setCyEtime(Date cyEtime) {
		this.cyEtime = cyEtime;
	}

	public Long getCyLengh() {
		return this.cyLengh;
	}

	public void setCyLengh(Long cyLengh) {
		this.cyLengh = cyLengh;
	}

	public String getCyUnit() {
		return this.cyUnit;
	}

	public void setCyUnit(String cyUnit) {
		this.cyUnit = cyUnit;
	}

	public String getCyWeekday() {
		return this.cyWeekday;
	}

	public void setCyWeekday(String cyWeekday) {
		this.cyWeekday = cyWeekday;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getFplCtime() {
		return this.fplCtime;
	}

	public void setFplCtime(Date fplCtime) {
		this.fplCtime = fplCtime;
	}

}