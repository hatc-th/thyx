package com.hatc.hibernate.pojo;

/**
 * TNv entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TNv implements java.io.Serializable {

	// Fields

	private TNvId id;

	// Constructors

	/** default constructor */
	public TNv() {
	}

	/** full constructor */
	public TNv(TNvId id) {
		this.id = id;
	}

	// Property accessors

	public TNvId getId() {
		return this.id;
	}

	public void setId(TNvId id) {
		this.id = id;
	}

}