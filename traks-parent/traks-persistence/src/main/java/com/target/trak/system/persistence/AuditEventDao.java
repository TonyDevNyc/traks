package com.target.trak.system.persistence;

import com.target.trak.system.entity.AuditEvent;

public interface AuditEventDao {

	public AuditEvent insertAuditEvent(final AuditEvent auditEvent);
}
