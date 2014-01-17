package com.hatc.hibernate.pojo;

/**
 * TbApproveFlowRecId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TbApproveFlowRecId implements java.io.Serializable {

	// Fields

	private Long approveId;
	private String siteId;

	// Constructors

	/** default constructor */
	public TbApproveFlowRecId() {
	}

	/** full constructor */
	public TbApproveFlowRecId(Long approveId, String siteId) {
		this.approveId = approveId;
		this.siteId = siteId;
	}

	// Property accessors

	public Long getApproveId() {
		return this.approveId;
	}

	public void setApproveId(Long approveId) {
		this.approveId = approveId;
	}

	public String getSiteId() {
		return this.siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbApproveFlowRecId))
			return false;
		TbApproveFlowRecId castOther = (TbApproveFlowRecId) other;

		return ((this.getApproveId() == castOther.getApproveId()) || (this
				.getApproveId() != null
				&& castOther.getApproveId() != null && this.getApproveId()
				.equals(castOther.getApproveId())))
				&& ((this.getSiteId() == castOther.getSiteId()) || (this
						.getSiteId() != null
						&& castOther.getSiteId() != null && this.getSiteId()
						.equals(castOther.getSiteId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getApproveId() == null ? 0 : this.getApproveId().hashCode());
		result = 37 * result
				+ (getSiteId() == null ? 0 : this.getSiteId().hashCode());
		return result;
	}

}