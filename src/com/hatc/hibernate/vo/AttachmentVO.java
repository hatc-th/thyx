package com.hatc.hibernate.vo;

import java.util.Date;

/*
 * 机场附件VO类
 * @author wangdonghua
 * @date 2013-10-10 9:00
 * @version 1.0
 * */
public class AttachmentVO {
	
	//附件ID
	private long attachmentId;
	
	//对象类型
	private String attachmentObjectType;
	
	//附件类型
	private String attachmentType;
	
	//对象ID
	private String objectId;
	
	//附件路径
	private String attachmentPath;
	
	//附件内容
	private String attachmentContent;
	
	//附件备注
	private String attachmentRemark;
	
	//创建时间
	private Date createTime;
	
	//更新时间
	private Date updateTime;
	
	//附件状态
	private long attachmentState;

	public long getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(long attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getAttachmentObjectType() {
		return attachmentObjectType;
	}

	public void setAttachmentObjectType(String attachmentObjectType) {
		this.attachmentObjectType = attachmentObjectType;
	}

	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getAttachmentPath() {
		return attachmentPath;
	}

	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}

	public String getAttachmentContent() {
		return attachmentContent;
	}

	public void setAttachmentContent(String attachmentContent) {
		this.attachmentContent = attachmentContent;
	}

	public String getAttachmentRemark() {
		return attachmentRemark;
	}

	public void setAttachmentRemark(String attachmentRemark) {
		this.attachmentRemark = attachmentRemark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public long getAttachmentState() {
		return attachmentState;
	}

	public void setAttachmentState(long attachmentState) {
		this.attachmentState = attachmentState;
	}
	
	
}
