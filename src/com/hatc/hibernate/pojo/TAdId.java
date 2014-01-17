package com.hatc.hibernate.pojo;

/**
 * TAdId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TAdId implements java.io.Serializable {

	// Fields

	private Long objectid;
	private String adId;
	private String caId;
	private Long status;
	private String versionNum;
	private String createDate;
	private String updateDate;
	private String effectDate;
	private String memo;
	private String icaoCode;
	private String iataCode;
	private String adName;
	private Long adType;
	private Long adLevel;
	private String adArpPos;
	private Double adBearing;
	private Double adElev;
	private Double adAltTrans;
	private Double adRefTemp;
	private Long adMagYear;
	private Double adMagVar;
	private Double adMagVarC;
	private String gisuser;
	private String shape;
	private String fcaCode;
	private String adFss;

	// Constructors

	/** default constructor */
	public TAdId() {
	}

	/** full constructor */
	public TAdId(Long objectid, String adId, String caId, Long status,
			String versionNum, String createDate, String updateDate,
			String effectDate, String memo, String icaoCode, String iataCode,
			String adName, Long adType, Long adLevel, String adArpPos,
			Double adBearing, Double adElev, Double adAltTrans,
			Double adRefTemp, Long adMagYear, Double adMagVar,
			Double adMagVarC, String gisuser, String shape, String fcaCode,
			String adFss) {
		this.objectid = objectid;
		this.adId = adId;
		this.caId = caId;
		this.status = status;
		this.versionNum = versionNum;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.effectDate = effectDate;
		this.memo = memo;
		this.icaoCode = icaoCode;
		this.iataCode = iataCode;
		this.adName = adName;
		this.adType = adType;
		this.adLevel = adLevel;
		this.adArpPos = adArpPos;
		this.adBearing = adBearing;
		this.adElev = adElev;
		this.adAltTrans = adAltTrans;
		this.adRefTemp = adRefTemp;
		this.adMagYear = adMagYear;
		this.adMagVar = adMagVar;
		this.adMagVarC = adMagVarC;
		this.gisuser = gisuser;
		this.shape = shape;
		this.fcaCode = fcaCode;
		this.adFss = adFss;
	}

	// Property accessors

	public Long getObjectid() {
		return this.objectid;
	}

	public void setObjectid(Long objectid) {
		this.objectid = objectid;
	}

	public String getAdId() {
		return this.adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getCaId() {
		return this.caId;
	}

	public void setCaId(String caId) {
		this.caId = caId;
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

	public String getIcaoCode() {
		return this.icaoCode;
	}

	public void setIcaoCode(String icaoCode) {
		this.icaoCode = icaoCode;
	}

	public String getIataCode() {
		return this.iataCode;
	}

	public void setIataCode(String iataCode) {
		this.iataCode = iataCode;
	}

	public String getAdName() {
		return this.adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public Long getAdType() {
		return this.adType;
	}

	public void setAdType(Long adType) {
		this.adType = adType;
	}

	public Long getAdLevel() {
		return this.adLevel;
	}

	public void setAdLevel(Long adLevel) {
		this.adLevel = adLevel;
	}

	public String getAdArpPos() {
		return this.adArpPos;
	}

	public void setAdArpPos(String adArpPos) {
		this.adArpPos = adArpPos;
	}

	public Double getAdBearing() {
		return this.adBearing;
	}

	public void setAdBearing(Double adBearing) {
		this.adBearing = adBearing;
	}

	public Double getAdElev() {
		return this.adElev;
	}

	public void setAdElev(Double adElev) {
		this.adElev = adElev;
	}

	public Double getAdAltTrans() {
		return this.adAltTrans;
	}

	public void setAdAltTrans(Double adAltTrans) {
		this.adAltTrans = adAltTrans;
	}

	public Double getAdRefTemp() {
		return this.adRefTemp;
	}

	public void setAdRefTemp(Double adRefTemp) {
		this.adRefTemp = adRefTemp;
	}

	public Long getAdMagYear() {
		return this.adMagYear;
	}

	public void setAdMagYear(Long adMagYear) {
		this.adMagYear = adMagYear;
	}

	public Double getAdMagVar() {
		return this.adMagVar;
	}

	public void setAdMagVar(Double adMagVar) {
		this.adMagVar = adMagVar;
	}

	public Double getAdMagVarC() {
		return this.adMagVarC;
	}

	public void setAdMagVarC(Double adMagVarC) {
		this.adMagVarC = adMagVarC;
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

	public String getFcaCode() {
		return this.fcaCode;
	}

	public void setFcaCode(String fcaCode) {
		this.fcaCode = fcaCode;
	}

	public String getAdFss() {
		return this.adFss;
	}

	public void setAdFss(String adFss) {
		this.adFss = adFss;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TAdId))
			return false;
		TAdId castOther = (TAdId) other;

		return ((this.getObjectid() == castOther.getObjectid()) || (this
				.getObjectid() != null
				&& castOther.getObjectid() != null && this.getObjectid()
				.equals(castOther.getObjectid())))
				&& ((this.getAdId() == castOther.getAdId()) || (this.getAdId() != null
						&& castOther.getAdId() != null && this.getAdId()
						.equals(castOther.getAdId())))
				&& ((this.getCaId() == castOther.getCaId()) || (this.getCaId() != null
						&& castOther.getCaId() != null && this.getCaId()
						.equals(castOther.getCaId())))
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
				&& ((this.getIcaoCode() == castOther.getIcaoCode()) || (this
						.getIcaoCode() != null
						&& castOther.getIcaoCode() != null && this
						.getIcaoCode().equals(castOther.getIcaoCode())))
				&& ((this.getIataCode() == castOther.getIataCode()) || (this
						.getIataCode() != null
						&& castOther.getIataCode() != null && this
						.getIataCode().equals(castOther.getIataCode())))
				&& ((this.getAdName() == castOther.getAdName()) || (this
						.getAdName() != null
						&& castOther.getAdName() != null && this.getAdName()
						.equals(castOther.getAdName())))
				&& ((this.getAdType() == castOther.getAdType()) || (this
						.getAdType() != null
						&& castOther.getAdType() != null && this.getAdType()
						.equals(castOther.getAdType())))
				&& ((this.getAdLevel() == castOther.getAdLevel()) || (this
						.getAdLevel() != null
						&& castOther.getAdLevel() != null && this.getAdLevel()
						.equals(castOther.getAdLevel())))
				&& ((this.getAdArpPos() == castOther.getAdArpPos()) || (this
						.getAdArpPos() != null
						&& castOther.getAdArpPos() != null && this
						.getAdArpPos().equals(castOther.getAdArpPos())))
				&& ((this.getAdBearing() == castOther.getAdBearing()) || (this
						.getAdBearing() != null
						&& castOther.getAdBearing() != null && this
						.getAdBearing().equals(castOther.getAdBearing())))
				&& ((this.getAdElev() == castOther.getAdElev()) || (this
						.getAdElev() != null
						&& castOther.getAdElev() != null && this.getAdElev()
						.equals(castOther.getAdElev())))
				&& ((this.getAdAltTrans() == castOther.getAdAltTrans()) || (this
						.getAdAltTrans() != null
						&& castOther.getAdAltTrans() != null && this
						.getAdAltTrans().equals(castOther.getAdAltTrans())))
				&& ((this.getAdRefTemp() == castOther.getAdRefTemp()) || (this
						.getAdRefTemp() != null
						&& castOther.getAdRefTemp() != null && this
						.getAdRefTemp().equals(castOther.getAdRefTemp())))
				&& ((this.getAdMagYear() == castOther.getAdMagYear()) || (this
						.getAdMagYear() != null
						&& castOther.getAdMagYear() != null && this
						.getAdMagYear().equals(castOther.getAdMagYear())))
				&& ((this.getAdMagVar() == castOther.getAdMagVar()) || (this
						.getAdMagVar() != null
						&& castOther.getAdMagVar() != null && this
						.getAdMagVar().equals(castOther.getAdMagVar())))
				&& ((this.getAdMagVarC() == castOther.getAdMagVarC()) || (this
						.getAdMagVarC() != null
						&& castOther.getAdMagVarC() != null && this
						.getAdMagVarC().equals(castOther.getAdMagVarC())))
				&& ((this.getGisuser() == castOther.getGisuser()) || (this
						.getGisuser() != null
						&& castOther.getGisuser() != null && this.getGisuser()
						.equals(castOther.getGisuser())))
				&& ((this.getShape() == castOther.getShape()) || (this
						.getShape() != null
						&& castOther.getShape() != null && this.getShape()
						.equals(castOther.getShape())))
				&& ((this.getFcaCode() == castOther.getFcaCode()) || (this
						.getFcaCode() != null
						&& castOther.getFcaCode() != null && this.getFcaCode()
						.equals(castOther.getFcaCode())))
				&& ((this.getAdFss() == castOther.getAdFss()) || (this
						.getAdFss() != null
						&& castOther.getAdFss() != null && this.getAdFss()
						.equals(castOther.getAdFss())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getObjectid() == null ? 0 : this.getObjectid().hashCode());
		result = 37 * result
				+ (getAdId() == null ? 0 : this.getAdId().hashCode());
		result = 37 * result
				+ (getCaId() == null ? 0 : this.getCaId().hashCode());
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
				+ (getIcaoCode() == null ? 0 : this.getIcaoCode().hashCode());
		result = 37 * result
				+ (getIataCode() == null ? 0 : this.getIataCode().hashCode());
		result = 37 * result
				+ (getAdName() == null ? 0 : this.getAdName().hashCode());
		result = 37 * result
				+ (getAdType() == null ? 0 : this.getAdType().hashCode());
		result = 37 * result
				+ (getAdLevel() == null ? 0 : this.getAdLevel().hashCode());
		result = 37 * result
				+ (getAdArpPos() == null ? 0 : this.getAdArpPos().hashCode());
		result = 37 * result
				+ (getAdBearing() == null ? 0 : this.getAdBearing().hashCode());
		result = 37 * result
				+ (getAdElev() == null ? 0 : this.getAdElev().hashCode());
		result = 37
				* result
				+ (getAdAltTrans() == null ? 0 : this.getAdAltTrans()
						.hashCode());
		result = 37 * result
				+ (getAdRefTemp() == null ? 0 : this.getAdRefTemp().hashCode());
		result = 37 * result
				+ (getAdMagYear() == null ? 0 : this.getAdMagYear().hashCode());
		result = 37 * result
				+ (getAdMagVar() == null ? 0 : this.getAdMagVar().hashCode());
		result = 37 * result
				+ (getAdMagVarC() == null ? 0 : this.getAdMagVarC().hashCode());
		result = 37 * result
				+ (getGisuser() == null ? 0 : this.getGisuser().hashCode());
		result = 37 * result
				+ (getShape() == null ? 0 : this.getShape().hashCode());
		result = 37 * result
				+ (getFcaCode() == null ? 0 : this.getFcaCode().hashCode());
		result = 37 * result
				+ (getAdFss() == null ? 0 : this.getAdFss().hashCode());
		return result;
	}

}