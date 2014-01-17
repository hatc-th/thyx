package com.hatc.hibernate.vo;

import java.util.Date;

/*
 * ��������VO��
 * @author wangdonghua
 * @date 2013-10-10 9:00
 * @version 1.0
 * */
public class AttachmentVO {
	
	//����ID
	private long attachmentId;
	
	//��������
	private String attachmentObjectType;
	
	//��������
	private String attachmentType;
	
	//����ID
	private String objectId;
	
	//����·��
	private String attachmentPath;
	
	//��������
	private String attachmentContent;
	
	//������ע
	private String attachmentRemark;
	
	//����ʱ��
	private Date createTime;
	
	//����ʱ��
	private Date updateTime;
	
	//����״̬
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
