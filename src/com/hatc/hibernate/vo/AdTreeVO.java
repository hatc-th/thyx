package com.hatc.hibernate.vo;

import java.util.List;

/**
 * @author wangdonghua
 * @description 机场相关bean
 * @date 2013-09-24
 * @version 10
 * */
public class AdTreeVO {
	
	//机场，管制分区，管制区ID
	private String Id;
	
	private String name;
	
	//机场（管制分区）所属管制分区（管制区）
	private String pId;
	
	//机场，管制分区，管制区状态
	private long status;
	
	//管制区（管制分区）对应的管制分区（机场）
	private List<AdTreeVO> childrenNodeList;
	
	private String code;
	
	private String pos;
	
	private String type;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPId() {
		return pId;
	}

	public void setPId(String id) {
		pId = id;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	public List<AdTreeVO> getChildrenNodeList() {
		return childrenNodeList;
	}

	public void setChildrenNodeList(List<AdTreeVO> childrenNodeList) {
		this.childrenNodeList = childrenNodeList;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
