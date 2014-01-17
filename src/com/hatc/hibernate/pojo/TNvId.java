package com.hatc.hibernate.pojo;

/**
 * TNvId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TNvId implements java.io.Serializable {

	// Fields

	private Long objectid;
	private String nvId;
	private String adId;
	private Long status;
	private String versionNum;
	private String createDate;
	private String updateDate;
	private String effectDate;
	private String memo;
	private String nvCode;
	private String nvName;
	private String nvPos;
	private Double nvElev;
	private Double nvTopAlt;
	private Double nvAntAlt;
	private Long nvType;
	private Double nvCoverHor;
	private Double nvCoverVert;
	private Double nvPower;
	private String gisuser;
	private String shape;

	// Constructors

	/** default constructor */
	public TNvId() {
	}

	/** full constructor */
	public TNvId(Long objectid, String nvId, String adId, Long status,
			String versionNum, String createDate, String updateDate,
			String effectDate, String memo, String nvCode, String nvName,
			String nvPos, Double nvElev, Double nvTopAlt, Double nvAntAlt,
			Long nvType, Double nvCoverHor, Double nvCoverVert, Double nvPower,
			String gisuser, String shape) {
		this.objectid = objectid;
		this.nvId = nvId;
		this.adId = adId;
		this.status = status;
		this.versionNum = versionNum;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.effectDate = effectDate;
		this.memo = memo;
		this.nvCode = nvCode;
		this.nvName = nvName;
		this.nvPos = nvPos;
		this.nvElev = nvElev;
		this.nvTopAlt = nvTopAlt;
		this.nvAntAlt = nvAntAlt;
		this.nvType = nvType;
		this.nvCoverHor = nvCoverHor;
		this.nvCoverVert = nvCoverVert;
		this.nvPower = nvPower;
		this.gisuser = gisuser;
		this.shape = shape;
	}

	// Property accessors

	public Long getObjectid() {
		return this.objectid;
	}

	public void setObjectid(Long objectid) {
		this.objectid = objectid;
	}

	public String getNvId() {
		return this.nvId;
	}

	public void setNvId(String nvId) {
		this.nvId = nvId;
	}

	public String getAdId() {
		return this.adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getVersionNum() {
		return this.versionNum;
	}

	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}

	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getEffectDate() {
		return this.effectDate;
	}

	public void setEffectDate(String effectDate) {
		this.effectDate = effectDate;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getNvCode() {
		return this.nvCode;
	}

	public void setNvCode(String nvCode) {
		this.nvCode = nvCode;
	}

	public String getNvName() {
		return this.nvName;
	}

	public void setNvName(String nvName) {
		this.nvName = nvName;
	}

	public String getNvPos() {
		return this.nvPos;
	}

	public void setNvPos(String nvPos) {
		this.nvPos = nvPos;
	}

	public Double getNvElev() {
		return this.nvElev;
	}

	public void setNvElev(Double nvElev) {
		this.nvElev = nvElev;
	}

	public Double getNvTopAlt() {
		return this.nvTopAlt;
	}

	public void setNvTopAlt(Double nvTopAlt) {
		this.nvTopAlt = nvTopAlt;
	}

	public Double getNvAntAlt() {
		return this.nvAntAlt;
	}

	public void setNvAntAlt(Double nvAntAlt) {
		this.nvAntAlt = nvAntAlt;
	}

	public Long getNvType() {
		return this.nvType;
	}

	public void setNvType(Long nvType) {
		this.nvType = nvType;
	}

	public Double getNvCoverHor() {
		return this.nvCoverHor;
	}

	public void setNvCoverHor(Double nvCoverHor) {
		this.nvCoverHor = nvCoverHor;
	}

	public Double getNvCoverVert() {
		return this.nvCoverVert;
	}

	public void setNvCoverVert(Double nvCoverVert) {
		this.nvCoverVert = nvCoverVert;
	}

	public Double getNvPower() {
		return this.nvPower;
	}

	public void setNvPower(Double nvPower) {
		this.nvPower = nvPower;
	}

	public String getGisuser() {
		return this.gisuser;
	}

	public void setGisuser(String gisuser) {
		this.gisuser = gisuser;
	}

	public String getShape() {
		return this.shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TNvId))
			return false;
		TNvId castOther = (TNvId) other;

		return ((this.getObjectid() == castOther.getObjectid()) || (this
				.getObjectid() != null
				&& castOther.getObjectid() != null && this.getObjectid()
				.equals(castOther.getObjectid())))
				&& ((this.getNvId() == castOther.getNvId()) || (this.getNvId() != null
						&& castOther.getNvId() != null && this.getNvId()
						.equals(castOther.getNvId())))
				&& ((this.getAdId() == castOther.getAdId()) || (this.getAdId() != null
						&& castOther.getAdId() != null && this.getAdId()
						.equals(castOther.getAdId())))
				&& ((this.getStatus() == castOther.getStatus()) || (this
						.getStatus() != null
						&& castOther.getStatus() != null && this.getStatus()
						.equals(castOther.getStatus())))
				&& ((this.getVersionNum() == castOther.getVersionNum()) || (this
						.getVersionNum() != null
						&& castOther.getVersionNum() != null && this
						.getVersionNum().equals(castOther.getVersionNum())))
				&& ((this.getCreateDate() == castOther.getCreateDate()) || (this
						.getCreateDate() != null
						&& castOther.getCreateDate() != null && this
						.getCreateDate().equals(castOther.getCreateDate())))
				&& ((this.getUpdateDate() == castOther.getUpdateDate()) || (this
						.getUpdateDate() != null
						&& castOther.getUpdateDate() != null && this
						.getUpdateDate().equals(castOther.getUpdateDate())))
				&& ((this.getEffectDate() == castOther.getEffectDate()) || (this
						.getEffectDate() != null
						&& castOther.getEffectDate() != null && this
						.getEffectDate().equals(castOther.getEffectDate())))
				&& ((this.getMemo() == castOther.getMemo()) || (this.getMemo() != null
						&& castOther.getMemo() != null && this.getMemo()
						.equals(castOther.getMemo())))
				&& ((this.getNvCode() == castOther.getNvCode()) || (this
						.getNvCode() != null
						&& castOther.getNvCode() != null && this.getNvCode()
						.equals(castOther.getNvCode())))
				&& ((this.getNvName() == castOther.getNvName()) || (this
						.getNvName() != null
						&& castOther.getNvName() != null && this.getNvName()
						.equals(castOther.getNvName())))
				&& ((this.getNvPos() == castOther.getNvPos()) || (this
						.getNvPos() != null
						&& castOther.getNvPos() != null && this.getNvPos()
						.equals(castOther.getNvPos())))
				&& ((this.getNvElev() == castOther.getNvElev()) || (this
						.getNvElev() != null
						&& castOther.getNvElev() != null && this.getNvElev()
						.equals(castOther.getNvElev())))
				&& ((this.getNvTopAlt() == castOther.getNvTopAlt()) || (this
						.getNvTopAlt() != null
						&& castOther.getNvTopAlt() != null && this
						.getNvTopAlt().equals(castOther.getNvTopAlt())))
				&& ((this.getNvAntAlt() == castOther.getNvAntAlt()) || (this
						.getNvAntAlt() != null
						&& castOther.getNvAntAlt() != null && this
						.getNvAntAlt().equals(castOther.getNvAntAlt())))
				&& ((this.getNvType() == castOther.getNvType()) || (this
						.getNvType() != null
						&& castOther.getNvType() != null && this.getNvType()
						.equals(castOther.getNvType())))
				&& ((this.getNvCoverHor() == castOther.getNvCoverHor()) || (this
						.getNvCoverHor() != null
						&& castOther.getNvCoverHor() != null && this
						.getNvCoverHor().equals(castOther.getNvCoverHor())))
				&& ((this.getNvCoverVert() == castOther.getNvCoverVert()) || (this
						.getNvCoverVert() != null
						&& castOther.getNvCoverVert() != null && this
						.getNvCoverVert().equals(castOther.getNvCoverVert())))
				&& ((this.getNvPower() == castOther.getNvPower()) || (this
						.getNvPower() != null
						&& castOther.getNvPower() != null && this.getNvPower()
						.equals(castOther.getNvPower())))
				&& ((this.getGisuser() == castOther.getGisuser()) || (this
						.getGisuser() != null
						&& castOther.getGisuser() != null && this.getGisuser()
						.equals(castOther.getGisuser())))
				&& ((this.getShape() == castOther.getShape()) || (this
						.getShape() != null
						&& castOther.getShape() != null && this.getShape()
						.equals(castOther.getShape())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getObjectid() == null ? 0 : this.getObjectid().hashCode());
		result = 37 * result
				+ (getNvId() == null ? 0 : this.getNvId().hashCode());
		result = 37 * result
				+ (getAdId() == null ? 0 : this.getAdId().hashCode());
		result = 37 * result
				+ (getStatus() == null ? 0 : this.getStatus().hashCode());
		result = 37
				* result
				+ (getVersionNum() == null ? 0 : this.getVersionNum()
						.hashCode());
		result = 37
				* result
				+ (getCreateDate() == null ? 0 : this.getCreateDate()
						.hashCode());
		result = 37
				* result
				+ (getUpdateDate() == null ? 0 : this.getUpdateDate()
						.hashCode());
		result = 37
				* result
				+ (getEffectDate() == null ? 0 : this.getEffectDate()
						.hashCode());
		result = 37 * result
				+ (getMemo() == null ? 0 : this.getMemo().hashCode());
		result = 37 * result
				+ (getNvCode() == null ? 0 : this.getNvCode().hashCode());
		result = 37 * result
				+ (getNvName() == null ? 0 : this.getNvName().hashCode());
		result = 37 * result
				+ (getNvPos() == null ? 0 : this.getNvPos().hashCode());
		result = 37 * result
				+ (getNvElev() == null ? 0 : this.getNvElev().hashCode());
		result = 37 * result
				+ (getNvTopAlt() == null ? 0 : this.getNvTopAlt().hashCode());
		result = 37 * result
				+ (getNvAntAlt() == null ? 0 : this.getNvAntAlt().hashCode());
		result = 37 * result
				+ (getNvType() == null ? 0 : this.getNvType().hashCode());
		result = 37
				* result
				+ (getNvCoverHor() == null ? 0 : this.getNvCoverHor()
						.hashCode());
		result = 37
				* result
				+ (getNvCoverVert() == null ? 0 : this.getNvCoverVert()
						.hashCode());
		result = 37 * result
				+ (getNvPower() == null ? 0 : this.getNvPower().hashCode());
		result = 37 * result
				+ (getGisuser() == null ? 0 : this.getGisuser().hashCode());
		result = 37 * result
				+ (getShape() == null ? 0 : this.getShape().hashCode());
		return result;
	}

}