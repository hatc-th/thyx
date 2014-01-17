package com.hatc.common.hibernate.pojo;

/**
 * TbCodeId entity. @author MyEclipse Persistence Tools
 */

public class TbCodeId implements java.io.Serializable {

	// Fields

	private String classId;
	private String codeId;

	// Constructors

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	/** default constructor */
	public TbCodeId() {
	}

	/** full constructor */
	public TbCodeId(String classId, String codeId) {
		this.classId = classId;
		this.codeId = codeId;
	}


	public String getCodeId() {
		return this.codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classId == null) ? 0 : classId.hashCode());
		result = prime * result + ((codeId == null) ? 0 : codeId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TbCodeId other = (TbCodeId) obj;
		if (classId == null) {
			if (other.classId != null)
				return false;
		} else if (!classId.equals(other.classId))
			return false;
		if (codeId == null) {
			if (other.codeId != null)
				return false;
		} else if (!codeId.equals(other.codeId))
			return false;
		return true;
	}

	

}