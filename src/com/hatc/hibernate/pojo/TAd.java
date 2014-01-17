package com.hatc.hibernate.pojo;

/**
 * TAd entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TAd implements java.io.Serializable {

	// Fields

	private TAdId id;

	// Constructors

	/** default constructor */
	public TAd() {
	}

	/** full constructor */
	public TAd(TAdId id) {
		this.id = id;
	}

	// Property accessors

	public TAdId getId() {
		return this.id;
	}

	public void setId(TAdId id) {
		this.id = id;
	}

}