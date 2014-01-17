package com.hatc.hibernate.vo;

/*
 * 飞行冲突VO
 * @author wangdh
 * @date 2013-10-08 11:30
 * */
public class FlyConflictVO {
	
	//唯一标示
	private long id;
	
	//冲突类型
	private String type;
	
	//数据来自哪里(方便将来查询不同的table)
	private String from;
	
	//冲突对象
	private String src;
	
	//冲突描述
	private String description;
	
	//飞行计划ID
	private long planId;
	
	//建议
	private String advice;
	
	//其他信息
	private String other;
	
	//开始时间
	private String startTime;
	
	//结束时间
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
