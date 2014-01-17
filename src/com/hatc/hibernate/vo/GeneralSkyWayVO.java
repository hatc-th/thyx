package com.hatc.hibernate.vo;

import java.util.Date;
import java.util.List;

public class GeneralSkyWayVO {
	
	private String rrId;
	private Long rrUser;
	private String rrCode;
	private String rrName;
	private String rrDep;
	private String rrDes;
	private String rrType;
	
	List<GeneralSkyWayPointVO> pointList;

	public String getRrId() {
		return rrId;
	}

	public void setRrId(String rrId) {
		this.rrId = rrId;
	}

	public Long getRrUser() {
		return rrUser;
	}

	public void setRrUser(Long rrUser) {
		this.rrUser = rrUser;
	}

	public String getRrCode() {
		return rrCode;
	}

	public void setRrCode(String rrCode) {
		this.rrCode = rrCode;
	}

	public String getRrName() {
		return rrName;
	}

	public void setRrName(String rrName) {
		this.rrName = rrName;
	}

	public String getRrDep() {
		return rrDep;
	}

	public void setRrDep(String rrDep) {
		this.rrDep = rrDep;
	}

	public String getRrDes() {
		return rrDes;
	}

	public void setRrDes(String rrDes) {
		this.rrDes = rrDes;
	}

	public List<GeneralSkyWayPointVO> getPointList() {
		return pointList;
	}

	public void setPointList(List<GeneralSkyWayPointVO> pointList) {
		this.pointList = pointList;
	}

	public String getRrType() {
		return rrType;
	}

	public void setRrType(String rrType) {
		this.rrType = rrType;
	}
	
	
}
