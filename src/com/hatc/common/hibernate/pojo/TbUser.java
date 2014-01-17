package com.hatc.common.hibernate.pojo;

import java.util.Date;
import java.util.Set;

public class TbUser implements java.io.Serializable {

	private static final long serialVersionUID = 6266035517948796582L;

	private String userId;

	private Date deleteTime;

	private Date updateTime;

	private Date createTime;

	private String validity;

	private String email;

	private String cell;

	private String tel;

	private String deptId;

	private String password;

	private Date exportTime;

	private Date importTime;

	private Date updatePasswdTime;

	private Date updateAuthTime;

	private String deleteSign;

	private String name;

	private String loginId;

	private String ip;

	private Date invalidDate;

	private String webHome;

	private String clientHome;

	/**user custom constructor*/
	public TbUser(String userId, String name) {
		this.userId = userId;
		this.name = name;
	}

	/** default constructor */
	public TbUser() {
	}

	/** minimal constructor */
	public TbUser(String userId) {
		this.userId = userId;
	}

	/** full constructor */
	public TbUser(String userId, Date deleteTime, Date updateTime,
			Date createTime, String validity, String email, String cell,
			String tel, String deptId, String password, Date exportTime,
			Date importTime, Date updatePasswdTime, Date updateAuthTime,
			String deleteSign, String name, String loginId, String ip,
			Date invalidDate, String webHome, String clientHome, Set tbContractsForApproverId, Set tbContractsForFillerId, Set tbContractsForApproverId2) {
		this.userId = userId;
		this.deleteTime = deleteTime;
		this.updateTime = updateTime;
		this.createTime = createTime;
		this.validity = validity;
		this.email = email;
		this.cell = cell;
		this.tel = tel;
		this.deptId = deptId;
		this.password = password;
		this.exportTime = exportTime;
		this.importTime = importTime;
		this.updatePasswdTime = updatePasswdTime;
		this.updateAuthTime = updateAuthTime;
		this.deleteSign = deleteSign;
		this.name = name;
		this.loginId = loginId;
		this.ip = ip;
		this.invalidDate = invalidDate;
		this.webHome = webHome;
		this.clientHome = clientHome;
	}

	// Property accessors

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getDeleteTime() {
		return this.deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getValidity() {
		return this.validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCell() {
		return this.cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getExportTime() {
		return this.exportTime;
	}

	public void setExportTime(Date exportTime) {
		this.exportTime = exportTime;
	}

	public Date getImportTime() {
		return this.importTime;
	}

	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}

	public Date getUpdatePasswdTime() {
		return this.updatePasswdTime;
	}

	public void setUpdatePasswdTime(Date updatePasswdTime) {
		this.updatePasswdTime = updatePasswdTime;
	}

	public Date getUpdateAuthTime() {
		return this.updateAuthTime;
	}

	public void setUpdateAuthTime(Date updateAuthTime) {
		this.updateAuthTime = updateAuthTime;
	}

	public String getDeleteSign() {
		return this.deleteSign;
	}

	public void setDeleteSign(String deleteSign) {
		this.deleteSign = deleteSign;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginId() {
		return this.loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getInvalidDate() {
		return this.invalidDate;
	}

	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	public String getWebHome() {
		return this.webHome;
	}

	public void setWebHome(String webHome) {
		this.webHome = webHome;
	}

	public String getClientHome() {
		return this.clientHome;
	}

	public void setClientHome(String clientHome) {
		this.clientHome = clientHome;
	}
}