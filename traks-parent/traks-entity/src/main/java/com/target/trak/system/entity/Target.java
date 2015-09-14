package com.target.trak.system.entity;

public class Target {

	private Long id;
	private ProcessOfService processService;
	private boolean isTargetEntity;
	private String targetEntityName;
	private String title;
	private String firstName;
	private String middleInitial;
	private String lastName;
	private String suffix;
	private String telephoneNumber;
	private String agentType;

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

	public boolean isTargetEntity() {
		return isTargetEntity;
	}

	public void setTargetEntity(boolean isTargetEntity) {
		this.isTargetEntity = isTargetEntity;
	}

	public String getTargetEntityName() {
		return targetEntityName;
	}

	public void setTargetEntityName(String targetEntityName) {
		this.targetEntityName = targetEntityName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getAgentType() {
		return agentType;
	}

	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}
}