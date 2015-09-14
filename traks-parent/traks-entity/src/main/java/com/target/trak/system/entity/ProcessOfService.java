package com.target.trak.system.entity;

import java.sql.Timestamp;

public class ProcessOfService {

	private Long id;
	private Matter matter;
	private String courtIndexNumber;
	private String plaintiff;
	private String defendant;
	private String caseCaption;
	private String createdBy;
	private Timestamp createDate;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDate;
	private int version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Matter getMatter() {
		return matter;
	}

	public void setMatter(Matter matter) {
		this.matter = matter;
	}

	public String getCourtIndexNumber() {
		return courtIndexNumber;
	}

	public void setCourtIndexNumber(String courtIndexNumber) {
		this.courtIndexNumber = courtIndexNumber;
	}

	public String getPlaintiff() {
		return plaintiff;
	}

	public void setPlaintiff(String plaintiff) {
		this.plaintiff = plaintiff;
	}

	public String getDefendant() {
		return defendant;
	}

	public void setDefendant(String defendant) {
		this.defendant = defendant;
	}

	public String getCaseCaption() {
		return caseCaption;
	}

	public void setCaseCaption(String caseCaption) {
		this.caseCaption = caseCaption;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Timestamp getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}