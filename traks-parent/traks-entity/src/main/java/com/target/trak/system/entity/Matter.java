package com.target.trak.system.entity;

import java.sql.Timestamp;

public class Matter {

	private Long id;
	private Contact primaryContact;
	private String matterStatus;
	private Timestamp serviceLevelAgreementDate;
	private Timestamp completionDate;
	private String createdBy;
	private Timestamp createDate;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDate;
	private int version;
	private CourtFilingService courtFiling;
	private ProcessOfService processService;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Contact getPrimaryContact() {
		return primaryContact;
	}

	public void setPrimaryContact(Contact primaryContact) {
		this.primaryContact = primaryContact;
	}

	public String getMatterStatus() {
		return matterStatus;
	}

	public void setMatterStatus(String matterStatus) {
		this.matterStatus = matterStatus;
	}

	public Timestamp getServiceLevelAgreementDate() {
		return serviceLevelAgreementDate;
	}

	public void setServiceLevelAgreementDate(Timestamp serviceLevelAgreementDate) {
		this.serviceLevelAgreementDate = serviceLevelAgreementDate;
	}

	public Timestamp getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Timestamp completionDate) {
		this.completionDate = completionDate;
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

	public CourtFilingService getCourtFiling() {
		return courtFiling;
	}

	public void setCourtFiling(CourtFilingService courtFiling) {
		this.courtFiling = courtFiling;
	}

	public ProcessOfService getProcessService() {
		return processService;
	}

	public void setProcessService(ProcessOfService processService) {
		this.processService = processService;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((completionDate == null) ? 0 : completionDate.hashCode());
		result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastUpdatedBy == null) ? 0 : lastUpdatedBy.hashCode());
		result = prime * result + ((lastUpdatedDate == null) ? 0 : lastUpdatedDate.hashCode());
		result = prime * result + ((matterStatus == null) ? 0 : matterStatus.hashCode());
		result = prime * result + ((primaryContact == null) ? 0 : primaryContact.hashCode());
		result = prime * result + ((serviceLevelAgreementDate == null) ? 0 : serviceLevelAgreementDate.hashCode());
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
		Matter other = (Matter) obj;
		if (completionDate == null) {
			if (other.completionDate != null)
				return false;
		} else if (!completionDate.equals(other.completionDate))
			return false;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastUpdatedBy == null) {
			if (other.lastUpdatedBy != null)
				return false;
		} else if (!lastUpdatedBy.equals(other.lastUpdatedBy))
			return false;
		if (lastUpdatedDate == null) {
			if (other.lastUpdatedDate != null)
				return false;
		} else if (!lastUpdatedDate.equals(other.lastUpdatedDate))
			return false;
		if (matterStatus == null) {
			if (other.matterStatus != null)
				return false;
		} else if (!matterStatus.equals(other.matterStatus))
			return false;
		if (primaryContact == null) {
			if (other.primaryContact != null)
				return false;
		} else if (!primaryContact.equals(other.primaryContact))
			return false;
		if (serviceLevelAgreementDate == null) {
			if (other.serviceLevelAgreementDate != null)
				return false;
		} else if (!serviceLevelAgreementDate.equals(other.serviceLevelAgreementDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Matter [id=" + id + ", primaryContact=" + primaryContact + ", matterStatus=" + matterStatus + ", serviceLevelAgreementDate=" + serviceLevelAgreementDate + ", completionDate=" + completionDate + ", createdBy=" + createdBy + ", createDate=" + createDate + ", lastUpdatedBy=" + lastUpdatedBy + ", lastUpdatedDate=" + lastUpdatedDate + "]";
	}
}