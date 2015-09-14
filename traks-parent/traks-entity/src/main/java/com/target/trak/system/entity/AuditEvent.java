package com.target.trak.system.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class AuditEvent implements Serializable {

	private static final long serialVersionUID = -4762488836553508955L;

	private Long id;
	private String username;
	private String auditEventCode;
	private String auditEventDescription;
	private Timestamp timestamp;
	private boolean success;
	private String errorMessage;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAuditEventCode() {
		return auditEventCode;
	}

	public void setAuditEventCode(String auditEventCode) {
		this.auditEventCode = auditEventCode;
	}

	public String getAuditEventDescription() {
		return auditEventDescription;
	}

	public void setAuditEventDescription(String auditEventDescription) {
		this.auditEventDescription = auditEventDescription;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "AuditEvent [id=" + id + ", username=" + username + ", auditEventCode=" + auditEventCode + ", auditEventDescription=" + auditEventDescription + ", timestamp=" + timestamp + ", success=" + success + ", errorMessage=" + errorMessage + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((auditEventCode == null) ? 0 : auditEventCode.hashCode());
		result = prime * result + ((auditEventDescription == null) ? 0 : auditEventDescription.hashCode());
		result = prime * result + ((errorMessage == null) ? 0 : errorMessage.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (success ? 1231 : 1237);
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		AuditEvent other = (AuditEvent) obj;
		if (auditEventCode == null) {
			if (other.auditEventCode != null)
				return false;
		} else if (!auditEventCode.equals(other.auditEventCode))
			return false;
		if (auditEventDescription == null) {
			if (other.auditEventDescription != null)
				return false;
		} else if (!auditEventDescription.equals(other.auditEventDescription))
			return false;
		if (errorMessage == null) {
			if (other.errorMessage != null)
				return false;
		} else if (!errorMessage.equals(other.errorMessage))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (success != other.success)
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}