package com.hatc.hibernate.pojo;

import java.util.Date;

/**
 * TbApproveFlowRec entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TbApproveFlowRec implements java.io.Serializable {

	// Fields

	private TbApproveFlowRecId id;
	private String objectType;
	private String fileType;
	private String objectId;
	private String beforeState;
	private String sendUserId;
	private String sendRoleId;
	private Date sendTs;
	private String approveType;
	private String approveUserId;
	private String approveState;
	private String note;
	private Date approveTs;
	private String fileName;
	private String processUrl;
	private String queryUrl;
	private String objectName;
	private String approveRoleId;
	private String approveResult;
	private Date endDate;

	// Constructors

	/** default constructor */
	public TbApproveFlowRec() {
	}

	/** minimal constructor */
	public TbApproveFlowRec(TbApproveFlowRecId id) {
		this.id = id;
	}

	/** full constructor */
	public TbApproveFlowRec(TbApproveFlowRecId id, String objectType,
			String fileType, String objectId, String beforeState,
			String sendUserId, String sendRoleId, Date sendTs,
			String approveType, String approveUserId, String approveState,
			String note, Date approveTs, String fileName, String processUrl,
			String queryUrl, String objectName, String approveRoleId,
			String approveResult, Date endDate) {
		this.id = id;
		this.objectType = objectType;
		this.fileType = fileType;
		this.objectId = objectId;
		this.beforeState = beforeState;
		this.sendUserId = sendUserId;
		this.sendRoleId = sendRoleId;
		this.sendTs = sendTs;
		this.approveType = approveType;
		this.approveUserId = approveUserId;
		this.approveState = approveState;
		this.note = note;
		this.approveTs = approveTs;
		this.fileName = fileName;
		this.processUrl = processUrl;
		this.queryUrl = queryUrl;
		this.objectName = objectName;
		this.approveRoleId = approveRoleId;
		this.approveResult = approveResult;
		this.endDate = endDate;
	}

	// Property accessors

	public TbApproveFlowRecId getId() {
		return this.id;
	}

	public void setId(TbApproveFlowRecId id) {
		this.id = id;
	}

	public String getObjectType() {
		return this.objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getObjectId() {
		return this.objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getBeforeState() {
		return this.beforeState;
	}

	public void setBeforeState(String beforeState) {
		this.beforeState = beforeState;
	}

	public String getSendUserId() {
		return this.sendUserId;
	}

	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}

	public String getSendRoleId() {
		return this.sendRoleId;
	}

	public void setSendRoleId(String sendRoleId) {
		this.sendRoleId = sendRoleId;
	}

	public Date getSendTs() {
		return this.sendTs;
	}

	public void setSendTs(Date sendTs) {
		this.sendTs = sendTs;
	}

	public String getApproveType() {
		return this.approveType;
	}

	public void setApproveType(String approveType) {
		this.approveType = approveType;
	}

	public String getApproveUserId() {
		return this.approveUserId;
	}

	public void setApproveUserId(String approveUserId) {
		this.approveUserId = approveUserId;
	}

	public String getApproveState() {
		return this.approveState;
	}

	public void setApproveState(String approveState) {
		this.approveState = approveState;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getApproveTs() {
		return this.approveTs;
	}

	public void setApproveTs(Date approveTs) {
		this.approveTs = approveTs;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getProcessUrl() {
		return this.processUrl;
	}

	public void setProcessUrl(String processUrl) {
		this.processUrl = processUrl;
	}

	public String getQueryUrl() {
		return this.queryUrl;
	}

	public void setQueryUrl(String queryUrl) {
		this.queryUrl = queryUrl;
	}

	public String getObjectName() {
		return this.objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getApproveRoleId() {
		return this.approveRoleId;
	}

	public void setApproveRoleId(String approveRoleId) {
		this.approveRoleId = approveRoleId;
	}

	public String getApproveResult() {
		return this.approveResult;
	}

	public void setApproveResult(String approveResult) {
		this.approveResult = approveResult;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}