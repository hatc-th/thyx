package com.hatc.hibernate.vo;

/*
 * ���г�ͻVO
 * @author wangdh
 * @date 2013-10-08 11:30
 * */
public class FlyConflictVO {
	
	//Ψһ��ʾ
	private long id;
	
	//��ͻ����
	private String type;
	
	//������������(���㽫����ѯ��ͬ��table)
	private String from;
	
	//��ͻ����
	private String src;
	
	//��ͻ����
	private String description;
	
	//���мƻ�ID
	private long planId;
	
	//����
	private String advice;
	
	//������Ϣ
	private String other;
	
	//��ʼʱ��
	private String startTime;
	
	//����ʱ��
	private String endTime;

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getPlanId() {
		return planId;
	}

	public void setPlanId(long planId) {
		this.planId = planId;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
