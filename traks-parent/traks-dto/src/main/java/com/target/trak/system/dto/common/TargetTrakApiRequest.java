package com.target.trak.system.dto.common;

public interface TargetTrakApiRequest {

	public TargetTrakRequestTypeEnum getRequestType();

	public void setRequestType(final TargetTrakRequestTypeEnum requestType);

	public TargetTrakPagingCriteria getPagingCriteria();

	public void setPagingCriteria(TargetTrakPagingCriteria criteria);
}
