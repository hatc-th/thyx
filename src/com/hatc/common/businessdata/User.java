package com.hatc.common.businessdata;

import org.springframework.beans.BeanUtils;

import com.hatc.common.hibernate.pojo.TbUser;

public class User extends TbUser {

	private static final long serialVersionUID = -1604022069365830023L;

	public User(TbUser tbUser){
		BeanUtils.copyProperties(tbUser, this);
	}
	
	public User(){
		
	}
	
	String dept_name ;

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	
}
