package com.hatc.hibernate.vo;

import java.util.List;

/**
 * @author wangdonghua
 * @description �������bean
 * @date 2013-09-24
 * @version 10
 * */
public class AdTreeVO {
	
	//���������Ʒ�����������ID
	private String Id;
	
	private String name;
	
	//���������Ʒ������������Ʒ�������������
	private String pId;
	
	//���������Ʒ�����������״̬
	private long status;
	
	//�����������Ʒ�������Ӧ�Ĺ��Ʒ�����������
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
