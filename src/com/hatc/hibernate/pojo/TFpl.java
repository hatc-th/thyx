package com.hatc.hibernate.pojo;

import java.util.Date;

/**
 * TFpl entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TFpl implements java.io.Serializable {

	// Fields

	private Long planid;
	private String FPlanName;
	private Long FPlanid;
	private String planstate;
	private Long alarm;
	private String unit;
	private String commander;
	private String duty;
	private String aircrew;
	private String acft;
	private Long acid;
	private String turb;
	private String equip;
	private String adep;
	private String ades;
	private String altn1;
	private String altn2;
	private String altn3;
	private String altn4;
	private String pclass;
	private String ptype;
	private Date dates;
	private String setd;
	private String seta;
	private Date setal;
	private String eet;
	private String types;
	private String total;
	private String rule;
	private String ssr;
	private String cspd;
	private String chgt;
	private String task;
	private String meteo;
	private String remark;
	private Date fplCtime;
	private String archiveState;
	private String FPlanCode;
	private String planUser;
	private Long activate;
	private Long createImitate;
	

	private String adepName;
	private String adepCode;
	private String adepPos;
	
	private String adesName;
	private String adesCode;
	private String adesPos;
		
	private String altn1Name;
	private String altn1Code;
	

	// Constructors

	/** default constructor */
	public TFpl() {
	}

	/** full constructor */
	public TFpl(String FPlanName, Long FPlanid, String planstate, Long alarm,
			String unit, String commander, String duty, String aircrew,
			String acft, Long acid, String turb, String equip, String adep,
			String ades, String altn1, String altn2, String altn3,
			String altn4, String pclass, String ptype, Date dates, String setd,
			String seta, Date setal, String eet, String types, String total,
			String rule, String ssr, String cspd, String chgt, String task,
			String meteo, String remark, Date fplCtime, String archiveState,
			String FPlanCode, String planUser, Long activate, Long createImitate) {
		this.FPlanName = FPlanName;
		this.FPlanid = FPlanid;
		this.planstate = planstate;
		this.alarm = alarm;
		this.unit = unit;
		this.commander = commander;
		this.duty = duty;
		this.aircrew = aircrew;
		this.acft = acft;
		this.acid = acid;
		this.turb = turb;
		this.equip = equip;
		this.adep = adep;
		this.ades = ades;
		this.altn1 = altn1;
		this.altn2 = altn2;
		this.altn3 = altn3;
		this.altn4 = altn4;
		this.pclass = pclass;
		this.ptype = ptype;
		this.dates = dates;
		this.setd = setd;
		this.seta = seta;
		this.setal = setal;
		this.eet = eet;
		this.types = types;
		this.total = total;
		this.rule = rule;
		this.ssr = ssr;
		this.cspd = cspd;
		this.chgt = chgt;
		this.task = task;
		this.meteo = meteo;
		this.remark = remark;
		this.fplCtime = fplCtime;
		this.archiveState = archiveState;
		this.FPlanCode = FPlanCode;
		this.planUser = planUser;
		this.activate = activate;
		this.createImitate = createImitate;
	}

	// Property accessors

	public Long getPlanid() {
		return this.planid;
	}

	public void setPlanid(Long planid) {
		this.planid = planid;
	}

	public String getFPlanName() {
		return this.FPlanName;
	}

	public void setFPlanName(String FPlanName) {
		this.FPlanName = FPlanName;
	}

	public Long getFPlanid() {
		return this.FPlanid;
	}

	public void setFPlanid(Long FPlanid) {
		this.FPlanid = FPlanid;
	}

	public String getPlanstate() {
		return this.planstate;
	}

	public void setPlanstate(String planstate) {
		this.planstate = planstate;
	}

	public Long getAlarm() {
		return this.alarm;
	}

	public void setAlarm(Long alarm) {
		this.alarm = alarm;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCommander() {
		return this.commander;
	}

	public void setCommander(String commander) {
		this.commander = commander;
	}

	public String getDuty() {
		return this.duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getAircrew() {
		return this.aircrew;
	}

	public void setAircrew(String aircrew) {
		this.aircrew = aircrew;
	}

	public String getAcft() {
		return this.acft;
	}

	public void setAcft(String acft) {
		this.acft = acft;
	}

	public Long getAcid() {
		return this.acid;
	}

	public void setAcid(Long acid) {
		this.acid = acid;
	}

	public String getTurb() {
		return this.turb;
	}

	public void setTurb(String turb) {
		this.turb = turb;
	}

	public String getEquip() {
		return this.equip;
	}

	public void setEquip(String equip) {
		this.equip = equip;
	}

	public String getAdep() {
		return this.adep;
	}

	public void setAdep(String adep) {
		this.adep = adep;
	}

	public String getAdes() {
		return this.ades;
	}

	public void setAdes(String ades) {
		this.ades = ades;
	}

	public String getAltn1() {
		return this.altn1;
	}

	public void setAltn1(String altn1) {
		this.altn1 = altn1;
	}

	public String getAltn2() {
		return this.altn2;
	}

	public void setAltn2(String altn2) {
		this.altn2 = altn2;
	}

	public String getAltn3() {
		return this.altn3;
	}

	public void setAltn3(String altn3) {
		this.altn3 = altn3;
	}

	public String getAltn4() {
		return this.altn4;
	}

	public void setAltn4(String altn4) {
		this.altn4 = altn4;
	}

	public String getPclass() {
		return this.pclass;
	}

	public void setPclass(String pclass) {
		this.pclass = pclass;
	}

	public String getPtype() {
		return this.ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	public Date getDates() {
		return this.dates;
	}

	public void setDates(Date dates) {
		this.dates = dates;
	}

	public String getSetd() {
		return this.setd;
	}

	public void setSetd(String setd) {
		this.setd = setd;
	}

	public String getSeta() {
		return this.seta;
	}

	public void setSeta(String seta) {
		this.seta = seta;
	}

	public Date getSetal() {
		return this.setal;
	}

	public void setSetal(Date setal) {
		this.setal = setal;
	}

	public String getEet() {
		return this.eet;
	}

	public void setEet(String eet) {
		this.eet = eet;
	}

	public String getTypes() {
		return this.types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getTotal() {
		return this.total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getRule() {
		return this.rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getSsr() {
		return this.ssr;
	}

	public void setSsr(String ssr) {
		this.ssr = ssr;
	}

	public String getCspd() {
		return this.cspd;
	}

	public void setCspd(String cspd) {
		this.cspd = cspd;
	}

	public String getChgt() {
		return this.chgt;
	}

	public void setChgt(String chgt) {
		this.chgt = chgt;
	}

	public String getTask() {
		return this.task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getMeteo() {
		return this.meteo;
	}

	public void setMeteo(String meteo) {
		this.meteo = meteo;
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

	public String getArchiveState() {
		return this.archiveState;
	}

	public void setArchiveState(String archiveState) {
		this.archiveState = archiveState;
	}

	public String getFPlanCode() {
		return this.FPlanCode;
	}

	public void setFPlanCode(String FPlanCode) {
		this.FPlanCode = FPlanCode;
	}

	public String getPlanUser() {
		return this.planUser;
	}

	public void setPlanUser(String planUser) {
		this.planUser = planUser;
	}

	public Long getActivate() {
		return this.activate;
	}

	public void setActivate(Long activate) {
		this.activate = activate;
	}

	public Long getCreateImitate() {
		return this.createImitate;
	}

	public void setCreateImitate(Long createImitate) {
		this.createImitate = createImitate;
	}

	public String getAdepName() {
		return adepName;
	}

	public void setAdepName(String adepName) {
		this.adepName = adepName;
	}

	public String getAdepCode() {
		return adepCode;
	}

	public void setAdepCode(String adepCode) {
		this.adepCode = adepCode;
	}

	public String getAdepPos() {
		return adepPos;
	}

	public void setAdepPos(String adepPos) {
		this.adepPos = adepPos;
	}

	public String getAdesName() {
		return adesName;
	}

	public void setAdesName(String adesName) {
		this.adesName = adesName;
	}

	public String getAdesCode() {
		return adesCode;
	}

	public void setAdesCode(String adesCode) {
		this.adesCode = adesCode;
	}

	public String getAdesPos() {
		return adesPos;
	}

	public void setAdesPos(String adesPos) {
		this.adesPos = adesPos;
	}

	public String getAltn1Name() {
		return altn1Name;
	}

	public void setAltn1Name(String altn1Name) {
		this.altn1Name = altn1Name;
	}

	public String getAltn1Code() {
		return altn1Code;
	}

	public void setAltn1Code(String altn1Code) {
		this.altn1Code = altn1Code;
	}

}