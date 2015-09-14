package com.target.trak.system.entity;

import java.sql.Timestamp;

public class Task {

	private Long id;
	private ProcessOfService processService;
	private Target target;
	private Timestamp createDate;
	private Timestamp serviceLevelAgreementDate;
	private Timestamp completionDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProcessOfService getProcessService() {
		return processService;
	}

	public void setProcessService(ProcessOfService processService) {
		this.processService = processService;
	}

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
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

}
